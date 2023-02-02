/*
 * Copyright (c) 2019-2029, xkcoding & Yangkai.Shen & 沈扬凯 (237497819@qq.com & xkcoding.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.suven.framework.http.util;

import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.exception.HttpClientRuntimeException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.BiConsumer;


/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明): http 网络请求参数转换实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/

public class HttpParamsUtil {
	/**
	 * 判断 map 不为空
	 *
	 * @param map map
	 * @return {@code true} - 不为空，{@code false} - 空
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return map != null && !map.isEmpty();
	}

	/**
	 * 判断 map 为空
	 *
	 * @param map map
	 * @return {@code true} - 空，{@code false} - 不为空
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return !isNotEmpty(map);
	}


	/**
	 * 判断 str 字符串是否 为空
	 *
	 * @param str String
	 * @return {@code true} - 空，{@code false} - 不为空
	 */
	public static boolean isEmpty(String str) {
		return null == str || str.trim().isEmpty();
	}

	/**
	 * 判断 str 字符串是否 为空
	 *
	 * @param str String
	 * @return {@code true} - 空，{@code false} - 不为空
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 如果给定字符串{@code str}中不包含{@code appendStr}，则在{@code str}后追加{@code appendStr}；
	 * 如果已包含{@code appendStr}，则在{@code str}后追加{@code otherwise}
	 *
	 * @param str       给定的字符串
	 * @param appendStr 需要追加的内容
	 * @param otherwise 当{@code appendStr}不满足时追加到{@code str}后的内容
	 * @return 追加后的字符串
	 */
	public static String appendIfNotContain(String str, String appendStr, String otherwise) {
		if (isEmpty(str) || isEmpty(appendStr)) {
			return str;
		}
		if (str.contains(appendStr)) {
			return str.concat(otherwise);
		}
		return str.concat(appendStr);
	}

	/**
	 * 编码
	 *
	 * @param value str
	 * @return encode str
	 */
	public static String urlEncode(String value) {
		if (value == null) {
			return "";
		}
		try {
			String encoded = URLEncoder.encode(value, HttpClientConstants.DEFAULT_ENCODING.displayName());
			return encoded.replace("+", "%20")
					.replace("*", "%2A")
					.replace("~", "%7E")
					.replace("/", "%2F");
		} catch (UnsupportedEncodingException e) {
			throw new HttpClientRuntimeException("Failed To Encode Uri", e);
		}
	}

	/**
	 * 解码URL<br>
	 * 将%开头的16进制表示的内容解码。
	 *
	 * @param url URL
	 * @return 解码后的URL
	 * @throws HttpClientRuntimeException SimplestHttpException
	 */
	public static String urlDecode(String url)  {
		if (isEmpty(url)) {
			return url;
		}
		try {
			return URLDecoder.decode(url, HttpClientConstants.DEFAULT_ENCODING.displayName());
		} catch (UnsupportedEncodingException e) {
			throw new HttpClientRuntimeException("Unsupported encoding", e);
		}
	}

	/**
	 * 遍历
	 *
	 * @param map    待遍历的 map
	 * @param action 操作
	 * @param <K>    map键泛型
	 * @param <V>    map值泛型
	 */
	public static <K, V> void  forFunction(Map<K, V> map, BiConsumer<String,String> action) {
		if (isEmpty(map) || action == null) {
			return;
		}
		for (Map.Entry<K, V> entry : map.entrySet()) {
			K k;
			V v;
			try {
				k = entry.getKey();
				v = entry.getValue();
			} catch (IllegalStateException ise) {
				// this usually means the entry is no longer in the map.
				throw new ConcurrentModificationException(ise);
			}
			String key = k instanceof byte[] ? new String((byte[])k): String.valueOf(k);
			String value = v instanceof byte[] ? new String((byte[])v): String.valueOf(v);
			action.accept(key, value);
		}
	}
	/**
	 * 遍历
	 *
	 * @param map    待遍历的 map
	 * @param action 操作
	 * @param <K>    map键泛型
	 * @param <V>    map值泛型
	 */
	public static <K, V> void  forEach(Map<K, V> map, BiConsumer<? super K, ? super V> action) {
		if (isEmpty(map) || action == null) {
			return;
		}
		for (Map.Entry<K, V> entry : map.entrySet()) {
			K k;
			V v;
			try {
				k = entry.getKey();
				v = entry.getValue();
			} catch (IllegalStateException ise) {
				// this usually means the entry is no longer in the map.
				throw new ConcurrentModificationException(ise);
			}
			action.accept(k, v);
		}
	}

	/**
	 * map转字符串，转换后的字符串格式为 {@code xxx=xxx&xxx=xxx}
	 *
	 * @param params 待转换的map
	 * @param encode 是否转码
	 * @return str
	 */
	public static String parseMapToString(Map<String, String> params, boolean encode) {
		if(isEmpty(params)){
			return "";
		}
		List<String> paramList = new ArrayList<>();
		forFunction(params, (k, v) -> {
			if (v == null) {
				paramList.add(k + "=");
			} else {
				paramList.add(k + "=" + (encode ? urlEncode(v) : v));
			}
		});
		return String.join("&", paramList);
	}

	/**
	 * 字符串转map，字符串格式为 {@code xxx=xxx&xxx=xxx}
	 *
	 * @param str    待转换的字符串
	 * @param decode 是否解码
	 * @return map
	 */
	public static Map<String, String> parseStringToMap(String str, boolean decode) {
		str = preProcess(str);

		Map<String, String> params = new HashMap<>(16);
		if (isEmpty(str)) {
			return params;
		}

		if (!str.contains("&")) {
			params.put(decode(str, decode), HttpClientConstants.EMPTY);
			return params;
		}

		final int len = str.length();
		String name = null;
		// 未处理字符开始位置
		int pos = 0;
		// 未处理字符结束位置
		int i;
		// 当前字符
		char c;
		for (i = 0; i < len; i++) {
			c = str.charAt(i);
			// 键值对的分界点
			if (c == '=') {
				if (null == name) {
					// name可以是""
					name = str.substring(pos, i);
				}
				pos = i + 1;
			}
			// 参数对的分界点
			else if (c == '&') {
				if (null == name && pos != i) {
					// 对于像&a&这类无参数值的字符串，我们将name为a的值设为""
					addParam(params, str.substring(pos, i), HttpClientConstants.EMPTY, decode);
				} else if (name != null) {
					addParam(params, name, str.substring(pos, i), decode);
					name = null;
				}
				pos = i + 1;
			}
		}

		// 处理结尾
		if (pos != i) {
			if (name == null) {
				addParam(params, str.substring(pos, i), HttpClientConstants.EMPTY, decode);
			} else {
				addParam(params, name, str.substring(pos, i), decode);
			}
		} else if (name != null) {
			addParam(params, name, HttpClientConstants.EMPTY, decode);
		}

		return params;
	}

	private static void addParam(Map<String, String> params, String key, String value, boolean decode) {
		key = decode(key, decode);
		value = decode(value, decode);
		if (params.containsKey(key)) {
			params.put(key, params.get(key) + "," + value);
		} else {
			params.put(key, value);
		}
	}

	private static String decode(String str, boolean decode) {
		return decode ? urlDecode(str) : str;
	}


	private static String preProcess(String str) {
		if (isEmpty(str)) {
			return str;
		}
		// 去除 URL 路径信息
		int beginPos = str.indexOf("?");
		if (beginPos > -1) {
			str = str.substring(beginPos + 1);
		}

		// 去除 # 后面的内容
		int endPos = str.indexOf("#");
		if (endPos > -1) {
			str = str.substring(0, endPos);
		}

		return str;
	}
}
