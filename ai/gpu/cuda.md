# kernal

//1D grid of 1D blocks
__device__ int getGlobalIdx_1D_1D()
{
	return blockIdx.x *blockDim.x + threadIdx.x;
}

//1D grid of 2D blocks
__device__ int getGlobalIdx_1D_2D()
{
	return blockIdx.x * blockDim.x * blockDim.y + threadIdx.y * blockDim.x + threadIdx.x;
}

//1D grid of 3D blocks
__device__ int getGlobalIdx_1D_3D()
{
	return blockIdx.x * blockDim.x * blockDim.y * blockDim.z 
	 + threadIdx.z * blockDim.y * blockDim.x + threadIdx.y * blockDim.x + threadIdx.x;
} 

//2D grid of 1D blocks 
__device__ int getGlobalIdx_2D_1D()
{
	int blockId   = blockIdx.y * gridDim.x + blockIdx.x;			 	
	int threadId = blockId * blockDim.x + threadIdx.x; 
	return threadId;
}

//2D grid of 2D blocks  
 __device__ int getGlobalIdx_2D_2D()
{
	int blockId = blockIdx.x + blockIdx.y * gridDim.x; 
	int threadId = blockId * (blockDim.x * blockDim.y) + (threadIdx.y * blockDim.x) + threadIdx.x;
	return threadId;
}

//2D grid of 3D blocks
__device__ int getGlobalIdx_2D_3D()
{
	int blockId = blockIdx.x 
			 + blockIdx.y * gridDim.x; 
	int threadId = blockId * (blockDim.x * blockDim.y * blockDim.z)
			   + (threadIdx.z * (blockDim.x * blockDim.y))
			   + (threadIdx.y * blockDim.x)
			   + threadIdx.x;
	return threadId;
} 

//3D grid of 1D blocks
__device__ int getGlobalIdx_3D_1D()
{
	int blockId = blockIdx.x 
			 + blockIdx.y * gridDim.x 
			 + gridDim.x * gridDim.y * blockIdx.z; 
	int threadId = blockId * blockDim.x + threadIdx.x;
	return threadId;
} 

//3D grid of 2D blocks
__device__ int getGlobalIdx_3D_2D()
{
	int blockId = blockIdx.x 
		         + blockIdx.y * gridDim.x 
			 + gridDim.x * gridDim.y * blockIdx.z; 
	int threadId = blockId * (blockDim.x * blockDim.y)
			  + (threadIdx.y * blockDim.x)
			  + threadIdx.x;
	return threadId;
}

//3D grid of 3D blocks
__device__ int getGlobalIdx_3D_3D()
{
	int blockId = blockIdx.x 
			 + blockIdx.y * gridDim.x 
			 + gridDim.x * gridDim.y * blockIdx.z; 
	int threadId = blockId * (blockDim.x * blockDim.y * blockDim.z)
			  + (threadIdx.z * (blockDim.x * blockDim.y))
			  + (threadIdx.y * blockDim.x)
			  + threadIdx.x;
	return threadId;
}


# 概念

由一个单独的kernel启动的所有线程组成一个grid，grid中所有线程共享global memory。一个grid由许多block组成，block由许多线程组成，grid和block都可以是一维二维或者三维，上图是一个二维grid和二维block。

线程层次结构：grid -> block -> thread。一个 grid，多个 block，一个网格上的线程共享相同的全局内存。一个 block，多个 thread，一个 block 上的 thread 使用共享内存来通信。grid 和 block 都是三维的，在核函数中可以使用 blockDim，gridDim 来获取维度大小。


https://www.cnblogs.com/zzk0/p/15506607.html

https://blog.51cto.com/u_15717531/5471126


# 物理



https://www.cnblogs.com/shouhuxianjian/p/4529172.html
https://zhuanlan.zhihu.com/p/53763285


