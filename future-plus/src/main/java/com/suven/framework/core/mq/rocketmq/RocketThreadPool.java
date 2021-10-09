package com.suven.framework.core.mq.rocketmq;

import com.suven.framework.util.constants.Env;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 注意：这个线程池只给Rocket消费队列使用
 * @author hoticeliang
 *
 */
@Component
public class RocketThreadPool {

	public static final int CORES = Runtime.getRuntime().availableProcessors();

	private final Logger logger = LoggerFactory.getLogger(getClass());


	
	private final static ExecutorService pools = Executors.newFixedThreadPool(Env.isProd() ? (CORES > 2 ? CORES - 2 : CORES ) : 2 ,
			new ThreadFactoryBuilder().setNameFormat("rocketmq-worker-%d").build());


	
	public void run(Runnable task) {
		try {
			pools.execute(task);
		} catch (Exception e) {
			logger.warn("ThreadPool - run error ", e);
		}
	}
}
