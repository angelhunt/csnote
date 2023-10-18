Pod是Kubernetes的最小工作单元.每个Pod包含一个或多个容器.Pod中的容器会作为一个整体被Master调度到一个Node上运行

Kubernetes引入Pod主要基于下面两个目的:

    可管理性

    有些容器天生就是需要紧密联系,一起工作.Pod提供了比容器更高层次的抽象,将它们封装到一个部署单元中.Kubernetes以Pod为最小单位进行调度,扩展,共享资源,管理生命周期

    通信和资源共享

    Pod中的所有容器使用同一个网络namespace,即相同的IP地址和Port空间.它们可以直接用localhost通信.同样的,这些容器可以共享存储,当Kubernetes挂载volume到Pod,本质上是将volume挂载到Pod中的每一个容器

大部分的Pod都运行单一的容器,只有少部分联系十分紧密以至于需要直接共享资源的容器才运行在同一个Pod中


https://staight.github.io/2018/08/07/k8s%E7%9F%A5%E8%AF%86%E7%82%B9%E6%80%BB%E7%BB%93/o


http://wiki.corp.pony.ai/display/~minglyu/p8s+and+kubectl+note


# pod环境

To access bash on the server, first start a persistent job like a jupyter. This will also enable you to use jupyter notebook on the server.

1. p8s create jupyter(persisitent job)
2. p8s list resource(only live and persistent job)
3. kubectl -n ${YOUR_TEAM} exec -it ${POD_NAME} -- bash

And often times we like to put data in /home/pony-trainer/jupyter/ so that the online jupyter notebook can easily access the data. Relative directory run by mpm jobs will be generated in temporary directories and get lost. To copy the result to local machine or to upload data: 

1. show pod log, kubectl logs
2. attach to pod's stdout, kubectl attach

# from local to remote
kubectl -n ${NAMESPACE} cp /from/local ${POD_NAME}:/home/pony-trainer/to/remote
 
# from remote to local
kubectl -n ${NAMESPACE} cp ${POD_NAME}:/home/pony-trainer/from/remote /to/local

http://wiki.corp.pony.ai/display/~minglyu/p8s+and+kubectl+note
http://wiki.corp.pony.ai/display/PER/k8s+operations

