## [主营项目接口文档](http-index.md)

### <h1 id = top>101.  [配置模块说明文档](config.md)</h1>

<a id ="catalog"> [目录 视频接口 1.5](#1.9.0)</a>
- [1.9.1 获取软件最新版本接口  get  /config/getVersion](#1.9.1)
- [1.9.2 ] (#1.9.2)
- [1.9.3 ](#1.9.3)
- [1.9.4 ] (#1.9.4)
- [1.9.5  ](#1.9.5)
- [1.9.6  ](#1.9.6)
- [1.9.7  ](#1.9.7)
- [1.9.8  ](#1.9.8)
- [1.9.9  ](#1.9.9)
- [1.9.10  ](#1.9.10)
- [1.9.11  ](#1.9.11)
- [1.9.12  ](#1.9.12)
- [1.9.13  ](#1.9.13)
- [1.9.14  ](#1.9.14)
- [1.9.15  ](#1.9.15)
- [1.9.16  ](#1.9.16)
- [1.9.17  ](#1.9.17)
- [1.9.18  ](#1.9.18)
- [1.9.19  ](#1.9.19)
- [1.9.20  ](#1.9.20)
- [1.9.21  ](#1.9.21)
- [1.9.22  ](#1.9.22)
- [1.9.23  ](#1.9.23)
- [1.9.24  ](#1.9.24)

<h3 id=1.9.1> 编号1 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 获取软件最新版本接口  get  /config/getVersion
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
 

	

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  platform            |  long      |  平台
  version             |  String    |  版本号
  channelCode         |  int       |  渠道编码
  forceUpdate         |  int       |  是否强制升级,默认0,否, 1.是强制升级,2.有新版本
  download            |  String    |  最新版本下载地址
  description         |  String    |  最新版本内容介绍

  	

  


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