Warp：warp是SM调度和执行的基础概念，通常一个SM中的SP(thread)会分成几个warp(也就是SP在SM中是进行分组的，物理上进行的分组)，一般每一个WARP中有32个thread.这个WARP中的32个thread(sp)是一起工作的，执行相同的指令，如果没有这么多thread需要工作，那么这个WARP中的一些thread(sp)是不工作的

（每一个线程都有自己的寄存器内存和local memory，一个warp中的线程是同时执行的，也就是当进行并行计算时，线程数尽量为32的倍数，如果线程数不上32的倍数的话；假如是1，则warp会生成一个掩码，当一个指令控制器对一个warp单位的线程发送指令时，32个线程中只有一个线程在真正执行，其他31个 进程会进入静默状态。）

块中相邻的Warp的threadIdx的值是连续的，假设第一个waro包含线程0-31，那么第二个就包含线程32-63，以此类推。


  一旦一个块分配给了一个SM，那么该块就会被以大小为32的线程warp所划分，warp的个数的多少（个人：英文版是size，觉得应该说的是分配给每个SM中划分的warp的个数，而每个warp是固定的32个线程）是在具体实现的时候指定的，对于程序员来说这个是透明的，因为无需我们关心。这个参数也可以在不同的显卡的属性信息中得知，在SM中，warp才是线程调度的单位，而不是单个的线程，这个概念不属于CUDA的规范，但是却有助于理解和优化在特定CUDA设备上运行的程序的性能。



Threads are numbered in order within blocks so that threadIdx.x varies the fastest, then threadIdx.y the second fastest varying, and threadIdx.z the slowest varying. This is functionally the same as column major ordering in multidimensional arrays. Warps are sequentially constructed from threads in this ordering. So the calculation for a 2d block is

unsigned int tid = threadIdx.x + threadIdx.y * blockDim.x;
unsigned int warpid = tid / warpSize;

https://www.cnblogs.com/shouhuxianjian/p/4529172.html

# 架构
从硬件调度的角度上来看：
1. Grid：GPU (GPC) 级别的基本调度单位

2. Block (CTA)：SM 级别的基本调度单位

3. Warp：ALU 级别的基本调度单位

从资源分配和通信的角度来看：
1. Grid：共享同样的 Kernel 和 Context
2. Block (CTA)：同一 CTA 的线程运行在同一个 SM，因此同一 CTA 中的不同线程可以通过 Shared Memory 通信，并共享 L1 cache（只读）
3. Warp：32 宽度的 SIMD，占用同一块向量寄存器，线程间可以通过特殊指令交换寄存器中的数据。 每个warp可以同时跑32个thread，他们执行完全同步




本质是数据并行

一次kernal launch涉及:
1. 两个层次之间的并行，多个block(sm)之间并行，对应的input是blockIdx, 
2. block中多个thread(sp)并行，对应的input是threadIdx.

gridDim，一个grid里面有多少个block, blockDim指一个block里有多少个thread.
这两个Dim都是三维的，GPU在硬件层次上并不知道什么“三维”线程。在硬件看来所有东西都是一维的。设计成三维的索引方式是因为许多并行计算的程序的本身具有维度这个概念。例如图像处理程序通常是在一个二维平面上做并行计算，许多科学计算则是在一个三维的空间里并行，很多时候用不到三维，只用一维的尺寸就好，复杂的情况下需要用threadIdx.x, threadIdx.y.

https://www.zhihu.com/question/63319070


cuda_kernel<<<grid_size, block_size, 0, stream>>>(...)

第三个参数是shared_memory分配的内存大小, 相同blockIdx的thread可以通过共享内存通信






# shared memory
CUDA里的shared memory是block级别的，所以两件事需要keep in mind：
1）当你allocate shared memory的时候，你其实在每个block里面都创建了一份同样大小却互相独立的share memory
2）当你进行__syncthreads()操作的时候，你只能保证此block里的thread在同步，此block里的shared memory在同步
这两个点在影响着shared memory使用的性能
