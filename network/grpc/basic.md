# RPC

Dubbo：国内最早开源的 RPC 框架，由阿里巴巴公司开发并于 2011 年末对外开源，仅支持 Java 语言。

Motan：微博内部使用的 RPC 框架，于 2016 年对外开源，仅支持 Java 语言。

Tars：腾讯内部使用的 RPC 框架，于 2017 年对外开源，仅支持 C++ 语言。

Spring Cloud：国外 Pivotal 公司 2014 年对外开源的 RPC 框架，仅支持 Java 语言

而跨语言平台的开源 RPC 框架主要有以下几种。

gRPC：Google 于 2015 年对外开源的跨语言 RPC 框架，支持多种语言。

Thrift：最初是由 Facebook 开发的内部系统跨语言的 RPC 框架，2007 年贡献给了 Apache 基金，成为 Apache 开源项目之一，支持多种语言。

# quick start
最基本的开发步骤是定义 proto 文件， 定义请求 Request 和 响应 Response 的格式，然后定义一个服务 Service， Service可以包含多个方法。


gRPC 本身提供了异步 API，当方法调用成功时，我们可以让 gRPC 自动将返回结果放到一个CompletionQueue的队列中（CompletionQueue是一个阻塞队列，并且是线程安全的）。


# 流式响应
gRPC的流可以分为三类， 客户端流式发送、服务器流式返回以及客户端／服务器同时流式处理, 也就是单向流和双向流。

通过使用流(streaming)，你可以向服务器或者客户端发送批量的数据， 服务器和客户端在接收这些数据的时候，可以不必等所有的消息全收到后才开始响应，而是接收到第一条消息的时候就可以及时的响应（相比http, request/reponse每个绑定的方式好很多）

当前gRPC通过 HTTP2 协议传输，可以方便的实现 streaming 功能。

Request 和 Response 的格式映射如下

![](pic/2020-11-02-10-59-08.png)

要实现服务器的流式响应，只需在proto中的方法定义中将响应返回类型前面加上stream标记.
生成的代码会自动包含了流的处理

双向流的例子。 客户端流式发送，服务器端流式响应，所有的发送和读取都是流式处理的。

