package com.suven.framework.sys.dto.response;


import java.io.Serializable;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suven.framework.common.data.BaseByTimeEntity;
import com.suven.framework.common.api.ApiDesc;

/**
 * @ClassName: SysDictResponseDto.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:09
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description: 后台字典类型表 RPC业务接口交互数据返回实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/

public class SysDictResponseDto  extends BaseByTimeEntity implements Serializable {

        private Logger logger = LoggerFactory.getLogger(SysDictResponseDto.class);


 		/** dict_name 字典名称  */
 		@ApiDesc(value = "字典名称", required = 0)
 		private String dictName;

 		/** dict_code 字典编码  */
 		@ApiDesc(value = "字典编码", required = 0)
 		private String dictCode;

 		/** description 描述  */
 		@ApiDesc(value = "描述", required = 0)
 		private String description;

 		/** del_flag 删除状态(1-正常,0-已删除)  */
 		@ApiDesc(value = "删除状态(1-正常,0-已删除)", required = 0)
 		private int delFlag;





 		/** type 字典类型0为string,1为number  */
 		@ApiDesc(value = "字典类型0为string,1为number", required = 0)
 		private int type;


        public static SysDictResponseDto build(){
                return new SysDictResponseDto();
        }

 		public void setDictName( String dictName){
 		 		this.dictName = dictName ; 
 		 		}
 		public SysDictResponseDto toDictName( String dictName){
 		 		this.dictName = dictName ; 
 		 		 return this ;
 		}

 		public String getDictName(){
 		 		return this.dictName;
 		}
 		public void setDictCode( String dictCode){
 		 		this.dictCode = dictCode ; 
 		 		}
 		public SysDictResponseDto toDictCode( String dictCode){
 		 		this.dictCode = dictCode ; 
 		 		 return this ;
 		}

 		public String getDictCode(){
 		 		return this.dictCode;
 		}
 		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}
 		public SysDictResponseDto toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}

 		public String getDescription(){
 		 		return this.description;
 		}
 		public void setDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		}
 		public SysDictResponseDto toDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		 return this ;
 		}

 		public int getDelFlag(){
 		 		return this.delFlag;
 		}
 		public void setType( int type){
 		 		this.type = type ; 
 		 		}
 		public SysDictResponseDto toType( int type){
 		 		this.type = type ; 
 		 		 return this ;
 		}

 		public int getType(){
 		 		return this.type;
 		}







}
