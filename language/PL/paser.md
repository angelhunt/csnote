
用lex/yacc做一个parser。
后来lex/yacc进化成flex/bison，在工作中我也无意中翻看了一本orelley叫『Flex & Bison』的书，这书的副标题赫然写着：text processing tools。

是啊，词法分析 - lexical parsing（lex/flex），语法分析 - grammar parsing（yacc/bison）只是更好的文本处理工具（parser），是个高效处理带有语法的文本的DSL（Domain Specific Language）.

Regular expression也是一种文本处理工具，也是个DSL，只不过，它处理不了复杂的语法。

自动化理论（automata theory）里，有FSA（Finite State Automata）和PDA（PushDown Automata），前者可以用regular expression表述，而后者可以处理CFG（Context Free Grammar）。而CFG便是flex/bison要处理的对象！

大部分语言都没有内置对CFG的处理，一旦文本处理的复杂度超过了regular expression可以表述的复杂度，我们便无能为力。


工具antlr, LLVM

新手写tokenize和parser建议使用lex和yacc吗?其实我更建议用ANTLR,因为它操作更加简单，更加无脑



语法分析做的是pattern matching的事情，

jison。我想你应该猜到了，这货是javascript bison，bison在javascript上的变态。

用javascript/jison做parser有什么好处呢？你可以在浏览器端做复杂的input validation，你可以允许用户输入符合你定义的语法的文本，然后生成想要的东西，比如思维导图，或者这个项目：

# antlr
antlr4对我而言，有三个强大的地方：

* 各种现成的语法定义（基本都是MIT/BSD license，跪拜吧，少年！）。打开这个repo：antlr/grammars-v4 · GitHub， 有没有想哭的赶脚？

* 生成主流程序语言的parser。嗯，你可以对着m4语法文件轻松生成python，javascript等的源码，然后集成到你自己的项目里。继续哭吧。

* SAX-like event driven。antlr4直接替你生成好了复杂的语法树 - 一般而言，antlr4生成的语法树没有使用instaparse/bison等生成的那么清爽，所以直接处理起来有些费劲，antlr4的创新之处在于：我先帮你生成好树，然后你可以随意遍历。就像SAX处理XML那样，每条规则（可以类比XML的每个Node）你都可以设置enter listener和exit listener，你把callback注册在你关心的节点的，antlr4会把上下文交给你处理。