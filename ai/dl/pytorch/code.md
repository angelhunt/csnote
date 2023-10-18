
# normalization
``` python
import torch
from torch import nn
bn = nn.BatchNorm2d(num_features=3, eps=0, affine=False, track_running_stats=False)
x = torch.rand(10, 3, 5, 5)*10000
official_bn = bn(x)   # 官方代码

x1 = x.permute(1, 0, 2, 3).reshape(3, -1) # 对(N, H, W)计算均值方差
mean = x1.mean(dim=1).reshape(1, 3, 1, 1)
# x1.mean(dim=1)后维度为(3,)
std = x1.std(dim=1, unbiased=False).reshape(1, 3, 1, 1)
my_bn = (x - mean)/std
print((official_bn-my_bn).sum())  # 输出误差

```