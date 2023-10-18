# resnet18
1. 初始stem层，使用7*7卷积做预处理，stride是2，输出结果image size减半, 输出channel size 为64
2. 之后接resudual layer, 每一层由residual block构成, 同样是每一层image size减半，总共四层，这个过程中channel size不断翻倍，由64变为512, 其中有的block, 输出尺寸会变小，相应的residual加过来的原始输入也需要用1*1卷积做downsample.
3. 最后一层1*1 conv做average global pooling,将channel维度下降为1

初始stem layer就是用于迅速降低空间维度,

https://zhuanlan.zhihu.com/p/79378841

code

https://www.cvmart.net/community/detail/5402

