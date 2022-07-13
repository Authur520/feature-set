RPC核心代理机制：







过期策略：
-FIFO、LRU
-设过期时间
-业务加权


缓存穿透：大量查询不存在的key
-











































## sharding jdbc

sharding jbdc 代码层面时间分库分表，以及读写分离，only Java可以使用

## sharding proxy

sharding proxy是一个数据库中间件类似于macat，它对业务代码的侵入性小，适用于mysql和postgresql，由于它是一个数据库中间件，所以会存在一定的网络资源的消耗，但是很小。

## sharding sclaing



# 分布式事务

## 强一致性：XA协议

AP、RM、TM

xa start -> xa end -> xa prepare -> xa commit/xa rollback

XA事物处理的过程:XA start 创建一个类似id的，事物会话管理器分发这个id给多个mysql server ，XA commit创建事务会话管理器提给多个mysql server，多个server返回状态给事务会话管理器，如果有一个server返回失败的状态，那个这组分布式事务就全部失败，回滚！

## 弱一致性：

###       补偿冲正

###       base柔性事物（最终一致性）

*  TCC：手动补偿处理 （Seata、hmily）
    1. 准备Try：完成所有业务检查，预留必须的业务资源。
    2. 确认Confirm：执行真正的业务，只使用Try阶段预留的业务资源，因此只要Try成功，Confirm必成功。Confirm需满足幂等性，保证分布式事务职能成功一次。
    3. 取消Cancel：取消Try阶段的业务资源，Cancel也满足幂等性。

​      问题:

             1. 允许空回滚   2. 防悬挂控制。 3. 幂等设计

* Saga

* AT：自动补偿处理

  相当于两阶段提交，自动生成反向sql

###        柔性事务的隔离级别

* 一般情况是独已提交（全局锁），读未提交（无全局锁）。

## 分布式事务框架

### Seata

支持TCC、AT

### hmily

基于TCC

# RPC技术

​	像调用本地方法一样调用远程方法

​	核心代理机制：

 	1. 本地代理存根：Stub
 	2. 本地序列化/反序列化
 	3. socket网络通信
 	4. 远程序列化/反序列化
 	5. 远程服务存根：Skeleton
 	6. 调用实际业务服务
 	7. 原路返回服务结果
 	8. 返回给本地调用方

## Dubbo











# 分布式缓存

## 缓存策略

-按FIFO---超过最大内存之后，先进先出

-LRU  ---  最近最少使用（主流）

-按固定时间过期

-按业务时间加权：如3+5X

## 缓存问题

### 	缓存穿透：大量并发查询不存在的key，穿过不存在的key，打到数据库上。

### 	解决办法：

1. 缓存一个空值的key
2. Bloom过滤或RoaringBitmap判断key是否存在
3. 完全以缓存为准，使用延迟异步加载的策略2，这样就不会出发更新。

### 	缓存击穿：某个key失效的时候，正好有大量的并发请求访问这个key。

### 	解决办法：

1. key的更新操作添加 全局互斥锁。
2. 完全以缓存为准，使用延迟异步加载的策略2，这样就不会出发更新。

### 缓存雪崩：某一时刻发生大规模的缓存失效

### 	解决办法：

1. 更新策略在时间上做到均匀
2. 热数据分散到不同的机器
3. 主从复制
4. 使用熔断限流机制，实现负载控制。

# Redis

# 基本数据结构

1. ## 字符串（string）

   二进制安全的，value最多长度是512M

   set/get/getset/del/exists/append

   ⚠️注意：

    1. append时，空间会扩容一倍
    2. 整数共享：redis内会共享使用整数，但是会影响淘汰策略LRU的使用
    3. 整数精度：整数大于16位时，redis就会丢失精确

2. ## 散列（Hash）

   hset/hget/hmget/hgetall/hdel/hincrby

   hexists/hlen/hkeys/hvals

3. ## 列表（list）

   类似Java中的LinkedList一样的字符串链表

   命令：lpush/rpush/lrange/lpop/rpop

4. ## 集合（set）

   类似Java中的set、

   基本命令：sadd/srem/smembers/sismember ～ set.add、remove、contains。

   进阶命令：sdiff/sinter/sunion ～ 集合 求差集/交集/并集

5. ## 有序集合（sortedset）又叫zset

   ![image-20220612143121628](/Users/authur/Desktop/Note/image/image-20220612143121628.png)

# 高级数据结构

1. ## bitmaps：setbit/getbit/bitop/bitcount/bitpos

   实际是string、结合**RoaringBitmap**

