package com.suven.framework.generator.temp;


/**
 * @Title: SysDepartFacade.java
 * @author suven
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: TODO(说明) java 实现类的模块实现
 */

public interface CreateCodeEnum {



    /** 模块路径 **/
    public String getTemp();

    /** 实现文件相对文件路径 **/
    public String getPath();

    /** 实现类的扩展名称,即后缀 **/
    public String getExt();

    /**
     * 1.为rpc项目, 2.api 项目; 3.http 项目
     * @return
     */
    public int getIndex();



}
