package com.suven.framework.util.crypt;

import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;


/**
 * @Title: SignParam.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) 接口请求参数tree型排序后加载实现类
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class SignParam {

	
	public static Logger logger =LoggerFactory.getLogger(SignParam.class);
	
	/**
	 * 获取签名 客户端
	 * @param head
	 * @param body
	 * @return
	 */
	public static String getClientSign(Object head,Object body)  {
		Map dataMap = getClientSignMap(head,body);
        String signParam = getServerSignParam(dataMap,Arrays.asList("cliSign"));
		String pass = paramMd5(signParam);
		return pass.substring(8, 24);
	}
	
	/**
	 * 获取签名 服务端
	 * @param param
	 * @return
	 */
	public static String getServerSign(Map param)  {
		String signParam = getServerSignParam(param,Arrays.asList("cliSign"));
		String pass = paramMd5(signParam);
		return pass.substring(8, 24);
		
	}
	

	/**
	 * 获取签名的参数 格式a=1&b=1
	 * @param param
	 * @param notContains
	 * @return
	 */
	public static String getServerSignParam(Map<String, Object> param,List<String> notContains){
		StringBuffer sb = new StringBuffer();
		String strBody="";
		if(null != param && param.size() > 0) {
			if(!(param instanceof  TreeMap)){
				param = new TreeMap<>(param);
			}
			for (Iterator<Entry<String, Object>> iterator = param.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, Object> obj = iterator.next();
				if(null != notContains && notContains.contains(obj.getKey())){
					continue;
				}
                Object valueObj = obj.getValue();
                String value = "";
                if(null == valueObj){
                    value = "";
                }else if(valueObj instanceof String[]){
                    String[] values = (String[])valueObj;
                    value = values[values.length-1];
                }else{
                    value = valueObj.toString();
                }
				sb.append(obj.getKey()).append("=").append(value).append("&");
			}
			if(sb.length()>1 ){
				strBody = sb.substring(0, sb.length() - 1);
			}
		}
		return strBody;
	}

//    /**
//     * 将getParameterMap转成可读的map
//     * @param properties
//     * @return
//     */
//    private static Map<String, Object> getParameterMap(Map<String, Object> properties) {
//        Map<String, Object> returnMap = new TreeMap<>();
//        Iterator<?> entries = properties.entrySet().iterator();
//        Map.Entry<String, Object> entry;
//        String name = "";
//        String value = "";
//        while (entries.hasNext()) {
//            entry = (Map.Entry) entries.next();
//            name = entry.getKey();
//            Object valueObj = entry.getValue();
//            if(null == valueObj){
//                value = "";
//            }else if(valueObj instanceof String[]){
//                String[] values = (String[])valueObj;
//                value = values[values.length-1];
//            }else{
//                value = valueObj.toString();
//            }
//            returnMap.put(name, value);
//        }
//        return returnMap;
//    }


    private static Map<String, Object> getClientSignMap(Object head,Object body){
		Map dataMap = new TreeMap();
		try {
            if(null != head){
                Map headMap = JsonUtils.objectToMap(head);
                if(headMap != null && !headMap.isEmpty()){
                    dataMap.putAll(headMap);
                }
            }
			if(null != body){
                Map bodyMap = JsonUtils.objectToMap(body);
                if(bodyMap != null && !bodyMap.isEmpty()){
                    dataMap.putAll(bodyMap);
                }
			}
		} catch (Exception e) {
				logger.error("body or head content should be key-value");
		}
		return dataMap;
	}

	/**
	 * 加密
	 * @param param
	 * @return
	 */
	public  static String paramMd5By16(String param) {
		String pass = "";
		try {
			pass = CryptUtil.md5(param).toLowerCase();
			pass.substring(0, 16);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pass;
	}
	
	
	/**
	 * 加密
	 * @param param
	 * @return
	 */
	private  static String paramMd5(String param) {
		String pass = "";
		try {
			pass = CryptUtil.md5(param + GlobalConfigConstants.TOP_SERVER_APPKEY).toLowerCase();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pass;
	}
	

	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("sysVersion", "android 5.1.x");
		map.put("userId", 123456);
		map.put("accessToken", "abcdefg");
		map.put("device", "3f25d333755240e");
		map.put("times", 123456);
		String cliSign =SignParam.getServerSign(map);
		System.out.println(cliSign);
	}
}
