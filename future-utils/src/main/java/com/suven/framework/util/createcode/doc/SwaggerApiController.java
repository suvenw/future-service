package com.suven.framework.util.createcode.doc;

import com.suven.framework.common.api.ApiDoc;
import com.suven.framework.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ApiDoc(module = "接口api测试例子说明")
public class SwaggerApiController {

    private static Logger logger = LoggerFactory.getLogger(SwaggerApiController.class);
    private final  String url_test_add = "/test/addTest";
    private final  String url_test_edit = "/test/editTest";


    @RequestMapping(value = url_test_add, method = RequestMethod.GET)
    @ApiDoc(value = "测试例子增加数据接口",request = SwaggerRequestVo.class, response = SwaggerResponseVo.class)
    @ResponseBody
    public SwaggerResultVo add(SwaggerRequestVo  vo){
        logger.info("[{}]  recepet : [{}] " ,url_test_add, JsonUtils.toJson(vo));
        SwaggerResponseVo sr =  SwaggerResponseVo.init().setRequestVo(vo).setBaseVoList(SwaggerBaseVo.build());
        return  SwaggerResultVo.build().setData(sr);
    }

    @RequestMapping(value = url_test_edit, method = RequestMethod.GET)
    @ApiDoc(value = "测试例子修改数据接口",request = SwaggerRequestVo.class, response = long.class)
    @ResponseBody
    public SwaggerResultVo edit(SwaggerRequestVo  vo){
        logger.info("[{}]  recepet : [{}] " ,url_test_edit ,JsonUtils.toJson(vo));
        return SwaggerResultVo.build().setData(Integer.valueOf("1")) ;
    }






}
