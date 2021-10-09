package com.suven.framework.core.db;

@Deprecated
public class ClassLoadDBUtil {

    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
    public static Class<?> loadClass(String className) {
        if (className == null) {
            return null;
        } else {
            Class clazz = null;
            try {
                ClassLoader classLoader = getContextClassLoader();
                if (classLoader == null) {
                    clazz = Class.forName(className);
                } else {
                    clazz = Class.forName(className, true, classLoader);
                }
            } catch (ClassNotFoundException var4) {
                try {
                    clazz = ClassLoadDBUtil.class.getClassLoader().loadClass(className);
                }catch (Exception e){
                }
            }

            return clazz;
        }
    }
}
