向动态类型语言学习auto, decltype
对比C++11，C++14改变比较大的有

Concept-lite

模板lambda表达式

函数返回decltype(auto)

模板lambda表达式可以让你少写巨长无比的类型，decltype(auto)也是


C++14主要是对lambda的补全和标准库易用性的改进。 
lambda捕捉类对象时，要么调用复制构造函数，要么通过引用，而不管通过什么手段，都不能让lambda在捕捉类对象时调用其转移构造函数

这一点导致std::unique_ptr的在异步操作时的实用性大打折扣，如果我们需要让用作异步回调的lambda函数捕捉一个智能指针，我们只能使用std::shared_ptr。在C++14中，引入了 Lambda captures expressions，解决了这个问题


C++14也改进了STL的易用性，例如std::less<T>的改进：
Module 确认进入 C++20 标准

module主要还是一个工程化特性，头文件中的信息现在分成导出的部分和不导出的部分，可以有效减少实现细节影响的范围，带来一个非常有用的特性 -- 重新编译的速度变快了。简单的说，C++的module的当前实现，可以看成一个加强版本，应用于所有文件的预编译头文件过程。考虑到编译系统参数对编译结果的影响太大，不太可能有二进制发布版本的模块（包括标准模块）。不支持宏大约也是这个原因。


module的出现破坏了C++编译器的编译逻辑，导致把自己的代码升级成module的时候还得顺手把build pipeline给改了，麻烦的一笔。10万个文件让你改，你怎么改？这东西估计等我们退休了大家就会接受了。
