使用MKL加速spark ml算法

spark调用线 ml/mllib -> breeze -> netlib -> f2jBLAS/openblas/mkl

![](pic/2021-04-14-04-16-02.png)

mllib中很多算法没有调用blas, ml中就改善了很多，上图是ml里面的算法结果

https://tech.meituan.com/2017/03/24/travel-recsys.html

