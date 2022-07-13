深入理解Spring循环依赖

话术
主要方法：doCreateBean、populateBean、addSingleton
重点⚠️三级缓存：aop代理
流程：创建Aservice，把lomda表达式放入三级缓存，填充属性Bservice，填充属性B的时候会挨个去一级、二级、三级缓存中去找，
如果没有找到，就会创建Bservice，把B的lomda表达式放入三级缓存，给B填充属性，这时候去找一级缓存中找A，发现A在三级缓存中有，
然后会拿出三级缓存中A的lomda表达式执行生成A对象，把生成的A对象放到二级缓存中，删除三级缓存中的A对象，返回Aservice，
此处会执行三级缓存B中的lomda表达式，然后把B放到一级缓存和单例池中，删除二三级缓存，Bservice创建成功，这时候回到给A
填充属性的流程，填充B成功，同样的去执行A的初始化以及添加到单例池中，循环依赖解决。


所谓循环依赖指的是：BeanA对象的创建依赖于BeanB，BeanB对象的创建也依赖于BeanA，这就造成了死循环，如果不做处理的话势必会造成栈溢出。Spring通过提前曝光机制，利用三级缓存解决循环依赖问题。本节将记录单实例Bean的创建过程，并且仅记录两种常见的循环依赖情况：普通Bean与普通Bean之间的循环依赖，普通Bean与代理Bean之间的循环依赖。

Bean创建源码
我们先通过源码熟悉下Bean创建过程（源码仅贴出相关部分）。

IOC容器获取Bean的入口为AbstractBeanFactory类的getBean方法：

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    ......

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null, null, false);
    }

    ......
}
该方法是一个空壳方法，具体逻辑都在doGetBean方法内：

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    ......

    protected <T> T doGetBean(
            String name, @Nullable Class<T> requiredType, @Nullable Object[] args, boolean typeCheckOnly)
            throws BeansException {

        // 获取Bean名称
        String beanName = transformedBeanName(name);
        Object bean;

        // 从三级缓存中获取目标Bean实例
        Object sharedInstance = getSingleton(beanName);
        if (sharedInstance != null && args == null) {
            ......

            // 不为空，则进行后续处理并返回
            bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);
        } else {
            ......

            try {
                ......
                RootBeanDefinition mbd = getMergedLocalBeanDefinition(beanName);
                ......

                // 从三级缓存中没有获取到Bean实例，并且目标Bean是单实例Bean的话
                if (mbd.isSingleton()) {

                    // 通过getSingleton(String beanName, ObjectFactory<?> singletonFactory)方法创建Bean实例
                    sharedInstance = getSingleton(beanName, () -> {
                        try {
                            // 创建Bean实例
                            return createBean(beanName, mbd, args);
                        }
                        catch (BeansException ex) {
                            ......
                        }
                    });
                    // 后续处理，并返回
                    bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
                }
                ......
            }
            catch (BeansException ex) {
                ......
            }
            finally {
                ......
            }
        }
        ......
        return (T) bean;
    }

    ......

}
doGetBean方法中先通过getSingleton(String beanName)方法从三级缓存中获取Bean实例，如果不为空则进行后续处理；如果为空，则通过getSingleton(String beanName, ObjectFactory<?> singletonFactory)方法创建Bean实例并进行后续处理。

这两个方法都是AbstractBeanFactory父类DefaultSingletonBeanRegistry的方法，AbstractBeanFactory层级关系图如下所示：

QQ20210128-160315@2x

getSingleton(String beanName)相关源码如下所示：

