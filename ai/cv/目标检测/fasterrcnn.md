参考代码

https://github.com/kevinjliang/tf-Faster-RCNN/tree/master/Networks



# anchor
feature map(上每一个点对应一组anchor，anchor是以这个点为中心，在原图上不同长宽比，不同尺寸的，总共9个。



# rpn

![](../目标检测/pic/2022-05-17-11-21-36.png)

1. 把feature map分割成多个小区域，识别出哪些小区域是前景，哪些是背景，简称RPN Classification，对应粉色框中上半分支
    * branch输出channel数是18, 对应每个anchor有两个score, 对应前景和后景分数.
2. 获取前景区域的大致坐标，简称RPN bounding box regression，对应下半分支


## 网络细节
3*3的卷积，不改变图像大小，增加卷积映射区域和空间信息
1*1的卷积，对以每个点为中心的9种候选框进行前后景分类(n,W,H,18)
(n,18,W,H)(n,2,9W,H)成[1,9*H,W,2]，便于caffee_softmax进行 fg/bg二分类
softmax判定单个框的foreground与background分数[1,9*H,W,2]，(n,2,9W,H)(n,18,W,H)转化为(1,H,W,18)，即以每个点为中心的9种候选框前后景分数
1*1的卷积，对前景区域的位置参数x1,y1,x2,y2进行回归
anchor_target_layer：得到(bg/fg)的标签值和bbox的偏移标签值
计算RPN的分类损失rpn_cls_loss和回归框损失rpn_bbox_loss

## loss计算
这里会根据anchor和真实的gt_box做match, 然后生成相应的classification_loss和bbox regression_loss

实现方法


1. 在原图上生成多种不同尺度大小的anchor
2. 选择所有的anchor中x1,y1,x2,y2没有超过图像边界的
3. 使用bbox_overlaps (ex, gt)，计算所有的没超过图像边界的anchor与gt_boxes之间的重合度IOU，每个gt_box对应的iou最大的ancho标记为前景， 同时大于0.7标记为前景图，小于0.3标记为背景图;返回类型(n,k),即第n个anchors与第K个gt_boxes的IOU重合度值。同时会有一个预设好的前景背景anchor数量，会随机舍弃一些。
4. 根据预设阈值和overlap重叠率，打上前背景标签1|0, -1表示不care.
5. 训练时一个batch的样本数是256，对应同一张图片的256个anchor，前景的个数不能超过一半，如果超出，就随机取128个做为前景，背景也有类似的筛选规则；随机抛弃一些前景anchors和背景anchors
6. 使用bbox_transform函数，计算每个anchor与最大的overlap的gt_boxes的框偏移量，作为标签值(tx,ty,th,tw)用于后续框回归
7. 计算positive_weights和negative_weights,这两个数组在训练anchor边框修正时有重大作用
8. 将所有图像边框内部的anchor映射回所有的anchor,统一所有的标签,并转化标签labels的格式shape后，返回rpn_labels,rpn_bbox_targets,rpn_bbox_inside_weights,rpn_bbox_outside_weights

## bbox相关操作
计算两个bbox的交集面积
a = (a1,a2,a3,a4), b=(b1,b2,b3,b4)
dh = min(a3, b3) - max(a1, b1) + 1
dw = min(a4, b4) - max(a2, b3) + 1

if dh < 0 or hw < 0:
    ua = 0
else:
    cross_area = dh * dw

iou = cross_area / (area(a) + area(b) - cross_area)

计算每个anchor与最大的overlap的gt_boxes的框偏移量
![](../目标检测/pic/2022-05-18-14-38-58.png)

