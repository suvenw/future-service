package sys;

import org.junit.Before;
import org.junit.Test;
import com.suven.framework.http.processor.url.SysURLCommand;
import com.suven.framework.test.junit.HttpClientTest;
import com.suven.framework.test.rule.annotation.UserAnno;
import com.suven.framework.util.json.JsonFormatTool;

import java.util.HashMap;
import java.util.Map;

public class SysRolePermissionTest {

    @Before
    @UserAnno(userId = 123456,token = "928E97A2CAB0D0AB92D692CAB7B87873",env = "dev")
    public void beforeTest() throws  Exception{
        HttpClientTest.setEnv(this.getClass());

    }

    @Test
    public void sys_sysRolePermission_list(){
        Map map = new HashMap();
        map.put("pageNo",1);
        map.put("pageSize",2);
        String result = HttpClientTest.getURL(SysURLCommand.sys_permission_get_system_menu_list,map);
        JsonFormatTool.formatJson(result);
    }


    @Test
    public void queryTreeList() {
        Map map = new HashMap();
        String result = HttpClientTest.getURL(SysURLCommand.sys_role_query_tree_list,map);
        JsonFormatTool.formatJson(result);
    }


    @Test
    public void roleList() {
        Map map = new HashMap();
        String result = HttpClientTest.getURL(SysURLCommand.sys_role_list,map);
        JsonFormatTool.formatJson(result);
    }
}
