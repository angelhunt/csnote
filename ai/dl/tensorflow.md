http://www.ldem.me/blog/2019/08/11/tensorflow%e5%ae%9e%e7%8e%b0%e7%9f%a9%e9%98%b5embedding%e5%8a%a0%e6%9d%83/


# 常用api
gather, 根据index从tensor中取值，对应的将稠密形式转为稀疏。缺点是只能指定某一维度的index, 没法多级index，比如我们index是[row, col]的情况就不行。
这就需要gather_nd, 对于一个n维tensor, gather_nd需要指定一个n维的index.

scatter, 则是将稀疏汇聚到稠密，也就是按照index给tensor赋值.

tf.tensor_scatter_nd_update，tf.tensor_scatter_nd_add,
满足下面关系
assert tf.rank(indices) >= 2
index_depth = indices.shape[-1]
batch_shape = indices.shape[:-1]
assert index_depth <= tf.rank(tensor)
outer_shape = tensor.shape[:index_depth]
inner_shape = tensor.shape[index_depth:]
assert updates.shape == batch_shape + inner_shape

indices indexes into the outer level of the input tensor (outer_shape). and replaces the sub-array at that location with the corresponding item from the updates list. The shape of each update is inner_shape.

tensor = tf.zeros([6, 3], dtype=tf.int32)
indices = tf.constant([[2], [4]])
updates = tf.constant([[1, 2, 3],
                       [4, 5, 6]])

                       tf.tensor_scatter_nd_update(tensor, indices, updates).