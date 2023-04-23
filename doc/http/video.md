<center><h1 id = top>  视频模块说明文档 </h1></center>

## [主营项目接口文档](http-index.md)
### 106.  [视频模块说明文档](video.md)

<a id ="catalog"> [目录 视频接口 1.5](#1.6.0)</a>
- [1.6.1 编号1 保存视频信息实现   /video/saveVideo](#1.6.1)
- [1.6.2 分页获取视频列表  /video/getVideoList](#1.6.2)
- [1.6.3 获取视频详情信息  /video/getVideoInfo ](#1.6.3)
- [1.6.4 查看获取视频详情信息包括统计数据  /video/getVideoCount ](#1.6.4)
- [1.6.5 查看获取视频详情信息包括统计数据 /video/getReportCount](#1.6.5)
- [1.6.6 发表视频评论接口 post  /video/saveComment](#1.6.6)
- [1.6.7 获取指定视频评论接口 get  /video/getCommentList ](#1.6.7)
- [1.6.8 删除视频评论接口 get  /video/delComment ](#1.6.8)
- [1.6.9 对视频点赞或点踩接口 post /video/saveUserPraise](#1.6.9)
- [1.6.10 分页获取我对视频点赞或点踩接口 get /video/getUserPraiseList ](#1.6.10)
- [1.6.11 验证用户与视频是否点赞或点踩关系接口 get /video/isUserPraise](#1.6.11)
- [1.6.12 删除用户与视频是否点赞或点踩接口 post /video/delUserPraise ](#1.6.12)
- [1.6.13 对视频收藏接口 post /video/saveUserStore](#1.6.13)
- [1.6.14 分页获取我对视频收藏列表接口 get /video/getUserStoreList](#1.6.14)
- [1.6.15 删除/取消收藏用户收藏的视频接口 post /video/delUserStore](#1.6.15)
- [1.6.16 保存用户播放历史列表 接口 post /video/saveUserPlay](#1.6.16)
- [1.6.17 分页获取用户播放历史列表 get /video/getUserPlayList](#1.6.17)
- [1.6.18 删除用户视频播放历史记录 接口 post /video/delUserPlay](#1.6.18)
- [1.6.19 获取电影分类 get   /video/getClassifyList](#1.6.19)
- [1.6.20 保存电影分类 post   /video/saveClassify](#1.6.20)
- [1.6.21 获取电影标签 get   /video/getLabelList](#1.6.21)
- [1.6.22 保存电影标签 post   /video/saveLabel](#1.6.22)
- [1.6.23 获取频道列表信息 get   /video/getStarFrequency](#1.6.23)
- [1.6.24 通过明星ID获取视频信息 get   /video/getVideoListByStarId ](#1.6.24)
- [1.6.25 获取明星详情信息 get   /video/getStar](#1.6.25)
- [1.6.26 获取明星列表信息 get   /video/getStarList](#1.6.26)
- [1.6.27 保存明星关系信息 post   /video/saveStarShip](#1.6.27)
- [1.6.28 通过分类和排序条件分页获取视频列表 get  /video/getVideoClassifyList](#1.6.28)
- [1.6.29 验证用户与视频列表是否收藏关系接口 get /video/isUserStoreList](#1.6.29)
- [1.6.30 标签搜索电影接口 get /video/getLabelVideoPage](#1.6.30)
- [1.6.31 根据视频ID获取对应标签和标签相关视频 get /video/getLabelVideoPage](#1.6.31)
- [1.6.32 根据用户ID和视频ID获取播放历史 get /video/getUserPlay](#1.6.32)
- [1.6.33 首页搜索 get /video/getVideoByKeyWordPage](#1.6.33)
- [1.6.34 大家都在搜 get /video/getTopSearchPage](#1.6.34)
- [1.6.35 猜你喜欢 get /video/getMayLikePage](#1.6.35)


<h3 id=1.6.1> 编号1 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 保存视频信息实现   /video/saveVideo
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
  videoName           |  String       |  N     |  视频名称
  videoImage          |  String       |  N     | 视频图片地址
  videoUrlFlu         |  String      | 视频地址 流畅,320px
    videoUrlSd          |  String      | 视频地址 标清,640px
    videoUrlHd          |  String      | 视频地址 高清,1280px
    videoUrlFhd         |  String      | 视频地址 蓝光,1920px
  videoAuthor         |  String       |  N     |  视频导演
  publishDate         |  long         |  N     | 视频发布时间(毫秒的时间截)
  information         |  String       |  N     |  视频介绍信息
  superiorVideo       |  String       |  N     |  优品推荐排序,大于或等于1
  hotVideo            |  String       |  N     |  热播推荐排序,大于或等于1

	

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
   id             |  long      |  大于>1成功, 0失败
#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "id":12
   },
   "msg":"成功",
   "times":1554364608797
}

 ```
  


<h3 id=1.6.2>编号2     &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 分页获取视频列表  /video/getVideoList
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型      |  必填   |  说明
  ----                |  ----      |  ----  |  ----
  pageNo              |  int       |  Y     |   页码,初始值为1.
  pageSize            |  int       |  Y     |  页大小,条数(最大是100条)
  type                |  int       |  Y     |  1.全部 2.最新的  3.热门的 4.优品推荐,详情请见下面参数

  

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  videoName           |  String        |  视频名称
  videoImage          |  String        | 视频图片地址
  videoAuthor         |  String        |  视频导演
  videoLength         |  long          | 视频播放时长(单位是秒)
  publishDate         |  long          | 视频发布时间(毫秒的时间截)
  information         |  String        |  视频介绍信息
    commentCount        |  long        | 评论总数
    shareCount          |  long        | 分享总数
    storeCount          |  long        | 收藏总数
    playCount           |  long        | 播放总次数
    praiseCount         |  long        | 点赞总数

#### type 参数值
```json
   VIDEO_ALL(1,"id","DESC", "全部"),
    VIDEO_NEW(2,"id","DESC", "最新的"),
    VIDEO_HOT(3,"hot_video","DESC", "热门的"),
    VIDEO_SUPERIOR(4,"superior_video","DESC", "优品推荐"),
    VIDEO_YOU_LIKE(5,"superior_video","DESC", "你喜欢的"),
    VIDEO_PLAY_COUNT(11,"play_count","DESC", "最多播放"),
    VIDEO_STORE_COUNT(12,"store_count","DESC", "最多喜欢"),
    VIDEO_PRAISE_COUNT(13,"praise_count","DESC", "最高评价"),
    VIDEO_SHARE_COUNT(14,"share_count","DESC", "分享数"),
    VIDEO_COMMENT_COUNT(15,"comment_count","DESC", "评论数"),
```

#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "isNextPage":0,
      "list":
      [
         {
            "commentCount":0,
            "id":5,
            "information":"日本电影",
            "playCount":0,
            "praiseCount":0,
            "publishDate":1557199996000,
            "shareCount":0,
            "storeCount":0,
            "videoAuthor":"日本",
            "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNe2AIOBuAALZIuFyAGc859.jpg",
            "videoLength":0,
            "videoName":"fate zero"
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}
```


<h3 id=1.6.3> 编号3     &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 获取视频详情信息  /video/getVideoInfo 
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
  videoId             |  long       |  Y     |  视频id

	

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
   id                 |  long       |  视频信息id
  classify            |  String      |  视频分类
  label               |  String      |  视频标签
  videoName           |  String      |  视频名称
  videoImage          |  String      | 视频图片地址
  videoUrlFlu         |  String      | 视频地址 流畅,320px
  videoUrlSd          |  String      | 视频地址 标清,640px
  videoUrlHd          |  String      | 视频地址 高清,1280px
  videoUrlFhd         |  String      | 视频地址 蓝光,1920px
  videoAuthor         |  String      |  视频导演
  videoLength         |  long          | 视频播放时长(单位是秒)
  publishDate         |  long        | 视频发布时间(毫秒的时间截)
  information         |  String      |  视频介绍信息
  superiorVideo       |  String      |  优品推荐排序,大于或等于1
  hotVideo            |  String      |  热播推荐排序,大于或等于1



#### 响应数据
```json
{
   "code":0,
   "data":
   {
       "classify":0,
      "hotVideo":0,
      "id":2,
      "information":"information123456789",
      "label":0,
      "publishDate":1555491664000,
      "superiorVideo":0,
      "videoLength": 1000,
      "videoAuthor":"videoAuthor",
      "videoImage":"videoImage",
      "videoName":"11111222215819123354",
      "videoUrlFhd":"蓝光,1920px",
      "videoUrlFlu":"流畅,320px",
      "videoUrlHd":"高清,1280px",
      "videoUrlSd":"标清,640px"
   },
   "msg":"成功"
}

```



<h3 id=1.6.4> 编号4     &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>


## 查看获取视频详情信息包括统计数据  /video/getVideoCount 
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
  videoId             |  long       |  Y     |  视频id

	

#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
    id                 |  long       |  视频信息id
   classify            |  String      |  视频分类
   label               |  String      |  视频标签
   videoName           |  String      |  视频名称
     videoUrlFlu         |  String      | 视频地址 流畅,320px
     videoUrlSd          |  String      | 视频地址 标清,640px
     videoUrlHd          |  String      | 视频地址 高清,1280px
     videoUrlFhd         |  String      | 视频地址 蓝光,1920px
   videoImage          |  String      | 视频图片地址
   videoAuthor         |  String      |  视频导演
   videoLength         |  long          | 视频播放时长(单位是秒)
   publishDate         |  long        | 视频发布时间(毫秒的时间截)
   information         |  String      |  视频介绍信息
   superiorVideo       |  String      |  优品推荐排序,大于或等于1
   hotVideo            |  String      |  热播推荐排序,大于或等于1
   commentCount        |  long        | 评论总数
   shareCount          |  long        | 分享总数
   storeCount          |  long        | 收藏总数
   playCount           |  long        | 播放总次数
   praiseCount         |  long        | 点赞总数
   stepOnCount         |  long        | 点踩总数




#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "classify":0,
      "hotVideo":0,
      "id":2,
      "information":"information123456789",
      "label":0,
      "publishDate":1555491664000,
      "superiorVideo":0,
       "videoLength": 1000,
      "videoAuthor":"videoAuthor",
      "videoImage":"videoImage",
      "videoName":"11111222215819123354",
      "videoUrlFhd":"蓝光,1920px",
      "videoUrlFlu":"流畅,320px",
     "videoUrlHd":"高清,1280px",
     "videoUrlSd":"标清,640px",
      "commentCount":0,
      "shareCount":0,
      "storeCount":0,
      "playCount":0,
      "praiseCount":0,
      "stepOnCount":1
   },
   "msg":"成功"
}

```






<h3 id=1.6.5> 编号5    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>


## 查看获取视频详情信息包括统计数据 /video/getReportCount
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
  videoId             |  long       |  Y     |  视频id

	

#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
    id                 |  long       |  视频信息id
   commentCount        |  long        | 评论总数
   shareCount          |  long        | 分享总数
   storeCount          |  long        | 收藏总数
   playCount           |  long        | 播放总次数
   praiseCount         |  long        | 点赞总数



#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "id":2,
      "commentCount":0,
      "shareCount":0,
      "storeCount":0,
      "playCount":0,
      "praiseCount":0
   },
   "msg":"成功"
}

```




<h3 id=1.6.6> 编号6    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 发表视频评论接口 post  /video/saveComment
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
  videoId             |  long       |  Y     |  视频id
  content             |  String     |  Y     |  评论内容
	

#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
    id                 |  long       |  视频评论id



#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "id":4
   },
   "msg":"成功"
}

```

<h3 id=1.6.7> 编号7    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>


## 获取指定视频评论接口 get  /video/getCommentList 
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----       |  ----  |  ----
  videoId             |  long       |  Y     |  视频id
  pageNo              |  int        |  Y     |   页码,初始值为1.
  pageSize            |  int        |  Y     |  页大小,条数(最大是100条)
    	

#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
    id                 |  long       |  视频评论id
    content            |  String     |  视频评论内容
    praiseCount        |  int        |  视频评论点赞总数
    videoId            |  long       |  视频id
    userId             |  long       |  视频评论用户id
    userImage          |  String     |  视频评论用户头像
    userName           |  String     |  视频评论昵称



#### 响应数据
```json
 {
   "code":0,
   "data":
   {
      "isNextPage":0,
      "list":
      [
         {
            "content":"VideoComment VideoComment2 4",
            "id":4,
            "praiseCount":0,
            "status":0,
            "userId":1,
            "userImage":"",
            "userName":"23421",
            "videoId":4
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}

```

<h3 id=1.6.8> 编号8    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>


## 获取指定视频评论接口 get  /video/delComment 
#### 工程	video 
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----       |  ----  |  ----
  commentIds                 |  String     |  Y     |  ids为评论表ID,支持删除多个,id之间用char7隔开
    	

#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
   result              |  int         | 1或0,1.为成功,0.为失败;


#### 响应数据
```json
 {
   "code":0,
   "data":
   {
      "result":1
   },
   "msg":"成功"
}

```



<h3 id=1.6.9> 编号9    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

##  对视频点赞或点踩接口 post /video/saveUserPraise
#### 工程	video
#### 测试用例：

#### 请求参数
  参数              |  类型     |  必填   |  说明
  ----              |  ----     |  ----   |  ----
  userId             |  long       |     Y     | 用户id
  videoId            |  long       |     Y     | 视频id
  type               |  int        |     Y    | 0.没有关系,4.是点赞,5.是点踩
   	
   
#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
   id                 |  long       |  点赞关系id
   
   
#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "id":4
   },
   "msg":"成功"
}
```
   


<h3 id=1.6.10> 编号10    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 分布获取我对视频点赞或点踩接口 get /video/getUserPraiseList 
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----   |  ----
  userId              |  long      |  Y      |  用户ID
  pageSize            |  int       |  Y      |  页大小
  pageNo              |  int       |  Y      |  页码
	

#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
    id                 |  long       |  点赞关系id
    userId             |  long       |  用户id
    videoId            |  long       |  视频id



#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "isNextPage":0,
      "list":
      [
         {
            "id":1,
            "type":4,
            "userId":1,
            "videoId":4
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}

```   



<h3 id=1.6.11> 编号11    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>


## 验证用户与视频是否点赞或点踩关系接口 get /video/isUserPraise
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----   |  ----
    userId             |  String       |  用户id,
    videoId            |  long       |  视频id
	

#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
    id                 |  long       |  点赞关系id
    userId             |  long       |  用户id
    videoId            |  long       |  视频id
    type               |  int        |  0.没有关系,4.是点赞,5.是点踩



#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "id":1,
      "type":4, 
      "userId":1,
      "videoId":4
   },
   "msg":"成功"
}

```


<h3 id=1.6.12> 编号12    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>


## 删除用户与视频是否点赞或点踩接口 post /video/delUserPraise  
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----   |  ----
    praiseIds         |  String      | Y  |  ids为点赞表关系ID,支持删除多个,id之间用char7隔开
	

#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
   result              |  int         | 1或0,1.为成功,0.为失败;


#### 响应数据
```json
 {
   "code":0,
   "data":
   {
      "result":0
   },
   "msg":"成功"
}

```





<h3 id=1.6.13> 编号13    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

 ## 对视频收藏接口 post /video/saveUserStore
#### 工程	video
#### 测试用例：

#### 请求参数
 参数              |  类型     |  必填   |  说明
 ----              |  ----     |  ----   |  ----
userId             |  long       |     Y     | 用户id
videoId            |  long       |     Y     | 视频id
   	
   
#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
   id                 |  long       |  收藏关系id
   
   
   
#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "id":5
   },
   "msg":"成功"
}
```
 
    
<h3 id=1.6.14> 编号14   &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 分页获取我对视频收藏列表接口 get /video/getUserStoreList
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----   |  ----
  userId              |  long      |  Y      |  用户ID
  pageSize            |  int       |  Y      |  页大小
  pageNo              |  int       |  Y      |  页码
	

#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
    id                 |  long       |  收藏表id
    userId             |  long       |  用户id
    videoId            |  long       |  视频id
    isStore            |  int        |  是否收藏1.是否收,0.否
    videoImage         |  String     |  视频介绍图片
    videoName          |  String     |  视频介绍名称



#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "isNextPage":0,
      "list":
      [
         {
            "id":3,
            "isStore":1,
            "userId":1,
            "videoId":2,
            "videoImage":"videoImage",
            "videoName":"11111222215819123354"
         },
         {
            "id":1,
            "isStore":1,
            "userId":1,
            "videoId":4,
            "videoImage":"videoImage",
            "videoName":"11111222215819123354"
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}

```  



<h3 id=1.6.15> 编号15   &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 删除/取消收藏 用户收藏的视频接口 post /video/delUserStore
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----   |  ----
    storeIds          |  String       |  用户对视频收藏的关系id,storeId 1,2,3,4(逗号隔开或char 7)
	

#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
   result              |  int         | 1或0,1.为成功,0.为失败;


#### 响应数据
```json
 {
   "code":0,
   "data":
   {
      "result":0
   },
   "msg":"成功"
}

```


<h3 id=1.6.16> 编号16   &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 保存用户播放历史列表 接口 post /video/saveUserPlay
#### 工程	video
#### 测试用例：

#### 请求参数
 参数              |  类型     |  必填   |  说明
 ----              |  ----     |  ----   |  ----
    userId             |  long       |     Y     | 用户id
    videoId            |  long       |     Y     | 视频id
    playLength         |  int        |     N     | 视频时长
   	
   
#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
   id                 |  long       |  用户播放历史id
   
   
   
#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "id":5
   },
   "msg":"成功"
}
```



<h3 id=1.6.17> 编号17    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 分页获取用户播放历史列表 get /video/getUserPlayList
#### 工程	video
#### 测试用例：

#### 请求参数
 参数              |  类型     |  必填   |  说明
 ----              |  ----     |  ----   |  ----
  userId              |  long      |  Y      |  用户ID
  pageSize            |  int       |  Y      |  页大小
  pageNo              |  int       |  Y      |  页码
   	
   
#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
   id                 |  long       |  用户播放历史id
   userId             |  long       |  用户id
   videoId            |  long       |  视频id
   videoImage         |  String     |  视频介绍图片
   videoName          |  String     |  视频介绍名称
   playLength         |  int        |  播放时长
   modifyDate         |  long       |  播放时间（时间戳）
   
   
#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "isNextPage":0,
      "list":
      [
         {
            "id":2,
            "modifyDate":1558338201000,
            "userId":1,
            "videoId":3,
            "videoImage":"videoImage",
            "videoName":"11111222215819123354"
         },
         {
            "id":1,
            "modifyDate":1558338201000,
            "userId":1,
            "videoId":4,
            "videoImage":"videoImage",
            "videoName":"11111222215819123354"
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}
```
 
 
 
<h3 id=1.6.18> 编号18    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 删除用户视频播放历史记录 接口 post /video/delUserPlay
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----   |  ----
    playIds               |  String        | Y  |  ids为播放历史表关系ID,支持删除多个,id之间用char7隔开 视频id 1,2,3,4(逗号隔开或char 7)
	

#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
   result              |  int         | 1或0,1.为成功,0.为失败;


#### 响应数据
```json
 {
   "code":0,
   "data":
   {
      "result":0
   },
   "msg":"成功"
}

```




<h3 id=1.6.19>编号19    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 获取电影分类 get   /video/getClassifyList
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
  classifyImage       |  String    |  分类名称标清贞
  classifyName        |  String    |  分类名称
  classifyOrder       |  int       |  排序字段升序;
  status              |  int       |  是否有效

 


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
            "classifyImage":"classifyImage",
            "classifyName":"classifyName",
            "classifyOrder":1,
            "id":1,
            "status":1
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}   

```



<h3 id=1.6.20>编号20    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 保存电影分类 post   /video/saveClassify
#### 工程	resource
#### 测试用例：

#### 请求参数：
参数                  |  类型       |  说明
  ----               |  ----      |  ----
  classifyImage       |  String    |  分类名称标清贞
  classifyName        |  String    |  分类名称
  classifyOrder       |  int       |  排序字段升序;
  status              |  int       |  是否有效

  
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




<h3 id=1.6.21 > 编号21   &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 获取电影标签 get   /video/getLabelList
#### 工程	resource
#### 测试用例：

#### 请求参数：
参数                   |  类型   | 必填    |  说明
  ----                |  ----   |  ----   |  ----
hasAll                |  int    |  N      |  是否包含（全部标签） 1：包含    0：不包含
type                  |  int    |  N      |  类型 参数 11：最多播放；12：最多喜欢；13：最高评介；14：最高分享数；15：最高评论数; 默认：1
pageNo                |  int    |  Y      |  页码(1,2,3)
pageSize              |  int    |  Y      |  页大小:50


#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  id                  |  long      |  广告bannerId;
  labelImage          |  String    |  分类名称标清贞
  labelName           |  String    |  分类名称
  labelOrder          |  int       |  排序字段升序;
  status              |  int       |  是否有效

 


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
             "labelImage":"labelImage",
            "labelName":"labelName",
            "labelOrder":1,
            "id":1,
            "status":1
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}   

```



<h3 id=1.6.22> 编号22    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 保存电影标签 post   /video/saveLabel
#### 工程	resource
#### 测试用例：

#### 请求参数：
参数                  |  类型       |  说明
  ----               |  ----      |  ----
  labelImage       |  String    |  分类名称标清贞
  labelName        |  String    |  分类名称
  labelOrder       |  int       |  排序字段升序;
  status              |  int       |  是否有效

  
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



<h3 id=1.6.23> 编号23    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 获取频道列表信息 get   /video/getStarFrequency
#### 工程	resource
#### 测试用例： http://127.0.0.1:9020/top/video/getStarFrequency?appId=1005&cliSign=818d88c05fb09781&pageNo=1&pageSize=10&platform=1&version=10200500

#### 请求参数：
参数                  |  类型       |  说明
  ----               |  ----      |  ----
  pageNo             |  int       |  页码
  pageSize           |  int       |  页大小

  
#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  id                  |  long       |  明星表ID
  starName            |  String    |  明星名称
  starImage           |  String    |  明星头像
  starInfo            |  String    |  明星介绍;
  videoCount          |  int       |  明星拥有电影数量


#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
   videoId            |  long       |  电影ID
   videoName           |  String    |  电影名称
   videoImage          |  String    |  电影头像
   publishDate          |  long     |  发布时间
   videoLength          |  int      |  播放时长
   information            String    |  电影介绍


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
            "id":2,
            "list":
            [
               {
                 "information":"明日战记电影",
                 "publishDate":1557200666000,
                 "videoId":8,
                 "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNiKABquKAAJxz3czLMk277.jpg",
                 "videoLength":0,
                 "videoName":"明日战记"
              }

            ],
            "starImage":"starImage2",
            "starInfo":"starInfo2",
            "starName":"starName2",
            "videoCount":22
         },
         {
            "id":1,
            "list":
            [
              {
                  "information":"明日战记电影",
                  "publishDate":1557200666000,
                  "videoId":8,
                  "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNiKABquKAAJxz3czLMk277.jpg",
                  "videoLength":0,
                  "videoName":"明日战记"
               }

            ],
            "starImage":"starImage",
            "starInfo":"starInfo",
            "starName":"starName",
            "videoCount":2
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}
```


<h3 id=1.6.24> 编号24    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 通过明星ID获取视频信息 get   /video/getVideoListByStarId
#### 工程	resource
#### 测试用例： 

#### 请求参数：
参数                  |  类型       |  说明
  ----               |  ----      |  ----
  pageNo             |  int       |  页码
  pageSize           |  int       |  页大小
  starId             |  long      |  明星ID


#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
   videoId            |  long       |  电影ID
   videoName           |  String    |  电影名称
   videoImage          |  String    |  电影头像
   publishDate          |  long     |  发布时间
   videoLength          |  int      |  播放时长
   information            String    |  电影介绍


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
             "information":"明日战记电影",
             "publishDate":1557200666000,
             "videoId":8,
             "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNiKABquKAAJxz3czLMk277.jpg",
             "videoLength":0,
             "videoName":"明日战记"
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}
```



<h3 id=1.6.25> 编号25    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 获取明星详情信息 get   /video/getStar
#### 工程	resource
#### 测试用例：

#### 请求参数：
  参数                  |  类型       |  说明
  ----                 |  ----      |  ----
  videoStarId          |  long       |  明星表Id

  
#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  id                  |  long       |  明星表ID
  starName            |  String    |  明星名称
  starImage           |  String    |  明星头像
  starInfo            |  String    |  明星介绍;
  videoCount          |  int       |  明星拥有电影数量
  starSex             |  int       |  明星性别:0.不详,1.女, 2.男
  starOrder           |  int       |  排序字段
  starHot             |  int       |  热门明星



#### 响应数据：
```json
{
   "code":0,
   "data":
   {
      "id":1,
      "starHot":1,
      "starImage":"starImage",
      "starInfo":"starInfo",
      "starName":"starName",
      "starOrder":1,
      "starSex":1,
      "starSpecial":1,
      "status":1,
      "videoCount":2
   },
   "msg":"成功"
}
```


<h3 id=1.6.26> 编号26    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 获取明星列表信息 get   /video/getStarList
#### 工程	resource
#### 测试用例：

#### 请求参数：
  参数                  |  类型       |  说明
  ----               |  ----      |  ----
  pageNo             |  int       |  页码
  pageSize           |  int       |  页大小

  
#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  id                  |  long       |  明星表ID
  starName            |  String    |  明星名称
  starImage           |  String    |  明星头像
  starInfo            |  String    |  明星介绍;
  videoCount          |  int       |  明星拥有电影数量
  starSex             |  int       |  明星性别:0.不详,1.女, 2.男
  starOrder           |  int       |  排序字段

 


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
            "id":1,
            "starHot":1,
            "starImage":"starImage",
            "starInfo":"starInfo",
            "starName":"starName",
            "starOrder":1,
            "starSex":1,
            "starSpecial":1,
            "status":1,
            "videoCount":2
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}
```


<h3 id=1.6.27> 编号27    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 保存明星关系信息 post   /video/saveStarShip
#### 工程	resource
#### 测试用例：

#### 请求参数：
  参数                  |  类型       |  说明
  ----               |  ----      |  ----
  starId             |  long       |  明星ID
  videoId            |  long       |  电影ID

  
#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  id                  |  long       |  明星表ID

#### 响应数据：
```json
{
   "code":0,
   "data":
   {
      "id":9
   },
   "msg":"成功"
}
```



<h3 id=1.6.28>编号28     &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 通过分类和排序条件分页获取视频列表 /video/getVideoClassifyList
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型      |  必填   |  说明
  ----                |  ----      |  ----  |  ----
  pageNo              |  int       |  Y     |   页码,初始值为1.
  pageSize            |  int       |  Y     |  页大小,条数(最大是100条)
  type                |  int       |  Y     |  1.全部 2.最新的  3.热门的 4.优品推荐,详情请见下面参数
  classifyId          |  int       |  Y     |  0,1.全部,其它为分类表classifyId的值;

  

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----       |  ----
  videoName           |  String        |  视频名称
  videoImage          |  String        | 视频图片地址
  videoAuthor         |  String        |  视频导演
  videoLength         |  long          | 视频播放时长(单位是秒)
  publishDate         |  long          | 视频发布时间(毫秒的时间截)
  information         |  String        |  视频介绍信息
    commentCount        |  long        | 评论总数
    shareCount          |  long        | 分享总数
    storeCount          |  long        | 收藏总数
    playCount           |  long        | 播放总次数
    praiseCount         |  long        | 点赞总数

#### type 参数值
```json
   VIDEO_ALL(1,"id","DESC", "全部"),
    VIDEO_NEW(2,"id","DESC", "最新的"),
    VIDEO_HOT(3,"hot_video","DESC", "热门的"),
    VIDEO_SUPERIOR(4,"superior_video","DESC", "优品推荐"),
    VIDEO_YOU_LIKE(5,"superior_video","DESC", "你喜欢的"),
    VIDEO_PLAY_COUNT(11,"play_count","DESC", "最多播放"),
    VIDEO_STORE_COUNT(12,"store_count","DESC", "最多喜欢"),
    VIDEO_PRAISE_COUNT(13,"praise_count","DESC", "最高评价"),
    VIDEO_SHARE_COUNT(14,"share_count","DESC", "分享数"),
    VIDEO_COMMENT_COUNT(15,"comment_count","DESC", "评论数"),
```

#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "isNextPage":0,
      "list":
      [
         {
            "commentCount":0,
            "id":5,
            "information":"日本电影",
            "playCount":0,
            "praiseCount":0,
            "publishDate":1557199996000,
            "shareCount":0,
            "storeCount":0,
            "videoAuthor":"日本",
            "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNe2AIOBuAALZIuFyAGc859.jpg",
            "videoLength":0,
            "videoName":"fate zero"
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}
```



<h3 id=1.6.29> 编号29    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>


## 验证用户与视频列表是否收藏关系接口 get /video/isUserStoreList
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                  |  类型       |  必填   |  说明
  ----                 |  ----       |  ----   |  ----
    userId             | long       |  用户id,
    videoIds           |  String    |  视频id,中间用char s = 7 隔开,eg: id1+s+id2+s+id3
	

#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
    storeId            |  long       |  用户与视频收藏ID
    userId             |  long       |  用户id
    videoId            |  long       |  视频id
    isStore            |  int        |  1.收藏,0未收藏



#### 响应数据
```json
{
   "code":0,
   "data":
   [
      {
         "storeId":123,
         "isStore":0,
         "userId":1,
         "videoId":1
      },
      {
       "storeId":124,
         "isStore":1,
         "userId":1,
         "videoId":2
      },
      {
        "storeId":125,
         "isStore":0,
         "userId":1,
         "videoId":4
      }

   ],
   "msg":"成功"
}

```
<h3 id=1.6.30> 编号30    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>


## 标签搜索电影接口 get /video/getLabelVideoPage
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                  |  类型       |  必填   |  说明
  ----                  |  ----      |  ----   |  ----
    pageNo              |  int       |   Y     |  页码(1,2,3)
    pageSize            |  int       |   Y     |  页大小:50
    type                |  int       |   N     |  类型 参数 11：最多播放；12：最多喜欢；13：最高评介；14：最高分享数；15：最高评论数;  默认：1
    labelIds            |  String    |   Y     |  标签ID 多个1,2,3,4  用逗号隔开或char 7


#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
    information         |  String     |  视频信息
    publishDate         |  date       |  视频发布时间
    videoId             |  long       |  视频ID
    videoImage          |  String     |  视频图片
    videoLength         |  int        |  播放时长
    videoName           |  String     |  视频名称




#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "isNextPage":0,
      "list":
      [
         {
            "information":"日本电影",
            "publishDate":1557199996000,
            "videoId":5,
            "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNe2AIOBuAALZIuFyAGc859.jpg",
            "videoLength":0,
            "videoName":"fate zero"
         },
         {
            "information":"漫画电影",
            "publishDate":1557200521000,
            "videoId":6,
            "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNiKAdLYpAAPFiGUmUXY932.jpg",
            "videoLength":0,
            "videoName":"漫画电影"
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}

```


<h3 id=1.6.31> 编号31    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>


## 根据视频ID获取对应标签和标签相关视频 get /video/getLabelAndVideoPage
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                  |  类型       |  必填   |  说明
  ----                  |  ----      |  ----   |  ----
    pageNo              |  int       |   Y     |  页码(1,2,3)
    pageSize            |  int       |   Y     |  页大小:50
    videoId             |  long      |   Y     |  视频ID


#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
    label               |  list       |  标签集合
    labelImage          |  String     |  标签图片
    labelName           |  String     |  名称
    labelOrder          |  int        |  排序号
    status              |  int        |  状态
    list                |  list       |  视频集合
    information         |  String     |  视频信息
    publishDate         |  date       |  视频发布时间
    videoId             |  long       |  视频ID
    videoImage          |  String     |  视频图片
    videoName           |  String     |  视频名称
    videoLength         |  int        |  播放时长




#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "label":
      [
         {
            "id":3,
            "labelImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zUE2OAeHzPAAAaKmjLFKY533.png",
            "labelName":"综艺标签",
            "labelOrder":2,
            "status":1
         }

      ],
      "list":
      {
         "isNextPage":0,
         "list":
         [
            {
               "information":"漫画电影",
               "publishDate":1557200521000,
               "videoId":6,
               "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNiKAdLYpAAPFiGUmUXY932.jpg",
               "videoLength":0,
               "videoName":"漫画电影"
            },
            {
               "information":"日本电影",
               "publishDate":1557199996000,
               "videoId":5,
               "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNe2AIOBuAALZIuFyAGc859.jpg",
               "videoLength":0,
               "videoName":"fate zero"
            }

         ],
         "pageIndex":0
      }

   },
   "msg":"成功"
}

```

<h3 id=1.6.32> 编号32    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 根据用户ID和视频ID获取播放历史 get /video/getUserPlay
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                  |  类型       |  必填   |  说明
  ----                  |  ----      |  ----   |  ----
    userId              |  long      |   Y     |  用户ID
    videoId             |  long      |   Y     |  视频ID


#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
    id                  |  long       |  播放视频历史ID
    playLength          |  int        |  播放时长
    userId              |  long       |  用户ID
    videoId             |  long       |  视频ID
    videoImage          |  String     |  视频图片
    videoName           |  String     |  视频名称


#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "id":14,
      "playLength":71,
      "userId":1,
      "videoId":5,
      "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNe2AIOBuAALZIuFyAGc859.jpg",
      "videoName":"fate zero"
   },
   "msg":"成功"
}

```

<h3 id=1.6.33> 编号33    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 首页搜索 get /video/getVideoByKeyWordPage
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                  |  类型       |  必填   |  说明
  ----                  |  ----      |  ----   |  ----
    pageNo              |  int       |   Y     |  页码(1,2,3)
    pageSize            |  int       |   Y     |  页大小
    userId              |  long      |   N     |  用户ID
    keyWord             |  String    |   Y     |  搜索关键字


#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
    information         |  String     |  视频信息
    publishDate         |  date       |  视频发布时间
    videoId             |  long       |  视频ID
    videoImage          |  String     |  视频图片
    videoLength         |  int        |  播放时长
    videoName           |  String     |  视频名称


#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "isNextPage":0,
      "list":
      [
         {
            "information":"日本电影",
            "publishDate":1557199996000,
            "videoId":5,
            "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNe2AIOBuAALZIuFyAGc859.jpg",
            "videoLength":1,
            "videoName":"fate zero"
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}

```

<h3 id=1.6.34> 编号34    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 首页搜索 get /video/getTopSearchPage
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                  |  类型       |  必填   |  说明
  ----                  |  ----      |  ----   |  ----
    pageNo              |  int       |   Y     |  页码(1,2,3)
    pageSize            |  int       |   Y     |  页大小


#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
    data                |  list     |  关键字数组



#### 响应数据 （返回结果不做分页，需要多少数据通过pageNo，pageSize控制）
```json
{
   "code":0,
   "data":
   [
      "明日",
      "fate",
      "哈哈",
      "明日1"
   ],
   "msg":"成功"
}

```


<h3 id=1.6.34> 编号34    &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>

## 猜你喜欢 get get /video/getMayLikePage
#### 工程	video
#### 测试用例：

#### 请求参数
  参数                  |  类型       |  必填   |  说明
  ----                  |  ----      |  ----   |  ----
    isHome              |  boolean   |   Y     |  首页：true  更多  false
    userId              |  long      |   N     |  用户ID
    pageNo              |  int       |   Y     |  页码(1,2,3)
    pageSize            |  int       |   Y     |  页大小


#### 响应参数类型说明  	 具体参数如下：
   参数                 |  类型       |  说明
   ----                |  ----       |  ----
    data                |  list     |  关键字数组



#### 响应数据 （返回结果不做分页，需要多少数据通过pageNo，pageSize控制）
```json

true返回：
{
   "code":0,
   "data":
   {
      "isNextPage":0,
      "list":
      [
         {
            "commentCount":1,
            "id":5,
            "information":"日本电影",
            "playCount":1024,
            "praiseCount":1,
            "publishDate":1557199996000,
            "shareCount":8,
            "stepOnCount":1,
            "storeCount":3,
            "videoAuthor":"虚渊玄",
            "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNe2AIOBuAALZIuFyAGc859.jpg",
            "videoLength":1,
            "videoName":"fate zero"
         },
         {
            "commentCount":99,
            "id":6,
            "information":"漫画电影",
            "playCount":121,
            "praiseCount":12,
            "publishDate":1557200521000,
            "shareCount":10,
            "stepOnCount":1,
            "storeCount":12,
            "videoAuthor":"日本",
            "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNiKAdLYpAAPFiGUmUXY932.jpg",
            "videoLength":2,
            "videoName":"漫画电影"
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}


false返回：

{
   "code":0,
   "data":
   {
      "isNextPage":0,
      "list":
      [
         {
            "information":"日本电影",
            "publishDate":1557199996000,
            "videoId":5,
            "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNe2AIOBuAALZIuFyAGc859.jpg",
            "videoLength":1,
            "videoName":"fate zero"
         },
         {
            "information":"漫画电影",
            "publishDate":1557200521000,
            "videoId":6,
            "videoImage":"http://192.168.25.200/group1/M00/00/00/CgoK51zNNiKAdLYpAAPFiGUmUXY932.jpg",
            "videoLength":2,
            "videoName":"漫画电影"
         }

      ],
      "pageIndex":0
   },
   "msg":"成功"
}

```

