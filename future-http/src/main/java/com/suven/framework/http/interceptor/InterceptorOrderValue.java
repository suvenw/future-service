package com.suven.framework.http.interceptor;

// new LogbackTraceIdHandlerInterceptor(),
//         new UrlHandlerInterceptor(),
//         new JsonHandlerInterceptorAdapter(),
//         new VersionHandlerInterceptor(), /** 所有post请求，版本提示强制升级返回 **/
//         new WhiteHandlerInterceptor(),/**  所有post请求，用户在白名单中，直接返回 **/
//         new BlackHandlerInterceptor(),/**  所有post请求，用户在黑名单或设备在黑名单中，直接返回 **/
//         new ParameterHandlerInterceptor(),/**  参数校验，公共参数检查 **/
//         new TokenHandlerInterceptor() /** 结合白名单和登录验证 **/
public interface InterceptorOrderValue {
    int HANDEL_ORDER_LOGBACK = 1;     /** 全局唯一日志跟踪标识 **/
    int HANDEL_ORDER_URL = 2;         /** url接口验证是否有效 **/
    int HANDEL_ORDER_JSON = 3;        /** 将接口url参数转换对应的实现类  **/
    int HANDEL_ORDER_VERSION = 4;     /** 校验软件版本信息,是否升级版本或强制更新版本**/
    int HANDEL_ORDER_WHITE = 5;       /** 校验软件指定白名单信息**/
    int HANDEL_ORDER_BLACK = 6;       /** 校验用户封号信息 黑名单信息**/

    int HANDEL_ORDER_PARAMETER = 7;    /** 校验对接接口的参数有效性验证**/
    int HANDEL_ORDER_TOKEN = 8;        /** 校验接口是否需求登陆和token的有效性 **/
    int HANDEL_ORDER_JWT = 9;        /** 校验接口是否需求登陆和token的有效性 **/
    int HANDEL_ORDER_REDIS_CACHE= 10;   /** 指定接口 redis缓存实现类似cdn结果 **/



}
