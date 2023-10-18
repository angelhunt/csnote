
基本结构上是挺像的，都是通过hidden state 的演化来刻画 序列间的依赖关系。不同是：
1. 隐状态的表示: hmm是onehot, RNN是分布表示，RNN的表示能力强很多，或者说在面对高维度时，表示效率更高。类似nlp里，对单个token的表示，一种是onehot, 一种是word vector 。
2. 隐状态的演化方式: hmm是线性的，RNN是高度非线性。
3. 在垂直方向上，实际中的lstm还会增加depth, 来增加不同层面的抽象表示，也会使得表示能力指数增加，随着depth 增加。

RNN也是基于马尔可夫假设的，当前的隐状态仅依赖前一个时刻的隐状态。在有马尔可夫假设的模型中，不代表距离超过1的两个状态是无依赖的。印象中有paper证明， 在HMM中两个状态的依赖关系随距离指数衰减，而在RNN中是power law decay.  也就是大家通常说的 rnn可以略好的刻画 long term dependency.另，SLAM中还有种算法跟hmm类似，kalman filter.

最大的区别就是：RNN 不是概率模型，HMM 是概率模型。
隐马尔可夫模型：状态离散，观测可以连续可以离散

循环神经网络：状态连续，观测连续（也可以离散化）

1.DL/RNN中用的是distributed representation,100个二值节点能表示2^100种状态, 而PGM/HMM中用的是One-hot的表示方式,Hidden variable也不可能太多,因而能表示的状态数有限.2.设计DL时一般不会为每个节点定义特别的意义,训练结束后通过可视化的方法才可能看出某些节点的特征;而PGM的节点一般要精心设计

两者最像的地方都是，时间平移不变。。这点很重要

rnn用非线性代替了hmm的线性，表达能力更强



# example

Many tasks involve predicting a large number of variables that depend on  each  other  as  well  as  on  other  observed  variables.  (意思就是需要预测大量变量，并且这些变量之间也有相互依赖)

Structure dprediction methods are essentially a combination of classification andgraphical  modeling.  

这种情况输出通常都是有特定结构的，比如时序输出，比如输出一个parse tree.

A natural way to represent the manner in which output variablesdepend  on  each  other  is  provided  by  graphical  models.

图模型分为无向图和有向图。
无向图通过将graph分成互不相交的最大团，来计算联合概率，最大团的概率通过势函数估算。