public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

    ......

    @Override
    @Nullable
    public Object getSingleton(String beanName) {
        return getSingleton(beanName, true);
    }

    ......

    @Nullable
    protected Object getSingleton(String beanName, boolean allowEarlyReference) {
        // 从一级缓存中获取目标Bean实例
        Object singletonObject = this.singletonObjects.get(beanName);
        // 如果从一级缓存中没有获取到，并且该Bean处于正在创建中的状态时
        if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
            // 从二级缓存获取目标Bean实例
            singletonObject = this.earlySingletonObjects.get(beanName);
            // 如果没有获取到，并且允许提前曝光的话
            if (singletonObject == null && allowEarlyReference) {
                synchronized (this.singletonObjects) {
                    // 在锁内重新从一级缓存中往下查找
                    singletonObject = this.singletonObjects.get(beanName);
                    if (singletonObject == null) {
                        singletonObject = this.earlySingletonObjects.get(beanName);
                        if (singletonObject == null) {
                            // 从三级缓存中取出目标Bean工厂对象
                            ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                            if (singletonFactory != null) {
                                // 工厂对象不为空，则通过调用getObject方法实例化Bean实例
                                singletonObject = singletonFactory.getObject();
                                // 放到二级缓存中
                                this.earlySingletonObjects.put(beanName, singletonObject);
                                // 删除对应的三级缓存
                                this.singletonFactories.remove(beanName);
                            }
                        }
                    }
                }
            }
        }
        return singletonObject;
    }

    ......
所谓的三级缓存指的是DefaultSingletonBeanRegistry类的三个成员变量：

public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

    /** Cache of singleton objects: bean name to bean instance. */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /** Cache of singleton factories: bean name to ObjectFactory. */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

    /** Cache of early singleton objects: bean name to bean instance. */
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);

    ......

}
变量	描述
singletonObjects	一级缓存，key为Bean名称，value为Bean实例。这里的Bean实例指的是已经完全创建好的，即已经经历实例化->属性填充->初始化以及各种后置处理过程的Bean，可直接使用。
earlySingletonObjects	二级缓存，key为Bean名称，value为Bean实例。这里的Bean实例指的是仅完成实例化的Bean，还未进行属性填充等后续操作。用于提前曝光，供别的Bean引用，解决循环依赖。
singletonFactories	三级缓存，key为Bean名称，value为Bean工厂。在Bean实例化后，属性填充之前，如果允许提前曝光，Spring会把该Bean转换成Bean工厂并加入到三级缓存。在需要引用提前曝光对象时再通过工厂对象的getObject()方法获取。
如果通过三级缓存的查找都没有找到目标Bean实例，则通过getSingleton(String beanName, ObjectFactory<?> singletonFactory)方法创建：

