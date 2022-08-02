HashMap：

JDK1.7：
数组+链表
链表采用头插法

JDK1.8：
数组+链表+红黑树
链表采用尾插法
当链表长度大于8，数组长度大于64时转为红黑树
红黑树的时间复杂度为nlog(n),为什么不用二叉树，平衡二叉树。




问题：
1.头插法
假设有T1，T2两个线程同时对一个HashMap进行put操作，刚好，HashMap达到了扩容的条件，
这是两个线程都会去对这个HashMap进行扩容。
 
链表A-->B，假设A B扩容后，计算的index依然相同，那么他们还会存放在同一链表中
假设当T1线程进入到transfer时，先会拿到A，Entry<K,V> next = e.next拿到B，这时T1线程被挂起。
T2线程进入transfer时，先会拿到A，Entry<K,V> next = e.next拿到B，然后向下执行，将A放入index，
之后循环至B，B继续执行时，就会将B.next->A, 特别注意，新数组属于线程专属的，但AB这种Enrty是
从原数组拿到的，所以它们属于全局的，T2修改了B的next，将其指向A.
T2线程执行完之后，T1继续执行，将A放入index，循环至B, 此时B的next指向A（T2做了全局的修改），
B执行完后。循环发现B.next!=null，将其取出继续循环，即A又执行了一次，根据头插法，A的next又指向B.
 
 
当使用get获取元素时，发现A.next=B,B.next=A;形成环状，导致查询出现了死循环。











































ConcurrentHashMap