2. ## Hyperloglogs：pfadd/pfcount/pfmerge

   类似**Bloom过滤**

3. ## GEO：geoadd/geohash/geopos/geodist。。。

## redis到底是单线程，还是多线程？

**IO线程**

Redis6.x之前是单线程：可以看做跟mysql一样是BIO的

redis6.x之后是多线程：变为NIO

**内存处理数据**

-单线程==》高性能的核心

# Redis六大使用场景

## 业务数据缓存

1. 通用数据缓存
2. 实时热点数据
3. 会话缓存（token）

## 业务数据处理

1. 非严格一致性要求的数据
2. 业务数据去重
3. 业务数据排序

## 全局一致性计数

1. 全局流控计数
2. 秒杀的库存计算
3. 抢红包
4. 全局ID生成

## 高效统计计数

1. id去重，记录访问ip等全局bitmap操作
2. UV、PV等访问量==》非严格一致性要求

## 发布订阅与Stream

1. Pub-Sub 模拟队列

   subscribe 订阅：subscribe comments

   publish     发布：publish comments liji

2. Redis Stream 是**5.0版本**新加的数据结构。

   应用：消息队列（MQ）

## 分布式锁

1. 获取锁--单个原子性操作

   SET dlock my_random_value NX PX 3000

   **关键点：**原子性、互斥、超时

2. 释放锁--lua脚本去保证原子性+单线程，从而具有事务性

![截屏2022-06-12 23.32.01](/Users/authur/Desktop/Note/image/截屏2022-06-12 23.32.01.png)

# Redis的Java客户端

## Jedis

基于BIO，线程不安全，需要配置连接池管理连接

类似于JDBC，几乎跟redis的命令一样

## Lettuce

基于NIO，API线程安全。

主流推荐，Spring SpringBoot默认使用

## Redission

基于NIO，API线程安全。

可以做分布式锁，使用跟JUC差不多。

# Redis的高级功能

## Redis事务

开启事务--->multi

进入队列

执行事务--->exec

撤销事务--->discard

**优化：**Watch实现乐观锁

watch一个key，如果这个key在事务执行的过程中修改了，那么事务自动的失败

## Redis Lua

-类似数据库的存储过程

**直接执行**命令：1.eval 脚本 0    ---0表示是0个参数

​		  2.eval 脚本 1 key value   ----1表示1个参数，key，value表示参数

**预编译：**script load  脚本 ------返回一个SHA-1签名

evalsha +签名 +参数个数（1）+参数

## Redis管道技术

## Redis持久化

刷盘方式：RDB、AOF

### RDB--二进制方式

save前台备份、bgsave后台备份

### AOF --binlog

开启：appendonly为yes时。

刷盘频率：

#appendfsync alway    ---来一条刷新到AOF一条

#appendfsync every    ---没秒同步一次

#appendfsync no。。。

## Redis性能优化

### 内部优化

1. 内存优化：10G/20G

   hash-max-ziplist-value 64

   zset-max-ziplist-value 64

2. CPU优化

   1）耗时Lua要谨慎

   2）谨慎使用范围操作。   ---如线上使用keys *

   3）SLOWLOG get 10，查看最近的10条

## Redis分区

1. key的容量、规划

   如：a.b.c.d

2. 分区

   缓存数据变大，可以分区

# Redis集群与高可用

## Redis主从复制

启动两redis，进入6380库执行如下：

**slaveof** 127.0.0.1 6379  ---->成为ip为127.0.0.1 端口为6379的从库

## Redis Sentinel主从切换：高可用—MHA





# 消息队列

## 消息通信方式：

基于文件、基于共享内存、基于IPC、基于Socket、基于数据库、基于RPC

### —基于文件：不方便、不及时

### —Socket：使用麻烦，多数情况还不如RPC

### —数据库：不实时，但是经常有人拿数据库来模拟消息队列

### —RPC：调用关系复杂，同步处理，压力大的时候无法缓冲







# Kafka

1. 高性能
2. 高吞吐
3. 分布式顺序传输
4. 实时与离线数据处理
5. 水平扩展

### 名词概念

-Broker：

-Topic：

-Partition：每个Topic

-Producer：生产者

-Consumer：消费者

-Consumer Group：消费者组

## Kafka的使用

命令：

bin/kafka-topics.sh --zookeeper localhost:2181 --list 查看topic

创建一个三个分区一个副本的topic：bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic testk --partitions 3 --replication-factor 1











