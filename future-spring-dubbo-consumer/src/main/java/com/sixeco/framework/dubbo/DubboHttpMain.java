package com.sixeco.framework.dubbo;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CountDownLatch;


@EnableDiscoveryClient
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,
         KafkaAutoConfiguration.class})
@EnableDubbo(scanBasePackages = {"com.sixeco"})
public class DubboHttpMain {
    public static void main(String[] args)  throws Exception{
        ApplicationContext application = SpringApplication.run(DubboHttpMain.class, args);
        System.out.println("dubbo service started");
<<<<<<< HEAD
        new CountDownLatch(1).await();
=======
//        new CountDownLatch(1).await();
>>>>>>> mcb
    }

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("example-app");
        return applicationConfig;
    }

//    @Bean
//    public DubboMetadataServiceExporter dubboMetadataServiceExporter(MetadataService metadataService, ApplicationConfig applicationConfig) {
//        DubboMetadataServiceExporter exporter = new DubboMetadataServiceExporter();
//        exporter.setMetadataService(metadataService);
//        exporter.setApplicationConfig(applicationConfig);
//        return exporter;
//    }

}