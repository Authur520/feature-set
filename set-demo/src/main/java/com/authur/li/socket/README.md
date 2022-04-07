压测工具：SuperBenchmarker

同步、异步：通信模式
阻塞、非阻塞：线程处理模式

同步阻塞：用户请求到达内核（挂起），内核读取数据，数据拷贝到用户，用户线程被唤醒
非阻塞IO：用户请求到达内核（不挂起），轮询观察是否拿到数据，内核把数据拷贝到用户，用户线程被唤醒
多路复用IO：Reactor（迎宾）
SEDA：EDA
异步式IO：全流程非阻塞
Netty特性：
    高吞吐
    低延迟
    低开销
    零拷贝
    可扩容
    松耦合
Netty
    Channel：读写IO
    ChannelFuture：
    Event& Handler：
    Encoder&Decoder 
    ChannelPipeline：事件处理器链