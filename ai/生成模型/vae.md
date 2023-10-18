# 贝叶斯推断
贝叶斯假设数据由隐变量生成，隐变量是不可直接观测的，它们控制了现实世界中数据（样本点，也叫观测点）的产生, 生成过程可表示为:

$p(x|z) = \prod \limits_{i=0}^n p(x_{i}|z)$

隐变量的后验分布可用贝叶斯表示为
$p(z|x) = \frac{p(x|z)p(z)}{\int_{z} p(x|z)p(z) dz }$

这个求解是个积分公式，积分是指数级别的复杂度，是不可解的。所以人们往往不会直接求解贝叶斯公式来得到后验分布，而是使用其他方案，常见的有马尔可夫-蒙特卡洛（Markov Chain Monte Carlo, MCMC），变分推断（Variational Inference）等。MCMC 是利用采样对分母中的积分做估计，虽然精确，但是计算效率低下。相反变分推断却能适应大数据场景，速度更快，因而广受欢迎。一般需要用

# 重参数采样
采样这个操作本身是不可导的，但是我们可以通过重参数化技巧，将简单分布的采样结果变换到特定分布中，如此一来则可以对变换过程进行求导

比如我们我们可以从标准高斯分布采样得到$\epsilon$, 通过$z=\mu + \epsilon \cdot \sigma$得到$\mathcal N(\mu,\sigma^2)$的采样，这个过程是可导的，可以通过梯度下降更新

https://juejin.cn/post/7213239578664157221j


# 变分推断

区别 MCMC 对分母中的积分做估计，变分推断另辟蹊径，
直接用一个参数化的概率分布$q(z;\theta)$近似后验分布$p(z|x)$，对应的优化目标就是

$\theta^* = argmin_{\theta} KL(q||p)$

注意这里的q和p是反的，相当于用q作为真实分布，p作为编码

对kl散度简化可得

$KL(q||p) = E_q(log(q(z))) - E_q(logp(z|x)) $
     $ \hspace{4.2em} = E_q(logq(z)) - E_q(logp(x, z)) + logp(x)$ 
     $ \hspace{4.2em} = -ELBO(q) + logp(x)$ 

最后一个等式出现了一个新定义 ELBO（Evidence Low Bound），问题可以转化为最大化 ELBO

由KL的正定性得到:
$log(p(x)) >= ELBO(q)$

$log(p(x))$是常数项，代表数据集出现的概率，也被叫做 evidence，而右边项是 evidence 的数值下界，这就是 ELBO 名字的由来 Evidence lower bound。


EM算法在抽象程度上比变分推断低了那么一点点，算是一个变分推断的实例或者特例，因为EM优化的是ELBO的一个特例或者简化形式，但是变分推断优化的是ELBO

https://kexue.fm/archives/5716

# 变分推断衍生

首先，本文先介绍了变分推断的一个新形式，这个新形式其实在博客以前的文章中就已经介绍过，它可以让我们在几行字之内导出变分自编码器（VAE）和EM算法。然后，利用这个新形式，我们能直接导出GAN，并且发现标准GAN的loss实则是不完备的，缺少了一个正则项。如果没有这个正则项，我们就需要谨慎地调整超参数，才能使得模型收敛。

变分推断的本质是将:$KL(p(x) || q(x))$ 转为$KL(p(x,z) || q(x,z))$ 或者$KL(q(x,z) || p(x,z))$, 同时由贝叶斯定理可得:

$KL(p(x,z) || q(x,z)) = KL(p(x) || q(x)) + \int p(x) KL(p(z|x) || q(z|x))
\newline \hspace{9em} \geq KL(p(x) || q(x))$

这样我们可以约束这个更强的上界, 变分推断是提供了一个可计算的方案, 因为这个上界更好计算。

## VAE
在vae中我们假设$q(x,z) = q(x|z)q(z), p(x,z) = p(x)p(z|x)$, 其中q(x|z)和p(z|x)都是带有未知参数的高斯分布

自编码机的思路就是用encoder建模p(z|x)，用decoder建模q(x|z), 最小化目标是
$KL(p(x,z) || q(x,z)) = \int_x \int_z p(x,z) log \frac{p(x, z)}{q(x,z)}$
$ \hspace{9em} = \int_x \int_z log p(x)p(z|x) \frac{p(x)p(z|x)}{q(z)q(z|x)}$

logp(x)可有看做常量，对x的积分可以看做是采样（min-batch）,因此最小化目标是
$E_{x ~ p(x)} = -\int_z p(z|x)logq(x|z) + KL(p(z|x)||q(z))$

最终优化目标变成了p(z|x)和q(x|z)的优化，因为vae假设这两个都是高斯分布，因此$KL(p(z|x)||q(z))$可以算出，并且通过重参数技巧来采样一个点完成上式的积分计算

最终得到vae的loss
$$E_{x ~ p(x)} = -logq(x|z) + KL(p(z|x)||q(z))$$


## em算法

https://kexue.fm/archives/5716




# 变分编码

无论EM算法还是MCMC有个共同的特点就是每次迭代都是使用全部数据，这个狠不狠？

结合深度学习，能使用梯度下降进行优化从而完成变分推断，VAE是第一个尝试的（2013/12），并且效果不错。Uber的工程师团队也受到启发开发了Pyro 称之为深度概率编程，主要就是深度学习加上变分推断，其主要思想应该来自VAE

