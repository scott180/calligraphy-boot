## README
> calligraphy-boot

*   [1、说明](#introduce)
*   [2、配置](#config)
*   [3、接口](#interface)

> [gitlab]( https://gitlab.com/xuyq123/calligraphy-boot ) &ensp; [github]( https://github.com/scott180/calligraphy-boot ) &ensp; [gitee]( https://gitee.com/calligraphy-boot )


<h2 id="introduce"></h2>

### 1、说明

```
各分支说明
springboot_init     SpringBoot初始化，只有框架，原始分支。
20210723_layui      整合前端layui分支。
dev_2021072301      展示“书法练习轨迹”jar包分支，java命令行启动。
feature_2021072701  展示“书法练习轨迹”war包分支，布署tomcat。
master              主分支

接口test
http://localhost:8080/calligraphy-boot/shu/test?type=1&name=java
http://localhost:8080/calligraphy-boot/shu

书法练习轨迹--明月几时有
http://localhost:8080/%E4%B9%A6%E6%B3%95%E7%BB%83%E4%B9%A0%E8%BD%A8%E8%BF%B9--%E6%98%8E%E6%9C%88%E5%87%A0%E6%97%B6%E6%9C%89.html

```

```
springboot项目打包布署
https://gitlab.com/xuyq123/calligraphy-boot

方法一：maven打包jar、运行jar

Administrator@ho-xyq MINGW64 /e/Project/gitlab/calligraphy-boot (dev_2021072301)
$ mvn clean package

Administrator@ho-xyq MINGW64 /e/Project/gitlab/calligraphy-boot/calligraphy-boot-start/target (dev_2021072301)
$ java -jar calligraphy-boot-start-1.0-SNAPSHOT.jar


方法二：maven打包war、布署tomcat

1、mvn clean package
2、将 calligraphy-boot.war 复制到 E:\ProgramFiles\apache-tomcat-8.5.31\webapps
3、启动tomcat   E:\ProgramFiles\apache-tomcat-8.5.31\bin\startup.bat


----

maven常用打包命令
1、mvn compile 编译,将Java 源程序编译成 class 字节码文件。
2、mvn test 测试，并生成测试报告
3、mvn clean 将以前编译得到的旧的 class 字节码文件删除
4、mvn pakage 打包,动态 web工程打 war包，Java工程打 jar 包。
5、mvn install 将项目生成 jar 包放在仓库中，以便别的模块调用
6、mvn clean install -Dmaven.test.skip=true  抛弃测试用例打包

```

<h2 id="config"></h2>

### 2、配置
>[springboot初始化]( https://start.spring.io/ ) &  [2小时学会springboot]( https://blog.csdn.net/forezp/article/details/61472783 )  &  [springCloud教程]( https://blog.csdn.net/forezp/article/details/70148833 )

- Initializr
```
https://blog.csdn.net/qq122516902/article/details/84584439
https://blog.csdn.net/qq_37174383/article/details/86702030
```

- 整合mybatis
```
https://blog.csdn.net/iku5200/article/details/82856621

mybatis-generator
https://www.imooc.com/article/28494?block_id=tuijian_wz

mvn mybatis-generator:generate
如果是在intellij 环境，直接鼠标点击 Maven--Plugins--mybatis--generator:generate

```

- 事务配置
```
https://blog.csdn.net/qq_38637066/article/details/82791228
https://msd.misuland.com/pd/2884250171976189972
```

- aop配置
```
https://www.cnblogs.com/moris5013/p/11026653.html

https://blog.csdn.net/hxpjava1/article/details/55504513  aop执行顺序
```
- log配置
```
https://www.cnblogs.com/bigdataZJ/p/springboot-log.html
https://blog.csdn.net/appleyk/article/details/78717388
```


 <h2 id="interface"></h2>

### 3、接口说明

[测试hello]( http://localhost:8080/user/hello )  & [测试]( http://localhost:8080/user/selectById?id=1 )

```
mysqldump -uroot -p12344 test >d:\\test.sql

用户列表 post
http://localhost:8080/user/queryUserList

活动列表 post
http://localhost:8080/activity/queryActivityList

活动详情 get
活动内容text格式 保留html标签
activity/queryActivityDetail

发布活动 post
activity/publishActivity

删除活动 post
activity/deleteActivity

加入活动 post
activity/joinActivity

取消活动 post
activity/cancelActivity

```
