package com.suven.framework.core.db.druid;

import com.alibaba.druid.filter.config.ConfigFilter;
import com.alibaba.druid.filter.encoding.EncodingConvertFilter;
import com.alibaba.druid.filter.logging.CommonsLogFilter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lihengming [89921218@qq.com]
 */
@ConfigurationProperties("spring.datasource.druid")
class DruidDataSourceInitWrapper extends DruidDataSource implements InitializingBean {


    private String url;
    private String username;

    private String password;
    private String driverClassName;
    public DruidDataSourceInitWrapper(){
        super();
    }
    public DruidDataSourceInitWrapper(DataSourceConnectionInfo dataSourceConnectionInfo) {

        super();
        this.url = dataSourceConnectionInfo.getUrl();
        this.username = dataSourceConnectionInfo.getUsername();
        this.password = dataSourceConnectionInfo.getPassword();
        this.driverClassName = dataSourceConnectionInfo.getDriverClassName();
    }
    public DruidDataSourceInitWrapper(String url, String username, String password, String driverClassName) {
        super();
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
    }

    @Override
    public void afterPropertiesSet()  {
        //if not found prefix 'spring.datasource.druid' jdbc properties ,'spring.datasource' prefix jdbc properties will be used.
        try {

            if (super.getUsername() == null) {
                super.setUsername(username);
            }
            if (super.getPassword() == null) {
                super.setPassword(password);
            }
            if (super.getUrl() == null) {
                super.setUrl(url);
            }
            if(super.getDriverClassName() == null){
                super.setDriverClassName(driverClassName);
            }
        }catch (Exception e){
            e.printStackTrace();

        }


    }

    @Autowired(required = false)
    public void addStatFilter(StatFilter statFilter) {
        super.filters.add(statFilter);
    }

    @Autowired(required = false)
    public void addConfigFilter(ConfigFilter configFilter) {
        super.filters.add(configFilter);
    }

    @Autowired(required = false)
    public void addEncodingConvertFilter(EncodingConvertFilter encodingConvertFilter) {
        super.filters.add(encodingConvertFilter);
    }

    @Autowired(required = false)
    public void addSlf4jLogFilter(Slf4jLogFilter slf4jLogFilter) {
        super.filters.add(slf4jLogFilter);
    }

    @Autowired(required = false)
    public void addLog4jFilter(Log4jFilter log4jFilter) {
        super.filters.add(log4jFilter);
    }

    @Autowired(required = false)
    public void addLog4j2Filter(Log4j2Filter log4j2Filter) {
        super.filters.add(log4j2Filter);
    }

    @Autowired(required = false)
    public void addCommonsLogFilter(CommonsLogFilter commonsLogFilter) {
        super.filters.add(commonsLogFilter);
    }

    @Autowired(required = false)
    public void addWallFilter(WallFilter wallFilter) {
        super.filters.add(wallFilter);
    }


}
