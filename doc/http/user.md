
## [主营项目接口文档](http-index.md)

### <h1 id = top>101.  [用户模块说明文档](user.md)</h1>

<a id ="catalog"> [目录 视频接口 1.5](#1.0.0)</a>
- [1.0.1 编号1 获取用户信息 get   /user/getUser](#1.0.1)
- [1.0.2 获取用户统计数据 POST   /user/getReport](#1.0.2)
- [1.0.3 发送短信验证码 post   /user/createSmsCode ](#1.0.3)
- [1.0.4 通过原始密码修改成新密码 post   /user/modifyPw ](#1.0.4)
- [1.0.5 通过手机短信修改成新密码 post   /user/modifyPwByPhone](#1.0.5)
- [1.0.6 修改指定类型的用户资料 post   /user/modifyField](#1.0.6)
- [1.0.7 获取用户状态等级等信息 get   /user/getStatus](#1.0.7)
- [1.0.8 获取用户推广 get   /user/getReferrerList ](#1.0.8)
- [1.0.9  ](#1.0.9)
- [1.0.10  ](#1.0.10)
- [1.0.11  ](#1.0.11)
- [1.0.12  ](#1.0.12)
- [1.0.13  ](#1.0.13)
- [1.0.14  ](#1.0.14)
- [1.0.15  ](#1.0.15)
- [1.0.16  ](#1.0.16)
- [1.0.17  ](#1.0.17)
- [1.0.18  ](#1.0.18)
- [1.0.19  ](#1.0.19)
- [1.0.20  ](#1.0.20)
- [1.0.21  ](#1.0.21)
- [1.0.22  ](#1.0.22)
- [1.0.23  ](#1.0.23)
- [1.0.24  ](#1.0.24)

<h3 id=1.0.1> 编号1 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 获取用户信息 get   /user/getUser
#### 工程	user
#### 测试用例：

#### 请求参数：
参数                 |  类型       |  说明
  ----                |  ----      |  ----
userId              |  long      |  用户Id

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  userId              |  long      |  用户Id
  nickname            |  String   |  用户名
  phone               |  long     |  手机号袭来
  sex                 |  int      |  性别:0.不详,1.男,2.女
  address             |  String   |  用户地址
  channel             |  long     |  合作渠道
  email               |  String   |  用户邮箱地址
  imei                |  String   |  手机imei地址
  ip                  |  String   |  登陆ip
  remarks             |  String   |  介绍说明
  status              |  String   |  账号状态,是否封号0.正常,1封号,2黑名单
  invitationCode      |  String   |  邀请码
  version             |  long     |  版本号:20.10.100()
 


#### 响应数据：
```json
      
{
    "code":0,
    "data":{
        "userId":1, 
        "nickname":"23421",
        "phone":"15819123355",
        "sex":"1",
        "headImage":"头像",
        "address":"地址",
        "channel":0,
        "email":"邮箱",
        "imei":"手机imei",
        "ip":"注册ip",
        "remarks":"说明介绍",
        "status":0,
        "version":2001
    },
    "msg":"成功",
    "times":1554272029103
}
```



<h3 id=1.0.2> 编号2 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 获取用户统计数据 POST   /user/getReport
#### 工程	user
#### 测试用例：

#### 请求参数：
参数                  |  类型       |  说明
  ----               |  ----      |  ----
  userId             |  long      |  用户Id

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  loginCount          |  int        |  用户登陆次数
  refereeCount        |  int        |  推荐人数据
  shareCount          |  int        |  分享次数
  userId              |  long       |  用户ID
 


#### 响应数据：
```json
      
{
   "code":0,
   "data":
   {
      "loginCount":3,
      "refereeCount":0,
      "shareCount":0,
      "userId":1
   },
   "msg":"成功",
   "times":1554359591524
}
```

<h3 id=1.0.3> 编号3 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  


##  发送短信验证码 post   /user/createSmsCode
#### 工程	user
#### 测试用例：

#### 请求参数：
参数                  |  类型       |  说明
  ----               |  ----      |  ----
  phoneNumber        |  long      |  手机号码
  smsType            |  int       |  发送短信类型:1.注册,2登陆,3.重置密码
  
#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  result              |  int        |  1.成功,2.失败

 


#### 响应数据：
```json
      
{
     "code":0,
     "data":
     {
        "result":1
     },
     "msg":"成功",
     "times":1554360989435
}
```

<h3 id=1.0.4> 编号4 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

##  通过原始密码修改成新密码 post   /user/modifyPw
#### 工程	user
#### 测试用例：

#### 请求参数：
参数                  |  类型       |  说明
  ----               |  ----      |  ----
  userId             |  long      |  用户ID
  password           |  int       |  将原始密码,base64后的值:C27CD8DFFF5B1DA1
  newPassword        |  int       |  将修改后密码数据,base64后的值:C27CD8DFFF5B1DA1
  
#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  result              |  int        |  1.成功,2.失败

 


#### 响应数据：
```json
      
{
   "code":0,
      "data":
      {
         "result":1
      },
      "msg":"成功",
      "times":1554360989435
}
```


<h3 id=1.0.5> 编号5 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 通过手机短信修改成新密码 post   /user/modifyPwByPhone
#### 工程	user
#### 测试用例：

#### 请求参数：
参数                  |  类型       |  说明
  ----               |  ----      |  ----
  phoneNumber        |  long      |  用户手机号码
  smsCode            |  String    |  用户手机短信验证码
  smsType            |  int       |  手机短信验证码类型,为3;发送短信类型:1.注册,2登陆,3.重置密码
  newPassword        |  String    |  将修改后密码数据,base64后的值:C27CD8DFFF5B1DA1
  
#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  result              |  int        |  1.成功,2.失败

 


#### 响应数据：
```json
      
{
   "code":0,
      "data":
      {
         "result":1
      },
      "msg":"成功",
      "times":1554360989435
}
```


<h3 id=1.0.6> 编号6 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 修改指定类型的用户资料 post   /user/modifyField
#### 工程	user
#### 测试用例：

#### 请求参数：
参数                  |  类型       |  说明
  ----               |  ----      |  ----
  userId             |  long      |  用户手机号码
  userFieldEnum      |  int       |   PHONE(1,"phone"), EMAIL(2,"email"),NICKNAME(4,"nickname"),QR_CODE (5,"qr_code"),HEAD_IMAGE (6,"head_image"),ADDRESS (7,"address"),REMAKES (8,"remakes")
  userFiledValue     |  String     |  手机短信验证码类型,为3;发送短信类型:1.注册,2登陆,3.重置密码
  password           |  String     |  用户输入密码数据,base64后的值:C27CD8DFFF5B1DA1
  
#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  result              |  int        |  1.成功,2.失败

 


#### 响应数据：
```json
      
{
   "code":0,
      "data":
      {
         "result":1
      },
      "msg":"成功",
      "times":1554360989435
}
```



<h3 id=1.0.7> 编号7 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 获取用户状态等级等信息 get   /user/getStatus
#### 工程	user
#### 测试用例：

#### 请求参数：
参数                  |  类型       |  说明
  ----               |  ----      |  ----
  userId             |  long      |  用户ID
  deviceId           |  String    |  设备ID

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  hasPlay             |  int        |  已经播放次数
  playMax             |  int        |  播放最大次数
  rank                |  int        |  等级
  rankDesc            |  int        |  等级描述
  userId              |  long       |  用户ID
  deviceId            |  String     |  设备ID
  nextRankNum         |  int        |  下一级还差人数
  nextRank            |  int        |  下一等级
  nextRankDesc        |  String     |  下一等级描述

#### 响应数据：
```json

{
   "code":0,
   "data":
   {
      "deviceId":"BBKKKXAK",
      "hasPlay":5,
      "nextRank":2,
      "nextRankDesc":"进阶",
      "nextRankNum":4,
      "playMax":6,
      "rank":1,
      "rankDesc":"入门",
      "userId":7
   },
   "msg":"成功"
}
```


<h3 id=1.0.8> 编号8 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 获取用户推广 get   /user/getReferrerList
#### 工程	user
#### 测试用例：

#### 请求参数：
参数                  |  类型       |  说明
  ----               |  ----      |  ----
  userId             |  long      |  用户ID

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  nickName            |  String     |  昵称
  phone               |  String     |  手机号码
  rank                |  int        |  等级
  rankDesc            |  String     |  等级描述
  createDate          |  Date       |  注册创建时间
  userId              |  long       |  用户ID

#### 响应数据：
```json

{
   "code":0,
   "data":
   [
      {
         "createDate":1554204459000,
         "nickName":"23421",
         "phone":"158****3354",
         "rank":2,
         "rankDesc":"进阶",
         "userId":0
      }

   ],
   "msg":"成功"
}
```



#### 错误编码：
  错误编码  |  信息
  ----     | ----      
 1101001   | 账号不存在！
 1101010   | 您的帐号已被禁止登录系统，如有疑问，请联系我司客服
 1101021   | 修改密码失败

 
