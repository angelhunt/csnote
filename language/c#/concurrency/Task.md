# Task Cancel

CancellationTokenSource – an object responsible for creating a cancellation token and sending a cancellation request to all copies of that token.
CancellationToken – a structure used by listeners to monitor token current state.

CancellationTokenSource用于创建cancel的条件, Token使用方式有
Token的使用方式
1. Take.Run直接传参数用Token

注意这几种方式都是不能cancel running状态的job的，CancelationToken is to prevent the Task to start while it is still scheduled.

with Cancel you cancel the task while it is still scheduled but with CancelAfter task is already started and there is no way to stop it anymore

CancellationToken cancels the scheduling of the continuation, not the continuation itself.
## Token
我们可以使用三种方式查看token状态
1. By polling – periodically check IsCancellationRequested property, ThrowIfCancellationRequested
2. Callback registration, token.Register(() => webClient.CancelAsync());
3. Waiting for the wait handle provided by CancellationToken

## CancellationTokenSource
CancellationTokenSource用于定义cancel方式

``` c#
// #1 create a token source with timeout
tokenSource = newCancellationTokenSource(TimeSpan.FromSeconds(2));
 
// #2 request cancellation after timeout
tokenSource.CancelAfter(TimeSpan.FromSeconds(2));
 
// #3 directly request cancellation
tokenSource.Cancel();
 
// or
tokenSource.Cancel(true);
```

## cancel running task
这些方法其实都没法cancel running状态的task, 因为running的时候已经绑定到thread上了，此时也只能用线程的abort方法，通过OS来终止，但是这个方式是不推荐的。

比较好的解决方案是再Task中轮询的调用Token的
IsCancellationRequested方法.当检测到token上有cancel事件的时候, return 

``` c#
while (true)
{
    // do some heavy work here
    Thread.Sleep(100);
    if (ct.IsCancellationRequested)
    {
        // another thread decided to cancel
        Console.WriteLine("task canceled");
        break;
    }
}
```
