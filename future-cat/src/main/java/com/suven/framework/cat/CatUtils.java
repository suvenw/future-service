package com.suven.framework.cat;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2023-06-27
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  Cat 监控工具类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.66os.com
 **/


public class CatUtils {

    /**
     * 字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return "".equals(str) || null == str;
    }
    /**
     * 判断集合是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(Collection collection) {
        return  null == collection || collection.isEmpty();
    }
    /**
     * 字符串是否不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !"".equals(str) && null != str;
    }

    /**
     * 判断聚合是否不为空
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection) {
        return null != collection && !collection.isEmpty();
    }

    /**
     * 判断map是否不为空
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?,?> map) {
        return  null == map || map.isEmpty();
    }

    /**
     * 判断map是否不为空
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?,?> map) {
        return null != map && !map.isEmpty();
    }
    /**
     * 异常上报cat
     * @param e
     */
    public static void onFailure(Transaction transaction, Throwable e) {
        transaction.setStatus(e);
        Cat.logMetricForCount("RemoteCallError");
        Cat.logError(e);
    }

    /**
     * 获取当前日期的无分隔符全数字格式
     *
     * @return yyyyMMddHHmmss
     */
    public static String getNowToNumFormat() {

        /** yyyyMMddHHmmss 格式 */
        String PATTERN_YYYYMMDDHHMMSS ="yyyyMMddHHmmss";
        DateFormat format = new SimpleDateFormat(PATTERN_YYYYMMDDHHMMSS);
        try {
            return format.format(new Date());
        } catch (Exception e) {
            return "";
        }
    }
    public static String halfUUID(){
        String s = UUID.randomUUID().toString();
        s = s.replace("-","").substring(0,16);
        return s;
    }

    /**
     * cat成功
     */
    public static void onSuccess(Transaction transaction) {
        transaction.setStatus(Transaction.SUCCESS);
    }


    public static String  getHostAddress() {
        StringBuilder sb = new StringBuilder();
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
                            && inetAddress.isSiteLocalAddress()) {
                        sb.append(inetAddress.getHostAddress().toString() + "\n");
                    }
                }
            }
        } catch (SocketException e) {
        }
        return sb.toString();
    }
}
