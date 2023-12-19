# monodepth2

The disp which we predict isn't true disparity, as is usually used in e.g. stereo matching. What we in fact predict should be called scaled_inverse_depth.

So mathematically we don't need the focal length and image width to convert our scaled inverse depth prediction into depth, as we have already taken care of those during training.

https://github.com/nianticlabs/monodepth2/issues/373