## [主营项目接口文档](http-index.md)

### 204.  [用户模块说明文档](user.md)

## 编号1  根据imei查询用户信息的个数   
#### 接口地址 zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.user.service.UserDataService/selectCountByImei
#### 工程	rpc-user
#### 测试用例：

#### 请求参数
  参数                |  类型       |  必填   |  说明
  ----                |  ----       |  ----   |  ----
  imei                |  String     |    Y    | 手机imei

	

#### 响应参数类型说明  	 具体参数如下：
  参数                |  类型          |  说明
  ----                |  ----          |  ----
  count               |  int           | imei注册个数


## 编号2  根据用户邮箱查询用户id   
#### 接口地址 zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.user.service.UserService/getUserByUserEmail
#### 工程	rpc-user
#### 测试用例：

#### 请求参数
  参数                |  类型       |  必填   |  说明
  ----                |  ----       |  ----   |  ----
  userEmail           |  String     |    Y    | 用户邮箱

	

#### 响应参数类型说明  	 具体参数如下：
  参数                |  类型          |  说明
  ----                |  ----          |  ----
  userId              |  long          | 用户id
  
  

## 编号3  根据用户手机号码查询用户id  
#### 接口地址 zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.user.service.UserService/getUserByUserMobilePhone 
#### 工程	rpc-user
#### 测试用例：

#### 请求参数
  参数                |  类型       |  必填   |  说明
  ----                |  ----       |  ----   |  ----
  userMobilePhone     |  String     |    Y    | 用户手机号码

	

#### 响应参数类型说明  	 具体参数如下：
  参数                |  类型          |  说明
  ----                |  ----          |  ----
  userId              |  long          | 用户id



## 编号4  根据key,value,time在redis中添加数据，并且设置时长
#### 接口地址 zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.user.service.UserService/saveRedisValue
#### 工程	rpc-user
#### 测试用例：

#### 请求参数
  参数                |  类型       |  必填   |  说明
  ----                |  ----       |  ----   |  ----
  key                 |  String     |    Y    | redis中key的值
  value               |  String     |    Y    | redis中value的值
  time                |  int        |    Y    | redis存在的时长(秒)
	

#### 响应参数类型说明  	 具体参数如下：
  参数                |  类型          |  说明
  ----                |  ----          |  ----
  成功/失败           |  boolean       | 设置是否成功
  

## 编号5  保存用户账号
#### 接口地址 zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.user.service.UserAccountService/saveUserAccount
#### 工程	rpc-user
#### 测试用例：

#### 请求参数
  参数                |  类型       |  必填   |  说明
  ----                |  ----       |  ----   |  ----
  userAccount         |  String     |    Y    | 用户账号
  accountType         |  int        |    Y    | 账号类型
	

#### 响应参数类型说明  	 具体参数如下：
  参数                |  类型          |  说明
  ----                |  ----          |  ----
  
  
## 编号6  根据用户账号和账号类型查询用户账号信息
#### 接口地址 zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.user.service.UserAccountService/getUserAccountByAccountTypeAndAccountName
#### 工程	rpc-user
#### 测试用例：

#### 请求参数
  参数                |  类型       |  必填   |  说明
  ----                |  ----       |  ----   |  ----
  userAccount         |  String     |    Y    | 用户账号
  accountType         |  int        |    Y    | 账号类型
	

#### 响应参数类型说明  	 具体参数如下：
  参数                |  类型          |  说明
  ----                |  ----          |  ----
  userId              |  long          |  用户id 
  
  
## 编号6  根据imei查询个数
#### 接口地址 zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.user.service.UserDataService/selectCountByImei
#### 工程	rpc-user
#### 测试用例：

#### 请求参数
  参数                |  类型       |  必填   |  说明
  ----                |  ----       |  ----   |  ----
  imei                |  String     |    Y    | 
	

#### 响应参数类型说明  	 具体参数如下：
  参数                |  类型          |  说明
  ----                |  ----          |  ----
  count               |  int           |  个数
  

## 编号6  保存用户信息
#### 接口地址 zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.user.service.UserDataService/saveUserData
#### 工程	rpc-user
#### 测试用例：

#### 请求参数
  参数                |  类型       |  必填   |  说明
  ----                |  ----       |  ----   |  ----
  imei                |  String     |    Y    | imei
  mac                 |  String     |    Y    | mac地址
  imageUrl            |  String     |    Y    | 图像地址
	

#### 响应参数类型说明  	 具体参数如下：
  参数                |  类型          |  说明
  ----                |  ----          |  ----
  
  
## 编号7  根据用户id查询用户信息
#### 接口地址 zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.user.service.UserDataService/selectByUserId
#### 工程	rpc-user
#### 测试用例：

#### 请求参数
  参数                |  类型       |  必填   |  说明
  ----                |  ----       |  ----   |  ----
  userId              |  long       |    Y    | 用户
	

#### 响应参数类型说明  	 具体参数如下：
  参数                |  类型           |  说明
  ----                |  ----           |  ----
  imei                |  String         | imei
  mac                 |  String         |  mac地址
  imageUrl            |  String         |  图像地址
  
  
## 编号8  修改用户信息表中的imei和mac地址
#### 接口地址 zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.user.service.UserDataService/updateUserDataImeiAndMac
#### 工程	rpc-user
#### 测试用例：

#### 请求参数
  参数                |  类型       |  必填   |  说明
  ----                |  ----       |  ----   |  ----
  userId              |  long       |    Y    | 用户
  imei                |  String     |    Y    | imei
  mac                 |  String     |    Y    | mac地址
	

#### 响应参数类型说明  	 具体参数如下：
  参数                |  类型           |  说明
  ----                |  ----           |  ----
  
  
## 编号9  根据用户id查询用户信息  
#### 接口地址 zookeeper://xxx.xxx.xxx.xxx:xxxx/cn.com.gc.hsz.user.service.UserService/getUserInfo 
#### 工程	rpc-user
#### 测试用例：

#### 请求参数
  参数                |  类型       |  必填   |  说明
  ----                |  ----       |  ----   |  ----
  userId              |  long       |    Y    | 用户

	

#### 响应参数类型说明  	 具体参数如下：
  参数                |  类型          |  说明
  ----                |  ----          |  ----
  userId              |  long          | 用户id
  status              |  String        | 删除状态
  enableStatus        |  String        | 禁用状态
  rbcAmount           |  int           | 红豆个数
  externalUserId      |  long          | 客户端用户id
  userClassify        |  String        | 用户类型
  scoreGrade          |  int           | 用户等级
  scoreAmount         |  int           | 用户等级积分
  imageUrl            |  String        | 图像地址
  nickName            |  String        | 昵称
  userEmail           |  String        | 用户邮箱
  