# WEEK02

#### 1.（选做）使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1 的案例。

串行GC 显示young GC 10次， Full GC 6次，Full GC  并没有明显标识， Full GC 的平均时间 58ms
```shell
java -Xmx512m -Xms512m  -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps  com.kong.jvm.GCLogAnalysis
```
并行GC minor GC 24次， Full GC 4次， Full GC 的平均时间是 52ms,  本次 Full GC 的标志是 [Full GC (Ergonomics)], 目的是自动调节吞吐量和暂停时间，引用[Full GC (Ergonomics) 产生的原因](https://blog.csdn.net/weixin_43194122/article/details/91526740)
```shell
java -Xmx512m -Xms512m   -XX:+UseParallelGC -XX:+UseParallelOldGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps  com.kong.jvm.GCLogAnalysis
```
CMS GC, 描述了CMS GC 的五个阶段， Full GC的平均响应时间 70ms, 略大于串行和并行GC
```shell
java -Xmx512m -Xms512m   -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps  com.kong.jvm.GCLogAnalysis
```
```text
2021-07-04T11:28:51.586-0800: 0.543: [GC (CMS Initial Mark) [1 CMS-initial-mark: 212178K(349568K)] 232456K(506816K), 0.0002542 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
2021-07-04T11:28:51.587-0800: 0.543: [CMS-concurrent-mark-start]
2021-07-04T11:28:51.589-0800: 0.546: [CMS-concurrent-mark: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
2021-07-04T11:28:51.589-0800: 0.546: [CMS-concurrent-preclean-start]
2021-07-04T11:28:51.590-0800: 0.547: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
2021-07-04T11:28:51.590-0800: 0.547: [CMS-concurrent-abortable-preclean-start]
2021-07-04T11:28:51.614-0800: 0.570: [GC (Allocation Failure) 2021-07-04T11:28:51.614-0800: 0.570: [ParNew: 156752K->17471K(157248K), 0.0328021 secs] 368930K->274154K(506816K), 0.0328520 secs] [Times: user=0.18 sys=0.02, real=0.03 secs]
2021-07-04T11:28:51.669-0800: 0.625: [GC (Allocation Failure) 2021-07-04T11:28:51.669-0800: 0.625: [ParNew: 157247K->17471K(157248K), 0.0270707 secs] 413930K->316285K(506816K), 0.0271185 secs] [Times: user=0.17 sys=0.02, real=0.03 secs]
2021-07-04T11:28:51.714-0800: 0.671: [GC (Allocation Failure) 2021-07-04T11:28:51.714-0800: 0.671: [ParNew: 157247K->17471K(157248K), 0.0325859 secs] 456061K->364056K(506816K), 0.0326338 secs] [Times: user=0.19 sys=0.01, real=0.03 secs]
2021-07-04T11:28:51.747-0800: 0.703: [CMS-concurrent-abortable-preclean: 0.004/0.157 secs] [Times: user=0.61 sys=0.05, real=0.15 secs]
2021-07-04T11:28:51.747-0800: 0.704: [GC (CMS Final Remark) [YG occupancy: 20353 K (157248 K)]2021-07-04T11:28:51.747-0800: 0.704: [Rescan (parallel) , 0.0008645 secs]2021-07-04T11:28:51.748-0800: 0.705: [weak refs processing, 0.0000223 secs]2021-07-04T11:28:51.748-0800: 0.705: [class unloading, 0.0003572 secs]2021-07-04T11:28:51.748-0800: 0.705: [scrub symbol table, 0.0003539 secs]2021-07-04T11:28:51.749-0800: 0.705: [scrub string table, 0.0001247 secs][1 CMS-remark: 346584K(349568K)] 366938K(506816K), 0.0018060 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
2021-07-04T11:28:51.749-0800: 0.705: [CMS-concurrent-sweep-start]
2021-07-04T11:28:51.750-0800: 0.706: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
2021-07-04T11:28:51.750-0800: 0.707: [CMS-concurrent-reset-start]
2021-07-04T11:28:51.751-0800: 0.707: [CMS-concurrent-reset: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
2021-07-04T11:28:51.766-0800: 0.723: [GC (Allocation Failure) 2021-07-04T11:28:51.766-0800: 0.723: [ParNew: 157247K->157247K(157248K), 0.0000210 secs]2021-07-04T11:28:51.766-0800: 0.723: [CMS: 300885K->266666K(349568K), 0.0693168 secs] 458133K->266666K(506816K), [Metaspace: 2584K->2584K(1056768K)], 0.0693938 secs] [Times: user=0.07 sys=0.00, real=0.07 secs]
```
G1 
```shell
java -Xmx512m -Xms512m  -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps  com.kong.jvm.GCLogAnalysis
```
#### 2.（选做）使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。
```shell
wrk -t8 -c200 -d30s --latency http://localhost:8088
Running 30s test @ http://localhost:8088
  8 threads and 200 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     5.23ms    5.34ms 132.77ms   93.03%
    Req/Sec     5.07k   749.41    11.18k    74.13%
  Latency Distribution
     50%    4.10ms
     75%    5.82ms
     90%    9.00ms
     99%   23.54ms
  1213832 requests in 30.10s, 301.19MB read
  Socket errors: connect 0, read 80, write 0, timeout 0
  Non-2xx or 3xx responses: 1213832
Requests/sec:  40329.18
Transfer/sec:     10.01MB
```

#### 3.（选做）如果自己本地有可以运行的项目，可以按照 2 的方式进行演练。
使用java bio serviceSocket 编写, 监听12345 端口
```shell
java -classpath java-socket-1.0-SNAPSHOT.jar com.billy.socket.JavaBIOTes
```
使用 jps 查看进程号
```shell
20115 JavaBIOTest
18675 jar
20222 Jps
```
使用netstat 查看接口监听状态
```shell
Active Internet connections (servers and established)
Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name    
tcp        0      0 0.0.0.0:80              0.0.0.0:*               LISTEN      22322/nginx: master 
tcp        0      0 0.0.0.0:22              0.0.0.0:*               LISTEN      3393/sshd           
tcp        0      0 0.0.0.0:8088            0.0.0.0:*               LISTEN      18675/java          
tcp        0      0 0.0.0.0:12345           0.0.0.0:*               LISTEN      20115/java          
tcp        0      0 172.17.231.36:22        120.245.112.13:16108    ESTABLISHED 18714/sshd: root@pt 
tcp        0      0 172.17.231.36:47772     100.100.30.25:80        ESTABLISHED 4108/AliYunDun      
tcp        0      0 172.17.231.36:51154     100.100.18.120:443      TIME_WAIT   -                   
tcp        0      0 172.17.231.36:22        120.245.112.13:15901    ESTABLISHED 20077/sshd: root@pt 
tcp        0     96 172.17.231.36:22        120.245.112.13:16055    ESTABLISHED 20137/sshd: root@pt 
tcp        0      0 172.17.231.36:22        120.245.112.13:16263    ESTABLISHED 18594/sshd: root@pt 
tcp6       0      0 :::3306                 :::*                    LISTEN      15020/mysqld     
```
根据Pid 查看用户态 文件描述符 ls -al /proc/20115/fd/, 其中 数字未文件描述符，句柄， 0 表示标准输入， 1 表示标准输出 2 表示标准错误 3 加载rt 4启动文件 5
```shell
lrwx------ 1 root root 64 Jul  4 12:27 0 -> /dev/pts/2
lrwx------ 1 root root 64 Jul  4 12:27 1 -> /dev/pts/2
lrwx------ 1 root root 64 Jul  4 12:27 2 -> /dev/pts/2
lr-x------ 1 root root 64 Jul  4 12:27 3 -> /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.212.b04-0.el7_6.x86_64/jre/lib/rt.jar
lr-x------ 1 root root 64 Jul  4 12:27 4 -> /data/geekJava/nio01/java-socket-1.0-SNAPSHOT.jar
lrwx------ 1 root root 64 Jul  4 12:27 5 -> socket:[23228172]
lrwx------ 1 root root 64 Jul  4 12:27 6 -> socket:[23228918]
```

#### 4.（必做）根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 和堆内存的总结，提交到 GitHub。
 | GC 名称 | 使用参数  | 年轻代算法 |  老年代算法 |  STW阶段 |  使用场景 | 
 | :------|  :-------| :--------| :----------| :----| :--------|
 | 串行GC（Serial）|-XX:+UseSerialGC| mark-copy|mark-sweep-compact|单线程处理 标记和清除都会STW|几百MB堆内存，单核处理器非常有效|
 | 并行GC(parallel) | -XX:+UseParallelGC -XX:UseParallelOldGC| mark-copy| mark-sweep-compact| 多线程处理阶段|并行处理， 吞吐量高，但是GC期间停顿时间长， 不能最为高响应系统使用|
 | CMS | 年轻代使用 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC | mark-copy | mark-sweep| 初始标记和最终标记阶段| 多核服务器，要求低延迟环境， 单吞吐量会变差|
 | G1(Garbage-First)| -XX:+UseG1GC | Evacuation Pause: Fully Young(转移暂停)| Evacuation Pause: Mixed(转移暂停混合模式) | 初始化标记和再次标记| 内存大， 延迟低系统|


拓宽知识[GC参考手册](https://www.bookstack.cn/read/gc-handbook/spilt.1.04_GC_Algorithms_Implementations_CN.md#3yv74t)
#### 5.（选做）运行课上的例子，以及 Netty 的例子，分析相关现象。
socket连接的过程, 通过strace , lsof, netstat, nc 等命令，查看连接过程中socket 状态
>  server 端
1.  创建一个socket，并返回一个句柄 socket(AF_INET, SOCK_STREAM, IPPROTO_IP) = 6
2. 绑定ip + 端口号 到标准输出 bind(6, {sa_family=AF_INET, sin_port=htons(12345), sin_addr=inet_addr("0.0.0.0")}, 16) = 0
3. 监听 6的文件描述符 listen(6, 50)
4. 阻塞 等待连接 poll([{fd=6, events=POLLIN|POLLERR}], 1, -1 
5. 客户端创建连接后 accept(6, {sa_family=AF_INET, sin_port=htons(55182), sin_addr=inet_addr("127.0.0.1")}, [16]) = 7
6. 阻塞 接收信息 recvfrom(7,"2\n", 8192, 0, NULL, NULL) = 2

> client 端
1. 创建socket socket(AF_INET, SOCK_STREAM|SOCK_NONBLOCK, IPPROTO_TCP) = 3 
2. 建立连接 connect(3, {sa_family=AF_INET, sin_port=htons(12345), sin_addr=inet_addr("127.0.0.1")}, 16) = -1 EINPROGRESS (Operation now in progress)
3. getsockopt(3, SOL_SOCKET, SO_ERROR, [0], [4]) = 0 
4. 阻塞执行发送过程 poll([{fd=0, events=POLLIN}, {fd=3, events=0}, {fd=3, events=POLLIN}, {fd=1, events=0}], 4, -1) = 1 ([{fd=0, revents=POLLIN}])
   read(0, "q\n", 16384)                   = 2
   poll([{fd=0, events=POLLIN}, {fd=3, events=POLLOUT}, {fd=3, events=POLLIN}, {fd=1, events=0}], 4, -1) = 1 ([{fd=3, revents=POLLOUT}])
   write(3, "q\n", 2)
   >  阻塞过程是等待数据输入 poll([{fd=0, events=POLLIN}, {fd=3, events=0}, {fd=3, events=POLLIN}, {fd=1, events=0}], 4, -1


#### 6.（必做）写一段代码，使用 HttpClient 或 OkHttp 访问  http://localhost:8801 ，代码提交到 GitHub