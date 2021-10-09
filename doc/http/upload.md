
## [主营项目接口文档](http-index.md)

### <h1 id = top>104.  [文件模块说明文档](upload.md)</h1>

<a id ="catalog"> [目录 视频接口 1.5](#1.4.0)</a>
- [1.4.1 按文件字节数组上传文件接口 post   /upload/fileByte](#1.4.1)
- [1.4.2 按文件字节数组分块断点上传文件接口 post   /upload/fileBlock](#1.4.2)
- [1.4.3 按文件上传接口 /upload/file](#1.4.3)
- [1.4.4 批量文件上传接口 /upload/mFile](#1.4.4)
- [1.4.5  ](#1.4.5)
- [1.4.6  ](#1.4.6)
- [1.4.7  ](#1.4.7)
- [1.4.8  ](#1.4.8)
- [1.4.9  ](#1.4.9)
- [1.4.10  ](#1.4.10)
- [1.4.11  ](#1.4.11)
- [1.4.12  ](#1.4.12)
- [1.4.13  ](#1.4.13)
- [1.4.14  ](#1.4.14)
- [1.4.15  ](#1.4.15)
- [1.4.16  ](#1.4.16)
- [1.4.17  ](#1.4.17)
- [1.4.18  ](#1.4.18)
- [1.4.19  ](#1.4.19)
- [1.4.20  ](#1.4.20)
- [1.4.21  ](#1.4.21)
- [1.4.22  ](#1.4.22)
- [1.4.23  ](#1.4.23)
- [1.4.24  ](#1.4.24)

<h3 id=1.4.1> 编号1 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 文件上传 post   /resource/upload
#### 工程	user
#### 测试用例：

#### 请求参数：
参数                 |  类型       |  必填   |  说  说明
  ----                |  ----     |  ----  |     ----
  blockSize           |  byte[]   |  Y     |     文件块字节数
  fileSize            |  int      |  Y     |     文件大小
  fileMd5             |  String   |  Y     |     文件md5字符串
  fileType            |  String   |  Y     |     文件类型.mp4/jpg
  offset              |  int      |  N     |     上传文件开始值:0

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  errorMsg            |  String     |  成功:OK,错误:提示信息
  offset              |  String     |  第几块
  path                |  String     |  文件路径
  status              |  int        |  状态:0.成功,1.失败
 
 


#### 响应数据：
```json
 {
    "code":0,
    "data":
    {
       "errorMsg":"OK",
       "offset":0,
       "path":"group1/M00/00/00/CgoK51zU7dCAaD1JAGcjWgC6-Cg230.mp4",
       "status":0
    },
    "msg":"成功"
 }
```
<h3 id=1.4.2> 编号2 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 文件分块断点上传 post   /resource/uploadBlock
#### 工程	user
#### 测试用例：

#### 请求参数：
参数                 |  类型       |  必填   |  说  说明
  ----                |  ----     |  ----  |     ----
  blockSize           |  byte[]   |  Y     |     文件块字节数
  fileSize            |  int      |  Y     |     文件大小
  fileMd5             |  String   |  Y     |     文件md5字符串
  fileType            |  String   |  Y     |     文件类型.mp4/jpg
  offset              |  int      |  N     |     上传文件开始值:0

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  errorMsg            |  String     |  成功:OK,错误:提示信息
  offset              |  String     |  第几块
  path                |  String     |  文件路径
  status              |  int        |  状态:0.成功,1.失败
 
 


#### 响应数据：
```json
 {
    "code":0,
    "data":
    {
       "errorMsg":"OK",
       "offset":0,
       "path":"group1/M00/00/00/CgoK51zU7dCAaD1JAGcjWgC6-Cg230.mp4",
       "status":0
    },
    "msg":"成功"
 }
```


<h3 id=1.4.3> 编号3 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 按文件上传接口 /upload/file
#### 工程	user
#### 测试用例：

#### 请求参数：
参数                 |  类型       |  必填   |  说  说明
  ----                |  ----     |  ----  |     ----
   fileSize            |  int      |  Y     |     文件大小
   fileMd5             |  String   |  Y     |     文件md5字符串
   files               |  File     |  Y     |     multipart/form-data类型的文件

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  errorMsg            |  String     |  成功:OK,错误:提示信息
  offset              |  String     |  第几块
  path                |  String     |  文件路径
  status              |  int        |  状态:0.成功,1.失败
 
 


#### 响应数据：
```json
{
    "code":0,
    "data":
     
    {
       "errorMsg":"OK",
       "offset":0,
       "path":"group1/M00/00/00/CgoK51zU7dCAaD1JAGcjWgC6-Cg230.mp4",
       "status":0
    },
    "msg":"成功"
 }
```

<h3 id=1.4.4> 编号4 &emsp;&emsp;<a href=#top>回到顶部</a>&emsp;&emsp;<a href=#catalog>回到目录</a></h3>  

## 文件分块断点上传 post   /upload/mFile
#### 工程	user
#### 测试用例：

#### 请求参数：
参数                 |  类型       |  必填   |  说  说明
  ----                |  ----     |  ----  |     ----
  fileSize            |  int      |  Y     |     文件大小
  fileMd5             |  String   |  Y     |     文件md5字符串
  files               |  File[]   |  Y     |     multipart/form-data类型的文件

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  errorMsg            |  String     |  成功:OK,错误:提示信息
  path                |  String     |  文件路径
  status              |  int        |  状态:0.成功,>0.失败
 
 


#### 响应数据：
```json
{
 "code":0,
 "data":
  [
    {
        "errorMsg":"OK",
        "offset":0,
        "path":"group1/M00/00/00/CgoK51zU7dCAaD1JAGcjWgC6-Cg230.mp4",
        "status":0
   }
 ],
 "msg":"成功"
}
```

