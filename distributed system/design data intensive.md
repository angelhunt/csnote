# charpter one
Reliable, Scalable, and Maintainable Applications

主要的应用方向
1. Store data so that they, or another application, can find it again later (databases) 
2. Remember the result of an expensive operation, to speed up reads (caches) 
3. Allow users to search data by keyword or filter it in various ways (search indexes) 
4. Send a message to another process, to be handled asynchronously (stream processing)
5. Periodically crunch a large amount of accumulated data (batch processing)

三大设计要求
1. reliable
2. scalable
3. maintainable
   
different access patterns, which means different performance characteristics, and thus very different implementations

Many new tools no longer neatly fit into traditional categories. For example, there are datastores that are also used as message queues (Redis), and there are message queues with database-like durability guarantees (Apache Kafka). 

当组合这些工具的时候，我们需要自己做他们之间的同步，比如更新数据库后需要同步Index和cache.
这个时候就需要考虑一致性的问题了，因为某个时刻，这些系统可能处于不一致的状态，需要明确定义出现这种情况，对于外部请求应该提供怎样的结果

所以设计一个data sytem需要考虑很多问题，比如下面的
* How do you ensure that the data remains correct and complete, even when things go wrong internally? (Reliability)
* How do you provide consistently good performance to clients, even when parts of your system are degraded? 
* How do you scale to handle an increase in load? (Scalability)
* What does a good API for the service look like?


## 1. Reliability
不可能预防所有错误，好的选择是只预防特定类型.

The things that can go wrong are called faults, and systems that anticipate faults and can cope with them are called fault-tolerant or resilient. 

Note that a fault is not the same as a failure.
A fault is usually defined as one component of the system deviating from its spec, whereas a failure is when the system as a whole stops providing the required service to the user. 

Fault可以通过randomly killing individual processes without warning来模拟。

by deliberately inducing faults, you ensure that the fault-tolerance machinery is continually exercised and tested, The Netflix Chaos Monkey [4] is an example of this approach.

### hardware fault

large datacenters can tell you that these things happen all the time when you have a lot of machines.

1. add redundancy to the individual hardware.
   Disks may be set up in a RAID configuration, servers may have dual power supplies and hot-swappable CPUs, and datacenters may have batteries and diesel generators for backup power


单单对hardware做备份，在你有大量机器的时候并不够用，
in some cloud platforms such as Amazon Web Services (AWS) it is fairly common for virtual machine instances to become unavailable without warning, as the platforms are designed to prioritize flexibility and elasticityi
over single-machine reliability

we need can tolerate the loss of entire machines, by using software fault-tolerance techniques in preference or in addition to hardware redundancy. 

a single-server system requires planned downtime if you need to reboot the machine (to apply operating system security patches, for example), whereas a system that can tolerate machine failure can be patched one node at a time, without downtime of the entire system system (a rolling upgrade)

### software error
Another class of fault is a systematic error within the system [8].

* A runaway process that uses up some shared resource
* A service that the system depends on that slows down, becomes unresponsive
  
There is no quick solution to the problem of systematic faults in software. Lots of small things can help: carefully thinking about assumptions and interactions in the system; thorough testing; process isolation; allowing processes to crash and restart; measuring, monitoring, and analyzing system behavior in production.





### Human Error
configuration errors by operators were the leading cause of outages

How do we make our systems reliable, in spite of unreliable humans

* minimizes opportunities for error. For example, well-designed abstractions, APIs, and admin interfaces
* 