public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

    ......

    public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
        synchronized (this.singletonObjects) {
            // 从一级缓存获取
            Object singletonObject = this.singletonObjects.get(beanName);
            if (singletonObject == null) {
                // 为空则继续
                ......
                // 方法内会将当前Bean名称添加到正在创建Bean的集合（singletonsCurrentlyInCreation）中
                beforeSingletonCreation(beanName);
                boolean newSingleton = false;
                ......
                try {
                    // 通过函数式接口创建Bean实例，该实例已经经历实例化->属性填充->初始化以及各种后置处理过程，可直接使用
                    singletonObject = singletonFactory.getObject();
                    newSingleton = true;
                }
                catch (IllegalStateException ex) {
                   ......
                }
                finally {
                    ......
                }
                if (newSingleton) {
                    // 添加到缓存中
                    addSingleton(beanName, singletonObject);
                }
            }
            return singletonObject;
        }
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            // 添加到一级缓存
            this.singletonObjects.put(beanName, singletonObject);
            // 删除对应的二三级缓存
            this.singletonFactories.remove(beanName);
            this.earlySingletonObjects.remove(beanName);
            this.registeredSingletons.add(beanName);
        }
    }

    ......

}
上述代码重点关注singletonFactory.getObject()，singletonFactory是一个函数式接口，对应AbstractBeanFactory的doGetBean方法中的lambda表达式：

 sharedInstance = getSingleton(beanName, () -> {
    try {
        // 创建Bean实例
        return createBean(beanName, mbd, args);
    }
    catch (BeansException ex) {
        ......
    }
});
重点关注createBean方法。该方法为抽象方法，由AbstractBeanFactory子类AbstractAutowireCapableBeanFactory实现：

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
        implements AutowireCapableBeanFactory {

    ......
    @Override
    protected Object createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
            throws BeanCreationException {

        ......

        try {
            // 创建Bean实例
            Object beanInstance = doCreateBean(beanName, mbdToUse, args);
            return beanInstance;
        }
        catch (BeanCreationException | ImplicitlyAppearedSingletonException ex) {
            ......
        }
    }
    ......
}
doCreateBean源码：

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
        implements AutowireCapableBeanFactory {

    ......
    protected Object doCreateBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
            throws BeanCreationException {

        BeanWrapper instanceWrapper = null;
        
        ......
        // 实例化Bean
        if (instanceWrapper == null) {
            instanceWrapper = createBeanInstance(beanName, mbd, args);
        }
        Object bean = instanceWrapper.getWrappedInstance();
        
        ......

        synchronized (mbd.postProcessingLock) {
            if (!mbd.postProcessed) {
                try {
                    // 执行MergedBeanDefinitionPostProcessor类型后置处理器
                    applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
                }
                catch (Throwable ex) {
                    ......
                }
            }
        }

        // 如果该Bean是单例，并且allowCircularReferences属性为true（标识允许循环依赖的出现）以及该Bean正在创建中
        // 的话，earlySingletonExposure就为true，标识允许单实例Bean提前暴露原始对象引用（仅实例化）
        boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences &&
                isSingletonCurrentlyInCreation(beanName));
        if (earlySingletonExposure) {
            // 添加到单实例工厂集合中，即三级缓存对象，该方法第二个参数类型为ObjectFactory<?> singletonFactory，
            // 前面提到过，它是一个函数式接口，这里用lambda表达式() -> getEarlyBeanReference(beanName, mbd, bean)表示
            addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
        }

        Object exposedObject = bean;
        try {
            // 属性赋值操作
            populateBean(beanName, mbd, instanceWrapper);
            // 初始化Bean（初始化操作主要包括xxxxAware注入，BeanPostProcessor后置处理器方法调用以
            // 及InitializingBean接口方法调用，感兴趣的可以自己查看源码）
            exposedObject = initializeBean(beanName, exposedObject, mbd);
        }
        catch (Throwable ex) {
            ......
        }

        // 如果earlySingletonExposure为true
        if (earlySingletonExposure) {
            // 第二个参数为false表示仅从一级和二级缓存中获取Bean实例
            Object earlySingletonReference = getSingleton(beanName, false);
            if (earlySingletonReference != null) {
                if (exposedObject == bean) {
                    // 如果从一级和二级缓存中获取Bean实例不为空，并且exposedObject == bean的话，
                    // 将earlySingletonReference赋值给exposedObject返回
                    exposedObject = earlySingletonReference;
                }
                ......
            }
        }

        ......
        // 返回最终Bean实例
        return exposedObject;
    }
    ......

    protected Object getEarlyBeanReference(String beanName, RootBeanDefinition mbd, Object bean) {
        Object exposedObject = bean;
        if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
            // SmartInstantiationAwareBeanPostProcessor类型后置处理，常见的场景为AOP代理
            for (SmartInstantiationAwareBeanPostProcessor bp : getBeanPostProcessorCache().smartInstantiationAware) {
                exposedObject = bp.getEarlyBeanReference(exposedObject, beanName);
            }
        }
        return exposedObject;
    }
}
addSingletonFactory方法为父类DefaultSingletonBeanRegistry的方法：

public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

    ......
    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        synchronized (this.singletonObjects) {
            // 一级缓存没有目标Bean实例的话，添加三级缓存
            if (!this.singletonObjects.containsKey(beanName)) {
                this.singletonFactories.put(beanName, singletonFactory);
                this.earlySingletonObjects.remove(beanName);
                this.registeredSingletons.add(beanName);
            }
        }
    }
    ......
}
上述整个过程可以用下图来总结（可右键选择新标签页中打开图片）：

getBean.svg

光看源码有点抽象，下面我们通过两个场景来加深理解。

普通Bean与普通Bean
首先模拟普通Spring Bean与普通Spring Bean之间循环依赖的场景。

