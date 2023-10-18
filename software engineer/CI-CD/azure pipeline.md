One of the first exercises building a dev team is to set up a standard development environment and configuration

而这个完成这个工作，同时需要客户端和云端的工具（因为代码同步在云端），云端就是CI，客户端主要需要依赖IDE插件之类的*。
the whole team needs to have the same standard to avoid situations like "It works on my machine!

# c# linting
As opposed to weakly-typed languages like JavaScript and Python where it’s used to catch stylistic and programming errors that are otherwise detectable only at runtime, or result from the burden of unconstrained freedom that such languages bestow upon unsuspecting engineers

首先做linting我们需要考虑analyzers and analyzer configuration
1. analyzer包括IDE analyzer(built into vs) and third party(StyleCop, FxCop)
2. 配置包括editorconfig or a ruleset file

StyleCop analyzers do not support editorconfig based configuration, but use their own stylecop.json with their own custom format


C# and its strongly-typed brethren linting has made a comeback as a policy tool in the CI/CD quality gates, to be run in parallel along with security and dependency scan jobs.


Since Microsoft has finally embraced EditorConfig for all Roslyn-powered projects in VS 2019 16.3+ (and analyzer toolset 3.3+), we don’t need to write ugly .ruleset files anymore to trigger build errors or to regulate the severity of violations. 


目前看来Roslyn Analyzers相比styleCop是更好地选择
* StyleCop.Analyzers in many projects and is very helpful. It just appears Microsoft.CodeAnalysis.FxCopAnalyzers is the future direction
* editorconfig is to replace StyleCop, use the .editorconfig for C# static code analysis and coding style conventions.


## EditorConfig
Microsoft has finally embraced EditorConfig for all Roslyn-powered projects in VS.
EditorConfig有了支持，就不需要自己痛苦的定义ruleset(stylecop的方式).

EditorConfig helps maintain consistent coding styles for multiple developers working on the same project across various editors and IDEs. 

The EditorConfig project consists of a file format for defining coding styles and a collection of text editor plugins that enable editors to read the file format and adhere to defined styles.

we define the .editorconfig file and simply put it into the root directory of the project. Then it provides indications of how consistently we keep our coding style.

## 分析工具
用于code analysis的工具很多， We can mix and choose many available analyzer packages like the StyleCop, the Roslynator. FxCop analyzers.

FxCop analyser不依赖VS，我们可以通过Nuget安装, 目前FxCop也在整合EditorConfig.

我们可以通过一些editorconfig的模板减轻工作量

Both FxCop analyzers and .NET analyzers refers to the .NET Compiler Platform ("Roslyn") analyzer implementations of FxCop CA rules. 

, these analyzers shipped as Microsoft.CodeAnalysis.FxCopAnalyzers NuGet package. Starting in Visual Studio 2019 16.8 and .NET 5.0, these analyzers are included with the .NET SDK. They are also available as Microsoft.CodeAnalysis.NetAnalyzers NuGet package



## CI集成
 For analyzers that are installed from a NuGet package, those rules are enforced at build time, including during a CI build.