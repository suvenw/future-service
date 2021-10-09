package com.suven.framework.test;

import java.io.*;
import java.util.*;

import com.suven.framework.util.excel.ExcelListener;
import com.suven.framework.util.excel.ExcelUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EasyExcel 文件导入导出 工具类  单元测试
 *
 * @author  dongxie
 */
//@Ignore
public class ExcelUtilsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtilsTest.class);

    /**
     * 最简单的读
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ExcelDemoData}
     * <p>
     * 2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link ExcelListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void simpleRead() throws FileNotFoundException {
        //方便快速测试，  直接将demo的excel文件 放置在编译的目录中
        InputStream initialStream = new FileInputStream(new File(TestFileUtil.getPath() + File.separator + "demo.xlsx"));
        ExcelDemoDao demoDao =new ExcelDemoDao();
        ExcelUtils.readExcel(initialStream,demoDao, ExcelDemoData.class,0);
    }

    /**
     * 最简单的写
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ExcelDemoData}
     * <p>
     * 2. 直接写即可
     */
    @Test
    public void simpleWrite() throws FileNotFoundException {
        //写入excel文件的数据
        ExcelDemoData demoData=new ExcelDemoData();
        demoData.setDate(new Date());
        demoData.setString("测试");

        List<ExcelDemoData> data=new ArrayList<ExcelDemoData>();
        data.add(demoData);
        //写入文件
        OutputStream outputStream = new FileOutputStream(new File(TestFileUtil.getPath()  + File.separator + "demo2.xlsx"));
        ExcelUtils.writeExcel(outputStream, ExcelDemoData.class,data,"测试");

    }

}
