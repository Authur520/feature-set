Thread.sleep：
    不会释放锁，线程对象的方法
Object#wait：
    会释放锁，是Object的方法
中断、异常处理(wait、sleep、join)：
    interrupt()，如果没有终止或者异常，interrupt不会起作用
8 happen before：
    次序规则：一个线程中，按照代码先后顺序
    锁定规则：unlock一定发生在lock之后
    Volatile规则：一个变量的写操作先于读操作
    传递规则：A先于B，B先于C，则A先于C
    线程启动规则：start代码发生在run之前
    线程中断规则：在try-catch的interrupt()方法catch了interrupt exception，interrupt一定在catch之前
    线程终结规则：
    对象终结规则：
    
