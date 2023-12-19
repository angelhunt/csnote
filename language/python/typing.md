# typing

python的类型系统


我逐渐地「被动」接受和支持，是因为现在 Python 开发者能力的下限真的是一年不如一年，如果没有类型注解的约束，很多 Python 开发者写的代码真的一言难尽，其次是方便代码阅读


类型系统除此之外还有执行类型检查的工具（做类型检查器，Type Checker，本文提到的是 mypy，一会还会介绍还有其他的工具)

现在 Python 社区的推荐的实践是「给 Python 代码标注类型，再配合静态检查工具，那么也会向静态语言那样在代码提交前就发现问题」，这样的方式已经在其他语言里面获得了成功，如 Javascript 到 TypeScript、PHP 到 Hack。  


Python 这种动态语言在阅读代码时很考验编程经验，即便是再资深的 Python 工程师也需要通过代码了解变量的类型，而加了类型注解后，对于开发者理解和维护会容易很多。

## PEP 3107 – Function Annotations


def func(a: str, b:int)

形参input: <type>是语言支持时的语法 (Python 3), 而#type: type这种注释是当语言不支持语法的兼容用法 (Python 2)。


## 类型注解的 PEP 提案 (Python 3.5)
接着，Guido 和 Ivan Levkivskyi 的<PEP 483 – The Theory of Type Hints>(延伸阅读链接 3, 也就是类型注解理论) 和 Guido、Jukka 和Łukasz Langa 的<PEP 484 – Type Hints>(延伸阅读链接 4，类型注解最主要的 PEP) 提交了。这些 PEP 提案在 Python 3.5 实现了，所以从 Python 3.5 开始正式支持类型注解了。


进化非常平缓，非侵入性的，如果你不关注可以完全忽略这部分内容

Dict[str, int] , Sequence[Item]


Union 组合多个类型
当单个参数可以是多个类型时可以使用 Union 组合

Stub 文件
Stub (存根) 文件是包含类型注解的文件，这些注解仅供类型检查器使用，而不是在运行时使用。

Stub 文件通常后缀是pyi，你可以理解为在 Stub 文件中重新定义了一遍相关的函数 / 类 / 变量等内容的类型，而原来的.py源文件不受影响。

## Python3.6

Python 3 的大的 feature 除了类型系统就是 asyncio，Python 3.6 里面除了引入异步生成器、异步推导式以外还加入了非常好用的 f-string。这个版本是我心目中第一个可以用生产环境的 Python 3 版本。

不过这个版本里面对于类型注解相关的新特性只有<PEP 526 – Syntax for Variable Annotations>(延伸阅读链接 6)。在 Python 3.5 引入的类型注解主要是针对函数 / 方法的，而 PEP 526 是针对于变量的


captain: str = 'Picard'   

Optional支持:   subfolder: Optional[str] = None,



## Python 3.7

这个版本中主要有 2 个类型系统的修改。

PEP 560 – Core support for typing module and generic types

解除不修改cpython的限制，可以通过添加两个特殊方法__class_getitem__和__mro_entries__以便更好地支持泛型类型。

PEP 563 – Postponed Evaluation of Annotations

类型注解是在模块导入时执行的，它的执行是需要有开销的，而第二个是向前引用 (Forward References) 的问题， 不能引用没定义好的类型



## Python 3.8

PEP 589 – TypedDict: Type Hints for Dictionaries with a Fixed Set of Keys
之前的Dict限时dict必须存放相同的类型key和value，TypedDict支持不同的key对应不同类型的value

movie = {'name': 'Blade Runner',
         'year': 1982}


PEP 586 – Literal Types

def open(path: Union[str, bytes, int],
         mode: Literal["r", "w", "a", "x", "r+", "w+", "a+", "x+"],
         ):



PEP 591 – Adding a final qualifier to typing
这个 PEP 定义了 Final 限定符，可以通过 final 装饰器或者 Final 作为类型注解。它用在:

被声明的方法不能被重载
被声明的类不能被继承 (子类化)
被声明的属性或者变量不能被重新设值。

@final
class Base:


PEP 544 – Protocols: Structural subtyping (static duck typing)

python的默认符号匹配就是用的鸭子类型，

```
from typing import Protocol


class Quacker(Protocol):
    def quack(self) -> str:
        ...


class OtherDuck:
    def quack(self) -> str:
        return "QUACK!"


def sonorize2(duck: Quacker) -> None:
    print(duck.quack())


sonorize2(Duck())
sonorize2(OtherDuck())
```


继承 Protocol 就可以声明一个接口类型，当遇到接口类型的注解时，只要接收到的对象实现了接口类型的所有方法，即可通过类型注解的检查




## Python 3.10 新加入的四个和类型系统相关的新特性

def square(number: int | float) -> int | float:
    return number ** 2

替代Union


isinstance('s', (int, str)) -> isinstance('s', int | str)


Board:TypeAlias = List[Tuple[str, str]]  # 这是一个类型别名

类型重命名，不适用Board类型检查无法识别

https://www.dongwm.com/post/python-3-10-new-typing-feature/#PEP604:NewTypeUnionOperator






mypy 用于类型检查


# 重构

以我阅读过很多优秀开源项目和认识一些非常优秀的 Python 工程师的经历来说，代码大面积重构一般难点在两个地方:

很多高效率、聪明的代码非常简短难懂，如果能力不够是很难理解和维护它的。
很多能力一般的开发者写的代码设计有问题、细节考虑不周，这样的代码在野蛮生长的过程中满足了产品开发进度要求，但是在不断地留坑。这些代码几经迭代，参数、逻辑非常混乱复杂，能力稍差的维护者不敢动它的逻辑。
所以长久的、从根本的解决代码的质量问题其实要编写可维护的代码，而不要滥用或者错误使用语言特性，尤其是不要炫技





# TYPE_CHECKING

用于解决循环import

PEP-0563 里给出了另一个解决方案：使用typing.TYPE_CHECKING。这个常量在编辑器检查变量类型的时候为 True，在代码实际运行的时候为 False。于是，我们可以用如下代码来导入声明类型时用到的类：

if typing.TYPE_CHECKING:
    from foo import bar


不Import导致的类型注释报错的问题，PEP-0563 里也给出了相关的解决方案。在 Python 4 以后，函数的 annotations 将不再运行时被执行，所以也就不会报错了

# 具体使用分析

