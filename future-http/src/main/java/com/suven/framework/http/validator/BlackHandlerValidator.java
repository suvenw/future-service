package com.suven.framework.http.validator;

import com.suven.framework.http.interceptor.IHandlerValidator;
import org.springframework.stereotype.Component;


/**
 * @Title: BlackHandlerValidator.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) 黑名单实现类
 */


@Component("blackHandlerValidator")
public class BlackHandlerValidator  implements IHandlerValidator {




    @Override
    public boolean validatorData() {
        return true;
    }
}
