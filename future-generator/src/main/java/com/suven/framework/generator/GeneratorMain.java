package com.suven.framework.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@SpringBootApplication
@MapperScan("com.suven")
public class GeneratorMain {

	private static Logger logger = LoggerFactory.getLogger(GeneratorMain.class);

	public static void main(String[] args) throws Exception{
		ApplicationContext application = SpringApplication.run(GeneratorMain.class, args);
		Environment env = application.getEnvironment();

		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");
		String ip = InetAddress.getLocalHost().getHostAddress();
		printLog(ip,port,path);
	}


	private static void  printLog(String ip, String port, String path){

		logger.warn(SERVICE_STARTED_LOGO
				+
				"\n----------------------------------------------------------\n\t" +
				"Application jetty Service Started is running! Access URLs:\n\t" +
				"Local: \t\thttp://localhost:" + port + path + "/\n\t" +
				"doc-ui: \thttp://" + ip + ":" + port + path + "/docs.html\n\t" +
				"----------------------------------------------------------");

		logger.warn("GeneratorMain Jetty Started on JVM ServerConnector  Successfully ... " );

	}

	public static String SERVICE_STARTED_LOGO =
			"\n" +
					"\n" +
					"\n" +
					"                          (_)                   | |               | |            | |\n" +
					"   ___   ___  _ __ __   __ _   ___   ___    ___ | |_   __ _  _ __ | |_   ___   __| |\n" +
					"  / __| / _ \\| '__|\\ \\ / /| | / __| / _ \\  / __|| __| / _` || '__|| __| / _ \\ / _` |\n" +
					"  \\__ \\|  __/| |    \\ V / | || (__ |  __/  \\__ \\| |_ | (_| || |   | |_ |  __/| (_| |\n" +
					"  |___/ \\___||_|     \\_/  |_| \\___| \\___|  |___/ \\__| \\__,_||_|    \\__| \\___| \\__,_|\n" +
					"                                                                                    \n" +
					"\n" +
					"\n :: Spring Boot (v2.X.0) : https://spring.io/projects/spring-boot" +
					"\n :: Jetty version (v9.4.X) : https://www.eclipse.org/jetty/documentation/current/" +
					"";


}
