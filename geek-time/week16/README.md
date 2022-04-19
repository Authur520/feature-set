## 毕业项目&毕业总结


### 毕业总结

经过了15周的学习，终于完成了Java进阶营的学习，非常的开心。参加这个训练营的最初的目的是为了建立自己的知识体系框架，做了几年的java开发的工作了，一直有接触各种知识，但是一直无法跳出来看到知识的全貌，知识孤立而未成体系。经过这次的学习，终于在老师的带领下，慢慢建立自己的知识体系了。



### 毕业项目：(必做)分别用 100 个字以上的一段话，加上一幅图 (架构图或脑图)，总结自己对下列技术的关键点思考和经验认识:

> 经过测试，为了保证的脑图的正常展示，需要翻墙。也可以下载下来进行查看。

#### 1 JVM

![JVM核心技术](https://github.com/binbinyouli27/JavaCourse00/tree/main/week16/mindMap/01-JVM.png?raw=true)

从学习Java开始，每次面试之前，总要对JVM的知识进行不断的巩固。对于JVM我们需要知道JVM的结构，每一部分的作用是什么。GC的策略我们不但要知道每种GC的年轻代和老年代的整理算法，而且要知道不同GC的使用场景。所有的GC没有绝对的性能好坏，因为每种GC的产生都有其原因。

#### 2 NIO

![NIO模型与Netty](https://github.com/binbinyouli27/JavaCourse00/tree/main/week16/mindMap/02-NIO&Netty.png?raw=true)

这部分学习到了接口性能的压测方法，从熟悉的SocketIO开始，了解五种IO模型：阻塞IO、非阻塞IO、IO多路复用、信号驱动IO、异步式IO的概念，场景介绍。Netty框架的介绍，是如何实现高性能的？Netty的网络程序如何优化。API网关的结构和功能、分类以及现有的产品对比。

#### 3 并发编程

![多线程和并发](https://github.com/binbinyouli27/JavaCourse00/tree/main/week16/mindMap/03-MutliThread.png?raw=true)

并发编程一直都要学习，也是面试老生常谈。在业务开发工作很少用到，实际的并发编程都被开发框架处理掉了。对Java常用的并发解决方案，Java的并发包又进行了详细的学习。线程的实现方式、状态以及各种方法，为了提供线程的性能的线程池相关内容进行了深入。

#### 4 Spring 和 ORM 等框架

![Java相关框架]https://github.com/binbinyouli27/JavaCourse00/tree/main/week16/mindMap/04-JavaFrame.png?raw=true)

通过Spring框架的AOP、Bean、XML配置、Messaging等技术原理的学习，透过框架的原理学习掌握解决适用场景解决方案。Spring Boot让开发、配置、运行都变得简单。Hibernate和MyBatis等ORM框架可以以面向对象的思维来操作数据库，MyBatis对DBA友好，Hibernate适用简单场景。

#### 5 MySQL 数据库和 SQL

![性能与SQL优化](https://github.com/binbinyouli27/JavaCourse00/tree/main/week16/mindMap/05-Performance&SQL.png?raw=true)

从关系性数据库的设计范式、6种结构化查询语言开始，深入MySQL的存储、执行流程、执行引擎、SQL的执行顺序、索引原理、SQL优化、事务和锁、配置等知识，这样在设计和实现数据库的才能给出更优的设计。

#### 6 分库分表

![超越分库分表](https://github.com/binbinyouli27/JavaCourse00/tree/main/week16/mindMap/06-SubDB&Table.png?raw=true)


MySQL从单机到集群、再到主从复制、读写分离和集群，不断提升高可用性。数据规模的急速膨胀导致数据容量上升、性能下降、可用性降低、运维成本提高，从而提出分库分表的方案。数据库垂直拆分、数据库水平分库、分表、分库分表等多种方式覆盖了各种场景。了解相关的框架和中间件（Apache ShardingSphere）。

#### 7 RPC 和微服务

![RPC与分布式服务化](https://github.com/binbinyouli27/JavaCourse00/tree/main/week16/mindMap/07-RPC.png?raw=true)

分布式服务化产生了服务间调用的问题，使用RPC可以像调用本地方法一样调用远程方法。设计RPC框架需要确定共享方式、代理、序列化、网络传输、查找实现类的方式。从配置/注册/元数据中心、注册与发现、集群与路由、过滤与流控等方面，深入了解分布式服务化。


#### 8 分布式缓存

![分布式缓存](https://github.com/binbinyouli27/JavaCourse00/tree/main/week16/mindMap/08-Cache.png?raw=true)

热数据和读写比较大的数据适合缓存，根据业务确定缓存的加载时机、确保有效性。缓存技术有本地缓存、远程缓存，缓存容量和过期等缓存策略。缓存穿透、击穿、雪崩等常见问题。Redis安装、性能测试、基本/高级数据结构、六大使用场景。通过Java客户端和与Spring整合应用到系统中来。

#### 9 分布式消息队列

![分布式消息](https://github.com/binbinyouli27/JavaCourse00/tree/main/week16/mindMap/09-MQ.png?raw=true)

分布式消息队列一种异步、简化各方以来、可缓冲、保障可靠性和顺序的通信方式。了解三代消息中间件ActiveMQ/RabbitMQ、Kafka/RocketMQ、Apache Pulsar。根据MQ的设计原理，手写MQ。



