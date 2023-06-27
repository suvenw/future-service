package com.suven.framework.cat;

/**
 * Created by bieber on 2015/11/12.
 */
public interface CatConstants {

    public final String  GC_SERVER_CAT = "top.server.cat";
    public  final String COMPONENT_SCAN_BASE_PACKAGES = "com.suven";
    public  final String  LOGBACK_TRACE_ID="";

    public    final String CROSS_CONSUMER ="PigeonCall";

    public   final String CROSS_SERVER = "PigeonService";
    
    public   final String PROVIDER_APPLICATION_NAME="serverApplicationName";
    
    public   final String CONSUMER_CALL_SERVER="PigeonCall.server";
    
    public   final String CONSUMER_CALL_APP="PigeonCall.app";
    
    public   final String CONSUMER_CALL_PORT="PigeonCall.port";
    
    public   final String PROVIDER_CALL_SERVER="PigeonService.client";
    
    public   final String PROVIDER_CALL_APP="PigeonService.app";

    public   final String FORK_MESSAGE_ID="m_forkedMessageId";

    public   final String FORK_ROOT_MESSAGE_ID="m_rootMessageId";

    public   final String FORK_PARENT_MESSAGE_ID="m_parentMessageId";
    
}
