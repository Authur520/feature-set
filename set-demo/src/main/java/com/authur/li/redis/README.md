缓存穿透：大量查询不存在的key
-

过期策略：
-FIFO、LRU
-设过期时间
-业务加权

redis脑裂：
问题：
    两个master，恢复通信之后，原先的master会降为salve，这样这段时间的数据就会丢失。
方案：
    min-slaves-to-write 3  #连接到master的最少slave数量
    min-slaves-max-lag 10  #slave连接到master的最大延迟时间
原理：
    上面的配置，要求至少3个slave节点，且数据复制和同步的延迟不能超过10秒，否则的话master就会拒绝写请求，配置了这两个参数之后，
    如果发生集群脑裂，原先的master节点接收到客户端的写入请求会拒绝，就可以减少数据同步之后的数据丢失。




















