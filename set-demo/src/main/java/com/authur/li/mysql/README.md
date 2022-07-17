mysql client
mysql server
连接器—分析器—优化器—执行器
存储引擎（innodb、myisam、内存数据库memory）
innodb/myisam使用b树（本质是b+树），memory使用的是hash
二叉树：只有左右子节点

磁盘预读：页（4k或者8k）

b+树：只有叶子节点存数据，根节点只存key（key默认选着主键id、唯一键、6字节的rowid(不可见)）













    



































表锁：selec方式上锁：
	共享锁：select * from table lock in share mode
	排他锁：select * from table for update
	




隔离级别
    读未提交
    读已提交
    不可重复读
    串行化
    
undo log：撤销日志

redo log：重做日志

MVCC

show create table t_dept;

show engine innodb status;

关闭自动事物：set autocommit = 0;

查慢查询日志：show variables like 'slow_query_log';
查慢查询时间：show variables like 'long_query_time';

排序后加函数：order by if()


Index类型：
    hash
    B树
    B+树

高可用：
   MHA
   MGR
   MySQL Cluster
   Orchestrator












漏数据：

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   







