新建SpringBoot项目，pom引入如下依赖：

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
</dependencies>
新建CircularReferenceTest类：

public class CircularReferenceTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanA.class, BeanB.class);
        BeanA beanA = context.getBean(BeanA.class);
        BeanB beanB = context.getBean(BeanB.class);
        BeanB beanBInBeanA = beanA.getBeanB();
        BeanA beanAInBeanB = beanB.getBeanA();
        System.out.println(beanA);
        System.out.println(beanB);
        System.out.println(beanB == beanBInBeanA);
        System.out.println(beanA == beanAInBeanB);
    }
}

class BeanA {

    @Autowired
    private BeanB beanB;

    public BeanB getBeanB() {
        return beanB;
    }

    public void setBeanB(BeanB beanB) {
        this.beanB = beanB;
    }
}

class BeanB {

    @Autowired
    private BeanA beanA;

    public BeanA getBeanA() {
        return beanA;
    }

    public void setBeanA(BeanA beanA) {
        this.beanA = beanA;
    }
}
上面代码通过AnnotationConfigApplicationContext创建了IOC容器，并先后注册了BeanA和BeanB，BeanA和BeanB相互依赖，程序输出如下：

cc.mrbird.BeanA@368f2016
cc.mrbird.BeanB@6f03482
true
true
可以看到，Spring成功解决了循环依赖。下面配合源码来分析这个过程。

上面程序中，先创建BeanA，Spring内部调用doGetBean方法获取BeanA。一开始三级缓存中肯定没有BeanA和BeanB相关实例：

2021年01月29日09-54-54

QQ20210129-095639@2x

QQ20210129-095708@2x

所以我们直接看doCreateBean相关源码：

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
        implements AutowireCapableBeanFactory {

    ......
    protected Object doCreateBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
            throws BeanCreationException {

        BeanWrapper instanceWrapper = null;
        
        ......
        // 实例化BeanA，BeanA的早期对象，属性还未赋值，还未进行后置处理
        if (instanceWrapper == null) {
            instanceWrapper = createBeanInstance(beanName, mbd, args);
        }
        Object bean = instanceWrapper.getWrappedInstance();
        
        ......

        // BeanA是单例对象，并且allowCircularReferences为true，BeanA正在创建中，所以
        // 最终earlySingletonExposure为true
        boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences &&
                isSingletonCurrentlyInCreation(beanName));
        if (earlySingletonExposure) {
            // 将BeanA早期对象传递给Bean工厂，并添加到三级缓存中
            addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
        }

        Object exposedObject = bean;
        try {
            // 属性赋值操作
            populateBean(beanName, mbd, instanceWrapper);
            ......
        }
        ......
    }
    ......
}
上面代码，Spring实例化了BeanA，然后往三级缓存中添加了BeanA的工厂对象，根据前面getEarlyBeanReference方法的源码我们可以知道，在不存在AOP代理的情况下，该方法直接返回原始BeanA对象。所以通过该工厂方法创建的BeanA对象仅仅是进行了实例化操作，属性还未被赋值，换句话说，该工厂用于提前曝光BeanA实例。

接着调用populateBean方法对BeanA属性赋值，赋值过程发现BeanA依赖于BeanB，所以Spring重复以上步骤创建BeanB。创建过程中同样会遇到populateBean方法对BeanB属性赋值，赋值过程中发现BeanB依赖于BeanA，于是Spring又回头创建BeanA，不过这时候情况就开始不一样了！！

doGetBean方法内部从三级缓存中获取BeanA对象时，三级缓存内容如下：

2021年01月29日15-08-41

QQ20210129-150926@2x

2021年01月29日15-10-15

可以看到一级缓存和二级缓存没有什么不一样，但三级缓存中已经存在BeanA和BeanB的工厂对象了！

所以此时getSingleton(String beanName, boolean allowEarlyReference)方法内的逻辑如下：

