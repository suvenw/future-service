package com.suven.framework.core.redis.cluster;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class RedisClientInvokeMethod {
        private Method method;
        private String methodName;
        private Class<?> methodReturnType;
        private Class<?>[] parameterTypes;

        public static RedisClientInvokeMethod build(){
            return new RedisClientInvokeMethod();
        }
        public RedisClientInvokeMethod method(Method method) {
            this.method =  method;
            this.methodName =  method.getName();;
            this.methodReturnType = method.getReturnType();
            this.parameterTypes = method.getParameterTypes();
            return this;
        }

        public String  toString(Method method) {
            this.method =  method;
            this.methodName =  method.getName();
            this.methodReturnType = method.getReturnType();;
            this.parameterTypes = method.getParameterTypes();;
            return this.toString();
        }

        public Method getMethod() {
            return method;
        }

        public String getMethodName() {
            return methodName;
        }

        public Class<?> getMethodReturnType() {
            return methodReturnType;
        }

        public Class<?>[] getParameterTypes() {
            return parameterTypes;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RedisClientInvokeMethod that = (RedisClientInvokeMethod) o;
            return Objects.equals(methodName, that.methodName) && Objects.equals(methodReturnType, that.methodReturnType)
                    && Arrays.equals(parameterTypes, that.parameterTypes);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(methodName, methodReturnType);
            result = 31 * result + Arrays.hashCode(parameterTypes);
            return result;
        }

        @Override
        public String toString() {
            return "RedisClientInvokeMethod{" +
                    ", methodName='" + methodName + '\'' +
                    ", methodReturnType=" + methodReturnType +
                    ", parameterTypes=" + Arrays.toString(parameterTypes) +
                    '}';
        }
    }