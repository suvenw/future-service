package com.suven.framework.sys.controller;

import com.suven.framework.common.api.ApiDoc;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.processor.url.SysURLCommand;
import com.suven.framework.sys.vo.DocumentConst;
import com.suven.framework.sys.vo.request.DuplicateCheckVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Title: DuplicateCheckAction
 * @Description: 重复校验工具
 * @author xxx.xxx
 * @Date 2019-11-21
 * @Version V1.0
 */
@Controller
@ApiDoc(
        group = DocumentConst.SysApi.DOC_API_GROUP,
        groupDesc= DocumentConst.SysApi.DOC_API_DES,
        module = "重复校验", isApp = false
)
public class DuplicateCheckController {

//	@Autowired
//	SysDictMapper sysDictMapper;

    /**
     * 校验数据是否在系统中是否存在
     *
     * @return
     */
    @ApiDoc(
            value = "重复校验接口"
    )
    @RequestMapping(value = SysURLCommand.sys_get_duplicate_check, method = RequestMethod.GET)
    public void doDuplicateCheck(OutputResponse out, DuplicateCheckVo duplicateCheckVo) {
        Long num = null;

//		log.info("----duplicate check------："+ duplicateCheckVo.toString());
//		if (StringUtils.isNotBlank(duplicateCheckVo.getDataId())) {
//			// [2].编辑页面校验
//			num = sysDictMapper.duplicateCheckCountSql(duplicateCheckVo);
//		} else {
//			// [1].添加页面校验
//			num = sysDictMapper.duplicateCheckCountSqlNoDataId(duplicateCheckVo);
//		}
        if (num == null || num == 0) {
            // 该值可用
            out.write("该值可用！");
        } else {
            // 该值不可用
//			log.info("该值不可用，系统中已存在！");
            out.write("该值不可用，系统中已存在！");
        }
    }
}
