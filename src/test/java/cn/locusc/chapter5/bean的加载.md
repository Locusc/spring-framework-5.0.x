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
```