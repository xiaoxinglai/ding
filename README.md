# 介绍
> 基于钉钉开放平台提供的api 封装的一个用于在钉钉群里面使用钉钉机器人发送通知消息或者报警信息的框架。 支持多个机器人，静默，异常信息保存 等。  
多个机器人时分别计算限流，因为钉钉官网上介绍每个机器人每分钟只能发20条消息，如果超过 会被禁止10分钟。 因此框架内的配置的每个机器人都单独计算限流，不会触发禁止。   
支持手动调用发送，方法注解，方法模版(类似spring的手动事务形式)  
发送是在线程池内进行的发送，线程个数等于配置钉钉的机器人数，不阻塞当前的程序运行

## 效果如下

###### 1.普通的消息通知
![d2](https://github.com/xiaoxinglai/ding/blob/master/img/d2.png "发送普通消息")

普通消息通知，艾特的人，在发送的时候可以填入参数，如果不填，默认选择该机器人的配置文件上的联系人。 常用于手动发送 或者 不需要发送异常信息的场景。
  
  
###### 2.带异常信息的消息通知
![d3](https://github.com/xiaoxinglai/ding/blob/master/img/d3.png "发送带异常的消息")

异常消息的通知，常用于异常情况的通知，会打印抛出异常的类名 方法名 和行号，异常原因。 
同时 点击查看异常栈详情 会跳转到查看异常栈详情的页面，查看整个异常栈。  



###### 3.静默 

静默后，该消息将不会发送，直至解除静默为止。 


###### 4.查看异常栈信息

点击后跳转至查看异常栈页面 ，异常信息会保存最近的50条，淘汰最近最少查看的异常栈。

形如：
![d4](https://github.com/xiaoxinglai/ding/blob/master/img/d4.png)

ps:这个可以修改成跳转到自己公司搭建的统一日志平台 这样就没有保存的条数限制了。 




## 使用例子

### 配置文件
###### 1.先创建配置文件  
ding-config.properties  
###### 2.配置内容如下   
以下是配置了两个钉钉机器人  对应两个通知业务线  如果是其他数量 依次类推

```aidl

  #项目域名 用于添加静默名单用
  ding.post.url=http://localhost:8080
  
  #配置业务线tag
  ding.send.type.demo=demo
  #发送的钉钉群url
  ding.send.url.demo= https://oapi.dingtalk.com/robot/send?xxxxx
  #要艾特的人的手机号列表
  ding.send.name.demo= xxxx
  #加密签名
  ding.send.secret.demo=xxxxx
  #日志异常栈启用配置 0-启用本地内存 1-启用三方存储 
  ding.send.localException.demo=0
  #项目名称 选填 用于在自己的统一日志平台中定位日志
  ding.send.logName.demo=xxxx
  
  #配置业务线2 tag
  ding.send.type.demo2=demo2
  #发送的钉钉群2 url
  ding.send.url.demo2= https://oapi.dingtalk.com/robot/send?xxxxx
  #要艾特的人的手机号列表 
  ding.send.name.demo2= xxxx
  #加密签名
  ding.send.secret.demo2=xxxxx
  #日志异常栈启用配置 0-启用本地内存 1-启用三方存储 
  ding.send.localException.demo2=0
  #项目名称 选填 用于在自己的统一日志平台中定位日志
  ding.send.logName.demo2=xxxx

```


### 调用方式



#### 手动调用


```aidl


    @Autowired
    DingTalkClientImpl dingTalkClient;

    /**
     * 普通消息通知
     * @throws Exception
     */
    @Test
    public void sendMsg() throws Exception {
     //demo为上面配置业务线tag 指定是哪个机器人发送
        dingTalkClient.sendMsg("demo","普通消息通知测试","今天天气真好");
      
    }
    
    
    
    
    /**
     * 带异常信息的消息通知
     * @throws Exception
     */
    @Test
    public void sendMsg1() throws Exception {
        try{
            throw  new Exception("异常信息");
        }catch (Exception ex){
            dingTalkClient.sendMsg("demo","异常消息通知测试","xx发生异常",ex);
        }

    }
    
    
    
      /**
         * 发送时候指定要艾特的人
         * @throws Exception
         */
      @Test
      public void sendMsg2() throws Exception {
            dingTalkClient.sendMsg("test","普通消息通知测试","今天天气真好", Arrays.asList("175125xxxx"));
        }
        
        
    
```


#### 手动调用api一览
```aidl


    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置 手动发异常
     * @param bizType
     * @param tag
     * @param ex
     */
    void sendMsg(String bizType, String tag, Throwable ex);

    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置 手动发异常给指定的人
     * @param bizType
     * @param tag
     * @param ex
     */
     void sendMsg(String bizType, String tag, Throwable ex, List<String> atNames);


    /**
     * 发送消息客户端
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param tag     问题tag
     * @param content 报警通知内容
     * @param ex      异常栈
     * @param atNames 要艾特的人列表
     */
     void sendMsg(String bizType, String tag, String content, Throwable ex, List<String> atNames);

    /**
     * 发送消息客户端
     * @param bizType
     * @param tag
     * @param content
     * @param templateTypeEnum
     */
     void sendMsg(String bizType, String tag, String content, String templateTypeEnum);


    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param tag     问题tag
     * @param content 报警通知内容
     * @param ex      异常栈
     */
    void sendMsg(String bizType, String tag, String content, Throwable ex);



    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置  且无异常 ，只是想给配置的人发content的内容
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param tag     问题tag
     * @param content 报警通知内容
     */
     void sendMsg(String bizType, String tag, String content) ;

    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置  且无异常 ，只是想给配置的人发content的内容
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param content 报警通知内容
     */
     void sendMsg(String bizType, String content);



    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置  且无异常 ，只是想给指定的人发content的内容
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param tag     问题tag 问题的标题
     * @param content 报警通知内容
     */
     void sendMsg(String bizType, String tag, String content, List<String> atNames) ;


    /**
     * 发送消息客户端  带有方法参数
     * @param bizType
     * @param tag
     * @param content
     * @param ex
     * @param names
     * @param args
     */
     void sendMsg(String bizType, String tag, String content, Throwable ex, List<String> names, Object[] args);
}


```



#### 注解形式使用

```aidl

    @Ding(BizType = "demo")
    public void testDemo() {
        int a=9/0;
    }
```

#### 注解参数的意义
```aidl


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface Ding {

    /**
     * bizType 业务线
     *
     * @return
     */
    String BizType() default "";
    /**
     * tag 问题唯一标签 不填就选取抛出异常的类名+方法名+行号
     *
     * @return
     */
    String tag() default "";

    /**
     * content 内容
     */
    String content() default "";

    /**
     * 艾特的人
     * @return
     */
    String[] name() default "";
}

```


#### 方法模版的使用

```aidl

    @Autowired
    DingTemplate dingTemplate;
    
    

       dingTemplate.execute("demo", "dingTemplate代码测试", () -> {
            int a = 1 / 0;
            return a;
        });
```

使用dingTemplate扩起来代码段，可以减少try-catch的这类到处都是的代码使用


#### 方法模版的设计
````aidl

    /**
     * 取默认配置的艾特
     * @param bizType 业务线tag 
     * @param tag 问题标题
     * @param action 
     * @param <T>
     * @return
     * @throws RuntimeException
     */
    public <T> T execute(String bizType, String tag, DingSend<T> action) throws RuntimeException {
        T result;
        try {
            result = action.execute();
        } catch (Throwable var5) {
            dingTalkClient.sendMsg(bizType, tag, var5);
            throw new RuntimeException("DingTemplate execute threw exception",var5);
        }
        return result;
    }


    /**
     * 指定艾特人
     * @param bizType 业务线tag
     * @param tag 问题标题
     * @param names
     * @param action
     * @param <T>
     * @return
     * @throws RuntimeException
     */
    public <T> T execute(String bizType, String tag,List<String> names,DingSend<T> action) throws RuntimeException {
        T result;
        try {
            result = action.execute();
        } catch (Throwable var5) {
            dingTalkClient.sendMsg(bizType, tag, var5,names);
            throw new RuntimeException("DingTemplate execute threw exception",var5);
        }
        return result;
    }
````


### 注意事项

0.因为依赖了钉钉官方的jar包，但是钉钉官方并没有给出对应jar包的maven地址，因此在此项目中，是直接以jar包的形式引入的 taobao-sdk-java-auto.jar ，在resources的lib目录中。 
建议使用的人，将这个jar也上传到私有maven仓库中，然后在项目的pom.xml里面配置。

这个非常重要！！！ 
目前在本项目中使用这个jar包 也是这样使用的
```aidl

    <dependency>
            <groupId>com.taobao</groupId>
            <artifactId>taobao-sdk-java</artifactId>
            <version>auto_1479188381469-20190603</version>
   </dependency>
```

但是这个是私库地址，各位需要自己将taobao-sdk-java-auto.jar 上传到自己的私库地址中引用。 
 
 
 

1.使用的时候 将此项目打成jar包，放到maven仓库内，然后在需要用的项目里面引入maven依赖配置就行了。    
打包的时候 需要在这个项目的pom.xml中配置要打包到的私库的地址，这样就能打包发布了，然后其他使用的项目引入即可。 


```aidl
            <dependency>
                <groupId>com.github</groupId>
                <artifactId>ding</artifactId>
                <version>1.0.0-SNAPSHOT</version>
            </dependency>
```


打包需要的仓库地址


```aidl
  <distributionManagement>
        <repository>
            <id>nexus-releases</id>
            <name>Local Nexus Repository</name>
            <url>https://repo.xxxxxx.com/repository/releases/</url>
        </repository>
        <snapshotRepository>
            <id>nexus-snapshots</id>
            <name>Local Nexus Repository</name>
            <url>https://repo.xxxxxx.com/repository/snapshots/</url>
        </snapshotRepository>
    </distributionManagement>

    <pluginRepositories>
        <pluginRepository>
            <id>xxx</id>
            <name>artifactory</name>
            <url>https://repo.xxxxxx.com/repository/public/</url>
        </pluginRepository>
    </pluginRepositories>

    <repositories>
        <repository>
            <id>xxx</id>
            <name>artifactory</name>
            <url>https://repo.xxxxxx.com/repository/public/</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>interval:2</updatePolicy>
            </snapshots>
        </repository>
    </repositories>
```


 
2.因为项目使用了spring框架，所以要注意包扫描的时候，要将这个项目扫描进去。
这两个配置一定要配置，不然扫描不到
```aidl
    <!-- 自动扫描包中的Controlller -->
    <context:component-scan base-package="com.github.ding"/>
    <!--<aop:aspectj-autoproxy/>-->
    <aop:aspectj-autoproxy proxy-target-class="true"/>
```


3.当项目配置好之后，访问该url  
http://localhost:8080/ding/?type=自己在配置中定义的ding.send.type的值 

比如说 http://localhost:8080/ding/?type=demo 

看到返回  
![d9](https://github.com/xiaoxinglai/ding/blob/master/img/d9.png)

同时看到自己配置的群内发出消息   

![d10](https://github.com/xiaoxinglai/ding/blob/master/img/d10.png) 

就是生效了。 






## 设计概要

### 功能设计
![d1](https://github.com/xiaoxinglai/ding/blob/master/img/d1.png "功能设计")


### 配置设计
![d5](https://github.com/xiaoxinglai/ding/blob/master/img/d5.png "配置设计")

### 手动调用接口发送时的参数设计

![d6](https://github.com/xiaoxinglai/ding/blob/master/img/d6.png) 


### 注解参数设计

![d7](https://github.com/xiaoxinglai/ding/blob/master/img/d7.png)

### 流程
![d8](https://github.com/xiaoxinglai/ding/blob/master/img/d8.png)

