/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.suven.framework.core.db.druid;

import java.util.LinkedHashMap;
import java.util.Map;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.suven.framework.util.json.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lihengming [89921218@qq.com]
 */
@ConfigurationProperties("spring.datasource")
public class DruidDataSourceConfigWrapper implements InitializingBean {

    private Map<Object,Object> druid;

    private Logger logger = LoggerFactory.getLogger(DruidDataSourceConfigWrapper.class);

    public DruidDataSourceConfigWrapper(){
        logger.info("DruidDataSourceInitWrapper to spring.datasource.druid by constructor method from logger info ");
    };

    @Override
    public void afterPropertiesSet() {
        logger.warn("afterPropertiesSet druid toString[{}]", JsonUtils.toJson(druid));
    }

    public DruidDataSource convertDruidDataSource(DataSourceConnectionInfo connectionInfo){
        DruidDataSource dataSource =  new DruidDataSource();
        try {
            DruidDataSourceFactory.config(dataSource, druid);
            this.initWrapper(dataSource,connectionInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dataSource;
    }

    public Map<Object, Object> getDruid() {
        return druid;
    }

    public void setDruid(Map<Object, Object> druid) {
       Map<Object,Object>  map = new LinkedHashMap<>();
        druid.forEach((k,v)->{
            Object key = convert(k);
            map.put(key, v);
        });
        this.druid = map;
    }
    private void initWrapper(DruidDataSource dataSource ,DataSourceConnectionInfo dataSourceConnectionInfo) {
        if(dataSource == null || dataSourceConnectionInfo == null ){
            return;
        }
        dataSource.setUrl(dataSourceConnectionInfo.getUrl());
        dataSource.setUsername( dataSourceConnectionInfo.getUsername());
        dataSource.setPassword(dataSourceConnectionInfo.getPassword());
        String driverClass = dataSourceConnectionInfo.getDriverClassName();
        if (null != driverClass && !"".equals(driverClass)){
            dataSource.setDriverClassName(driverClass);
        }

    }
    private Object convert(Object key){
        if (null == key){
            return null;
        }
        String paramKey = String.valueOf(key);
       final String chars = "-";
        if(paramKey.contains(chars))
            paramKey = WordUtils.capitalize(String.valueOf(key), new char[]{'-'})
                    .replaceAll(chars, "" )
                    .trim();
        paramKey =  StringUtils.uncapitalize(paramKey);
        return paramKey;
    }

}
