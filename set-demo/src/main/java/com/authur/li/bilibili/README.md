# 四大函数接口

函数式接口：传入一个参数，返回一个参数。

断定型接口：传入一个参数，返回boolean。

供给型接口：没有输入参数，返回值。

消费型接口：传入一个参数，无返回值。

# kafka

两种模式：消息队列、发布订阅模式

消息队列：缓存削峰、解藕、异步通信

## Kafka Producer

生产者：main线程--》拦截器--〉序列化器--》分区器

​				sender线程发送到consumer

![截屏2022-06-22 22.50.45](/Users/authur/Desktop/Note/image/:Users:authur:Desktop:截屏2022-06-22 22.50.45.png)双端队列

**注意**：

生产者应答机制：acks

重试机制retries默认是int的最大值

同步发送：send(...).get();

自定义分区器：通过properties.put();关联生产者

### 高吞吐量

1. Buffer_Memory_Config缓冲区大小
2. batch.size累积到一定大小后发送（默认16k）

3. linger.ms：如果迟迟没有达到batch.size，可以linger.ms设置时间（默认是0ms）
4. 压缩

### 可靠性

应答acks

ack=-1可能会导致数据重复（幂等性）

### 幂等性

开启幂等性配置，只能保证一个会话的幂等性，重启之后PID会变，所以终极还得使用事务。

### 事务

原理：使用一个唯一的事务id

**注意**⚠️开启事务必须开启幂等性，因为事务的底层还得使用幂等性。

使用：初始化、开启、提交、回滚

## Kafka Broker

Kafka Broker ：每个broker中都有Controller，谁先注册谁就是leader，Controller(leader)监听brokers/ids、从而判断brokers中leader的变化，   选举isr中存活的foller成为leader（规则：顺序）

分区副本分配：默认基本负载均衡，可以手动调整存储位置

**Leader Partition自动平衡**：配置auto.leader.rebalance.enable=true，默认是true

什么时候自动平衡?Leader.imbalance.per.broker.percentage，默认是超过10%的时候，控制器会触发leader平衡

检测是否超过10%?leader.imbalance.checkinterval.seconds，默认是300秒检查leader时候平衡。

注意⚠️生产环境尽量不要频繁触发再平衡，因为再平衡耗性能

**Kafka文件存储**：默认存储7天

单位：topic—〉partition—〉log—〉segment

存储：log和索引(index)，index为稀疏索引，log文件存入4kb的时候会往index里写一条索引。默认4kb

存的是相对offset

**文件清除策略**：配置log.retention.hours，默认是7天，默认5分钟检查一次，log是否达到七天。——均可修改

**清除策略**：配置：log.cleanup.policy=delete、默认是delete

1. 删除delete

   1〉时间到就删除：以segment中最大的时间戳为准

   2〉容量大小：默认关闭，超过log，删除最早的！

2. 压缩compact

   规则：相同的key，保留最后一个版本。

**kafka的高吞吐原因**：

1. 分区技术、生产者分批发送

2. 读数据采用稀疏索引，快速定位

3. 顺序写入数据：省去了磁头寻址的时间

4. 页缓存+零拷贝技术

   不经过网络层不压缩，直接发送给网卡


## Kafka Consumer

Consumer是否挂掉：

1. 心跳检测45秒后
2. 处理任务5分钟以上

### 分区分配策略

1. range

​	分区数除以consumer余数

2. 分区分配策略RoundRobib

   **数据倾斜**⚠️：多个topic都会多给第一个consumer多分一个分区

3. 粘性分区Sticky：均匀随机分配

## offset

offset存在在内置的_consumer_offsets的主题中

**自动提交offset**

配置：enable.auto.commit：自动提交offset功能，默认是true

​		  auto.commit.interval.ms：自动提交offset的时间间隔，默认是5s

**手动提交offset**

kafkaConsumer.

同步提交（commitSync()）

异步提交（commitAsync()）

### 指定Offset消费**

**指定从offset开始消费**

上代码：获取分区集合：Set<TopicPartition> assignment kafkaConsumer.assignment();

​			指定消费的offset：遍历assignment，确定分区，然后指定从哪个offset开始消费

​			kafkaConsumer.seek(分区(TopicPartition)，位置)

![截屏2022-07-01 00.11.56](/Users/authur/Desktop/Note/image/:Users:authur:Desktop:Note:image:Users:authur:Desktop:Note:image:Users:authur:Desktop:截屏2022-07-01 00.11.56.png)

注意⚠️：需要保证已经分区分配（while）

配置：auto.offset.reset：earliest/latest/none.  默认是latest

1. earlest：从头消费
2. latest：从尾消费
3. none：抛异常





1.顺序写入数据、2.批量发送、3.零拷贝

FileChannel.transferTo方式

日志策略：

1. 日志保留策略

   时间和大小

2. 日志压缩策略

   相同key合并

# Spring循环依赖

主要方法：doCreateBean、populateBean、addSingleton

重点⚠️三级缓存：aop代理

流程：创建Aservice，把lomda表达式放入三级缓存，填充属性Bservice，填充属性B的时候会挨个去一级、二级、三级缓存中去找，如果没有找到，就会创建Bservice，把B的lomda表达式放入三级缓存，给B填充属性，这时候去找一级缓存中找A，发现A在三级缓存中有，然后会拿出三级缓存中A的lomda表达式执行生成A对象，把生成的A对象放到二级缓存中，删除三级缓存中的A对象，返回Aservice，此处会执行三级缓存B中的lomda表达式，然后把B放到一级缓存和单例池中，删除二三级缓存，Bservice创建成功，这时候回到给A填充属性的流程，填充B成功，同样的去执行A的初始化以及添加到单例池中，循环依赖解决。

