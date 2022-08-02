volatile


不能保证原子性：保证原子性需要通过字节码指令monitorenter和monitorexit，volatile没有使用这两个指令。
CPU的高速缓存（三级缓存），通过使用一个lock的汇编指令，保证缓存中的数据刷回主存。
但是在多核CPU的情况下，其他核不能及时的发现更改，这样就会导致缓存一致性的问题，需要通过MESI缓存一致性协议来保证。
为了保证CPU的利用率。引入了storebuffer，但是storebuffer又会导致执行的顺序不一致，这是需要加内存屏障来保证。
volatile通过对变量的前后加内存屏障防止CPU指令重排。







































