jvm参数
jvm命令
    jps
    java的进程，可以用来查看java进程的pid
    jstat 动态的
    jstat -gc -t pid 1000 1000 / jstat -gcutil -t pid 1000 1000
    jmap：静态的，打印当前堆内存整体的情况
    jmap -heap pid
    jmap -histo pid 可以查看哪些类占用的空间最多
    jmap -dump:format=b,file=pid.hprof pid
    jstack
    jstack -l pid
    jstack -F pid
    jstack -l -F pid

jvm图形化工具：jconsole、jvisualvm、VisualGC、jmc

GC
    年轻代：标记-复制算法
    老年代：标记清除整理算法
串行GC
    不能并行处理，会触发全线暂停（STW）
并行GC：jdk678默认
    ParallelGC执行的时候会使用全部的线程，吞吐量高，但是也会造成短暂的STW
CMS GC
    -XX:+UseConcMarkSweepGC 年轻代采用并行STW的标记-复制算法，老年代采用并发的标记-清除算法。
    方法：1.老年代不整理，使用空闲列表（free-lists），类似索引的东西，记录空闲的空间
         2.标记-清除算法可以和应用线程一起并行执行
    默认CMS并行的线程数是CPU的1/4
    六个阶段
        1初始标记
        2并发标记
        3并发预清理：卡片标记
        4最终标记：标记卡片标记的脏区
        5并发清除
        6并发重置
    缺点：老年代中没有整理，
G1：Garbage First 垃圾优先
    jdk9默认
    -XX:+UseG1GC开启G1 -XX:MaxGCPauseMillis=50(STW时间50毫秒)，默认200
    把堆内存划分成多个Rigion（2048个小块），这块区域可以动态的被指定成各个代 ，会分析每个Rigion占的垃圾多，优先垃圾回收
    GC退化：
        1老年代快速的满
        2full GC次数增多
        3大对象在Region中放不下
ZGC：Java11 100G以上的内存建议用
    缺点：吞吐量少了
Shenandoah GC





    


