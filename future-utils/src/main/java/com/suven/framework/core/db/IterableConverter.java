package com.suven.framework.core.db;

import com.suven.framework.common.api.IBeanClone;
import org.apache.commons.collections.CollectionUtils;
import com.suven.framework.common.api.IBaseApi;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class IterableConverter {



    /**
     * List -> Map
     * 需要注意的是：
     * toMap 如果集合对象有重复的key，会报错Duplicate key ....
     *  apple1,apple12的id都为1。
     *  可以用 (k1,k2)->k1 来设置，如果有重复的key,则保留key1,舍弃key2
     *     List<T> list =  new ArrayList<>();
     *     Map<Long,T> map = list.stream().collect(Collectors.toMap(T::getId, a -> a,(k1, k2)->k1));
     *
     *
     *  List 以ID分组 Map<Integer,List<Apple>>
     *  Map<Integer, List<Apple>> groupBy = appleList.stream().collect(Collectors.groupingBy(Apple::getId));
     */

    /**
     * List 以ID分组 Map<Long,List<T>>
     * Map<Integer, List<T>> groupBy = appleList.stream().collect(Collectors.groupingBy(T::getId));
     * @param list
     * @return
     */
    public static <T extends IBaseApi>  Map<Long,T > converterMap(List<T> list){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(T::getId, entity -> entity));
    }

    /**
     * List 以ID分组 Map<Long,List<T>>
     * Map<Integer, List<T>> groupBy = appleList.stream().collect(Collectors.groupingBy(T::getId));
     * @param list
     * @return
     */
    public static <T extends IBaseApi>  Map<Long,List<T>> converterListToMapGroup(List<T> list){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.groupingBy(T::getId));
    }

    /**
     * Map<Long,T> map --> list<T>
     * @param map
     * @return
     */
    public static <T extends IBaseApi>  List<T> convertMapToList(Map<Long,T> map){
        if(null ==map){
            return Collections.emptyList();
        }
        List<T> result = map.values().stream().collect(Collectors.toList());
        return result;
    }

    /**
     * List<A> list --> list<B>
     * @param list
     * @return  List<B>
     */
    public static < A extends Serializable, T extends IBeanClone>  List<T> convertList(List<A> list, Class<T> claxx){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        List result =  list.stream().map(obj -> {
              try {
                  return  claxx.newInstance().clone(obj);
              } catch (InstantiationException e) {
                  e.printStackTrace();
              } catch (IllegalAccessException e) {
                  e.printStackTrace();
              }
              return null;
          }).collect(Collectors.toList());
          return result;
    }

    /**
     * List 以ID分组 Map<Long,List<T>>
     * Map<Integer, List<T>> groupBy = appleList.stream().collect(Collectors.groupingBy(T::getId));
     * @param list
     * @return
     */
    public static <K extends IBaseApi,V extends IBeanClone>  Map<Long,V > converterMap(List<K> list, Class<V> claxx){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(K::getId, entity ->{
            try {
                return  claxx.newInstance().clone(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }));

    }


}
