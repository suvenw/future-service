## [主营项目接口文档](http-index.md)

### <h1 id = top>101.  [TOKEN验证服务模块说明文档](oauth.md)</h1>

<a id ="catalog"> [目录 视频接口 1.5](#1.2.0)</a>
- [1.2.1 用户账号密码登陆接口  post  /oauth/token](#1.2.1)
- [1.2.2 用户账号刷新token接口  post  /oauth/token} (#1.2.2)
- [1.2.3 用户账号第三方授权接口  post  /oauth/token](#1.2.3)
- [1.2.4 获取授权码接口  post /oauth/authorize] (#1.2.4)
- [1.2.5 注册用户接口 post  /oauth/register ](#1.2.5)
- [1.2.6  检查token接口 post  /oauth/checkToken](#1.2.6)
- [1.2.7  ](#1.2.7)
- [1.2.8  ](#1.2.8)
- [1.2.9  ](#1.2.9)
- [1.2.10  ](#1.2.10)
- [1.2.11  ](#1.2.11)
- [1.2.12  ](#1.2.12)
- [1.2.13  ](#1.2.13)
- [1.2.14  ](#1.2.14)
- [1.2.15  ](#1.2.15)
- [1.2.16  ](#1.2.16)
- [1.2.17  ](#1.2.17)
- [1.2.18  ](#1.2.18)
- [1.2.19  ](#1.2.19)
- [1.2.20  ](#1.2.20)
- [1.2.21  ](#1.2.21)
- [1.2.22  ](#1.2.22)
- [1.2.23  ](#1.2.23)
- [1.2.24  ](#1.2.24)

<h3 id=1.2.1> 编号1 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 用户账号密码或短信登陆接口  post  /oauth/token
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
 client_id            |  String    |  Y     |  "unity-client"
 grant_type           |  String    |  Y     | 请求类型为: "password",grant_type=authorization_code,grant_type=password,grant_type=refresh_token,grant_type=client_credentials,sms_code_login
 username            |  String    |  Y     |  别名名称:包括:电话号码,邮箱,第三方的UUID
 password            |  String    |  Y     |  password密码或短信码, 用户密码,使用DES加密, 原始密码+干扰码:base_64Pass!dslive 


	

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  accessToken        |  long      |  验证token
  refreshToken       |  String    |  刷新token
  tokenType          |  int       |  token类型 "Bearer"
  userId              |  long      |  用户ID
  expiresIn          |  long      | token过期时间
  


#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "accessToken":"2366071f34ac911374dfa3925f572e0e",
      "refreshToken":"9c4bd5c68e022f923ac49880cd21aa8b",
      "tokenType":"Bearer",
      "userId":"1",
      "expiresIn":1554369279272
   },
   "msg":"成功",
   "times":1554365679295
}
```


<h3 id=1.2.2> 编号2 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 用户账号刷新token接口  post  /oauth/token
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
 client_id            |  String    |  Y     |  "unity-client"
 grant_type           |  String    |  Y     | 请求类型为: "refresh_token",grant_type=refresh_token
 refresh_token        |  String    |  Y     |  登陆后收到的刷新refreshToken
 userId               |  long      |  Y     |  用户ID
	

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  accessToken        |  long      |  验证token
  refreshToken       |  String    |  刷新token
  tokenType          |  int       |  token类型 "Bearer"
  userId              |  long      |  用户ID
  expiresIn          |  long      | token过期时间
  


#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "accessToken":"2366071f34ac911374dfa3925f572e0e",
      "refreshToken":"9c4bd5c68e022f923ac49880cd21aa8b",
      "tokenType":"Bearer",
      "userId":"1",
      "expiresIn":1554369279272
   },
   "msg":"成功",
   "times":1554365679295
}
```



<h3 id=1.2.3> 编号3 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 用户账号第三方授权接口  post  /oauth/token
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
 client_id            |  String    |  Y     |  "unity-client"
 client_secret        |  String    |  Y     |  "client_secret-client"
 grant_type           |  String    |  Y     | 请求类型为: "authorization_code",grant_type=authorization_codeTest
 code                 |  String    |  Y     |  接口/oauth/authorize,返回的code信息
 state                |  String    |  N     |  随机盐
 userId               |  long      |  Y     |  用户ID
	

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  accessToken        |  long      |  验证token
  refreshToken       |  String    |  刷新token
  tokenType          |  int       |  token类型 "Bearer"
  userId              |  long      |  用户ID
  expiresIn          |  long      | token过期时间
  


#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "accessToken":"2366071f34ac911374dfa3925f572e0e",
      "refreshToken":"9c4bd5c68e022f923ac49880cd21aa8b",
      "tokenType":"Bearer",
      "userId":"1",
      "expiresIn":1554369279272
   },
   "msg":"成功",
   "times":1554365679295
}
```


#### 错误编码
  错误编码  |  信息
  ----      |  ----      
 1102002    |  获取ACCESS_TOKEN失败



<h3 id=1.2.4> 编号4 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 获取授权码接口 post  /oauth/authorize
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
 client_id            |  String    |  Y     |  "unity-client"
 response_type        |  String    |  Y     | 请求类型为: "code"
 redirect_uri         |  String     |  Y     | 回调地址 http://127.0.0.1:9020/top/oauth2-login
 scope                |  String     |  Y     |  权限限制scope:read
 state                |  String     |  Y     |  随机码:123456
 refresh_token        |  String     |  Y     |  刷新token
 userId               |  String     |  Y     |  用户id
   

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----


#### 响应数据
```json
{
	"code": 0,
	"response":{
	},
	"msg": "",
	"times": 1465366629577
}
```



<h3 id=1.2.5> 编号5 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

##  注册用户接口 post  /oauth/register
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----       |  ----  |  ----
 userOauth            |  String     |  Y     |  账号信息
 nickName             |  String     |  N     |  用户昵称
 password             |  String     |  Y     |  用户密码,使用DES加密, 原始密码+干扰码:base_64Pass!live   
 userType             |  String     |  Y     |  用户类型:0.缺省平台,官方账号,1.手机号码,2.邮箱地址 3.QQ,4.微信,5.微博
 code                 |  String     |  Y     |  短信验证码:123456
 referrerId           |  long       |  Y     |  推荐人ID,缺少值为0;
   

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----


#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "accessToken":"8dd1dcfdc75458c92d736ce3a575dbd4",
      "expires":"1556950523215",
      "expiresIn":1556950523215,
      "refreshToken":"f83147200e2a3f2712b3dae0b779539d",
      "scope":"",
      "tokenType":"Bearer",
      "userId":4
   },
   "msg":"成功"
}
```
#### 备注:``
```
 
原始数据:1234567890qwe
干扰码:base_64Pass!live  
加密后数据:

des 加解密码:
des encrypt : 1EE927927711B7836976295A4591FB43
des decrypt : 1234567890qwe

aes 加解密码:
aes encrypt : 2DF4E5B3D4A0CC09A548E1B0BD7A5E43
aes encrypt : 1234567890qw
```



<h3 id=1.2.6> 编号6 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

##  检查token接口 post  /oauth/checkToken
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----       |  ----  |  ----
 access_token         |  String     |  Y     |  验证token
 userId               |  long       |  N     |  用户id
   

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----


#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "result":"1"
   },
   "msg":"成功"
}
```

#### 错误编码
  错误编码  |  信息
  ----      |  ----      
 1102001    | ACCESS_TOKEN校验失败,请重新登陆!
 

 
 
#### 错误编码
  错误编码  |  信息
  ----      |  ----      
  1100003   |  参数请求错误
  1102024   |  验证码失效，请重新获取
  1102025   |  两次输入密码不一致
  1102026   |  验证码不正确，请重新输入
  1102027   |  请输入正确手机号码格式
  1102028   |  两次输入密码不一致密码错误，密码须为8-20位字母和数字的组成
  1102029   |  验证参数错误，请重新注册
  1103015   |  邀请码错误