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

package com.suven.framework.http.proxy;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明): http 网络请求,请求头对象
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/

public class HttpProxyHeader {
	private final Map<String, String> headers;

	public HttpProxyHeader() {
		this.headers = new HashMap<>(16);
	}

	public HttpProxyHeader(Map<String, String> headers) {
		this.headers = headers;
	}

	public HttpProxyHeader add(String key, String value) {
		this.headers.put(key, value);
		return this;
	}

	public HttpProxyHeader addAll(Map<String, String> headers) {
		this.headers.putAll(headers);
		return this;
	}

	public Map<String, String> getHeaders() {
		return this.headers;
	}
}
