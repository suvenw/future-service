## [主营项目接口文档](http-index.md)
### 105.  [资产模块说明文档](assets.md)
## 编号1  生成商品订单  /assets/addOrder
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
userId                |  Long      |  Y  |  用户ID
goodsId               |  Long      |  Y  |  商品id

  

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型     |  说明
id    |  Long     |  订单id




#### 响应数据
```json
 {
   "code":0,
   "data":
   {
      "id":1
   },
   "msg":"成功",
   "times":1554364365244
}
```


## 编号2 订单支付   /assets/payOrder
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
  userId             |  int       |  N     |  用户ID
  orderId            |  int       |  N     | 订单号
  payCount           |  int       |  N     |  支付金额,单位为分,1元== 100分

	

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  result                  |  long      |  1.成功,0失败



#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "result":1
   },
   "msg":"成功",
   "times":1554364608797
}

```


## 编号3 订单分成实现   /assets/intoOrder
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
  userId             |  int       |  N     |  用户ID
  orderId            |  int       |  N     | 订单号

	

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
   userId             |  long      |  1.成功,0失败
  orderId             |  long      |  订单号
  payCount            |  long      |  1支付金额;单位为分.1元== 100分
  payStatus           |  long      |  支付状态,1.完成,2.未支付
  payType             |  long      |  支付类型
    


#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "orderId":0,
      "payCount":0,
      "payStatus":0,
      "payType":0,
      "userId":0
   },
   "msg":"成功",
   "times":1554364844288
}

```



## 编号3 查看订单  /assets/getOrder
#### 工程	hsz
#### 测试用例：

#### 请求参数
  参数                 |  类型       |  必填   |  说明
  ----                |  ----      |  ----  |  ----
  userId             |  int       |  N     |  用户ID
  orderId            |  int       |  N     | 订单号

	

#### 响应参数类型说明  	 具体参数如下：
  参数                 |  类型       |  说明
  ----                |  ----      |  ----
  result                  |  long      |  1.成功,0失败



#### 响应数据
```json
{
   "code":0,
   "data":
   {
      "result":1
   },
   "msg":"成功",
   "times":1554364608797
}

```


