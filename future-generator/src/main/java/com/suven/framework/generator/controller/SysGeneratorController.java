
package com.suven.framework.generator.controller;

import com.alibaba.fastjson.JSON;
import com.suven.framework.generator.entity.ClassConfigEntity;
import com.suven.framework.generator.entity.ColumnBean;
import com.suven.framework.generator.entity.ColumnClassEntity;
import com.suven.framework.generator.entity.DatabaseEnum;
import com.suven.framework.generator.utils.DateUtils;
import com.suven.framework.generator.utils.PageUtils;
import com.suven.framework.generator.utils.Query;
import com.suven.framework.generator.utils.ResultVo;
import org.springframework.web.bind.annotation.*;
import com.suven.framework.generator.config.ProjectPathConfig;
import com.suven.framework.generator.config.SysConfig;
import com.suven.framework.generator.config.SysDataConfig;
import com.suven.framework.generator.service.SysGeneratorService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 代码生成器
 * 
 * @author Mark sunlightcs@gmail.com
 */
@Controller
public class SysGeneratorController {
	@Autowired
	private SysGeneratorService sysGeneratorService;
	@Autowired
	private SysDataConfig sysDataConfig;



	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/sys/generator/list")
	public ResultVo list(@RequestParam Map<String, Object> params){
		PageUtils pageUtil = sysGeneratorService.queryList(new Query(params));
		ClassConfigEntity entity = ClassConfigEntity.init();
		return ResultVo.ok().put("page", pageUtil).put("entity",entity);
	}

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/sys/generator/columns")
	public ResultVo queryColumns(@RequestParam Map<String, Object> params){
		String tables =null;
		//第二次重新加载表格时，传的参数为tables2
		if(params.get("tables2") != null && !"".equals(params.get("tables2"))){
			tables =( String)params.get("tables2");
		}else{
			tables =( String)params.get("tables");
		}
		//tables =( String)params.get("tables");
		List<ColumnClassEntity> list = sysGeneratorService.queryColumns(tables);
		List<ColumnBean> cList = new ArrayList<>();
		list.forEach(e -> {
			cList.add(ColumnBean.build(e));
		});
		PageUtils page = new PageUtils(cList,1,1,1);
		return ResultVo.ok().put("page", page);
	}

	/**
	 * 生成代码  下载压缩包的方式
	 */
	@RequestMapping(value="/sys/generator/codeZip",method = RequestMethod.POST)
	public void codeZip(@RequestBody Map<String, Object> params, HttpServletResponse response) throws IOException{
		String columns =( String)params.get("columns");
		String table =( String)params.get("tables");
		List<ColumnBean> entityList = JSON.parseArray(columns,ColumnBean.class);
		List<ClassConfigEntity> entity = JSON.parseArray(table, ClassConfigEntity.class);
		byte[] data = sysGeneratorService.generatorCodeToFileByZip(entity.get(0),entityList);

		String fileName = "future"+ DateUtils.formatName() +".zip";
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");

		IOUtils.write(data, response.getOutputStream());
	}

//	/**
//	 * 生成代码
//	 */
//	@RequestMapping("/sys/generator/file")
//	@ResponseBody
//	public ResultVo codeFile(@RequestParam Map<String, Object> params, HttpServletResponse response) throws IOException{
//		String paramJson =  JSON.toJSONString(params);
//		ClassConfigEntity entity = JSON.parseObject(paramJson, ClassConfigEntity.class);
//		sysGeneratorService.generatorCodeToFile(entity);
//		return ResultVo.ok();
//	}
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/sys/config/list")
	public ResultVo configList(@RequestParam Map<String, Object> params){
		PageUtils page = new PageUtils(Arrays.asList(sysDataConfig.getSysConfig()),1,1,1);
		return ResultVo.ok().put("page", page);
	}



	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/sys/config/update")
	public ResultVo configUpdate(@RequestParam Map<String, Object> params){
		if(null == params || params.isEmpty()){
			return ResultVo.error(100,"数据,不能为空");
		}
		String json = JSON.toJSONString(params);
		SysConfig sys = JSON.parseObject(json,SysConfig.class);
		String database = DatabaseEnum.getValue(sys.getDatabaseType());
		if(null == database ){
			return ResultVo.error(100,"请选择对应的数据库类型");
		}
		if(sys.getTempEnum() == null){
			return ResultVo.error(100,"请选择生成模板");
		}
		sysDataConfig.updateSysConfig(sys);
//		BeanUtils.copyProperties(sys,sysConfig);
//		System.out.println(sysConfig.toString());
		return ResultVo.ok();

	}

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/sys/path/list")
	public ResultVo pathList(@RequestParam Map<String, Object> params){

		List<ProjectPathConfig> pathList = sysDataConfig.getList();
		PageUtils page = new PageUtils(pathList,100,100,1);
		return ResultVo.ok().put("page", page);
	}

	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/sys/path/update")
	public ResultVo pathUpdate(@RequestParam Map<String, Object> params){
		String json = JSON.toJSONString(params);
		ProjectPathConfig path = JSON.parseObject(json,ProjectPathConfig.class);
		int id = 0;
		if(null != path){
			for (ProjectPathConfig c : sysDataConfig.getList()){
				//设置唯一使用
				if(path.getIsUse() == 1 && c.getIsUse() == 1){
					c.setIsUse(0);
					sysDataConfig.put(c.getId(),c);
				}//获取最大id
				if(id < c.getId()){
					id = c.getId();
				}
			}//最大id+1
			if(path.getId() <= 0){
				path.setId(++id);
			}
			sysDataConfig.put(path.getId(),path);

		}
		return ResultVo.ok();
	}


	/**
	 * 生成代码
	 */
	@ResponseBody
	@RequestMapping(value = "/sys/generator/codeCreate",method = RequestMethod.POST)
	public ResultVo codeCreate(@RequestBody Map<String, Object> params) throws IOException{
		String columns =( String)params.get("columns");
		String table =( String)params.get("tables");
		List<ColumnBean> entityList = JSON.parseArray(columns,ColumnBean.class);
		List<ClassConfigEntity> entity = JSON.parseArray(table, ClassConfigEntity.class);
		sysGeneratorService.generatorCodeToFileByPage(entity.get(0),entityList);
		return ResultVo.ok();
	}

}
