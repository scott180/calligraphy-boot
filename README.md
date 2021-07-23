## README
> spring-test

*   [1、配置](#config)
*   [2、接口说明](#interface)   

> [markdown]( https://jbt.github.io/markdown-editor/ )    [马克飞象]( https://maxiang.io )   [作业部落]( https://www.zybuluo.com/mdeditor )    

> [github]( https://github.com/scott180 )  [gitlab]( https://git.lug.ustc.edu.cn/ja )     [gitee]( https://gitee.com )


<h2 id="config"></h2>

### 1、配置
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

>浏览器 http请求插件
RESTED



 <h2 id="interface"></h2>

### 2、接口说明

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