public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

    ......

    @Nullable
    protected Object getSingleton(String beanName, boolean allowEarlyReference) {
        Object singletonObject = this.singletonObjects.get(beanName);
        // 一级缓存中没有BeanA，并且BeanA正在创建中
        if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
            singletonObject = this.earlySingletonObjects.get(beanName);
            // 二级缓存中也没有BeanA
            if (singletonObject == null && allowEarlyReference) {
                synchronized (this.singletonObjects) {
                    // 在锁内重新从一级缓存中往下查找
                    singletonObject = this.singletonObjects.get(beanName);
                    if (singletonObject == null) {
                        singletonObject = this.earlySingletonObjects.get(beanName);
                        if (singletonObject == null) {
                            // 从三级缓存中取出目标BeanA的工厂对象
                            ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                            if (singletonFactory != null) {
                                // 工厂对象不为空，调用getObject方法获取前面提前曝光的BeanA早期实例
                                singletonObject = singletonFactory.getObject();
                                // 将BeanA早期实例放到二级缓存中
                                this.earlySingletonObjects.put(beanName, singletonObject);
                                // 删除对应的三级缓存
                                this.singletonFactories.remove(beanName);
                            }
                        }
                    }
                }
            }
        }
        // 返回BeanA早期实例
        return singletonObject;
    }

    ......
此时查看二级缓存：

2021年01月29日15-15-49

可以看到，BeanA确实只是早期实例，属性BeanB还未被赋值呢。

随后BeanB在属性填充的时候获取到了BeanA早期实例，完成属性填充、初始化等后续操作，BeanB创建完毕。BeanB完整创建完毕后，BeanA随之也完成属性填充、初始化等后续操作，BeanA也创建完毕，循环依赖得以解决。

BeanB虽然获取到的是BeanA的早期对象，但当BeanA完整创建完毕后，BeanB里的BeanA也将会是完整的，因为指针指向的都是同一个BeanA地址。

画个图总结上面的过程（可右键选择新标签页中打开图片）：

20210129155555.svg

普通Bean与代理Bean
普通Bean和代理Bean之间的循环依赖和上面过程差不多，不过细节上有些许差异。

删除上面创建的CircularReferenceTest类。为了模拟AOP代理的情况，我们需要引入AOP依赖：

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
然后修改Boot入口类：

@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MyApplication.class, args);
        BeanA beanA = context.getBean(BeanA.class);
        BeanB beanB = context.getBean(BeanB.class);
        BeanB beanBInBeanA = beanA.getBeanB();
        BeanA beanAInBeanB = beanB.getBeanA();
        System.out.println("BeanA是否为代理对象：" + AopUtils.isAopProxy(beanA));
        System.out.println("BeanB是否为代理对象：" + AopUtils.isAopProxy(beanB));
        System.out.println("beanAInBeanB是否为代理对象：" + AopUtils.isAopProxy(beanAInBeanB));
        System.out.println(beanB == beanBInBeanA);
        System.out.println(beanA == beanAInBeanB);
    }
}

@Component
class BeanA {

    @Autowired
    private BeanB beanB;

    public BeanB getBeanB() {
        return beanB;
    }

    public void setBeanB(BeanB beanB) {
        this.beanB = beanB;
    }
}

@Component
class BeanB {

    @Autowired
    private BeanA beanA;

    public BeanA getBeanA() {
        return beanA;
    }

    public void setBeanA(BeanA beanA) {
        this.beanA = beanA;
    }
}

@Aspect
@Component
class MyAspect {

    @Pointcut("execution(public * cc.mrbird.BeanA.getBeanB())")
    public void pointcut() {

    }
    @Before("pointcut()")
    public void onBefore(JoinPoint joinPoint) {
        System.out.println("onBefore：" + joinPoint.getSignature().getName() + "方法开始执行");
    }

}
因为MyAspect切面类的存在，BeanA将会是个代理类，而BeanB则是普通Bean，程序输出如下：

onBefore：getBeanB方法开始执行
BeanA是否为代理对象：true
BeanB是否为代理对象：false
beanAInBeanB是否为代理对象：true
true
true
假设容器先创建BeanA，过程和上面的例子一致，属性填充时，发现BeanA依赖BeanB，然后Spring开始创建BeanB。创建BeanB时候又发现其依赖BeanA，这时三级缓存中已经存在BeanA的工厂对象了，所以直接通过该工厂对象获取BeanA的早期实例：

