
# 异构计算
异构计算（英语：Heterogeneous computing），又译异质运算，主要是指使用不同类型指令集和体系架构的计算单元组成系统的计算方式。



# jargon

Host =	CPU
* Device =	GPU
* Kernel =	function	executed	on	the	
GPU	by	each	thread
* Launch =	CPU	instructs	GPU	to	
execute	a	kernel	
* Execution	configuration	=	definition	
of	how	many	threads	to	run	on	the	
GPU	and	how	to	group	themcudaMalloc (void** devPtr, size_t size)


# memory

• To	allocate	memory	on	the	GPU,	CUDA	provides	cudaMalloc(),
similar	to	C	cudaMalloc (void** devPtr, size_t size)malloc()


move	data	from/to	GPU	by	copying	data	with	cudaMemcpy(void* dest, void* src, size_t
size, cudaMemcpyDeviceToHost) cudaMemcpy()
from/to	GPU	memory	location	to/from	a	CPU	memory	location.

cudaMemcpy从设备端拷贝回主机端时，会触发一个隐式同步.


# kernel

__global__ 代表在host调用, device上执行.

__device__ 代表在device调用和执行.

__host__ 代表在host上执行. 默认的


kernel的限制
* Kernels	execute	on	the	GPU	and	do	not,	in	general,	have	
access	to	data	stored	on	the	host	side.
* Kernels	cannot	return	a	value,	so	the	return	type	is	always	
void,	and	kernel	declarations	starts	as
* 不支持可变数量的参数
* 不支持静态变量
* 显示异步行为


因此我们需要通过callback来获取结果.
错误码打印

char* cudaGetErrorString(cudaError_t error)   // 将错误码转换成字符串

```c
// 可以使用如下宏
#define CHECK(call)\
{\
  const cudaError_t error=call;\
  if(error!=cudaSuccess)\
  {\
      printf("ERROR: %s:%d,",__FILE__,__LINE__);\
      printf("code:%d,reason:%s\n",error,cudaGetErrorString(error));\
      exit(1);\
  }\
}
```

cuda所有kernel启动都是异步的



