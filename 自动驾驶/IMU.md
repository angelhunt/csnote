# 数据预处理

normalizing raw data, filtering
raw data, selecting features and extracting features.




time-domain features
mean, median, variance, range, average and standard deviations (SD)。


From frequency domain
energy, correlation, velocity, acceleration, fundamental frequencies
(using Discrete Fourier Transform (DFT)) and signal peaks (using
Power Spectral Density (PSD) were used.



The value of raw data sampling rate is not accurate at 50 Hz; this
refers to the fact that during the raw data acquisition phase it could be
subjected to slight changes due to the hardware limitation; which in
its turn may produce a non-unified data and this will have an impact
on the outcome

可以使用插值算法处理缺失值问题，频率不一致可以用重采样解决。


resampling
algorithms were used to ensure that the sampled data will be unified at
50 Hz accurately.


built-in Matlab functions smooth, sort,
acc, varfun, pca and horzcat were used over the accelerometer and
gyroscope sensors data signals to apply normalization process over
them.
