，CUDA有两个非常重要的特性，一个是Thread Hierarchy，主要是说CUDA运行时，其线程是如何分层次执行的，另一个是Memory Hierarchy，主要说CUDA显存是如何分层次进行分配和管理的。这篇文章主要阐述的是CUDA运行机制，也就是CUDA Thread Hierarchy

# 1. Thread Hierarchy
CUDA的并行架构其实本质上就是SIMT(Single Instruction Multiple Threads)架构，其中SIMT类似SIMD，都是单指令多数据，只是SIMT比SIMD更加灵活。

CUDA的线程分为三个层次，分别为Grid、Block、Thread。一个device对应一个grid.

简单点说CUDA将一个GPU设备抽象成了一个Grid，而每个Grid里面有很多Block，每个Block里面又会有很多Thread，最终由每个Thread去处理kernel函数。这里其实有一个疑惑，每个device抽象成一个Grid还能理解，为什么不直接将Grid抽象成许多Thread呢，中间为什么要加一层Block抽象呢？要能够回答这个问题，就不得不提CUDA的硬件架构，因为这种层次性的体现是和CUDA的硬件架构密不可分的。

我们需要定义block数量，每个block的thread数量.

When	you	define	a	kernel	in	your	code,	you	need	to	the	
__global__ or	__device__ qualifiers	prepend	before	the	
function	name	when	you	launch	the	kernel	from	the	CPU	and	GPU	
respectively.
e	SIMT	parallelism	
model	where	each	CUDA	thread	
executes	the	same	function	(kernel)

my_kernel <<<Dg, Db>>>(arg1, arg2, …).
Dg =	number	of	thread	blocks	
Db =	number	of	threads	per	block

Thread	blocks	are	organized	in	grids that	can	be	1D	(x),	2D	(x and	y)	or	3D	(x,	
y and	z)


2D	grids	are	convenient	for	processing	images
• 3D	grids	are	convenient	for	processing	stacks	of	images.

	my_kernel <<< dim3(Dgx,Dgy,Dgz), dim3(Dbx,Dby,Dbz) >>>(arg1, arg2, …);

我们需要指定每一个维度block的数量, 这里指定的并不是具体数量，cuda会根据gpu配置来决定SMs,	SPs数量





# 执行模型

GPU中每个SM都能支持数百个线程并发执行，每个GPU通常有多个SM，当一个核函数的网格被启动的时候，多个block会被同时分配给可用的SM上执行。

注意: 当一个blcok被分配给一个SM后，他就只能在这个SM上执行了，不可能重新分配到其他SM上了，多个线程块可以被分配到同一个SM上。
在SM上同一个块内的多个线程进行线程级别并行，而同一线程内，指令利用指令级并行将单个线程处理成流水线。

SIMD。这种机制的问题就是过于死板，不允许每个分支有不同的操作，所有分支必须同时执行相同的指令，必须执行没有例外。
相比之下单指令多线程SIMT就更加灵活了，虽然两者都是将相同指令广播给多个执行单元，但是SIMT的某些线程可以选择不执行，也就是说同一时刻所有线程被分配给相同的指令，SIMD规定所有人必须执行，而SIMT则规定有些人可以根据需要不执行，这样SIMT就保证了线程级别的并行，而SIMD更像是指令级别的并行。


SIMT包括以下SIMD不具有的关键特性：

每个线程都有自己的指令地址计数器
每个县城都有自己的寄存器状态
每个线程可以有一个独立的执行路径
而上面这三个特性在编程模型可用的方式就是给每个线程一个唯一的标号（blckIdx,threadIdx），并且这三个特性保证了各线程之间的独立

## SM
GPU中每个SM都能支持数百个线程并发执行，每个GPU通常有多个SM，当一个核函数的网格被启动的时候，多个block会被同时分配给可用的SM上执行。一个SM某一时刻就执行一个thread block.

32是个神奇数字，他的产生是硬件系统设计的结果，也就是集成电路工程师搞出来的，所以软件工程师只能接受。
从概念上讲，32是SM以SIMD方式同时处理的工作粒度，这句话这么理解，可能学过后面的会更深刻的明白，一个SM上在某一个时刻，有32个线程在执行同一条指令，这32个线程可以选择性执行，虽然有些可以不执行，但是他也不能执行别的指令，需要另外需要执行这条指令的线程执行完。 

注意: 当一个blcok被分配给一个SM后，他就只能在这个SM上执行了，不可能重新分配到其他SM上了，多个线程块可以被分配到同一个SM上。

SM则包括下面这些资源：

执行单元（CUDA核）
调度线程束的调度器和调度单元
共享内存，寄存器文件和一级缓存
每个多处理器SM有16个加载/存储单元所以每个时钟周期内有16个线程（半个线程束）计算源地址和目的地址

每个SM有两个线程束调度器，和两个指令调度单元，当一个线程块被指定给一个SM时，线程块内的所有线程被分成线程束，两个线程束选择其中两个线程束，在用指令调度器存储两个线程束要执行的指令.

## 处理器

SP(Streaming Processor):流处理器， 是GPU最基本的处理单元，在fermi架构开始被叫做CUDA core。

SM(Streaming MultiProcessor): 一个SM由多个CUDA core组成，**每个SM根据GPU架构不同有不同数量的CUDA core**，Pascal架构中一个SM有128个CUDA core。SM还包括特殊运算单元(SFU)，共享内存(shared memory)，寄存器文件(Register File)和调度器(Warp Scheduler)等。register和shared memory是稀缺资源，这些有限的资源就使每个SM中active warps有非常严格的限制，也就限制了并行能力。



## 线程束(warp)
CUDA 采用单指令多线程SIMT架构管理执行线程，不同设备有不同的线程束大小，但是到目前为止基本所有设备都是维持在32，也就是说每个SM上有多个block，一个block有多个线程（可以是几百个，但不会超过某个最大值），但是从机器的角度，在某时刻T，SM上只执行一个线程束，也就是32个线程在同时同步执行，线程束中的每个线程执行同一条指令，包括有分支的部分，这个我们后面会讲到.

每个线程束包含32个线程。
当某个线程块被分配到一个SM上的时候，会被分成多个线程束，线程束在SM上交替执行.

当一个kernel被执行时，gird中的线程块被分配到SM上，一个线程块的thread只能在一个SM上调度，SM一般可以调度多个线程块，大量的thread可能被分到不同的SM上.
一个CUDA core可以执行一个thread，一个SM的CUDA core会分成几个warp（即CUDDA core在SM中分组)，由warp scheduler负责调度。GPU规定warp中所有线程在同一周期执行相同的指令.
一个warp中的线程必然在同一个block中，如果block所含线程数目不是warp大小的整数倍，那么多出的那些thread所在的warp中，会剩余一些inactive的thread，也就是说，即使凑不够warp整数倍的thread，硬件也会为warp凑足，只不过那些thread是inactive状态，需要注意的是，即使这部分thread是inactive的，也会消耗SM资源。由于warp的大小一般为32，所以block所含的thread的大小一般要设置为32的倍数。

https://zhuanlan.zhihu.com/p/266633373