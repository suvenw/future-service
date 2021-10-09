## [主营项目接口文档](http-index.md)

### <h1 id = top>103.  [资源模块说明文档](resource.md)</h1>

<a id ="catalog"> [目录 视频接口 1.5](#1.3.0)</a>
- [1.3.1 获取banner广告 get   /resource/getBannerList](#1.3.1)
- [1.3.2 保存或发布banner广告 post   /resource/saveBanner](#1.3.2)
- [1.3.3 获取启动页 get   /resource/getStartPageList ](#1.3.3)
- [1.3.4 保存或发布启动页广告 post   /resource/saveStartPage ](#1.3.4)
- [1.3.5 意见反馈-常见问题FAQ get  /resource/getFeedbackFAQList](#1.3.5)
- [1.3.6 意见反馈-我要反馈-你的问题(类型) get  /resource/getFeedbackTypeList](#1.3.6)
- [1.3.7 意见反馈-我要反馈-提交 post  /resource/saveFeedback](#1.3.7)
- [1.3.8 意见反馈-我的反馈 get  /resource/getFeedbackList](#1.3.8)
- [1.3.9 获取环境变量所有配置接口 get   /resource/getConfigList ](#1.3.9)
- [1.3.10  保存环境变量接口 post   /resource/saveConfig](#1.3.10)
- [1.3.11  ](#1.3.11)
- [1.3.12  ](#1.3.12)
- [1.3.13  ](#1.3.13)
- [1.3.14  ](#1.3.14)
- [1.3.15  ](#1.3.15)
- [1.3.16  ](#1.3.16)
- [1.3.17  ](#1.3.17)
- [1.3.18  ](#1.3.18)
- [1.3.19  ](#1.3.19)
- [1.3.20  ](#1.3.20)
- [1.3.21  ](#1.3.21)
- [1.3.22  ](#1.3.22)
- [1.3.23  ](#1.3.23)
- [1.3.24  ](#1.3.24)

<h3 id=1.3.1> 编号1 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

##  获取banner广告 get   /resource/getBannerList
#### 工程	resource
#### 测试用例：

#### 请求参数：
参数                   |  类型   | 必填    |  说明
  ----                |  ----   |  ----   |  ----
type                  |  int    |  Y      |  类型（1：首页；2：首页中间；3：频道; 4：视频；5：我的；6：首页底部；7：搜索页）
pageNo                |  int    |  Y      |  页码(1,2,3)
pageSize              |  int    |  Y      |  页大小:50


#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  id                  |  long      |  广告bannerId;
  type                |  int       |  广告类型(1：首页；2：首页中间,3:频道; 4.视频,5.我的）
  image               |  String    |  banner图片地址
  url                 |  String    |  点击连接地址;
  formJson            |  String    |  点击连接参数,json对象字符串
  focusId             |  int       |  点击统计数据
  startDate           |  long      |  广告有效开始时间,格式为时间截,单位为毫秒
  endDate             |  long      |  广告有效结束时间,格式为时间截,单位为毫秒
  status              |  long      |  是否有效

 


#### 响应数据：
```json
{
   "code":0,
   "data":
   {
      "isNextPage":0,
      "list":
      [
         {
            "endDate":1556176865000,
            "focusId":1,
            "formJson":"",
            "id":5,
            "image":"http://sina.com.cn",
            "startDate":1555113600000,
            "status":0,
            "type":1,
            "url":"http://sina.com.cn"
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}     

```



<h3 id=1.3.2> 编号2 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 保存或发布banner广告 post   /resource/saveBanner
#### 工程	resource
#### 测试用例：

#### 请求参数：
参数                  |  类型        |   必填   |   说明
  ----               |  ----        |  ----   |   --- 
    type                |  int      |  Y      |    |  广告类型(1：首页；2：首页中间,3:频道; 4.视频,5.我的）
    image               |  String   |  Y      |    |  banner图片地址
    url                 |  String   |  Y      |    |  点击连接地址;
    formJson            |  String    |  Y      |   |  点击连接参数,json对象字符串
    focusId             |  int      |  Y      |    |  点击统计数据
    startDate           |  long     |  Y      |    |  广告有效开始时间,格式为时间截,单位为毫秒
    endDate             |  long     |  Y      |    |  广告有效结束时间,格式为时间截,单位为毫秒
    status              |  long     |  Y      |    |  是否有效

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  id                  |  long        |  bannerId
 


#### 响应数据：
```json
  {
     "code":0,
     "data":
     {
        "id":6
     },
     "msg":"成功"
  }
```


<h3 id=1.3.3> 编号3 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

##  获取启动页 get   /resource/getStartPageList
#### 工程	resource
#### 测试用例：

#### 请求参数：
参数                   |  类型   | 必填    |  说明
  ----                |  ----   |  ----   |  ----
pageNo                |  int    |  Y      |  页码(1,2,3)
pageSize              |  int    |  Y      |  页大小:50


#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  id                  |  long      |  广告bannerId;
  type                |  int       |  广告类型(1：首页；2：首页中间,3:频道; 4.视频,5.我的）
  image               |  String    |  banner图片地址
  url                 |  String    |  点击连接地址;
  formJson            |  String    |  点击连接参数,json对象字符串
  focusId             |  int       |  点击统计数据
  startDate           |  long      |  广告有效开始时间,格式为时间截,单位为毫秒
  endDate             |  long      |  广告有效结束时间,格式为时间截,单位为毫秒
  status              |  long      |  是否有效

 


#### 响应数据：
```json
{
   "code":0,
   "data":
   {
      "isNextPage":0,
      "list":
      [
         {
            "endDate":1556176865000,
            "focusId":1,
            "formJson":"",
            "id":5,
            "image":"http://sina.com.cn",
            "startDate":1555113600000,
            "status":0,
            "type":1,
            "url":"http://sina.com.cn"
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}     

```


<h3 id=1.3.4> 编号4 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 保存或发布启动页广告 post   /resource/saveStartPage
#### 工程	resource
#### 测试用例：

#### 请求参数：
参数                  |  类型        |   必填   |   说明
  ----               |  ----        |  ----   |   --- 
    type                |  int      |  Y      |    |  广告类型(1：首页；2：首页中间,3:频道; 4.视频,5.我的）
    image               |  String   |  Y      |    |  banner图片地址
    url                 |  String   |  Y      |    |  点击连接地址;
    formJson            |  String    |  Y      |   |  点击连接参数,json对象字符串
    focusId             |  int      |  Y      |    |  点击统计数据
    startDate           |  long     |  Y      |    |  广告有效开始时间,格式为时间截,单位为毫秒
    endDate             |  long     |  Y      |    |  广告有效结束时间,格式为时间截,单位为毫秒
    status              |  long     |  Y      |    |  是否有效

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  id                  |  long        |  bannerId
 


#### 响应数据：
```json
  {
     "code":0,
     "data":
     {
        "id":6
     },
     "msg":"成功"
  }
```




<h3 id=1.3.5> 编号3 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

##  意见反馈-FAQ列表 get   /resource/getFeedbackFAQList
#### 工程	resource
#### 测试用例：

#### 请求参数：
参数                   |  类型   | 必填    |  说明
  ----                |  ----   |  ----   |  ----


#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  id                  |  long      |  列表Id;
  question            |  String    |  问题
  answer              |  String    |  回答
  type                |  String    |  类型




#### 响应数据：
```json
{
   "code":0,
   "data":
   [
      {
         "answer":"A：别做梦，好好干活",
         "id":1,
         "question":"Q：我顶，明天放假吗？",
         "type":"新手攻略"
      },
      {
         "answer":"A：别做梦，好好干活",
         "id":2,
         "question":"Q：我顶，明天放假吗？",
         "type":"新手攻略"
      },
      {
         "answer":"A：别做梦，好好干活",
         "id":3,
         "question":"Q：要赚钱，赚钱了？？",
         "type":"视频相关"
      },
      {
         "answer":"A：别做梦，好好干活",
         "id":4,
         "question":"Q：要赚钱，赚钱了？？",
         "type":"视频相关"
      },
      {
         "answer":"A：别做梦，好好干活",
         "id":5,
         "question":"Q：要赚钱，赚钱了？？",
         "type":"视频相关"
      }

   ],
   "msg":"成功"
}

```

<h3 id=1.3.6> 编号3 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

##  意见反馈-我要反馈-您的问题（类型） get   /resource/getFeedbackTypeList
#### 工程	resource
#### 测试用例：

#### 请求参数：
参数                   |  类型   | 必填    |  说明
  ----                |  ----   |  ----   |  ----


#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  id                  |  long      |  类型Id;
  name                |  String    |  类型名称




#### 响应数据：
```json
{
   "code":0,
   "data":
   [
      {
         "id":1,
         "name":"系统问题"
      },
      {
         "id":2,
         "name":"业务问题"
      }

   ],
   "msg":"成功"
}

```


<h3 id=1.3.7> 编号3 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

##  意见反馈-我要反馈-提交  post   /resource/saveFeedback
#### 工程	resource
#### 测试用例：

#### 请求参数：
参数                  |  类型        |   必填   |   说明
  ----               |  ----        |  ----   |   ---
    content             |  String   |  Y      |    |  内容、描述
    imageUrl            |  String   |  N      |    |  图片上传返回的URL
    userId              |  long     |  Y      |    |  用户ID
    feedbackType        |  int      |  Y      |    |  类型ID


#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  id                  |  long      |  反馈Id;




#### 响应数据：
```json
{
   "code":0,
   "data":
   {
      "id":10
   },
   "msg":"成功"
}

```

<h3 id=1.3.8> 编号1 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

##  获取banner广告 get   /resource/getFeedbackList
#### 工程	resource
#### 测试用例：

#### 请求参数：
参数                   |  类型   | 必填    |  说明
  ----                |  ----   |  ----   |  ----
userId                |  long   |  Y      |  用户ID
pageNo                |  int    |  Y      |  页码(1,2,3)
pageSize              |  int    |  Y      |  页大小:50


#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  id                  |  long      |  列表ID
  content             |  String    |  内容
  feedbackType        |  int       |  类型ID
  feedbackTypeName    |  String    |  类型名称
  imageUrl            |  String    |  图片URL
  reply               |  String    |  系统回复
  submitDate          |  date      |  提交日期
  userId              |  long      |  用户ID




#### 响应数据：
```json
{
   "code":0,
   "data":
   {
      "isNextPage":1,
      "list":
      [
         {
            "content":"反馈哇哈哈哈哈哈哈哈哈哈哈哈哈哈哈",
            "feedbackType":2,
            "feedbackTypeName":"业务问题",
            "id":2,
            "imageUrl":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNe2AIOBuAALZIuFyAGc859.jpg",
            "reply":"解决了",
            "submitDate":1554825600000,
            "userId":2
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}

```

<h3 id=1.3.9> 编号9 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

##  获取环境变量所有配置接口 get   /resource/getConfigList
#### 工程	resource
#### 测试用例：

#### 请求参数：公共参数
参数                   |  类型   | 必填    |  说明
  ----                |  ----   |  ----   |  ----


#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  id                  |  long      |  广告bannerId;
  configKey        |  String       |    环境变理对应参数名称  
  configValue      |  String       |    环境变理对应结果值
  configDescribe   |  String       |    描述说明;

 


#### 响应数据：
```json
{
   "code":0,
   "data":
      [
         {
            "configKey":"HOME_URL",
            "configValue":"http://home.com",
            "configDescribe":"首页地址"
         }

      ],
   "msg":"成功"
}     

```

<h3 id=1.3.10> 编号10 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 保存环境变量接口 post   /resource/saveConfig
#### 工程	resource 
#### 测试用例：

#### 请求参数：
参数                  |  类型        |   必填   |   说明
  ----               |  ----        |  ----   |   --- 
    configKey        |  String      |  Y      |    环境变理对应参数名称  
    configValue      |  String      |  Y      |    环境变理对应结果值
    configDescribe   |  String      |  Y      |    描述说明;
   
#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  id                  |  long        |  bannerId
 


#### 响应数据：
```json
  {
     "code":0,
     "data":
     {
        "id":6
     },
     "msg":"成功"
  }
```




#### 错误编码：
  错误编码  |  信息
  ----     | ----      
 1101001   | 账号不存在！
 1101010   | 您的帐号已被禁止登录系统，如有疑问，请联系我司客服
 1101021   | 修改密码失败

 
