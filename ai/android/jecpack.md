# lifecycle
2个问题点：

1. activity的生命周期内有大量管理组件的代码，难以维护。
2. 无法保证组件会在 Activity/Fragment停止后不执行启动（启动的耗时过大，生命周期结束还没初始化完，因此资源可能没有被释放）



lifecycle抽象了activity, fragment的生成周期，使得其他自定义的类也可以绑定到activity的生命周期上。

这样让各个组件存储自己的逻辑，可以减轻Activity/Fragment中代码，更易于管理.
比如mvp架构中，Presenter类自动感知生命周期，如果需要在其他的Activity/Fragment也使用这个Presenter，只需添加其为观察者即可.

提供api，可以对当前生命周期状态进行了检查：至少处于STARTED状态才会继续执行start()方法

Lifecycle的使用很简单：

1、生命周期拥有者 使用getLifecycle()获取Lifecycle实例，然后代用addObserve()添加观察者；
2、观察者实现LifecycleObserver，方法上使用OnLifecycleEvent注解关注对应生命周期，生命周期触发时就会执行对应方法

https://zhuanlan.zhihu.com/p/347896274

# live data
LiveData使得 数据的更新 能以观察者模式 被observer感知，且此感知只发生在 LifecycleOwner的活跃生命周期状态。

LiveData是一个数据持有者，给源数据包装一层。
源数据使用LiveData包装后，可以被observer观察，数据有更新时observer可感知。
但 observer的感知，只发生在（Activity/Fragment）活跃生命周期状态（STARTED、RESUMED）。

LiveData使得 数据的更新 能以观察者模式 被observer感知，且此感知只发生在 LifecycleOwner的活跃生命周期状态

使用 LiveData 具有以下优势：

1. 确保界面符合数据状态，当生命周期状态变化时，LiveData通知Observer，可以在observer中更新界面。观察者可以在生命周期状态更改时刷新界面，而不是在每次数据变化时刷新界面。
2. 不会发生内存泄漏，observer会在LifecycleOwner状态变为DESTROYED后自动remove。
3. 不会因 Activity 停止而导致崩溃，如果LifecycleOwner生命周期处于非活跃状态，则它不会接收任何 LiveData事件。
4. 不需要手动解除观察，开发者不需要在onPause或onDestroy方法中解除对LiveData的观察，因为LiveData能感知生命周期状态变化，所以会自动管理所有这些操作。
5. 数据始终保持最新状态，数据更新时 若LifecycleOwner为非活跃状态，那么会在变为活跃时接收最新数据。例如，曾经在后台的 Activity 会在返回前台后，observer立即接收最新的数据。

https://zhuanlan.zhihu.com/p/348773409

# ViewModel
ViewModel，意为 视图模型，即 为界面准备数据的模型。简单理解就是，ViewModel为UI层提供数据。

ViewModel 以注重生命周期的方式存储和管理界面相关的数据。(作用)
ViewModel 类让数据可在发生屏幕旋转等配置更改后继续留存。(特点)

存在的问题
1. Activity可能会在某些场景（例如屏幕旋转）销毁和重新创建界面，那么存储在其中的界面相关数据都会丢失。此时需要我们自己在 onSaveInstanceState函数中保存状态和恢复状态.
2. UI层（如 Activity 和 Fragment）经常需要通过逻辑层（如MVP中的Presenter, MVVM中的Model）进行异步请求，可能需要一些时间才能返回结果，如果逻辑层持有UI层应用（如context），那么UI层需要管理这些请求，确保界面销毁后清理这些调用以避免潜在的内存泄露，但此项管理需要大量的维护工作。 那么如何更好的避免因异步请求带来的内存泄漏呢？

ViewModel有以下特点：
1. 在因屏幕旋转而重新创建Activity后，ViewModel对象依然会保留。 生命周期长于Activity
2. 不持有UI层引用,因为ViewModel的生命周期更长,而是基于观察者模式的LiveData更新UI。避免了可能的内存泄漏


# ViewBinding