# 1. 模块 module 与 包 package
典型的，一个 .py 后缀文件即是 Python 的一个模块。在模块的内部，可以通过全局变量 __name__ 来获得模块名。模块可以包含可执行的语句，这些语句会在模块 初始化 的时候执行 —— 当所在模块被 import 导入时它们有且只会执行一次。


根据目前 PEP420 的提案，目前的 Python 实际上是有两种包的存在：正规包（regular Package） 以及 命名空间包（Namespace package）。

要注意的是，Python 的 package 实际上都是特殊的 module ：可以通过导入 package 之后查看 globals() 可知；实际上，任何带有 __path__ 属性的对象都会被 Python 视作 package 。

正规包：
在 Python 3.2 之前就已经存在了的，通常是以包含一个 __init__.py 文件的目录形式展现。当 package 被导入时，这个 __init__.py 文件会被 隐式 地执行。

命名空间包：
根据 PEP420 的定义，命名空间包是由多个 portion 组成的 —— portion 类似于父包下的子包，但它们物理位置上不一定相邻，而且它们可能表现为 .zip 中的文件、网络上的文件等等。命名空间包不需要 __init__.py 文件，只要它本身或者子包（也就是 portion）被导入时，Python 就会给顶级的部分创建为命名空间包 —— 因此，命名空间包不一定直接对应到文件系统中的对象，它可以是一个 虚拟 的 module 。

# 2. import

模块中的 Python 代码可以通过 import（导入）操作访问另一个模块内的代码。import 语句时调起导入机制的常用方式，但不是唯一方式。importlib.import_module() 以及内置的 __import__() 函数都可以调起导入机制。

import 语句实际上结合了两个操作：

搜索操作：根据指定的命名查找模块
绑定操作：将搜索的结果绑定到当前作用域对应的命名上

当一个模块被首次导入时，Python 会搜索该模块，如果找到就创建一个 module 对象并初始化；如果位找到则抛出 ModuleNotFoundError 异常。至于如何找到这些模块，Python 定义了多种的 搜索策略 （search strategy），而这些策略可以通过 importlib 等提供的各类 hook 来修改和扩展

根据 Python 3.3 的 changlog 可知目前导入系统已完全实现了 PEP302 的提案，所有的导入机制都会通过 sys.meta_path 暴露出来，不会再有任何隐式的导入机制

Python 提供了两种导入机制：

relative import 相对导入
absolute import 绝对导入

在 PEP8 中也提倡使用完全导入，它的使用方式如下

，我们经常会遇到 —— 因为位置问题，Python 找不到相应的库文件从而抛出 ImportError 异常。
其中很常用的一个办法是，将当前工作目录添加到 sys.path 这个列表当中去（注意要在你实际 import 该模块或包之前）

sys.path 的初始值来自于：

运行脚本所在的目录（如果打开的是交互式解释器则是当前目录）
PYTHONPATH 环境变量（类似于 PATH 变量，也是一组目录名组成）
Python 安装时的默认设置

值得注意的是，如果当前目录包含有和标准库同名的模块，会直接使用当前目录的模块而不是标准模块。


## cache
在导入搜索开始前，会先检查 sys.modules ，它是导入系统的缓存，本质上是一个字典

注意，如果要更新缓存，使用 删除 sys.modules 的键 这种做法会有副作用，因为这样回导致前后导入的同名模块的 module 对象不是同一个。最好的做法应该是使用 importlib.reload() 函数。

## import hook

meta hook 会在导入的最开始被调用（在查找缓存 sys.modules 之后），你可以在这里重载对 sys.path、frozen module 甚至内置 module 的处理。只需要往 sys.meta_path 添加一个新的 finder 即可注册 meta_hook 。

import path hook 会在 sys.path （或 package.__path__）处理时被调用，它们会负责处理 sys.path 中的条目。只需要往 sys.path_hooks 添加一个新的可调用对象即可注册 import path hook 。

http://sinhub.cn/2019/05/python-import-machinery-part-one/

## PEP 562

能在模块下定义__getattr__和__dir__方法，实现定制访问模块属性了。有什么用呢？其实官网已经给出了答案：

弃用某些属性 / 函数
懒加载 (lazy loading)

## magic method

```python
'''
当前模块对外的白名单,只允许 foo和bar对外提供服务.
也用于from picture import *.
__init__中没有设置时，会是Module中所有的子文件
'''
__all__ = ["foo", "bar"] 
__dir__ # 列出对象的所有属性（方法）名
```

## lazy import

如果你的 Python 程序程序有大量的 import，而且启动非常慢，那么你应该尝试懒导入，本文分享一种实现惰性导入的一种方法

实现方法可以用PEP 562，即在__init__.py中定义__getattr__和__dir__
```python

def _attach(package_name, submodules=None, submod_attrs=None):
    if submod_attrs is None:
        submod_attrs = {}

    if submodules is None:
        submodules = set()
    else:
        submodules = set(submodules)

    attr_to_modules = {attr: mod for mod, attrs in submod_attrs.items() for attr in attrs}
    __all__ = list(submodules | attr_to_modules.keys())

    def __getattr__(name):
        if name in submodules:
            return importlib.import_module(f"{package_name}.{name}")
        elif name in attr_to_modules:
            submod_path = f"{package_name}.{attr_to_modules[name]}"
            submod = importlib.import_module(submod_path)
            attr = getattr(submod, name)

            # If the attribute lives in a file (module) with the same
            # name as the attribute, ensure that the attribute and *not*
            # the module is accessible on the package.
            if name == attr_to_modules[name]:
                pkg = sys.modules[package_name]
                pkg.__dict__[name] = attr

            return attr
        else:
            raise AttributeError(f"No {package_name} attribute {name}")

    def __dir__():
        return __all__

    if os.environ.get("EAGER_IMPORT", ""):
        for attr in set(attr_to_modules.keys()) | submodules:
            __getattr__(attr)

    return __getattr__, __dir__, list(__all__)
__getattr__, __dir__, __all__ = _attach(__name__, submodules=[], submod_attrs=_SUBMOD_ATTRS)

```