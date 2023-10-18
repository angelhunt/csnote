# FindBugs
支持的注解。
做接口设计的时候，一个好的习惯就是注明Nullbility。 对于Nullable的参数或者返回值，从Java 8后Optional 是推荐的方式，因为它强迫接口使用者显示进行空值检测。
https://www.cnblogs.com/andy-songwei/p/11820564.html

// 被Findbugs用于代码静态分析，已被Deprecate来推荐JSR-305
edu.umd.cs.findbugs.annotations.NonNull

// JSR-305标准，被Findbugs支持并推荐
javax.annotation.Nonnull

https://dzone.com/articles/when-to-use-jsr-305-for-nullability-in-java

