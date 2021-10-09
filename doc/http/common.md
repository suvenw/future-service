#  通信协议公共说明
## [主营项目接口文档](http-index.md)
### 100.  [公共模块说明文档](common.md)

## 概述

一. 服务器：

    正式环境地址
    

    开发环境地址
	http://192.168.25.200
	
	测试环境地址
	http://192.168.25.200
	
二. 格式说明

	1.所有内容为UTF-8编码
	
	2.int不带""
	
	3.get方式时Encode的内容只能为key对应的value值，保留?、=、&,参数不能带属性为body(服务器端已占用)

三. 接口编号规则:

    1. 接口编号长度统一为7位数
    
    2. 第一个表示项目(红手指-1)，
    
    3. 第二位表示功能类型：1.弹窗提示，2.软接连，3.跳转url
    
    3. 第三，四位表示项目模块
		01	 用户 user
		02	 验证 oauth
		03	 资源 resource
		04  支付 pay
		05  资产 assets
		06  视频 pad  
		07  订单 order
		09  配置 config
		11  MQ mq
		
		15  日志记录模块
    4. 第五，六，七位按顺序定义业务错误编码
    
#### 公共错误码
编码        |说明 
---         |--- 
1100000     |系统繁忙!
1100001     |请重新登录
1100002     |无效请求
1100003     |参数请求错误，%s！（%s是具体的参数问题）
1100004     |参数检验不符要求！
1100005     |cuid不合法！
1100006     |服务维护中！


	
#### POST请求公共请求参数
属性        |必选   |类型    	|说明
---         |---    |---    	|---
appId       |true   |int  	    |客户端申请的appid( 100000001)
accessToken |false  |string     |令牌,需登录的接口必传
userId      |false  |long    	|用户id,需登录的接口必传
version     |true   |int    	|客户端app版本（前2位产品,中间两位大版本，最后三位补丁例如2.1.1就是201001）
sysVersion  |true   |string    	|客户端操作系统版本号
platform   	|true   |int  		|平台(0."缺省平台" 1,"苹果手机"  2,"安卓手机" 3,"window 手机" 4,"网站" 5,"H5小程序"  6,"官方网站")
device      |false  |string     |设备标识
times    	|false  |long    	|时间戳13位1387614995111
channel     |false  |String    	|渠道编码
ip          |false  |String    	|ip
cliSign   	|true   |string     |签名机制16位，全部小写(登录接口必传)
...   		|true   |...  		|具体的参数比如playerId

#### GET请求公共请求参数
属性        |必选   |类型    	|说明
---         |---    |---    	|---
appId       |true   |int  	    |客户端申请的appid(红手指国内 100000001)
version    	|true   |int        |客户端版本（前2位产品,中间两位大版本，最后三位补丁例如2.1.1就是201001）
platform    |true   |int     	|平台(ANDROID 1 IOS 2 PC 3 云控 4 官网 5)
cliSign   	|true   |string     |签名机制16位，全部小写(登录接口必传)
...   		|true   |...  		|具体的参数比如playerId
接口签名规范:

	1.需要参于签名的参数：
	
		a. 在请求参数列表中，除去 cliSign 参数外，其他需要使用到的参数皆是要签名的参数。
		
	2.生成签名字符串
	
		a.	没有值的参数无需传递，也无需包含到待签名数据中
		
		b.	签名数据应该是原生值而不是 encoding 之后的值
		
		c.	若遇参数值为数组时，请以char=7对应字符进行分割此参数的多个值
		
		d. 按签名参数 a 到 z 的顺序排序（"&"是分割开出多个参数）
		
			签名参数字符串如下：
			
			params = channel =0&password=abc&userid=13876
			
			cliSign = md5(params + appkey) 注：“+”加与为字符串相连符，不在签名字符里,appkey为秘钥，为双方平台约定字符
			
		e.  针对部分CDN接口若有传中文，客户端请在对中文URLEncoder后加密
		
	3.开发测试环境签名密钥:
	
		appkey="H@s0zRed!fiNger8"

#### 公共返回协议
属性        |必选   |类型    	|说明
---         |---    |---    	|---
code    	|true   |int     	|状态码
msg    		|true   |string     |状态描述
times      	|true   |long    	|时间戳 13位1387614995111
data     	|true   |...  		|详细字段说明


