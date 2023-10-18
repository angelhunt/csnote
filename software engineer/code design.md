# 1. Null exception
Checking for null parameters, however, is not the correct reaction to this admitted shortcoming of the type-system, because it ignores that there are actually two distinct categories of null-related issues.

The first category is the simple programming error. 此时的NullPointerException完全是没有问题的，因为这个NullPointerException就是帮助定位了Bug.

The second category is the problematic one. It is when code deals with null values, often assigning meaning to the null value, like the Weld code with the conditions above. In these cases, there will be no exceptions generated; there will only be some different behavior. 
It wouldn't be visible if some of the parameters were null by accident, rather, there would be perhaps some business-level issue. Instead of a clear exception to follow, this would probably require debugging or a more in-depth analysis.

In the case of programming errors, it is very easy to find out that nulls were passed on. 
此时我们不用对null做任何处理，但是当null is a legal value of some parameter or parameters, 就不work了.
这个方案只有当调用者不会要检查返回值null的时候work, 作者需要可以free pass result from other, 因此绝对不要返回null, 而是使用Option.
Every method that returns null legally needs to be changed depending on what meaning this null value carries. 
当然这种方法其实是比较依赖调用函数的可靠性，因为我们不确定调用的外部函数是否会返回null.

The unfortunate reality is that we can not ignore nulls everywhere. There are a lot of classes, objects, and frameworks over which we have no influence. We have to deal with these. Some of these are in the JDK itself.
For these cases and for these cases only, null-checks are allowed, if only just to package them with Optional.ofNullable()or check with Objects.requireNonNull().


In a perfect world, it should not be possible to pass or return null values, at least not in public methods.
This is one area where Kotlin, Haskell, and others are clearly better designed than Java. We can, however, pretend that nulls don't exist. 

Note on Optional: don't use the get() method, use map(), orElse(), orElseGet(), ifPresent(), etc.

总结
0. We should instead pretend that nulls don't exist and treat any occurrence of null as a programming error.
1. 不用if(xx == null)或者null annotation来检查null, 只需要保证函数不返回Null，而是返回Optional
2. 然而世界不是完美的, 外部的framework或者JDK本身都可能返回null ,此时我们需要通过 Optional.ofNullable()or check with Objects.requireNonNull()来进行封装。

四条准则
* Methods and constructors should assume that all parameters are non-null
* Methods that allowed null should be redesigned or split into multiple methods
* Constructors with multiple parameters that aren't required should consider using the Builder Pattern.
* Methods that returned null should return Optional or a special implementation of whatever logic was expected on null previously.

Optional 
*  don't use the get() method, use map(), orElse(), orElseGet() etc.
*  Optional.of会抛出异常, ofNullable不会而是替换
*  灵活使用map搭配OrElse, opt.map(Customer::getName().orElse("UNKNOWN"),因为map会在变换时自动将Null变成Optional

https://gist.github.com/durgaswaroop/cf6a6f6ce90a27830e451a8f28e91921