
- [IO模型](#io模型)
- [线程池](#线程池)
- [IsThreadPoolLow](#isthreadpoollow)
  
# IO模型
clr的IO模型采用proactor模型，在windows下采用IOCP， vm中提供了IO线程和IOCP的支持，并在CLR进行了进一步封装
c#的主要接口如下：
1. ThreadPoolBoundHandle.BindHandle对应下面过程
    * "BindIOCompletionCallbackNative", ThreadPoolNative::CorBindIoCompletionCallback
    * 主要用于将文件句柄注册到IOCP，对应于IOCP模型中的设备绑定， 大部分的一部IO库都需要在创建一个stream的时候调用它，比如FileStream, 

2. IO线程/completionPortThreads
   * Native threadpool类中定义了CompletionPortThreadStart函数
   * 这个函数会在死循环中调用GetQueuedCompletionStatus来，阻塞到完成端口上等待IO事件
   * 相应的在c#层面提供了, the managed API ThreadPool.UnsafeQueueNativeOverlapped(), calling native CorPostQueuedCompletionStatus()

下面简单说下FileStream的实现，在我们创建FileStream时可以指定isAsync参数此时
1. 构造函数中调用BindHandle注册file handle到iocp上
2. 每次调用WriteAsync, 实际调用WIN32的WriteFile(lpOverlapped)函数，进行异步读写，callback将保存在lpOverlapped中进行传递
3. 事件完成后，IO线程捕获到事件，并从对应的lpOverlapped中取出callback执行

# 线程池
ThreadPool中有两种类型的线程：CLR worker threads和completionPortThreads




# IsThreadPoolLow

WebRequest在执行异步操作之前会调用IsThreadPoolLow 判断
WebRequest实现不同， FileStream的异步操作实现不会做IsThreadPoolLow 判断

HttpWebRequest queues a work item to the ThreadPool for every outbound request.

At the end of BeginGetResponse, just before the work item is queued, System.Net.Connection.IsThreadPoolLow is used to query how many worker threads are available in the pool. If the number is too low (less than two), an InvalidOperationException is thrown.