public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

    ......

    @Nullable
    protected Object getSingleton(String beanName, boolean allowEarlyReference) {
        Object singletonObject = this.singletonObjects.get(beanName);
        // 一级缓存中没有BeanA，并且BeanA正在创建中
        if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
            singletonObject = this.earlySingletonObjects.get(beanName);
            // 二级缓存中也没有BeanA
            if (singletonObject == null && allowEarlyReference) {
                synchronized (this.singletonObjects) {
                    // 在锁内重新从一级缓存中往下查找
                    singletonObject = this.singletonObjects.get(beanName);
                    if (singletonObject == null) {
                        singletonObject = this.earlySingletonObjects.get(beanName);
                        if (singletonObject == null) {
                            // 从三级缓存中取出目标BeanA的工厂对象
                            ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                            if (singletonFactory != null) {
                                // 工厂对象不为空，调用getObject方法获取前面提前曝光的BeanA早期实例
                                singletonObject = singletonFactory.getObject();
                                // 将BeanA早期实例放到二级缓存中
                                this.earlySingletonObjects.put(beanName, singletonObject);
                                // 删除对应的三级缓存
                                this.singletonFactories.remove(beanName);
                            }
                        }
                    }
                }
            }
        }
        // 返回BeanA早期实例
        return singletonObject;
    }

    ......
singletonFactory.getObject()实际实现为lambda表达式() -> getEarlyBeanReference(beanName, mbd, bean)，getEarlyBeanReference方法源码：

protected Object getEarlyBeanReference(String beanName, RootBeanDefinition mbd, Object bean) {
    Object exposedObject = bean;
    if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
        for (SmartInstantiationAwareBeanPostProcessor bp : getBeanPostProcessorCache().smartInstantiationAware) {
            exposedObject = bp.getEarlyBeanReference(exposedObject, beanName);
        }
    }
    return exposedObject;
}
在引入AOP依赖后，容器中将会有一个SmartInstantiationAwareBeanPostProcessor接口的实现类AbstractAutoProxyCreator，用于创建AOP代理，所以上面getEarlyBeanReference方法里的bp.getEarlyBeanReference(exposedObject, beanName)逻辑实际上为AbstractAutoProxyCreator实现的getEarlyBeanReference方法：

public abstract class AbstractAutoProxyCreator extends ProxyProcessorSupport
        implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware {

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) {
        // 生成缓存Key
        Object cacheKey = getCacheKey(bean.getClass(), beanName);
        // 放入earlyProxyReferences集合中，标识BeanA为早期代理对象
        this.earlyProxyReferences.put(cacheKey, bean);
        // 在这个例子中，BeanA将被包装为代理对象
        return wrapIfNecessary(bean, beanName, cacheKey);
    }

}
所以BeanB从三级缓存中获取到的为代理后的BeanA实例：

2021年01月31日09-31-16

BeanB创建完毕后，BeanA属性填充操作随之结束。

通过深入理解Spring-AOP原理对AOP的学习我们知道，代理对象是在后置处理BeanPostProcessor的postProcessAfterInitialization方法内完成的，而该方法的调用时机为Bean属性填充后的初始化操作时，所以在BeanA属性填充操作结束时，BeanA还只是一个普通对象，而BeanB里的BeanA已经是代理对象了。

继续BeanA的创建过程，BeanA属性填充完后，执行initializeBean(beanName, exposedObject, mbd)方法进行初始化操作：

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
        implements AutowireCapableBeanFactory {

    ......
    protected Object doCreateBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
            throws BeanCreationException {

        ......
        Object exposedObject = bean;
        try {
            // 属性赋值操作
            populateBean(beanName, mbd, instanceWrapper);
            // 初始化操作
            exposedObject = initializeBean(beanName, exposedObject, mbd);
        }
        ......
    }
    ......
}
我们主要关注初始化操作阶段执行动态代理的后置处理方法过程：

