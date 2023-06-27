package com.suven.framework.cat.factory;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.suven.framework.cat.CatGcConstants;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.FactoryBean;

public class CacheProxyFactory implements FactoryBean<Object>{

	private static final String PREFIX = "Cache." ;

	private Object target ;

	private String name ;

	@Override
	public Object getObject() throws Exception {

		ProxyFactoryBean proxyFactory = new ProxyFactoryBean() ;
		proxyFactory.setTarget(target); 

		proxyFactory.addAdvice(new MethodInterceptor() {  
			@Override 
			public Object invoke(MethodInvocation invocation) throws Throwable {  
				String methodName = invocation.getMethod().getName() ; 
				String type = PREFIX + name ;
				Transaction t = Cat.newTransaction(type , methodName) ; 
				try {
					Object obj = invocation.proceed() ; 

					if(obj == null){
						Cat.logEvent(type, CatGcConstants.TYPE_REDIS_MISS);
					}else{
						Cat.logEvent(type, CatGcConstants.TYPE_REDIS_HIT);
					} 
					t.setStatus(Transaction.SUCCESS);
					return obj ;
				} catch (Throwable e) {
					t.setStatus(e);
					throw e ;
				}finally{
					t.complete();
				}
			} 
		});  
		return proxyFactory.getObject() ;
	}

	@Override
	public Class<?> getObjectType() { 
		return target.getClass() ;
	}

	@Override
	public boolean isSingleton() { 
		return true ;
	} 
	public void setTarget(Object target) {
		this.target = target;
	}

	public void setName(String name) {
		this.name = name;
	} 

}
