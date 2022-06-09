package com.suven.framework.core.db;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.suven.framework.common.api.IBeanClone;
import com.suven.framework.common.data.BaseEntity;
import com.suven.framework.common.api.IBaseApi;
import com.suven.framework.common.data.BaseStatusEntity;
import org.apache.commons.collections.CollectionUtils;


import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
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
    public static <T extends IBaseApi>  Map<Long,T > convertMap(List<T> list){
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
    public static <T extends IBaseApi,V extends IBeanClone>  Map<Long,V > convertMap(Collection<T> list, Class<V> clazz){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(T::getId, entity ->{
            try {
                return  clazz.newInstance().clone(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }));

    }

    /**
     * T extends Serializable 为系列化对象
     * K extends Serializable 为T对象具体的属性值;存储在map集合的Key属性,但不能有重复key
     * V extends IBeanClone 为返回map存储结果对象
     *
     * List 以keyMapper属性分组 Map<Long,List<T>>
     * <p> 备注:但不能有重复key <p/>
     *  1.使用例子:
     *   List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(2l));
     *   Map<Long,BaseStatusEntity> map =  converterMap(list,BaseEntity::getId, BaseStatusEntity.class);
     *
     *   Map<Integer, List<T>> groupBy = appleList.stream().collect(Collectors.groupingBy(T::getId));
     *   And the following produces a Map mapping a unique identifier to students:
     *   Map<String, Student> studentIdToStudent
     *   students.stream().collect(toMap(Student::getId,Functions.identity());
     * @param list
     *
     * @return
     */
    public static <T,K, V extends IBeanClone> Map<K, V> convertMap(Collection<T> list, Function<T,K> keyMapper, Class<V> clazz){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, entity ->{
            try {
                V value =  clazz.newInstance().clone(entity);
                return value;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }));

    }

    /**
     * T extends Serializable 为系列化对象
     * K extends Serializable 为T对象具体的属性值;存储在map集合的Key属性,但有重复key,后面的值 value2 会覆盖前端的value1值
     * V extends IBeanClone 为返回map存储结果对象
     *
     * List 以keyMapper属性分组 Map<Long,List<T>>
     * <p> 备注:但有重复key,后面的值 value2 会覆盖前端的value1值<p/>
     *  1.使用例子:
     *   List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(2l));
     *   Map<Long,BaseStatusEntity> map =  converterMap(list,BaseEntity::getId, BaseStatusEntity.class);
     *
     *   Map<Integer, List<T>> groupBy = appleList.stream().collect(Collectors.groupingBy(T::getId));
     *   And the following produces a Map mapping a unique identifier to students:
     *   Map<String, Student> studentIdToStudent
     *   students.stream().collect(toMap(Student::getId,Functions.identity());
     * @param list
     *
     * @return
     */
    public static <T,K, V extends IBeanClone> Map<K, V> convertMapSame(Collection<T> list, Function<T,K> keyMapper, Class<V> clazz) {
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(keyMapper, entity -> {
            try {
                return clazz.newInstance().clone(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        },(value1, value2) ->{
            return value2;
        }));

    }



    /**
     * T extends Serializable 为系列化对象或Object对象
     * K extends Serializable 为T对象主键ID属性值;存储在map集合的Key属性
     * V extends IBeanClone 必须是IBeanClone接口实现业,值为克隆后的对象
     *
     * List 以keyMapper属性分组 Map<K,List<T>>
     * Map<Integer, List<T>> groupBy = appleList.stream().collect(Collectors.groupingBy(T::getId));
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  Map<Long,BaseEntity> map =  convertMapGroup(list,BaseEntity::getId);
     * @param list
     * @return Map
     */
    public static <T,K>  Map<K,List<T>> convertMapGroup(List<T> list,Function<T,K> keyMapper){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.groupingBy(keyMapper));
    }


    /**
     * 将map<K,V> 集合转换成List<V>集合实现方法
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


    /** List<A> list --> list<B>
     * T extends Serializable 为系列化对象或Object对象
     * V extends IBeanClone 必须是IBeanClone接口实现业,值为克隆后的对象
     *
     * List 以为V为对象基于T做克隆属性后的对象的集合;
     *
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  List<BaseStatusEntity> resultList =  convertList(list, BaseStatusEntity.class);
     * @param list
     * @return   List<B>
     */
    public static < T extends Serializable, V extends IBeanClone>  List<V> convertList(Collection<T> list, Class<V> clazz){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        List result =  list.stream().map(entity -> {
            try {
                return  clazz.newInstance().clone(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return result;
    }

    /**
     * T extends Serializable 为系列化对象或Object对象
     * K extends Serializable 为T对象主键ID属性值;存储在map集合的Key属性
     * V extends IBeanClone 为返回map存储结果对象
     *
     * List 以keyMapper属性分组 Map<Long,List<T>>
     *
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  Map<Long,BaseStatusEntity> map =  convertLinkedHashMap(list,BaseEntity::getId, BaseStatusEntity.class);
     * @param list
     * @return
     */
    public  static <T extends IBaseApi,V extends IBeanClone> Map<Long, V> convertLinkedHashMap(Collection<T> list, Class<V> clazz){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyMap();
        }
        Map<Long,V > map =   new LinkedHashMap<>();
        list.forEach(entity ->{
            try {
                Long key = entity.getId();
                V vo =  clazz.newInstance().clone(entity);
                map.put(key,vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return map;
    }

    /**
     * T extends Serializable 为系列化对象或Object对象
     * K extends Serializable 为T对象具体的属性值;存储在map集合的Key属性
     * V extends IBeanClone 为返回map存储结果对象
     *
     * List 以keyMapper属性分组 Map<Long,List<T>>
     *
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  Map<Long,BaseStatusEntity> map =  convertLinkedHashMap(list,BaseEntity::getId, BaseStatusEntity.class);
     * @param list
     * @return
     */
    public  static <T,K,V extends IBeanClone> Map<K, V> convertLinkedHashMap(Collection<T> list, Function<T,K> keyMapper,Class<V> clazz){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyMap();
        }
        Map<K,V > map =   new LinkedHashMap<>();
        list.forEach(entity ->{
            try {
                K key = keyMapper.apply(entity);
                V vo =  clazz.newInstance().clone(entity);
                map.put(key,vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return map;
    }


    /**
     * T extends Serializable 为系列化对象或Object对象
     * K extends Serializable 为T对象具体的属性值;存储在map集合的Key属性
     * V extends IBeanClone 为返回map存储结果对象
     *
     * List 以keyMapper属性分组 Map<Long,List<T>>
     *
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  Map<Long,BaseStatusEntity> map =  convertGroupMap(list, BaseStatusEntity.class);
     * @param list
     * @return
     */
    public  static <K extends IBaseApi,V extends IBeanClone>   Map<Long, Collection<V>> convertGroupMap(Collection<K> list,Class<V> clazz){
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyMap();
        }
        Multimap<Long,V > map =   ArrayListMultimap.create();
        list.forEach(entity ->{
            try {
                V vo =  clazz.newInstance().clone(entity);
                map.put(entity.groupId() ,vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Map<Long,Collection<V>> mapList =  map.asMap();
        return mapList;

    }

    /**
     * T extends Serializable 为系列化对象或Object对象
     * K extends Serializable 为T对象具体的属性值;存储在map集合的Key属性
     * V extends IBeanClone 为返回map存储结果对象
     *
     * List 以keyMapper属性分组 Map<Long,List<T>>
     *
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  Multimap<Serializable,BaseStatusEntity> map =  convertMultimap(list,BaseEntity::getId, BaseStatusEntity.class);
     * @param list
     * @return
     */
    public  static <T,K , V extends IBeanClone> Multimap<K, V> convertMultimap(Collection<T> list, Function<T,K> keyMapper, Class<V> clazz){

        Multimap<K,V > map =   ArrayListMultimap.create();
        if(CollectionUtils.isEmpty(list)){
            return map;
        }
        list.forEach(entity ->{
            try {
                V vo =  clazz.newInstance().clone(entity);
                K key =  keyMapper.apply(entity);
                map.put(key,vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return map;

    }

    /**
     * T extends Serializable 为系列化对象或Object对象
     * V 为T(Object)对象的指定属性的类型,返回指定属性的类型的值
     *
     * List 以keyMapper属性的值的集合
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  List<Long> resultList =  convertFieldsList(list,BaseEntity::getId, BaseStatusEntity.class);
     * @param list
     * @return
     */
    public  static <T,V> List<V> convertFieldsList(Collection<T> list, Function<T,V> keyMapper){

        if(CollectionUtils.isEmpty(list)){
            return new ArrayList<>();
        }
        List<V> resultList =  list.stream().map(keyMapper).collect(Collectors.toList());
        return resultList;

    }
    /**
     * T extends Serializable 为系列化对象或Object对象
     * V 为T(Object)对象的指定属性的类型,返回指定属性的类型的值
     *
     * List 以keyMapper属性的值的集合
     *  1.使用例子:
     *  List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
     *  Set<Long> resultSet =  convertFieldsSet(list,BaseEntity::getId, BaseStatusEntity.class);
     * @param list
     * @return
     */
    public  static <T,V> Set<V> convertFieldsSet(Collection<T> list, Function<T,V> keyMapper){

        if(CollectionUtils.isEmpty(list)){
            return new HashSet<>();
        }
        Set<V> resultSet =  list.stream().map(keyMapper).collect(Collectors.toSet());
        return resultSet;

    }


/*    public static void main(String[] args) {
        List list = Arrays.asList( new BaseEntity().toId(1l), new BaseEntity().toId(3l),new BaseEntity().toId(2l));
        Map<Date,BaseStatusEntity> map =  convertMapSame(list,BaseEntity::getCreateDate,BaseStatusEntity.class);
        System.out.println(map);
    }*/

}
