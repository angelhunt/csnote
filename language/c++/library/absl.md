谷歌标准库


这个库的特点是用 C++ 11 的代码实现了许多 C++ 14 和 C++ 17 的特性，所以你的编译器也要支持 C++ 11（gcc 4.8+/clang 3.3+，不同平台略有差异）。




类似的 folly 库


# 总览
Abseil 简要组成部分如下：

base Abseil Fundamentals ：包含初始化代码和其它部分依赖的代码。除了 C++ 标准库外不依赖外部代码algorithm ：C++ <algorithm> 库的增强container ：STL 风格容器debugging ：内存泄露检查memory ：智能指针和内存管理meta ：用 C++11 兼容代码支持 C++14 和 C++17 版本的 <type_traits> 库numeric ：支持 C++11 兼容的 128 位整数strings ：string 相关函数增强synchronization ：同步原语和抽象支持time ：时间方面的计算types ：非容器类型的工具类型


比较有用的是string库


abseil 里有些类是 c++14/17 的 stl drop-in replacement，比如 std::string_view，比如 std::make_unique，比如 std::apply，这就非常非常的有意义。 string_view 还好，基本流行的 c++ 开源库都有实现，像 apply 就非常困难，基本上对我这种不懂 C++ 的实现一轮得脱层皮下来。（std::apply 对于实现过类似 seastar::future 这类工具的同学应该很熟悉）还有些实现优秀但是对我来说用处不大的类，比如 Duration（可以用 std::chrono 替代），Substitute（fmtlib 足够替代，就是 binary 大一点），和 endian 这种顺手就能实现的。


提前采用了几个 C++17 的类型：string_view
                                    ，optional 和 any 。不久将支持variant。
                                
                            
                            
                                主要的同步类型 absl::Mutex
                                    ，接口优雅，而且做了大量优化。
                                        
                                
                            
                            
                                高效支持时间处理，提供了概念上与 std::chrono
                                     类似的 absl::Time 和   
                                        absl::Duration 类型。但他们是具体类型，没有使用模板，在所有情况下的行为是固定的。此外，与常用的标准库相比，时钟采样 API absl::Now() 也做了大量优化。
                                
                            
                            
                                字符串处理程序，包括 absl::StrCat()
                                    ，absl::StrJoin() 和 
                                        absl::StrSplit() 的等。



https://cloud.tencent.com/developer/article/1966916


# 数据结构

https://www.cnblogs.com/papering/p/17226111.html


