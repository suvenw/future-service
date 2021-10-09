package com.suven.framework.core.db;

/**
 * @Title: DataSourceGroupNameEnum.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) DataSource Group NameEnum  按业务模块 实现多组数据库的实现类, 该类由DatasourceEnumManager数据库模块管理对象类,进行描述加载；
 * eg: 如果该类写的不满足业务需要,可以按相同的规范,自己定义实现类;
 * * 该实现必须继承DataSourceGroupEnumInterface,和标上 @DatasourceEnumAnno标签;
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

import com.suven.framework.core.db.druid.DataSourceGroupModuleName;

/**
 * 项目数据源名称泛型枚举实现类，
 * 用来实现多数据源自动切换使用，配合 DataSourceAutoConfiguration 启动初化数据源注入到spring bean 中
 * 创建spring DynamicDataSource bean 对象，并注入到spring容器中，命名为dataSource;
 *
 *     DATA_NAME_SYSTEM     (   MODULE_NAME_SYSTEM, "后台管理模块数据库"),
 *     DATA_NAME_USER       (   MODULE_NAME_USER,   "用户模块数据库"),
 *     DATA_NAME_OAUTH      (   MODULE_NAME_OAUTH,  "验证模块数据库"),
 *     DATA_NAME_ASSETS     (   MODULE_NAME_ASSETS, "用户资产模块数据库"),
 *     DATA_NAME_PAY        (   MODULE_NAME_PAY,    "支付模块数据库"),
 *     DATA_NAME_ORDER      (   MODULE_NAME_ORDER,  "订单模块数据库"),
 *     DATA_NAME_GIFT       (   MODULE_NAME_GIFT,   "礼物模块数据库"),
 *     DATA_NAME_GOODS      (   MODULE_NAME_GOODS,   "商品模块数据库"),
 *     DATA_NAME_RESOURCE   (   MODULE_NAME_RESOURCE,"资源模块数据库"),
 *     DATA_NAME_CONFIG     (   MODULE_NAME_CONFIG,  "配置模块数据库"),
 *     DATA_NAME_VIDEO      (   MODULE_NAME_VIDEO,    "视频模块数据库"),
 *     DATA_NAME_ACTIVITY   (   MODULE_NAME_ACTIVITY,"活动模块数据库"),
 *     DATA_NAME_MARKET     (   MODULE_NAME_MARKET,  "市场模块数据库"),
 *     DATA_NAME_LOG        (   MODULE_NAME_LOG,     "日志模块数据库"),
 *     DATA_NAME_DEPLOY     (   MODULE_NAME_DEPLOY,  "开发模块数据库"),
 *     DATA_NAME_PAD        (   MODULE_NAME_PAD,     "设备块数据库"),
 *     DATA_NAME_SPIDER     (   MODULE_NAME_SPIDER,  "爬虫模块数据库"),
 *     DATA_NAME_MEDIA      (   MODULE_NAME_MEDIA,   "媒体模块数据库"),
 *     DATA_NAME_STATISTIC  (   MODULE_NAME_STATISTIC,"采摘模块数据库"),
 */
@DatasourceEnumAnno
@Deprecated
public enum DataSourceGroupNameEnum implements DataSourceGroupEnumInterface, DataSourceGroupModuleName {

    DATA_NAME_SYSTEM     (   MODULE_NAME_SYSTEM, "后台管理模块数据库"),
    DATA_NAME_USER       (   MODULE_NAME_USER,   "用户模块数据库"),
    DATA_NAME_OAUTH      (   MODULE_NAME_OAUTH,  "验证模块数据库"),
    DATA_NAME_ASSETS     (   MODULE_NAME_ASSETS, "用户资产模块数据库"),
    DATA_NAME_PAY        (   MODULE_NAME_PAY,    "支付模块数据库"),
    DATA_NAME_ORDER      (   MODULE_NAME_ORDER,  "订单模块数据库"),
    DATA_NAME_GIFT       (   MODULE_NAME_GIFT,   "礼物模块数据库"),
    DATA_NAME_GOODS      (   MODULE_NAME_GOODS,   "商品模块数据库"),
    DATA_NAME_RESOURCE   (   MODULE_NAME_RESOURCE,"资源模块数据库"),
    DATA_NAME_CONFIG     (   MODULE_NAME_CONFIG,  "配置模块数据库"),
    DATA_NAME_VIDEO      (   MODULE_NAME_VIDEO,    "视频模块数据库"),
    DATA_NAME_ACTIVITY   (   MODULE_NAME_ACTIVITY,"活动模块数据库"),
    DATA_NAME_MARKET     (   MODULE_NAME_MARKET,  "市场模块数据库"),
    DATA_NAME_LOG        (   MODULE_NAME_LOG,     "日志模块数据库"),
    DATA_NAME_DEPLOY     (   MODULE_NAME_DEPLOY,  "开发模块数据库"),
    DATA_NAME_PAD        (   MODULE_NAME_PAD,     "设备块数据库"),
    DATA_NAME_SPIDER     (   MODULE_NAME_SPIDER,  "爬虫模块数据库"),
    DATA_NAME_MEDIA      (   MODULE_NAME_MEDIA,   "媒体模块数据库"),
    DATA_NAME_STATISTIC  (   MODULE_NAME_STATISTIC,"采摘模块数据库"),
    DATA_NAME_SHORTER  (   MODULE_NAME_SHORTER,"短视频业务数据库"),
    ;

    private String module;
    private String desc;





    DataSourceGroupNameEnum (String module,String desc) {
        this.module = module;
        this.desc = desc;
    }

    public String getModule () {
        return module;
    }

    private String getDesc(){
        return desc;
    }



}