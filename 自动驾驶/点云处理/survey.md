
CNN早早地在图像和视频理解方案中占据了统治地位。但是当前3D点云的处理方案仍然是八仙过海，各显神通。每年不同3D任务SOTA算法的网络往往千变万化，没有定式。主要流派可分为三类：PointNet-based，Graph-based，Voxel-based。经过多年发展，目前不同3D任务能够看到他们各式的混血儿，在CVPR 2020中的两位代表：PointNet + Graph-based: Grid-GCN for Fast and Scalable Point Cloud Learning, CVPR 2020；PointNet + Voxel-based: PV-RCNN: Point-Voxel Feature Set Abstraction for 3D Object Detection, CVPR 2020.





目前采用的方式主要有两种：



1. 将点云数据投影到二维平面。此种方式不直接处理三维的点云数据，而是先将点云投影到某些特定视角再处理，如前视视角和鸟瞰视角。同时，也可以融合使用来自相机的图像信息。通过将这些不同视角的数据相结合，来实现点云数据的认知任务。比较典型的算法有MV3D和AVOD。

2. 将点云数据划分到有空间依赖关系的voxel。此种方式通过分割三维空间，引入空间依赖关系到点云数据中，再使用3D卷积等方式来进行处理。这种方法的精度依赖于三维空间的分割细腻度，而且3D卷积的运算复杂度也较高。****

3. 直接在点云数据上应用深度学习模型的方法，称为PointNet。

以PointNet++为主干网再加上Detection Head，代表论文有PointRCNN。这一类是基于点的检测算法，对原始点云每一个点进行分类预测。基于Voxel的主干网，代表论文有VoxelRCNN。将原始点云划分为规则的Voxel，然后使用3DCNN预测。


要是说自动驾驶公司使用的网络，可以看看Waymo和Uber的论文，他们最新论文是基于点云序列进行目标检测，检测与追踪相结合，不过他们目的是为了对点云进行自动标注，替代人工标注，也是很好发论文的一个方向。


# 工业界

PointPillars，Complex Yolo这类基于Bird Eye View和2D CNN的应该是最受业界欢迎的。因为2D CNN的效率高，工具链成熟，硬件支持好。
PiontPillars当年发论文刚出来的时候，帧率惊人的高，仔细一看，用了TensorRT加速。后来的论文经常黑这一点，
几乎全是二维卷积和Pooling，是个加速芯片都支持。基于纯3D表示的方法，绕不开PointNet++，3D Conv或者他们的变种，速度高不到哪儿去，而且基本都会卡在硬件部署上。GPU部署的时候还能硬着头皮给TensorRT写PlugIn，硬件如果是FPGA那就慢慢去写IP Core吧。在ASIC上部署限制就更多了。

另外2D CNN的量化压缩，理论和工具链都比较成熟。那些真·3D算子，INT8量化一下会发生什么还真的不太好说。
至于性能什么的，可以靠trick往上堆嘛，魔改loss，加数据增强，堆数据。大力出奇迹。
另外个人觉得最近的基于Range Image的方法比如RangeRCNN也是值得关注的。但是RangeRCNN为了刷到SOTA的性能，结构还是过于复杂了。尝试简化一下或许也能得到不错的折中效果。

学术界搞3D检测还是以在KITTI等数据集上刷高AP为主，基本无视落地需求。比如PointRCNN，性能确实高，但是一想到要让这玩意儿落地，感觉就大事不妙。KITTI上的AP已经被刷的非常高了，不管是idea还是提升余地，感觉都快被榨干了。但是路上跑的智能车还是智障的一匹。

Point Pillars改改改 + Faster RCNN改改改。

PointRCNN类太慢。

单阶段的方法性能差一些，也不灵活，加点功能动不动就是几个大conv，玩不起。

纯点云3d检测的话，基本就是pointpillars的各种变重了，performance和耗时都不错，部署也比较容易

ointNet(vanilla)的网络结构能够拟合任意一个在Hausdorff空间上连续的函数。其作用类似证明神经网络能够拟合任意连续函数一样。同时，作者发现PointNet模型的表征能力和maxpooling操作输出的数据维度(K)相关，K值越大，模型的表征能力越强。



多人对Kitti的规模存在一定程度的误解。Kitti Odometry规模多大呢？以数据量算，能下载大约是激光+双目共140GB的数据量。这又是什么概念呢？以apollo为例，现在自动驾驶车辆每分钟能记录5-6GB数据，以5GB算，Kitti相当于半个小时的数据量。当然一方面现在自动驾驶车辆数据量比2012年那会儿大。如果以里程算，Kitti Odometry大约39.2km里程，咱们就跟四环比一比：

localization（定位）和odometry（速度计）是有本质区别的，自动驾驶定位要求是能够提供绝对位置，而odometry是提供相对位置，相对位置做得再好也有误差，这时候便需要localization修复误差。要提供精确的绝对定位便需要精确的地图，而保持一个实时更新的地图是现在自动驾驶的一个难题。

整个自动驾驶在算法层面需要解决的问题也主要是应对长尾问题而从来就不在于缺乏一个足够真实的数据集上。市区如果有足够多密集的RTK基站，那么GPS会是比visual odometry甚至laser理论上更可靠的定位方式，而且odometry的精度也从来都不是自动驾驶的最大问题吧