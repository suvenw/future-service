package sys;

import org.junit.Before;
import org.junit.Test;
import com.suven.framework.test.junit.HttpClientTest;
import com.suven.framework.test.rule.annotation.UserAnno;
import com.suven.framework.util.json.JsonFormatTool;

import java.util.HashMap;
import java.util.Map;

public class SysDataLogTest {

    @Before
    @UserAnno(userId = 123456,token = "928E97A2CAB0D0AB92D692CAB7B87873",env = "dev")
    public void beforeTest() throws  Exception{
        HttpClientTest.setEnv(this.getClass());

    }
    ////password  sdk->oauth
    //http://127.0.0.1:9020/top/oauth/token?client_id=unity-client&grant_type=password&username=mobile&password=mobile
    @Test
    public void passwordTest(){
        Map map = new HashMap();
        map.put("id","1");
//        map.put("accountId","88888");
//        map.put("device","device88");
//        map.put("softPackage","softPackage88");
        String result = HttpClientTest.getURL("/sys/sysDataLog/info",map);
        JsonFormatTool.formatJson(result);

    }

    @Test
    public void sys_sysDataLog_addTest(){
        Map map = new HashMap();
//        map.put("id","1");
//        map.put("accountId","88888");
//        map.put("device","device88");
//        map.put("softPackage","softPackage88");
//        SysDataLogRequestVo  vo  = SysDataLogRequestVo.build();
//        vo.toDataId(222).toDataTable("tables").toDataContent("toDataContent").toDataVersion(111112222);
//        String result = HttpClientTest.postURL("/sys/sysDataLog/add",vo);
//        JsonFormatTool.formatJson(result);

    }

    @Test
    public void gradeTest(){
        Map map = new HashMap();
        map.put("playLength","8888");
        String result = HttpClientTest.postURL("/sys/userGrade/add",map);
        JsonFormatTool.formatJson(result);
    }

}
