package com.suven.framework;

import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.http.inters.IProjectModule;
import com.suven.framework.http.inters.ProjectModuleEnum;
import com.suven.framework.http.jetty.AbstractJettyAppServer;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages={GlobalConfigConstants.COMPONENT_SCAN_BASE_PACKAGES})
//@NacosPropertySource( groupId= SERVICE_NAME, dataId = TOP_SERVER_HTTP_FILE_NAME,   autoRefreshed = true)
public class FileMain extends AbstractJettyAppServer {
    public static void main(String[] args) {
        jettyStart(FileMain.class,args);
    }

    @Override
    protected IProjectModule getServiceName() {
        return ProjectModuleEnum.HTTP_FILE;
    }
}
