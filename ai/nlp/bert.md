https://zhuanlan.zhihu.com/p/107688176

Bert Transfromer中的激活函数gelu（Gaussian error linear units，高斯误差线性单元），其公式为：

GELU(x)=xP(X<=x)=xΦ(x)

其中P(X<=x)满足伯努利分布，X满足高斯分布，决定了x中有多少信息保留，也满足了非线性的特征，并且更加符合数据的分布预期。

bert是基于encoder架构的预训练模型，目标是为了对条件概率建模p(xt|x1...xt-1,xt+1,...),从而学习到包含context信息的语言模型。

bert对语句进行随机mask(替换成随机token或者[Mask]或者不变)

bert不擅长生成任务。BERT and other pretrained encoders don’t naturally lead to nice autoregressive (1-word-at-a-time) generation methods.


