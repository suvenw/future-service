package com.suven.framework.http;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.exception.BusinessLogicException;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName:
 * @Description:
 * @Author lixiangling
 * @Date 2018/5/21 15:05
 * @Copyright: (c) 2018 gc by https://www.suven.top
 * @Version : 1.0.0
 * --------------------------------------------------------
 * modifyer    modifyTime                 comment
 * <p>
 * --------------------------------------------------------
 */
public class SysMsgEnumCache {

    private static LoadingCache<Integer, SysResultCodeEnum> cache = CacheBuilder.newBuilder()
            .build(new CacheLoader<Integer, SysResultCodeEnum>() {
                @Override
                public SysResultCodeEnum load(Integer code) throws BusinessLogicException {
                    SysResultCodeEnum request =  loadSysMsgEnumType(code);
                    if(null == request){
                        return null;
                    }
                    return request;
                }
            });
    /**
     * @return 返回 cache。
     */
    public static LoadingCache<Integer, SysResultCodeEnum> getCache() {
        return cache;
    }

    public static SysResultCodeEnum loadSysMsgEnumType(int code){
        Map<Integer, SysResultCodeEnum> sysMsgEnumTypeMap = Arrays.stream(SysResultCodeEnum.values()).collect(Collectors.toMap(e->e.getCode(), e->e));
        return sysMsgEnumTypeMap.get(code);
    }

}
