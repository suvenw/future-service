package com.suven.framework.core.db;



import java.lang.annotation.*;

/**
 * @Title: DatasourceEnumAnno.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) DruidDataSource  数据库连接模块组扩写类必须标上该标签@DatasourceEnumAnno,由DatasourceEnumManager数据库模块管理对象类,进行扫描加载；
 * eg: 具体实现类如: DataSourceGroupNameEnum, 如果该类写的不满足业务需要,
 * 可以按相同的规范,自己定义实现类;该实现必须继承DataSourceGroupEnumInterface,和@DatasourceEnumAnno标签;
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
@Deprecated
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DatasourceEnumAnno {

}