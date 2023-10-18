volatile关键字保证了两个性质：


可见性：可见性是指当多个线程访问同一个变量时，一个线程修改了这个变量的值，其他线程能够立即看得到修改的值。
有序性：对一个volatile变量的写操作，执行在任意后续对这个volatile变量的读操作之前。或者说禁止指令重排序（happens-before原则）
单单缓存一致性协议无法实现volatile。


缓存一致性可以通过Store Buffer和Invalidate Queue两种结构进行加速，但这两种方式会造成一系列不一致性的问题。


因此后续提出了内存屏障的概念，分为读屏障和写屏障，以此修正Store Buffer和Invalidate Queu产生的问题。


通过读屏障和写屏障，又发展出了LoadLoad屏障，StoreStore屏障，LoadStore屏障，StoreLoad屏障 JVM也是利用了这几种屏障，实现volatile关键字。

c++中 volatile相当于显式的要求编译器禁止对 volatile 变量进行优化，并且要求每个变量赋值时，需要显式从寄存器%eax拷贝。

++中的volatile的顺序性是指：

C/C++ Volatile变量，与非Volatile变量之间的操作，是可能被编译器交换顺序的。
C/C++ Volatile变量间，编译器是能够保证不交换顺序的。
C/C++ Volatile关键词，并不能用于构建happens-before语义，因此在进行多线程程序设计时，要小心使用volatile，不要掉入volatile变量的使用陷阱之中。
————————————————
版权声明：本文为CSDN博主「「已注销」」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/LU_ZHAO/article/details/104802876

