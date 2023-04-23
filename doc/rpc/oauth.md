## [主营项目接口文档](http-index.md)

### 202.  [TOKEN验证服务模块说明文档](oauth.md)

## 编号1 获取TOKEN接口    zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.oauth.service.AuthorizeService
#### 工程	rpc-oauth
#### 请求方法	getAccessToken
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
  clientId            |  long      |  N     |  客户端编号
  userId              |  long      |  Y     |  用戶编号
  accessToken         |  String    |  N     |  授权码


	

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  userId              |  long      |  用户编号
  authCode            |  String    |  认证编码
  accessToken         |  String    |  授权码
  expiresIn           |  int       |  授权码超时时间
  refreshToken        |  String    |  刷新授权码的token



## 编号2 验证TOKEN接口    zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.oauth.service.AuthorizeService
#### 工程	rpc-oauth
#### 请求方法	checkToken
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
  clientId            |  long      |  Y     |  客户端编号
  userId              |  long      |  Y     |  用戶编号
  accessToken         |  String    |  Y     |  授权码


	

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  userId              |  long      |  用户编号
  authCode            |  String    |  认证编码
  accessToken         |  String    |  授权码
  expiresIn           |  int       |  授权码超时时间
  refreshToken        |  String    |  刷新授权码的token


## 编号3 验证TOKEN接口    校验token是否一致
#### 接口地址 zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.oauth.service.AuthorizeService
#### 工程	rpc-oauth
#### 请求方法	compareValue
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----        |  ----  |  ----
  key                 |  String      |  Y     |  redisKey(存放token的key)
  value               |  String      |  Y     |  token


	

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  flag                |  boolean   |  true为成功，false为失败
  
  
## 编号4 获取token和session接口    
#### 接口地址 zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.oauth.service.AuthorizeService
#### 工程	rpc-oauth
#### 请求方法	getTokenAndSession
#### 测试用例：

#### 请求参数
参数                |  类型       |  必填   |  说明
----                |  ----        |  ----  |  ----
userId              |  long        |  Y     |  用户id




#### 响应参数类型说明  	 具体参数如下：
参数                 |  类型       |  说明
----                |  ----      |  ----
sessionId           |  String   |  sessionId(老接口所用)
accessToken         |  String   |  token（新接口所用）
expiresIn           |  int      |  token过期时间