public abstract class AbstractAutoProxyCreator extends ProxyProcessorSupport
        implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware {

    ......

    @Override
    public Object postProcessAfterInitialization(@Nullable Object bean, String beanName) {
        if (bean != null) {
            Object cacheKey = getCacheKey(bean.getClass(), beanName);
            // 在BeanB填充属性时，BeanA已经被放入到earlyProxyReferences集合中了
            // 所以该if不成立，直接跳过，避免二次代理
            if (this.earlyProxyReferences.remove(cacheKey) != bean) {
                return wrapIfNecessary(bean, beanName, cacheKey);
            }
        }
        // 所以这里返回的还是BeanA原始对象，并非代理对象
        return bean;
    }
    ......
到这里BeanA依旧是普通对象，继续查看doCreateBean方法的后续逻辑：

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
        implements AutowireCapableBeanFactory {

    ......
    protected Object doCreateBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
            throws BeanCreationException {

        ......
        // 原始BeanA赋值给exposedObject
        Object exposedObject = bean;
        try {
            // 属性赋值操作
            populateBean(beanName, mbd, instanceWrapper);
            // 初始化操作，通过上面分析，此时返回的还是原始的BeanA对象
            exposedObject = initializeBean(beanName, exposedObject, mbd);
        }
        ......
        if (earlySingletonExposure) {
            // 从缓存中获取BeanA，此时二级缓存中已经存在BeanA的代理对象了，所以
            // 这里earlySingletonReference为BeanA的代理对象（如下图）
            Object earlySingletonReference = getSingleton(beanName, false);
            if (earlySingletonReference != null) {
                // exposedObject和bean相等，因为BeanA并未在初始化的时候被二次代理
                if (exposedObject == bean) {
                    // 这里将代理对象BeanA赋值给exposedObject
                    exposedObject = earlySingletonReference;
                }
                ......
            }
        }
        ......
        // 最终返回的exposedObject对象为从二级缓存中获取到的BeanA代理对象
        return exposedObject;
    }
    ......
}
QQ20210131-094307@2x

到这里，无论是BeanB里的BeanA，还是IOC容器中的BeanA，都是代理后的BeanA了。

画张图总结下上面的过程（可右键选择新标签页中打开图片）：

20210131100707.svg

总结
上面的例子都是基于属性注入的情况，假如存在构造器注入情况下的循环依赖，Spring将没办法解决。这是因为对象的提前曝光时机发生在对象实例化之后，而构造器注入时机为对象实例化时，所以此时还未进行提前曝光操作，循环依赖也就没办法解决了，比如下面这种情况：

@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}

@Component
class BeanA {

    private BeanB beanB;

    public BeanA(BeanB beanB) {
        this.beanB = beanB;
    }

    public BeanB getBeanB() {
        return beanB;
    }

    public void setBeanB(BeanB beanB) {
        this.beanB = beanB;
    }
}

@Component
class BeanB {

    private BeanA beanA;

    public BeanB(BeanA beanA) {
        this.beanA = beanA;
    }

    public BeanA getBeanA() {
        return beanA;
    }

    public void setBeanA(BeanA beanA) {
        this.beanA = beanA;
    }
}
程序将抛出如下异常：

***************************
APPLICATION FAILED TO START
***************************

Description:

The dependencies of some of the beans in the application context form a cycle:

┌─────┐
|  beanA defined in file [/Users/mrbird/idea workspace/aop-deep-learn/target/classes/cc/mrbird/BeanA.class]
↑     ↓
|  beanB defined in file [/Users/mrbird/idea workspace/aop-deep-learn/target/classes/cc/mrbird/BeanB.class]
└─────┘
此外，这里讨论了普通Bean与普通Bean之间的循环依赖，代理Bean与普通Bean之间的循环依赖，实际情况还可能存在工厂Bean与普通Bean、代理Bean之间的循环依赖，这种情况比较复杂，本文不讨论，因为就理解Spring解决循环依赖的思想而言，上面两种情况搞清楚了就OK了。