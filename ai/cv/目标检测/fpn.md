# 常见结构
ResNet+FPN的网络结构，以三种经典的目标检测算法为例

![](../目标检测/pic/2022-05-06-14-06-21.png)


C1~C5指的是ResNet的特征层，FPN的主要结构是本特征层1*1的卷积结果和上一特征层上采样结果进行add，图中黄色的1*1卷积就是mmdetection代码中的lateral_convs，紫色的3*3卷积就是mmdetection代码中的fpn_convs。可见三种算法最后的输出都是5个特征层。



