# 1. basic
## First-class types

Usually it means instances of T can be

returned from functions
passed into functions
constructed at runtime

A type or class can be created/modified at run-time

There are functions to manipulate types/classes (and expressions to create types without names)

No need to build extra dynamic objects just to hold types, because the type objects themselves will do

可以用与实现抽象工厂。Types are runtime objects; serve as factories (No need for factory/product dual hierarchy)。
The classes themselves serve as factories This works because classes are first-class values We can say make(c)

## First-Class Dynamic Functions 
There are operations on functions (compose, conjoin) 

Function closures capture local state variables (Objects are state data with attached behavior; Closures are behaviors with attached state data and without the overhead of classes.) 

## Macro

Macros provide syntactic abstraction You build the language you want to program in

Languages for Macros 
* String substitution (cpp) 
* Expression substitution (Dylan, extend-syntax) 
* Expression computation (Lisp) Provides the full power of the language while you are writing code 

Macros make Interpreter  pattern look like (almost) standard BNF Command(move(D)) -> “go”, Direction(D)

Does parsing as well as interpretation 

## High order function
Build a method from components in different classes

Around methods: wrap around everything; Used to add tracing information, etc. 可用于实现装饰器


## multi-method
也称为Multiple dispatch， dynamic dispatch

这个需要将方法与对象解耦。Dispatch works on types, pattern matching on values. 但是可以通过pattern matching很 方便地实现multi-dispatch

multi-dispatch/pattern matching可以轻松实现visitor pattern


可用于实现
## Module
按照class来组织代码会带来问题

This leads to problems: 
* Compromises between two purposes 
* Need more selective access than public/private 
* Friend classes don’t work well

可用于实现Facade 模式

降低访问复杂系统的内部子系统时的复杂度，简化客户端与之的接口。
Example: A Compiler class that calls scanner, parser, code generator in the right way 

Facade pattern with modules is invisible.
Don’t need any bookkeeping objects or classes .
Just export the names that make up the interface.



## 类型变换
子类化反映了类型之间的关系

型变是关于怎么＂诱导＂子类化的问题？我们都知道，程序员除了直接定义新的类型以外，还可以利用给定的规律从旧的类型中组合出新的类型，比如说：

和类型Union，Union{Missing,Real}表明一个类型，
乘积类型Product，结构体，元组都可以看做是乘积类型
等等

那么型变是什么呢？如果我们把构造新类型的规则看做是一种函数f，而参与构造的类型叫做T，那么这种规则无外乎有以下几种情况（这里假设f为一元函数，对多元函数可以类推）：

协变　　A<:B，f(A)<:f(B)
反变　　A<:B，f(B)<:f(A)
不变　　A<:B，f(A),f(B)之间没有关系
例如假设有一个泛型叫做向量Vector{T}，T代表某一个类型

那么对应的情况就是

协变　　Vector{Int}<:Vector{Real}
反变　　Vector{Real}<:Vector{Int}
不变　　Vector{Real},Vector{Int}之间没有关系