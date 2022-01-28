```text
5.bean的加载
入口AbstractBeanFactory.getBean(String)
    1.转换对应beanName(因为有可能传入的是alias和FactoryBean)
    AbstractBeanFactory.transformedBeanName(beanName)
        (1)去除FactoryBean的修饰符, 也就是如果name=&aa,那么首先会去除&而使name="aa".
        (2)取指定alias所表示的最终beanName, 例如别名A指向名称为B的bean则返回B;
        若别名A指向别名B, 别名B又指向名称为C的bean则返回C.
    2.尝试从缓存中加载单例
    单例在Spring的同一个容器内只会被创建一次, 后续再获取bean, 就直接从单例缓存中获取了
    当然这里只是尝试加载, 首先尝试从缓存中加载, 如果加载不成功则再次尝试从singletonFactories
    中加载, 因为在单例bean的时候会存在依赖注入的情况, 而在创建依赖的时候为了避免循环依赖
    在Spring中创建bean的原则是不等bean创建完成就会将创建bean的ObjectFactory提前曝光
    加载到缓存中, 一旦下一个bean创建的时候需要依赖上一个bean则直接使用ObjectFactory
    3.bean的实例化
    如果从缓存中得到了bean的原始状态, 则需要对bean进行实例化, 缓存中记录的只是最原始的bean状态
    并不一定是我们最终想要的bean, 举个例子:假如我们需要对工厂bean进行处理, 那么这里得到的
    其实是工厂bean的初始状态, 但是我们真正需要的是工厂bean中定义的factory-method方法中返回的bean
    而getObjectForBeanInstance就是完成这个工作的
    4.只有在单例情况下才会尝试解决循环依赖, 如果存在A中有B的属性, B中有A的属性
    那么当依赖注入的时候, 就会产生当A还未创建完的时候因为对于B的创建再次返回创建A
    造成循环依赖, 也就是isPrototypeCurrentlyInCreation(beanName)判断true
    5.检测parentBeanFactory
    如果缓存没有数据的话直接转到父类工厂上去加载了,
    parentBeanFactory != null && !containsBeanDefinition(beanName)
    如果父类的parentBeanFactory != null, 检测如果当前加载的XML配置文件中不包含
    beanName所对应的配置, 就只能到parentBeanFactory去尝试下了, 然后再去递归的调用getBean方法
    6.将存储XML配置文件的GenericBeanDefinition转换为RootBeanDefinition
    因为从XML配置文件中读取到的bean信息是存储在GenericBeanDefinition中的, 但是所有的
    bean后续处理都是针对RootBeanDefinition的, 所以这里需要进行一个转换
    7.寻找依赖
    因为bean的初始化过程中很可能会用到某些属性, 而某些属性很可能是动态配置的,
    并且配置成依赖于其他的bean, 那么这个时候有必要先加载依赖的bean, 在Spring的
    加载顺序中, 在初始化某一个bean的时候首先会初始化这个bean所对应的依赖
    8.针对不同的scope进行bean的创建
    在Spring中存在着不同的scope, 其中默认的是singleton, 但是还有诸如prototype,
    request等, 在这个步骤中, Spring根据不同的配置进行不同的初始化策略
    9.类型转换
    通常对该方法的调用参数requiredType是为空的，
    但是可能会存在这样的情况, 返回的bean其实是个String, 但是requiredType却传入Integer
    类型, 那么这时候本步骤就会起作用了, 它的功能是将返回的bean转换为requiredType所指定
    的类型. 当然, String换为Integer是最简单的一种转换, 在Spring中提供了各种各样的转换
    器, 也可以自己扩展转换器来满足需求

5.1 FactoryBean的使用
一般情况下, Spring通过反射机制利用bean的class属性指定实现类来实例化bean,
在某些情况下, 实例化bean过程比较复杂, 如果按照传统的方式, 需要在<bean>中提供大量
的配置信息, 配置方式的灵活性是受限的, 这时候Spring为此提供了一个
org.springframework.beans.factory.FactoryBean(3.0开始支持泛型)
的工厂类接口, 可以通过实现该接口定制实例化bean的逻辑

cn.locusc.chapter5.code.test.Chapter5Test.fbTest
当调用getBean("car")时, Spring通过反射机制发现CarFactoryBean的接口
这是Spring的容器就调用方法CarFactoryBean#getObject()方法返回, 如果
希望获取CarFactoryBean的实例, 则需要再使用getBean(beanName)前显示的加上
"&"前缀, 例如getBean("&car")


5.2缓存中获取单例的bean
单例在Spring的同一个容器内只会被创建一次, 后续再获取bean直接从单例缓存中获取,
这里只是尝试加载, 首先尝试从缓存中加载, 然后再次尝试从singletonFactories中加载
因为在创建单例bean的时候会存在依赖注入的情况, 而在创建依赖的时候为了避免循环依赖,
Spring创建bean的原则是不等bean创建完成就会将创建bean的ObjectFactory提前曝光
加入到缓存中, 一旦下一个bean创建时需要依赖上一个bean, 则直接使用ObjectFactory
DefaultSingletonBeanRegistry.getSingleton(java.lang.String, boolean)


5.3从bean的实例中获取对象
在getBean方法中, getObjectForBeanInstance是个高频率使用的方法, 
无论是从缓存中获得bean还是根据不同的scope策略加载bean. 总之, 得到
bean的实例后要做的第一步就是调用这个方法来检测一下正确性, 其实就是用于
检测当前的bean是否是FactoryBean类型的bean, 如果是, 那么需要调用该bean
对应的FactoryBean实例中的getObject()作为返回值

无论是从缓存中获取到的bean还是通过不同的scope策略加载的bean都只是最原始的
bean, 并不一定是我们最终想要的bean
举个例子, 假如我们需要对工厂bean进行处理, 那么这里得到的其实是工厂bean的初始状态
但是我们真正需要的是工厂bean中定义的factory-method方法中返回的bean,
而getObjectForBeanInstance方法就是完成这个工作的

如果bean声明为FactoryBean类型, 则当提取bean时提取的并不是FactoryBean
而是FactoryBean中对应的getObject方法返回的bean
而doGetObjectFromFactoryBean正是实现这个功能的

5.4获取单例
在5.2中从缓存获取单例的bean, 那么如果缓存中不存在已经加载的单例bean就需要从头开始
bean的加载过程了, 在Spring是使用getSingleton的重载方法是实现bean的加载过程
ObjectFactory的核心部分其实只是调用了createBean的方法
DefaultSingletonBeanRegistry.getSingleton(java.lang.String, org.springframework.beans.factory.ObjectFactory<?>)

5.5准备创建bean
Spring的函数有一些规律, 一个真正干活的函数其实是以do开头的,
比如doGetObjectFromFactoryBean; 而一些会误以为是在真正处理的函数
比如getObjectFromFactoryBean, 其实只是从全局角度去做些统筹的工作
这个规则对于createBean也不例外
AbstractAutowireCapableBeanFactory.createBean(java.lang.String, org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[])
5.5.1处理override属性
AbstractBeanDefinition.prepareMethodOverrides
5.5.2实例化的前置处理
在真正调用doCreate方法创建bean的实例前使用了这样一个方法
AbstractAutowireCapableBeanFactory.resolveBeforeInstantiation
对BeanDefinition中的属性做些前置处理, 无论其中是否有对应的逻辑实现我们
都可以理解, 因为真正逻辑实现前后留有处理函数也是可扩展的一种体现,
但更重要的是函数还提供了一个短路判断, 这才是最为关键的地方
    1.实例化前的后处理器应用
    AbstractAutowireCapableBeanFactory
    .applyBeanPostProcessorsBeforeInstantiation
    2.实例化后的后处理器应用
    AbstractAutowireCapableBeanFactory
    .applyBeanPostProcessorsAfterInitialization


5.6循环依赖
实例化bean是一个复杂的过程, 其中循环依赖是其中最重要的一点
5.6.1什么是循环依赖
循环依赖就是循环引用, 就是两个或多个bean相互之间的持有对方, 比如CircleA引用
CircleB, CircleB引用CircleC, CircleC引用CircleA, 则它们最终反映为一个环,
此处不是循环调用, 循环调用是方法之间的环调用(循环调用是无法解决的, 除非有终结条件,
否则就是死循环, 最终导致内存溢出错误, 其实就是递归)
5.6.2Spring如何解决循环依赖
spring容器循环依赖包括构造器循环依赖和setter循环依赖
cn.locusc.chapter5.code.circle.CirCleTest
```