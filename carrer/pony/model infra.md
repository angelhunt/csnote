# data generation

DataFlow, Simulation-based training data generation (labeling packets to splitted & shuffled tfrecords).

DataFlow compare to data extraction Jenkins pipeline, DataFlow is more scalable and flexible by running on dynamic configurable k8s resources.
Jenkins use fixed resources. Your data extraction job is usually limited to running with only one machine. It's slow if you want to extract huge amount of data.

http://wiki.corp.pony.ai/display/PER/DataFlow




## Jenkins pipeline.

http://wiki.corp.pony.ai/display/PER/Training+data+export

https://github.com/ponyai/perception/blob/master/training/tensorflow/bird_view_detection/generate_training_data.py#L115


## dataflow
scenario is a way to define a set of records. For example, a scenario can be a 3min truncated data.