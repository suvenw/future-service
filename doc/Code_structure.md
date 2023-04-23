### 公司架构包目录说明文档,包文件类按模块分类存放，遵循MVC设计模式，例如：
```
	com.suven.framework	                              // 顶级包名 
	    |--future-http                            // 统一请求规范入口实现模块
		 |    |--http                              // 架构 http 模块名称
		 |    |--config     		                // InterceptorConfiguration,实现动态加载按规范编写的拦截处理类,和返回结果业务对象后的业务处理实现
		 |    |--data.vo		                       // 所有http 返回封装对象类,int code, String msg, T data , long times;
		 |    |--exception                         // 接收或返回前端口对象包目录(View Object)
		 |    |--filter                            // CatFilterConfigure(大众点评cat的拦截器),HttpDoSFilter(jetty防Dos的拦截器)
		 |    |--handler                           // HandlerMethodArgumentResolver 业务数据分类转换实现业务逻辑类
		 |    |--interceptor                       // 各业务分类实现拦截器,包括全局日志LogbackTraceId,URL检查,JSON转换,Parameter校验,White白名单,Black黑名单,Token验证,Version软件版本验证,Redis CDN缓存;
        |        |--jetty 			                    // http应用服务统一启动实现类，
		 |    |--message				   		          // http 请求接收基本参数实现类
        |    |    |--processor.url                // 解释http 请求的url相关标签配置,包括白名单,是否强制登陆验证,CDN缓存时间
        |    |    |--rpc                          // RPC 请求的基础类
        |    |    |__validator                    // 验证业务实现逻辑
        |--future-global                          // 架构 全局参数
        |    |--common                            // 公共包目录
        |    |    |--api                          // api EntityBean 实现接口,接口文档实现标签
        |    |    |--cat                          // cat 监控类实现标签
        |    |    |--constants                    // constants
        |    |    |--data                         // BaseAipEntity 实现bean包目录
        |    |    |__enums                        // 系统已定义的枚举类
        |    |--http                              // http 模块名称
        |    |    |--data.vo     		             // 构架基础的请求参数对象bean, 通过id,page,List实现查找类对象
        |    |    |--exception                    // 业务运行异常类,和业务处理异常BusinessLogicException
        |    |    |--inters                       // 启动服务动态规范接口
        |    |    |--validator                    // 参数校验接口,文字说明类
        |    |    |__JsonParse                    // 表单提交转换java bean 实现处理转类  
        |--future-utils                           // 架构 通过工具类
        |     |--common                           // 公共包目录
        |     |   |--aop                          // 项目切面实现
        |     |   |--excel                        // excel 文档导入导出
        |     |   |--constants                    // constants
        |     |   |--platform                     // 第三方登陆
        |     |   |__util                         // 第三方配置参数工具类
        |     |--core                             // http 模块名称
        |     |    |--aiyun.sms     		          // 阿里云api,包括短信验证
        |     |    |--baidu                       // baidu api
        |     |    |--config                      // 参数配置类
        |     |    |--db                          // 动态多数据库,主-从(1-N)加载实现逻辑实现
        |     |    |--es                          // es搜索实现 api 
        |     |    |--jetty                       // jetty 服务启动与 Abstract抽象实现
        |     |    |--lock                        // redis 锁和并发销实现
        |     |    |--mybatis                     // mybatis 实现与api base实现代码
        |     |    |--redis                       // 扩写redis对就的jedis api,支持多组多聚群api,  
        |     |    |--spring                      // spring 项目的扩张类
        |     |    |__rocketmq.starter            // rocketmq api 管理业务 
        |     |--util                             // 业务工具类
        |     |    |--bean                        // Bean 处理业务,包括 类拷贝,类泛型类获取,枚举转换工具类,简体字与繁体字相互转换
        |     |    |--constants                   // 启动服务动态规范接口
        |     |    |--createcode                  // 生成api 文档目录
        |     |    |  |__swagger                  // 生成api 文档实现
        |     |    |--crypt                       // 加密实现类
        |     |    |--date                        // 时间处理类
        |     |    |--gif                         // git 图片处理类
        |     |    |--http                        // http ok3 实现api
        |     |    |--ids                         // id生成类,uuid, workerId花算法实现全局唯一ID
        |     |    |--io                          // 文件读写
        |     |    |--ips                         // ip相关业务类
        |     |    |--json                        // json 扩写类
        |     |    |--location                    // 地理位置处理类
        |     |    |--metric                      // Metric 监控实现类
        |     |    |--proxy                       // 代理实现类
        |     |    |--qrcode                      // 二维码实现类
        |     |    |--random                      // 随机生成实现类
        |     |    |--tool                        // 工具类 
        |     |    |--validate                    // 验证实现类
        |     |    |__zip                         // 压缩实现类 
        |     |__test                             // 测试架构类
        |           |--junit                      // 实现api
        |           |--rule                       // 测试规则实现
		|--业务模块名	
        |    |--controller		                    // 控制层，请求总入口。处理请求分发，参数校验等
        |    |--facade		                       // service组装层，提供给controller使用
        |    |--vo                                // 接收或返回前端口对象包目录(View Object)
        |    |    |--request                      // 接收前端接口对象包目录(request)
        |    |    |   |__BeanRequestVo            // 接收前端接口对象Bean(BeanRequestVo)
        |    |    |--response                     // 返回前端接口对象包目录(response)
        |    |        |__BeanResponseVo           // 返回前端接口对象Bean(BeanResponseVo)
        |    |--service/serviceImpl		          // 服务层接口与实现类，
        |    |--dto						             // 接收或返回RPC微服务接口对象包目录(View Object)
        |    |    |--request                      // 接收业务接口请求对象包目录(request)
        |    |    |   |__BeanRequestDto           // 接收业务接口请求对象Bean(BeanRequestVo)
        |    |    |--response                     // 返回业务接口返回对象包目录(response)
        |    |         |__BeanResponseDto         // 返回业务接口返回对象Bean(DepartResponseDto)
        |    |--dao						             // 数据库业务编写实现包类,或jdbc_dao.java 或mybatis_dao.java
        |    |--cache						          // 一个实体类对应redis缓存实现或扩写实现
        |    |--mapper						          // 一个实体类对应mybatis的实现和xml配置
        |    |--entity	                           // 数据库表对应的实体类,一个数据库一张表对应一个实体对象，
		|--future-http-system			             // 后台管理系统
		|	 |--controller
		|	 |--service
		|	 |--dao
		|    |....(如上)
		|--future-vue                             //基于jeecg 架构改写的前端架构
		    
		



```