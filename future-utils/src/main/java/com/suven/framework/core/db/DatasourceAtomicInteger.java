//package top.suven.future.core.db;
//
//import java.util.Timer;
//import java.util.concurrent.atomic.AtomicInteger;
//
//
///**
// * @Title: Datasource.java
// * @author Joven.wang
// * @date   2016年9月7日
// * @version V1.0
// * @Description: TODO(说明)  实现监控数据源可用性切换与上下线心跳功能
// */
//
//public class DatasourceAtomicInteger {
//	//简单模拟干活的
//    static Timer timer = new Timer("job-timer");
//
//    //计数干活次数
//    static AtomicInteger count = new AtomicInteger(0);
//    CleanWorkThread cwt = new CleanWorkThread();
//
//    public Timer getTime(){
//    	return timer;
//    }
//    public boolean getcwt(String add){
//    	return cwt.set(add);
//
//    }
//
//    public void setDatasource(CleanWorkThread cwt){
//    	this.cwt = cwt;
//    }
//	 /**
//     * hook线程
//     */
//    public static class CleanWorkThread extends Thread{
//        @Override
//        public void run() {
//            System.out.println("clean some work.");
//            timer.cancel();
//            try {
//                Thread.sleep(2 * 1000);//sleep 2s
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        public boolean set(String add ){
//        	System.out.println(add);
//        	return true;
//        }
//
//    }
//
//    public void get(){
//
//		Runtime.getRuntime().addShutdownHook(new CleanWorkThread(){
//	        public void run(){
//	            System.out.println("jvm is close");
//	        }
//	  });
//    }
//
//    public static void main(String[] args) {
//
//	}
//}
