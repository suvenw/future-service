import org.junit.Before;
import org.junit.Test;
import com.suven.framework.test.junit.HttpClientTest;
import com.suven.framework.test.rule.annotation.UserAnno;
import com.suven.framework.util.crypt.CryptUtil;
import com.suven.framework.util.io.FileToByteArrayUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadTest extends HttpClientTest {



    @Before
    @UserAnno(userId = 123456,token = "928E97A2CAB0D0AB92D692CAB7B87873",env = "test")
    public void beforeTest() throws  Exception{
        HttpClientTest.setEnv(this.getClass());

    }

    //http://localhost:8090/oauth/authorize?response_type=code&scope=read write&client_id=test&redirect_uri=http://localhost:8000/login&state=09876999
    @Test
    public void test() throws Exception{

        String path = "/Users/xinye/Pictures/banner1@2x (2).png";
//        path = "/Users/suven/sample-debug.apk";
        byte[] bytes = FileToByteArrayUtils.toFileChannel(path);
        String str = "1,2,3,4,5";
//        byte[] bytes = str.getBytes();
        String type = path.substring(path.lastIndexOf(".")+1);

//        map.put("blockSize",bytes);
        Map map = new HashMap();
        map.put("fileSize",bytes.length);
        map.put("fileMd5",CryptUtil.md5(bytes));
        map.put("fileType",type);
        map.put("offset","0");

//        HttpClientTest.uploadURL(path, URLFileCommand.upload_post_file,map);



    }

    @Test
    public void test2() throws Exception{
        List<String> urls = new ArrayList<>();
        for (int i = 1 ; i < 3 ;i++){
            Map map = new HashMap();
//            String path = "/Users/suven/"+i+".png";
            String path = "/Users/xinye/Downloads/Telegram Desktop/logo.png";
            byte[] bytes = FileToByteArrayUtils.toFileChannel(path);
            map.put("blockSize",bytes);
            map.put("fileSize",bytes.length);
            map.put("fileMd5", CryptUtil.md5(bytes));
            map.put("fileType","png");
            map.put("offset","0");

//            String url =   HttpClientTest.postURL(URLFileCommand.upload_post_file_byte,map);
//            JSONObject object = JsonUtils.readValue(url, JSONObject.class);
//            urls.add(object.getJSONObject("data").getString("path"));
        }
        System.out.println("\n"+urls.get(urls.size()-1));




    }

    @Test
    public void file_byteTest() throws Exception{

        Map map = new HashMap();
        String path = "/Users/xinye/Pictures/女装/yui002.jpg";
//        path = "/Users/suven/sample-debug.apk";
        byte[] bytes = FileToByteArrayUtils.toFileChannel(path);
        String str = "1,2,3,4,5";
//        byte[] bytes = str.getBytes();
        String type = path.substring(path.lastIndexOf(".")+1);
//        System.arraycopy()
//        byte[] data = new byte[3000];
//        System.arraycopy(bytes,  0,data, 0,3000);

        map.put("blockSize",bytes);
        map.put("fileSize",bytes.length);
        map.put("fileMd5", CryptUtil.md5(bytes));
        map.put("fileType",type);
        map.put("offset","0");

//        HttpClientTest.getURL(URLFileCommand.upload_post_file_byte,map);



    }

    @Test
    public void blockSizeTest2() throws Exception{

        Map map = new HashMap();

        String path = "/Users/xinye/Pictures/女装/yui002.jpg";
//        path = "/Users/suven/sample-debug.apk";
        byte[] bytes = FileToByteArrayUtils.toFileChannel(path);
        String str = "1,2,3,4,5";
//        byte[] bytes = str.getBytes();
        String mdPath =  CryptUtil.md5(bytes)+"c12";
        String type = path.substring(path.lastIndexOf(".")+1);
        System.out.println("=========="+bytes.length);

        byte[] data = new byte[6698];
        System.arraycopy(bytes,  0,data, 0,6698);//6698

        map.put("blockSize",data);
        map.put("fileSize",bytes.length);
        map.put("fileMd5", mdPath);
        map.put("fileType",type);
        map.put("offset",0);
//        HttpClientTest.postURL(URLFileCommand.upload_post_file_string_block,map);





    }
    @Test
    public void blockSizeTest() throws Exception{

        Map map = new HashMap();
        String path = "/Users/xinye/Pictures/女装/yui002.jpg";
        long bytesLength = new File(path).length();
//        String path = "/Users/suven/2.png";
//        path = "/Users/suven/sample-debug.apk";
//        byte[] bytes = FileToByteArrayUtils.toFileChannel(path);
        String str = "1,2,3,4,5";
//        byte[] bytes = str.getBytes();
        String type = path.substring(path.lastIndexOf(".")+1);

        int buf_size = 1024*1024;
        byte[] datas = FileToByteArrayUtils.toAccessFile(path,0,buf_size);
        String mdPath = CryptUtil.md5(new String(datas));
//        int buf_size = 3000;
//        byte[] data = new byte[buf_size];
        int forCount = (int) bytesLength/buf_size;
        int forEnd =  (int) bytesLength%buf_size;
        for (int i = 0 ; i < forCount +1; i++ ){
            int offset = i*buf_size;
            if(forCount == i){
                buf_size = forEnd;
//                data = new byte[buf_size];
            }
//            System.arraycopy(bytes,  offset,data, 0,buf_size);
            byte[] data = FileToByteArrayUtils.toAccessFile(path,offset,buf_size);
            map.put("blockSize",data);
            map.put("fileSize",bytesLength);
            map.put("fileMd5", mdPath);
            map.put("fileType",type);
            map.put("offset",offset);
//            HttpClientTest.postURL(URLCommand.upload_post_file_block,map);
        }






//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        BufferedInputStream in = null;
//        try {
//            in = new BufferedInputStream(new FileInputStream(path));
//            int buf_size = 1024*100;
//            byte[] buffer = new byte[buf_size];
//            int len = 0;
//            int i = 0;
//            while (-1 != (len = in.read(buffer, 0, buf_size))) {
//                bos.write(buffer, 0, len);
//                byte[] data =  bos.toByteArray();
//
//                map.put("blockSize",data);
//                map.put("fileSize",bytes.length);
//                map.put("fileMd5", mdPath);
//                map.put("fileType",type);
//                map.put("offset",i);
//                i =  i + data.length;
//                HttpClientTest.uploadURL(path,URLCommand.upload_post_file_block,map);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw e;
//        } finally {
//            try {
//                in.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            bos.close();
//        }




    }

    @Test
    public void blockSizeTest3() throws Exception{
        System.out.println(1234/100);
        System.out.println(1234%100);
    }

    @Test
    public void blockSlaveSizeTest() throws Exception {

        Map map = new HashMap();
        String basePath = "/group1/M00/00/72/CgoPC10luheAa5emAANEdKItXDY1837.ts";
//        String path = "/Users/suven/aikai-app.mp4";
//        String type = path.substring(path.lastIndexOf(".") + 1);
        String json = "{\"list\":[\"48fbf2591ff000000.ts\",\"48fbf2591ff000001.ts\",\"48fbf2591ff000002.ts\",\"48fbf2591ff000003.ts\",\"48fbf2591ff000004.ts\",\"48fbf2591ff000005.ts\",\"48fbf2591ff000006.ts\",\"48fbf2591ff000007.ts\",\"48fbf2591ff000008.ts\",\"48fbf2591ff000009.ts\",\"48fbf2591ff000010.ts\",\"48fbf2591ff000011.ts\",\"48fbf2591ff000012.ts\",\"48fbf2591ff000013.ts\",\"48fbf2591ff000014.ts\",\"48fbf2591ff000015.ts\",\"48fbf2591ff000016.ts\",\"48fbf2591ff000017.ts\",\"48fbf2591ff000018.ts\",\"48fbf2591ff000019.ts\",\"48fbf2591ff000020.ts\",\"48fbf2591ff000021.ts\",\"48fbf2591ff000022.ts\",\"48fbf2591ff000023.ts\",\"48fbf2591ff000024.ts\",\"48fbf2591ff000025.ts\",\"48fbf2591ff000026.ts\",\"48fbf2591ff000027.ts\",\"48fbf2591ff000028.ts\",\"48fbf2591ff000029.ts\",\"48fbf2591ff000030.ts\",\"48fbf2591ff000031.ts\",\"48fbf2591ff000032.ts\",\"48fbf2591ff000033.ts\",\"48fbf2591ff000034.ts\",\"48fbf2591ff000035.ts\",\"48fbf2591ff000036.ts\",\"48fbf2591ff000037.ts\",\"48fbf2591ff000038.ts\",\"48fbf2591ff000039.ts\",\"48fbf2591ff000040.ts\",\"48fbf2591ff000041.ts\",\"48fbf2591ff000042.ts\",\"48fbf2591ff000043.ts\",\"48fbf2591ff000044.ts\",\"48fbf2591ff000045.ts\",\"48fbf2591ff000046.ts\",\"48fbf2591ff000047.ts\",\"48fbf2591ff000048.ts\",\"48fbf2591ff000049.ts\",\"48fbf2591ff000050.ts\",\"48fbf2591ff000051.ts\",\"48fbf2591ff000052.ts\",\"48fbf2591ff000053.ts\",\"48fbf2591ff000054.ts\",\"48fbf2591ff000055.ts\",\"48fbf2591ff000056.ts\",\"48fbf2591ff000057.ts\",\"48fbf2591ff000058.ts\",\"48fbf2591ff000059.ts\",\"48fbf2591ff000060.ts\",\"48fbf2591ff000061.ts\",\"48fbf2591ff000062.ts\",\"48fbf2591ff000063.ts\",\"48fbf2591ff000064.ts\",\"48fbf2591ff000065.ts\",\"48fbf2591ff000066.ts\",\"48fbf2591ff000067.ts\",\"48fbf2591ff000068.ts\",\"48fbf2591ff000069.ts\",\"48fbf2591ff000070.ts\",\"48fbf2591ff000071.ts\",\"48fbf2591ff000072.ts\",\"48fbf2591ff000073.ts\",\"48fbf2591ff000074.ts\",\"48fbf2591ff000075.ts\",\"48fbf2591ff000076.ts\",\"48fbf2591ff000077.ts\",\"48fbf2591ff000078.ts\",\"48fbf2591ff000079.ts\",\"48fbf2591ff000080.ts\",\"48fbf2591ff000081.ts\",\"48fbf2591ff000082.ts\",\"48fbf2591ff000083.ts\",\"48fbf2591ff000084.ts\",\"48fbf2591ff000085.ts\",\"48fbf2591ff000086.ts\",\"48fbf2591ff000087.ts\",\"48fbf2591ff000088.ts\",\"48fbf2591ff000089.ts\",\"48fbf2591ff000090.ts\",\"48fbf2591ff000091.ts\",\"48fbf2591ff000092.ts\",\"48fbf2591ff000093.ts\",\"48fbf2591ff000094.ts\",\"48fbf2591ff000095.ts\",\"48fbf2591ff000096.ts\",\"48fbf2591ff000097.ts\",\"48fbf2591ff000098.ts\",\"48fbf2591ff000099.ts\",\"48fbf2591ff000100.ts\",\"48fbf2591ff000101.ts\",\"48fbf2591ff000102.ts\",\"48fbf2591ff000103.ts\",\"48fbf2591ff000104.ts\",\"48fbf2591ff000105.ts\",\"48fbf2591ff000106.ts\",\"48fbf2591ff000107.ts\",\"48fbf2591ff000108.ts\",\"48fbf2591ff000109.ts\",\"48fbf2591ff000110.ts\",\"48fbf2591ff000111.ts\",\"48fbf2591ff000112.ts\",\"48fbf2591ff000113.ts\",\"48fbf2591ff000114.ts\",\"48fbf2591ff000115.ts\",\"48fbf2591ff000116.ts\",\"48fbf2591ff000117.ts\",\"48fbf2591ff000118.ts\",\"48fbf2591ff000119.ts\",\"48fbf2591ff000120.ts\",\"48fbf2591ff000121.ts\",\"48fbf2591ff000122.ts\",\"48fbf2591ff000123.ts\",\"48fbf2591ff000124.ts\",\"48fbf2591ff000125.ts\",\"48fbf2591ff000126.ts\",\"48fbf2591ff000127.ts\",\"48fbf2591ff000128.ts\",\"48fbf2591ff000129.ts\",\"48fbf2591ff000130.ts\",\"48fbf2591ff000131.ts\",\"48fbf2591ff000132.ts\",\"48fbf2591ff000133.ts\",\"48fbf2591ff000134.ts\",\"48fbf2591ff000135.ts\",\"48fbf2591ff000136.ts\",\"48fbf2591ff000137.ts\",\"48fbf2591ff000138.ts\",\"48fbf2591ff000139.ts\",\"48fbf2591ff000140.ts\",\"48fbf2591ff000141.ts\",\"48fbf2591ff000142.ts\",\"48fbf2591ff000143.ts\",\"48fbf2591ff000144.ts\",\"48fbf2591ff000145.ts\",\"48fbf2591ff000146.ts\",\"48fbf2591ff000147.ts\",\"48fbf2591ff000148.ts\",\"48fbf2591ff000149.ts\",\"48fbf2591ff000150.ts\",\"48fbf2591ff000151.ts\",\"48fbf2591ff000152.ts\",\"48fbf2591ff000153.ts\",\"48fbf2591ff000154.ts\",\"48fbf2591ff000155.ts\",\"48fbf2591ff000156.ts\",\"48fbf2591ff000157.ts\",\"48fbf2591ff000158.ts\",\"48fbf2591ff000159.ts\",\"48fbf2591ff000160.ts\",\"48fbf2591ff000161.ts\",\"48fbf2591ff000162.ts\",\"48fbf2591ff000163.ts\",\"48fbf2591ff000164.ts\",\"48fbf2591ff000165.ts\",\"48fbf2591ff000166.ts\",\"48fbf2591ff000167.ts\",\"48fbf2591ff000168.ts\",\"48fbf2591ff000169.ts\",\"48fbf2591ff000170.ts\",\"48fbf2591ff000171.ts\",\"48fbf2591ff000172.ts\",\"48fbf2591ff000173.ts\",\"48fbf2591ff000174.ts\",\"48fbf2591ff000175.ts\",\"48fbf2591ff000176.ts\",\"48fbf2591ff000177.ts\",\"48fbf2591ff000178.ts\",\"48fbf2591ff000179.ts\",\"48fbf2591ff000180.ts\",\"48fbf2591ff000181.ts\",\"48fbf2591ff000182.ts\",\"48fbf2591ff000183.ts\",\"48fbf2591ff000184.ts\",\"48fbf2591ff000185.ts\",\"48fbf2591ff000186.ts\",\"48fbf2591ff000187.ts\",\"48fbf2591ff000188.ts\",\"48fbf2591ff000189.ts\",\"48fbf2591ff000190.ts\",\"48fbf2591ff000191.ts\",\"48fbf2591ff000192.ts\",\"48fbf2591ff000193.ts\",\"48fbf2591ff000194.ts\",\"48fbf2591ff000195.ts\",\"48fbf2591ff000196.ts\",\"48fbf2591ff000197.ts\",\"48fbf2591ff000198.ts\",\"48fbf2591ff000199.ts\",\"48fbf2591ff000200.ts\",\"48fbf2591ff000201.ts\",\"48fbf2591ff000202.ts\",\"48fbf2591ff000203.ts\",\"48fbf2591ff000204.ts\",\"48fbf2591ff000205.ts\",\"48fbf2591ff000206.ts\",\"48fbf2591ff000207.ts\",\"48fbf2591ff000208.ts\",\"48fbf2591ff000209.ts\",\"48fbf2591ff000210.ts\",\"48fbf2591ff000211.ts\",\"48fbf2591ff000212.ts\",\"48fbf2591ff000213.ts\",\"48fbf2591ff000214.ts\",\"48fbf2591ff000215.ts\",\"48fbf2591ff000216.ts\",\"48fbf2591ff000217.ts\",\"48fbf2591ff000218.ts\",\"48fbf2591ff000219.ts\",\"48fbf2591ff000220.ts\",\"48fbf2591ff000221.ts\",\"48fbf2591ff000222.ts\",\"48fbf2591ff000223.ts\",\"48fbf2591ff000224.ts\",\"48fbf2591ff000225.ts\",\"48fbf2591ff000226.ts\",\"48fbf2591ff000227.ts\",\"48fbf2591ff000228.ts\",\"48fbf2591ff000229.ts\",\"48fbf2591ff000230.ts\",\"48fbf2591ff000231.ts\",\"48fbf2591ff000232.ts\",\"48fbf2591ff000233.ts\",\"48fbf2591ff000234.ts\",\"48fbf2591ff000235.ts\",\"48fbf2591ff000236.ts\",\"48fbf2591ff000237.ts\",\"48fbf2591ff000238.ts\",\"48fbf2591ff000239.ts\",\"48fbf2591ff000240.ts\",\"48fbf2591ff000241.ts\",\"48fbf2591ff000242.ts\",\"48fbf2591ff000243.ts\",\"48fbf2591ff000244.ts\",\"48fbf2591ff000245.ts\",\"48fbf2591ff000246.ts\",\"48fbf2591ff000247.ts\",\"48fbf2591ff000248.ts\",\"48fbf2591ff000249.ts\",\"48fbf2591ff000250.ts\",\"48fbf2591ff000251.ts\",\"48fbf2591ff000252.ts\",\"48fbf2591ff000253.ts\",\"48fbf2591ff000254.ts\",\"48fbf2591ff000255.ts\",\"48fbf2591ff000256.ts\",\"48fbf2591ff000257.ts\",\"48fbf2591ff000258.ts\",\"48fbf2591ff000259.ts\",\"48fbf2591ff000260.ts\",\"48fbf2591ff000261.ts\",\"48fbf2591ff000262.ts\",\"48fbf2591ff000263.ts\",\"48fbf2591ff000264.ts\",\"48fbf2591ff000265.ts\",\"48fbf2591ff000266.ts\",\"48fbf2591ff000267.ts\",\"48fbf2591ff000268.ts\",\"48fbf2591ff000269.ts\",\"48fbf2591ff000270.ts\",\"48fbf2591ff000271.ts\",\"48fbf2591ff000272.ts\",\"48fbf2591ff000273.ts\",\"48fbf2591ff000274.ts\",\"48fbf2591ff000275.ts\",\"48fbf2591ff000276.ts\",\"48fbf2591ff000277.ts\",\"48fbf2591ff000278.ts\",\"48fbf2591ff000279.ts\",\"48fbf2591ff000280.ts\",\"48fbf2591ff000281.ts\",\"48fbf2591ff000282.ts\",\"48fbf2591ff000283.ts\",\"48fbf2591ff000284.ts\",\"48fbf2591ff000285.ts\",\"48fbf2591ff000286.ts\",\"48fbf2591ff000287.ts\",\"48fbf2591ff000288.ts\",\"48fbf2591ff000289.ts\",\"48fbf2591ff000290.ts\",\"48fbf2591ff000291.ts\",\"48fbf2591ff000292.ts\",\"48fbf2591ff000293.ts\",\"48fbf2591ff000294.ts\",\"48fbf2591ff000295.ts\",\"48fbf2591ff000296.ts\",\"48fbf2591ff000297.ts\",\"48fbf2591ff000298.ts\",\"48fbf2591ff000299.ts\",\"48fbf2591ff000300.ts\",\"48fbf2591ff000301.ts\",\"48fbf2591ff000302.ts\",\"48fbf2591ff000303.ts\",\"48fbf2591ff000304.ts\",\"48fbf2591ff000305.ts\",\"48fbf2591ff000306.ts\",\"48fbf2591ff000307.ts\",\"48fbf2591ff000308.ts\",\"48fbf2591ff000309.ts\",\"48fbf2591ff000310.ts\",\"48fbf2591ff000311.ts\",\"48fbf2591ff000312.ts\",\"48fbf2591ff000313.ts\",\"48fbf2591ff000314.ts\",\"48fbf2591ff000315.ts\",\"48fbf2591ff000316.ts\",\"48fbf2591ff000317.ts\",\"48fbf2591ff000318.ts\",\"48fbf2591ff000319.ts\",\"48fbf2591ff000320.ts\",\"48fbf2591ff000321.ts\",\"48fbf2591ff000322.ts\",\"48fbf2591ff000323.ts\",\"48fbf2591ff000324.ts\",\"48fbf2591ff000325.ts\",\"48fbf2591ff000326.ts\",\"48fbf2591ff000327.ts\",\"48fbf2591ff000328.ts\",\"48fbf2591ff000329.ts\",\"48fbf2591ff000330.ts\",\"48fbf2591ff000331.ts\",\"48fbf2591ff000332.ts\",\"48fbf2591ff000333.ts\",\"48fbf2591ff000334.ts\",\"48fbf2591ff000335.ts\",\"48fbf2591ff000336.ts\",\"48fbf2591ff000337.ts\",\"48fbf2591ff000338.ts\",\"48fbf2591ff000339.ts\",\"48fbf2591ff000340.ts\",\"48fbf2591ff000341.ts\",\"48fbf2591ff000342.ts\",\"48fbf2591ff000343.ts\",\"48fbf2591ff000344.ts\",\"48fbf2591ff000345.ts\",\"48fbf2591ff000346.ts\",\"48fbf2591ff000347.ts\",\"48fbf2591ff000348.ts\",\"48fbf2591ff000349.ts\",\"48fbf2591ff000350.ts\",\"48fbf2591ff000351.ts\",\"48fbf2591ff000352.ts\",\"48fbf2591ff000353.ts\",\"48fbf2591ff000354.ts\",\"48fbf2591ff000355.ts\",\"48fbf2591ff000356.ts\",\"48fbf2591ff000357.ts\",\"48fbf2591ff000358.ts\",\"48fbf2591ff000359.ts\",\"48fbf2591ff000360.ts\",\"48fbf2591ff000361.ts\",\"48fbf2591ff000362.ts\",\"48fbf2591ff000363.ts\",\"48fbf2591ff000364.ts\",\"48fbf2591ff000365.ts\",\"48fbf2591ff000366.ts\",\"48fbf2591ff000367.ts\",\"48fbf2591ff000368.ts\",\"48fbf2591ff000369.ts\",\"48fbf2591ff000370.ts\",\"48fbf2591ff000371.ts\",\"48fbf2591ff000372.ts\",\"48fbf2591ff000373.ts\",\"48fbf2591ff000374.ts\",\"48fbf2591ff000375.ts\",\"48fbf2591ff000376.ts\",\"48fbf2591ff000377.ts\",\"48fbf2591ff000378.ts\",\"48fbf2591ff000379.ts\",\"48fbf2591ff000380.ts\",\"48fbf2591ff000381.ts\",\"48fbf2591ff000382.ts\",\"48fbf2591ff000383.ts\",\"48fbf2591ff000384.ts\",\"48fbf2591ff000385.ts\",\"48fbf2591ff000386.ts\",\"48fbf2591ff000387.ts\",\"48fbf2591ff000388.ts\",\"48fbf2591ff000389.ts\",\"48fbf2591ff000390.ts\",\"48fbf2591ff000391.ts\",\"48fbf2591ff000392.ts\",\"48fbf2591ff000393.ts\",\"48fbf2591ff000394.ts\",\"48fbf2591ff000395.ts\",\"48fbf2591ff000396.ts\",\"48fbf2591ff000397.ts\",\"48fbf2591ff000398.ts\",\"48fbf2591ff000399.ts\",\"48fbf2591ff000400.ts\",\"48fbf2591ff000401.ts\",\"48fbf2591ff000402.ts\",\"48fbf2591ff000403.ts\",\"48fbf2591ff000404.ts\",\"48fbf2591ff000405.ts\",\"48fbf2591ff000406.ts\",\"48fbf2591ff000407.ts\",\"48fbf2591ff000408.ts\",\"48fbf2591ff000409.ts\",\"48fbf2591ff000410.ts\",\"48fbf2591ff000411.ts\",\"48fbf2591ff000412.ts\",\"48fbf2591ff000413.ts\",\"48fbf2591ff000414.ts\",\"48fbf2591ff000415.ts\",\"48fbf2591ff000416.ts\",\"48fbf2591ff000417.ts\",\"48fbf2591ff000418.ts\",\"48fbf2591ff000419.ts\",\"48fbf2591ff000420.ts\",\"48fbf2591ff000421.ts\",\"48fbf2591ff000422.ts\",\"48fbf2591ff000423.ts\",\"48fbf2591ff000424.ts\",\"48fbf2591ff000425.ts\",\"48fbf2591ff000426.ts\",\"48fbf2591ff000427.ts\",\"48fbf2591ff000428.ts\",\"48fbf2591ff000429.ts\",\"48fbf2591ff000430.ts\",\"48fbf2591ff000431.ts\",\"48fbf2591ff000432.ts\",\"48fbf2591ff000433.ts\",\"48fbf2591ff000434.ts\",\"48fbf2591ff000435.ts\",\"48fbf2591ff000436.ts\",\"48fbf2591ff000437.ts\",\"48fbf2591ff000438.ts\",\"48fbf2591ff000439.ts\",\"48fbf2591ff000440.ts\",\"48fbf2591ff000441.ts\",\"48fbf2591ff000442.ts\",\"48fbf2591ff000443.ts\",\"48fbf2591ff000444.ts\",\"48fbf2591ff000445.ts\",\"48fbf2591ff000446.ts\",\"48fbf2591ff000447.ts\",\"48fbf2591ff000448.ts\",\"48fbf2591ff000449.ts\",\"48fbf2591ff000450.ts\",\"48fbf2591ff000451.ts\",\"48fbf2591ff000452.ts\",\"48fbf2591ff000453.ts\",\"48fbf2591ff000454.ts\",\"48fbf2591ff000455.ts\",\"48fbf2591ff000456.ts\",\"48fbf2591ff000457.ts\",\"48fbf2591ff000458.ts\",\"48fbf2591ff000459.ts\",\"48fbf2591ff000460.ts\",\"48fbf2591ff000461.ts\",\"48fbf2591ff000462.ts\",\"48fbf2591ff000463.ts\",\"48fbf2591ff000464.ts\",\"48fbf2591ff000465.ts\",\"48fbf2591ff000466.ts\",\"48fbf2591ff000467.ts\",\"48fbf2591ff000468.ts\",\"48fbf2591ff000469.ts\",\"48fbf2591ff000470.ts\",\"48fbf2591ff000471.ts\",\"48fbf2591ff000472.ts\",\"48fbf2591ff000473.ts\",\"48fbf2591ff000474.ts\",\"48fbf2591ff000475.ts\",\"48fbf2591ff000476.ts\",\"48fbf2591ff000477.ts\",\"48fbf2591ff000478.ts\",\"48fbf2591ff000479.ts\",\"48fbf2591ff000480.ts\",\"48fbf2591ff000481.ts\",\"48fbf2591ff000482.ts\",\"48fbf2591ff000483.ts\",\"48fbf2591ff000484.ts\",\"48fbf2591ff000485.ts\",\"48fbf2591ff000486.ts\",\"48fbf2591ff000487.ts\",\"48fbf2591ff000488.ts\",\"48fbf2591ff000489.ts\",\"48fbf2591ff000490.ts\",\"48fbf2591ff000491.ts\",\"48fbf2591ff000492.ts\",\"48fbf2591ff000493.ts\",\"48fbf2591ff000494.ts\",\"48fbf2591ff000495.ts\",\"48fbf2591ff000496.ts\",\"48fbf2591ff000497.ts\",\"48fbf2591ff000498.ts\",\"48fbf2591ff000499.ts\",\"48fbf2591ff000500.ts\",\"48fbf2591ff000501.ts\",\"48fbf2591ff000502.ts\",\"48fbf2591ff000503.ts\",\"48fbf2591ff000504.ts\",\"48fbf2591ff000505.ts\",\"48fbf2591ff000506.ts\",\"48fbf2591ff000507.ts\",\"48fbf2591ff000508.ts\",\"48fbf2591ff000509.ts\",\"48fbf2591ff000510.ts\",\"48fbf2591ff000511.ts\",\"48fbf2591ff000512.ts\",\"48fbf2591ff000513.ts\",\"48fbf2591ff000514.ts\",\"48fbf2591ff000515.ts\",\"48fbf2591ff000516.ts\",\"48fbf2591ff000517.ts\",\"48fbf2591ff000518.ts\",\"48fbf2591ff000519.ts\",\"48fbf2591ff000520.ts\",\"48fbf2591ff000521.ts\",\"48fbf2591ff000522.ts\",\"48fbf2591ff000523.ts\",\"48fbf2591ff000524.ts\",\"48fbf2591ff000525.ts\",\"48fbf2591ff000526.ts\",\"48fbf2591ff000527.ts\",\"48fbf2591ff000528.ts\",\"48fbf2591ff000529.ts\",\"48fbf2591ff000530.ts\",\"48fbf2591ff000531.ts\",\"48fbf2591ff000532.ts\",\"48fbf2591ff000533.ts\",\"48fbf2591ff000534.ts\",\"48fbf2591ff000535.ts\",\"48fbf2591ff000536.ts\",\"48fbf2591ff000537.ts\",\"48fbf2591ff000538.ts\",\"48fbf2591ff000539.ts\",\"48fbf2591ff000540.ts\",\"48fbf2591ff000541.ts\",\"48fbf2591ff000542.ts\",\"48fbf2591ff000543.ts\",\"48fbf2591ff000544.ts\",\"48fbf2591ff000545.ts\",\"48fbf2591ff000546.ts\",\"48fbf2591ff000547.ts\",\"48fbf2591ff000548.ts\",\"48fbf2591ff000549.ts\",\"48fbf2591ff000550.ts\",\"48fbf2591ff000551.ts\",\"48fbf2591ff000552.ts\",\"48fbf2591ff000553.ts\",\"48fbf2591ff000554.ts\",\"48fbf2591ff000555.ts\",\"48fbf2591ff000556.ts\",\"48fbf2591ff000557.ts\",\"48fbf2591ff000558.ts\",\"48fbf2591ff000559.ts\",\"48fbf2591ff000560.ts\",\"48fbf2591ff000561.ts\",\"48fbf2591ff000562.ts\",\"48fbf2591ff000563.ts\",\"48fbf2591ff000564.ts\",\"48fbf2591ff000565.ts\",\"48fbf2591ff000566.ts\",\"48fbf2591ff000567.ts\",\"48fbf2591ff000568.ts\",\"48fbf2591ff000569.ts\",\"48fbf2591ff000570.ts\",\"48fbf2591ff000571.ts\",\"48fbf2591ff000572.ts\",\"48fbf2591ff000573.ts\",\"48fbf2591ff000574.ts\",\"48fbf2591ff000575.ts\",\"48fbf2591ff000576.ts\",\"48fbf2591ff000577.ts\",\"48fbf2591ff000578.ts\",\"48fbf2591ff000579.ts\",\"48fbf2591ff000580.ts\",\"48fbf2591ff000581.ts\",\"48fbf2591ff000582.ts\",\"48fbf2591ff000583.ts\",\"48fbf2591ff000584.ts\",\"48fbf2591ff000585.ts\",\"48fbf2591ff000586.ts\",\"48fbf2591ff000587.ts\",\"48fbf2591ff000588.ts\",\"48fbf2591ff000589.ts\",\"48fbf2591ff000590.ts\",\"48fbf2591ff000591.ts\",\"48fbf2591ff000592.ts\",\"48fbf2591ff000593.ts\",\"48fbf2591ff000594.ts\",\"48fbf2591ff000595.ts\",\"48fbf2591ff000596.ts\",\"48fbf2591ff000597.ts\",\"48fbf2591ff000598.ts\",\"48fbf2591ff000599.ts\",\"48fbf2591ff000600.ts\",\"48fbf2591ff000601.ts\",\"48fbf2591ff000602.ts\",\"48fbf2591ff000603.ts\",\"48fbf2591ff000604.ts\",\"48fbf2591ff000605.ts\",\"48fbf2591ff000606.ts\",\"48fbf2591ff000607.ts\",\"48fbf2591ff000608.ts\",\"48fbf2591ff000609.ts\",\"48fbf2591ff000610.ts\",\"48fbf2591ff000611.ts\",\"48fbf2591ff000612.ts\",\"48fbf2591ff000613.ts\",\"48fbf2591ff000614.ts\",\"48fbf2591ff000615.ts\",\"48fbf2591ff000616.ts\",\"48fbf2591ff000617.ts\",\"48fbf2591ff000618.ts\",\"48fbf2591ff000619.ts\",\"48fbf2591ff000620.ts\",\"48fbf2591ff000621.ts\",\"48fbf2591ff000622.ts\",\"48fbf2591ff000623.ts\",\"48fbf2591ff000624.ts\",\"48fbf2591ff000625.ts\",\"48fbf2591ff000626.ts\",\"48fbf2591ff000627.ts\",\"48fbf2591ff000628.ts\",\"48fbf2591ff000629.ts\",\"48fbf2591ff000630.ts\",\"48fbf2591ff000631.ts\",\"48fbf2591ff000632.ts\",\"48fbf2591ff000633.ts\",\"48fbf2591ff000634.ts\",\"48fbf2591ff000635.ts\",\"48fbf2591ff000636.ts\",\"48fbf2591ff000637.ts\",\"48fbf2591ff000638.ts\",\"48fbf2591ff000639.ts\",\"48fbf2591ff000640.ts\",\"48fbf2591ff000641.ts\",\"48fbf2591ff000642.ts\",\"48fbf2591ff000643.ts\",\"48fbf2591ff000644.ts\",\"48fbf2591ff000645.ts\",\"48fbf2591ff000646.ts\",\"48fbf2591ff000647.ts\",\"48fbf2591ff000648.ts\",\"48fbf2591ff000649.ts\",\"48fbf2591ff000650.ts\",\"48fbf2591ff000651.ts\",\"48fbf2591ff000652.ts\",\"48fbf2591ff000653.ts\",\"48fbf2591ff000654.ts\",\"48fbf2591ff000655.ts\",\"48fbf2591ff000656.ts\",\"48fbf2591ff000657.ts\",\"48fbf2591ff000658.ts\",\"48fbf2591ff000659.ts\",\"48fbf2591ff000660.ts\",\"48fbf2591ff000661.ts\",\"48fbf2591ff000662.ts\",\"48fbf2591ff000663.ts\",\"48fbf2591ff000664.ts\",\"48fbf2591ff000665.ts\",\"48fbf2591ff000666.ts\",\"48fbf2591ff000667.ts\",\"48fbf2591ff000668.ts\",\"48fbf2591ff000669.ts\",\"48fbf2591ff000670.ts\",\"48fbf2591ff000671.ts\",\"48fbf2591ff000672.ts\",\"48fbf2591ff000673.ts\",\"48fbf2591ff000674.ts\",\"48fbf2591ff000675.ts\",\"48fbf2591ff000676.ts\",\"48fbf2591ff000677.ts\",\"48fbf2591ff000678.ts\",\"48fbf2591ff000679.ts\",\"48fbf2591ff000680.ts\",\"48fbf2591ff000681.ts\",\"48fbf2591ff000682.ts\",\"48fbf2591ff000683.ts\",\"48fbf2591ff000684.ts\",\"48fbf2591ff000685.ts\",\"48fbf2591ff000686.ts\",\"48fbf2591ff000687.ts\",\"48fbf2591ff000688.ts\",\"48fbf2591ff000689.ts\",\"48fbf2591ff000690.ts\",\"48fbf2591ff000691.ts\",\"48fbf2591ff000692.ts\",\"48fbf2591ff000693.ts\",\"48fbf2591ff000694.ts\",\"48fbf2591ff000695.ts\",\"48fbf2591ff000696.ts\",\"48fbf2591ff000697.ts\",\"48fbf2591ff000698.ts\",\"48fbf2591ff000699.ts\",\"48fbf2591ff000700.ts\",\"48fbf2591ff000701.ts\",\"48fbf2591ff000702.ts\",\"48fbf2591ff000703.ts\",\"48fbf2591ff000704.ts\",\"48fbf2591ff000705.ts\",\"48fbf2591ff000706.ts\",\"48fbf2591ff000707.ts\",\"48fbf2591ff000708.ts\",\"48fbf2591ff000709.ts\",\"48fbf2591ff000710.ts\",\"48fbf2591ff000711.ts\",\"48fbf2591ff000712.ts\",\"48fbf2591ff000713.ts\",\"48fbf2591ff000714.ts\",\"48fbf2591ff000715.ts\",\"48fbf2591ff000716.ts\",\"48fbf2591ff000717.ts\",\"48fbf2591ff000718.ts\",\"48fbf2591ff000719.ts\",\"48fbf2591ff000720.ts\",\"48fbf2591ff000721.ts\",\"48fbf2591ff000722.ts\",\"48fbf2591ff000723.ts\",\"48fbf2591ff000724.ts\",\"48fbf2591ff000725.ts\",\"48fbf2591ff000726.ts\",\"48fbf2591ff000727.ts\",\"48fbf2591ff000728.ts\",\"48fbf2591ff000729.ts\",\"48fbf2591ff000730.ts\",\"48fbf2591ff000731.ts\",\"48fbf2591ff000732.ts\",\"48fbf2591ff000733.ts\",\"48fbf2591ff000734.ts\",\"48fbf2591ff000735.ts\",\"48fbf2591ff000736.ts\",\"48fbf2591ff000737.ts\",\"48fbf2591ff000738.ts\",\"48fbf2591ff000739.ts\",\"48fbf2591ff000740.ts\",\"48fbf2591ff000741.ts\",\"48fbf2591ff000742.ts\",\"48fbf2591ff000743.ts\",\"48fbf2591ff000744.ts\",\"48fbf2591ff000745.ts\",\"48fbf2591ff000746.ts\",\"48fbf2591ff000747.ts\",\"48fbf2591ff000748.ts\",\"48fbf2591ff000749.ts\",\"48fbf2591ff000750.ts\",\"48fbf2591ff000751.ts\",\"48fbf2591ff000752.ts\",\"48fbf2591ff000753.ts\",\"48fbf2591ff000754.ts\",\"48fbf2591ff000755.ts\",\"48fbf2591ff000756.ts\",\"48fbf2591ff000757.ts\",\"48fbf2591ff000758.ts\",\"48fbf2591ff000759.ts\",\"48fbf2591ff000760.ts\",\"48fbf2591ff000761.ts\",\"48fbf2591ff000762.ts\",\"48fbf2591ff000763.ts\",\"48fbf2591ff000764.ts\",\"48fbf2591ff000765.ts\",\"48fbf2591ff000766.ts\",\"48fbf2591ff000767.ts\",\"48fbf2591ff000768.ts\",\"48fbf2591ff000769.ts\",\"48fbf2591ff000770.ts\",\"48fbf2591ff000771.ts\",\"48fbf2591ff000772.ts\",\"48fbf2591ff000773.ts\",\"48fbf2591ff000774.ts\",\"48fbf2591ff000775.ts\",\"48fbf2591ff000776.ts\",\"48fbf2591ff000777.ts\",\"48fbf2591ff000778.ts\",\"48fbf2591ff000779.ts\",\"48fbf2591ff000780.ts\",\"48fbf2591ff000781.ts\",\"48fbf2591ff000782.ts\",\"48fbf2591ff000783.ts\",\"48fbf2591ff000784.ts\",\"48fbf2591ff000785.ts\",\"48fbf2591ff000786.ts\",\"48fbf2591ff000787.ts\",\"48fbf2591ff000788.ts\",\"48fbf2591ff000789.ts\",\"48fbf2591ff000790.ts\",\"48fbf2591ff000791.ts\",\"48fbf2591ff000792.ts\",\"48fbf2591ff000793.ts\",\"48fbf2591ff000794.ts\",\"48fbf2591ff000795.ts\",\"48fbf2591ff000796.ts\",\"48fbf2591ff000797.ts\",\"48fbf2591ff000798.ts\",\"48fbf2591ff000799.ts\",\"48fbf2591ff000800.ts\",\"48fbf2591ff000801.ts\",\"48fbf2591ff000802.ts\",\"48fbf2591ff000803.ts\",\"48fbf2591ff000804.ts\",\"48fbf2591ff000805.ts\",\"48fbf2591ff000806.ts\",\"48fbf2591ff000807.ts\",\"48fbf2591ff000808.ts\",\"48fbf2591ff000809.ts\",\"48fbf2591ff000810.ts\",\"48fbf2591ff000811.ts\",\"48fbf2591ff000812.ts\",\"48fbf2591ff000813.ts\",\"48fbf2591ff000814.ts\",\"48fbf2591ff000815.ts\",\"48fbf2591ff000816.ts\",\"48fbf2591ff000817.ts\",\"48fbf2591ff000818.ts\",\"48fbf2591ff000819.ts\",\"48fbf2591ff000820.ts\",\"48fbf2591ff000821.ts\",\"48fbf2591ff000822.ts\",\"48fbf2591ff000823.ts\",\"48fbf2591ff000824.ts\",\"48fbf2591ff000825.ts\",\"48fbf2591ff000826.ts\",\"48fbf2591ff000827.ts\",\"48fbf2591ff000828.ts\",\"48fbf2591ff000829.ts\",\"48fbf2591ff000830.ts\",\"48fbf2591ff000831.ts\",\"48fbf2591ff000832.ts\",\"48fbf2591ff000833.ts\",\"48fbf2591ff000834.ts\",\"48fbf2591ff000835.ts\",\"48fbf2591ff000836.ts\",\"48fbf2591ff000837.ts\",\"48fbf2591ff000838.ts\",\"48fbf2591ff000839.ts\",\"48fbf2591ff000840.ts\",\"48fbf2591ff000841.ts\",\"48fbf2591ff000842.ts\",\"48fbf2591ff000843.ts\",\"48fbf2591ff000844.ts\",\"48fbf2591ff000845.ts\",\"48fbf2591ff000846.ts\",\"48fbf2591ff000847.ts\",\"48fbf2591ff000848.ts\",\"48fbf2591ff000849.ts\",\"48fbf2591ff000850.ts\",\"48fbf2591ff000851.ts\",\"48fbf2591ff000852.ts\",\"48fbf2591ff000853.ts\",\"48fbf2591ff000854.ts\",\"48fbf2591ff000855.ts\",\"48fbf2591ff000856.ts\",\"48fbf2591ff000857.ts\",\"48fbf2591ff000858.ts\",\"48fbf2591ff000859.ts\",\"48fbf2591ff000860.ts\",\"48fbf2591ff000861.ts\",\"48fbf2591ff000862.ts\",\"48fbf2591ff000863.ts\",\"48fbf2591ff000864.ts\",\"48fbf2591ff000865.ts\",\"48fbf2591ff000866.ts\",\"48fbf2591ff000867.ts\",\"48fbf2591ff000868.ts\",\"48fbf2591ff000869.ts\",\"48fbf2591ff000870.ts\",\"48fbf2591ff000871.ts\",\"48fbf2591ff000872.ts\",\"48fbf2591ff000873.ts\",\"48fbf2591ff000874.ts\",\"48fbf2591ff000875.ts\",\"48fbf2591ff000876.ts\",\"48fbf2591ff000877.ts\",\"48fbf2591ff000878.ts\",\"48fbf2591ff000879.ts\",\"48fbf2591ff000880.ts\",\"48fbf2591ff000881.ts\",\"48fbf2591ff000882.ts\",\"48fbf2591ff000883.ts\",\"48fbf2591ff000884.ts\",\"48fbf2591ff000885.ts\",\"48fbf2591ff000886.ts\",\"48fbf2591ff000887.ts\",\"48fbf2591ff000888.ts\",\"48fbf2591ff000889.ts\",\"48fbf2591ff000890.ts\",\"48fbf2591ff000891.ts\",\"48fbf2591ff000892.ts\",\"48fbf2591ff000893.ts\",\"48fbf2591ff000894.ts\",\"48fbf2591ff000895.ts\",\"48fbf2591ff000896.ts\",\"48fbf2591ff000897.ts\",\"48fbf2591ff000898.ts\",\"48fbf2591ff000899.ts\",\"48fbf2591ff000900.ts\",\"48fbf2591ff000901.ts\",\"48fbf2591ff000902.ts\",\"48fbf2591ff000903.ts\",\"48fbf2591ff000904.ts\",\"48fbf2591ff000905.ts\",\"48fbf2591ff000906.ts\",\"48fbf2591ff000907.ts\",\"48fbf2591ff000908.ts\",\"48fbf2591ff000909.ts\",\"48fbf2591ff000910.ts\",\"48fbf2591ff000911.ts\",\"48fbf2591ff000912.ts\",\"48fbf2591ff000913.ts\",\"48fbf2591ff000914.ts\",\"48fbf2591ff000915.ts\",\"48fbf2591ff000916.ts\",\"48fbf2591ff000917.ts\",\"48fbf2591ff000918.ts\",\"48fbf2591ff000919.ts\",\"48fbf2591ff000920.ts\",\"48fbf2591ff000921.ts\",\"48fbf2591ff000922.ts\",\"48fbf2591ff000923.ts\",\"48fbf2591ff000924.ts\",\"48fbf2591ff000925.ts\",\"48fbf2591ff000926.ts\",\"48fbf2591ff000927.ts\",\"48fbf2591ff000928.ts\",\"48fbf2591ff000929.ts\",\"48fbf2591ff000930.ts\",\"48fbf2591ff000931.ts\",\"48fbf2591ff000932.ts\",\"48fbf2591ff000933.ts\",\"48fbf2591ff000934.ts\",\"48fbf2591ff000935.ts\",\"48fbf2591ff000936.ts\",\"48fbf2591ff000937.ts\",\"48fbf2591ff000938.ts\",\"48fbf2591ff000939.ts\",\"48fbf2591ff000940.ts\",\"48fbf2591ff000941.ts\",\"48fbf2591ff000942.ts\",\"48fbf2591ff000943.ts\",\"48fbf2591ff000944.ts\",\"48fbf2591ff000945.ts\",\"48fbf2591ff000946.ts\",\"48fbf2591ff000947.ts\",\"48fbf2591ff000948.ts\",\"48fbf2591ff000949.ts\",\"48fbf2591ff000950.ts\",\"48fbf2591ff000951.ts\",\"48fbf2591ff000952.ts\",\"48fbf2591ff000953.ts\",\"48fbf2591ff000954.ts\",\"48fbf2591ff000955.ts\",\"48fbf2591ff000956.ts\",\"48fbf2591ff000957.ts\",\"48fbf2591ff000958.ts\",\"48fbf2591ff000959.ts\",\"48fbf2591ff000960.ts\",\"48fbf2591ff000961.ts\",\"48fbf2591ff000962.ts\",\"48fbf2591ff000963.ts\",\"48fbf2591ff000964.ts\",\"48fbf2591ff000965.ts\",\"48fbf2591ff000966.ts\",\"48fbf2591ff000967.ts\",\"48fbf2591ff000968.ts\",\"48fbf2591ff000969.ts\",\"48fbf2591ff000970.ts\",\"48fbf2591ff000971.ts\",\"48fbf2591ff000972.ts\",\"48fbf2591ff000973.ts\",\"48fbf2591ff000974.ts\",\"48fbf2591ff000975.ts\",\"48fbf2591ff000976.ts\",\"48fbf2591ff000977.ts\",\"48fbf2591ff000978.ts\",\"48fbf2591ff000979.ts\",\"48fbf2591ff000980.ts\",\"48fbf2591ff000981.ts\",\"48fbf2591ff000982.ts\",\"48fbf2591ff000983.ts\",\"48fbf2591ff000984.ts\",\"48fbf2591ff000985.ts\",\"48fbf2591ff000986.ts\",\"48fbf2591ff000987.ts\",\"48fbf2591ff000988.ts\",\"48fbf2591ff000989.ts\",\"48fbf2591ff000990.ts\",\"48fbf2591ff000991.ts\",\"48fbf2591ff000992.ts\",\"48fbf2591ff000993.ts\",\"48fbf2591ff000994.ts\",\"48fbf2591ff000995.ts\",\"48fbf2591ff000996.ts\",\"48fbf2591ff000997.ts\",\"48fbf2591ff000998.ts\",\"48fbf2591ff000999.ts\",\"48fbf2591ff001000.ts\",\"48fbf2591ff001001.ts\",\"48fbf2591ff001002.ts\",\"48fbf2591ff001003.ts\",\"48fbf2591ff001004.ts\",\"48fbf2591ff001005.ts\",\"48fbf2591ff001006.ts\",\"48fbf2591ff001007.ts\",\"48fbf2591ff001008.ts\",\"48fbf2591ff001009.ts\",\"48fbf2591ff001010.ts\",\"48fbf2591ff001011.ts\",\"48fbf2591ff001012.ts\",\"48fbf2591ff001013.ts\",\"48fbf2591ff001014.ts\",\"48fbf2591ff001015.ts\",\"48fbf2591ff001016.ts\",\"48fbf2591ff001017.ts\",\"48fbf2591ff001018.ts\",\"48fbf2591ff001019.ts\",\"48fbf2591ff001020.ts\",\"48fbf2591ff001021.ts\",\"48fbf2591ff001022.ts\",\"48fbf2591ff001023.ts\",\"48fbf2591ff001024.ts\",\"48fbf2591ff001025.ts\",\"48fbf2591ff001026.ts\",\"48fbf2591ff001027.ts\",\"48fbf2591ff001028.ts\",\"48fbf2591ff001029.ts\",\"48fbf2591ff001030.ts\",\"48fbf2591ff001031.ts\",\"48fbf2591ff001032.ts\",\"48fbf2591ff001033.ts\",\"48fbf2591ff001034.ts\",\"48fbf2591ff001035.ts\",\"48fbf2591ff001036.ts\",\"48fbf2591ff001037.ts\",\"48fbf2591ff001038.ts\",\"48fbf2591ff001039.ts\",\"48fbf2591ff001040.ts\",\"48fbf2591ff001041.ts\",\"48fbf2591ff001042.ts\",\"48fbf2591ff001043.ts\",\"48fbf2591ff001044.ts\",\"48fbf2591ff001045.ts\",\"48fbf2591ff001046.ts\",\"48fbf2591ff001047.ts\",\"48fbf2591ff001048.ts\",\"48fbf2591ff001049.ts\",\"48fbf2591ff001050.ts\",\"48fbf2591ff001051.ts\",\"48fbf2591ff001052.ts\",\"48fbf2591ff001053.ts\",\"48fbf2591ff001054.ts\",\"48fbf2591ff001055.ts\",\"48fbf2591ff001056.ts\",\"48fbf2591ff001057.ts\",\"48fbf2591ff001058.ts\",\"48fbf2591ff001059.ts\",\"48fbf2591ff001060.ts\",\"48fbf2591ff001061.ts\",\"48fbf2591ff001062.ts\",\"48fbf2591ff001063.ts\",\"48fbf2591ff001064.ts\",\"48fbf2591ff001065.ts\",\"48fbf2591ff001066.ts\",\"48fbf2591ff001067.ts\",\"48fbf2591ff001068.ts\",\"48fbf2591ff001069.ts\",\"48fbf2591ff001070.ts\",\"48fbf2591ff001071.ts\",\"48fbf2591ff001072.ts\",\"48fbf2591ff001073.ts\",\"48fbf2591ff001074.ts\",\"48fbf2591ff001075.ts\",\"48fbf2591ff001076.ts\",\"48fbf2591ff001077.ts\",\"48fbf2591ff001078.ts\",\"48fbf2591ff001079.ts\",\"48fbf2591ff001080.ts\",\"48fbf2591ff001081.ts\",\"48fbf2591ff001082.ts\",\"48fbf2591ff001083.ts\",\"48fbf2591ff001084.ts\",\"48fbf2591ff001085.ts\",\"48fbf2591ff001086.ts\",\"48fbf2591ff001087.ts\",\"48fbf2591ff001088.ts\",\"48fbf2591ff001089.ts\",\"48fbf2591ff001090.ts\",\"48fbf2591ff001091.ts\",\"48fbf2591ff001092.ts\",\"48fbf2591ff001093.ts\",\"48fbf2591ff001094.ts\",\"48fbf2591ff001095.ts\",\"48fbf2591ff001096.ts\",\"48fbf2591ff001097.ts\",\"48fbf2591ff001098.ts\",\"48fbf2591ff001099.ts\",\"48fbf2591ff001100.ts\",\"48fbf2591ff001101.ts\",\"48fbf2591ff001102.ts\",\"48fbf2591ff001103.ts\",\"48fbf2591ff001104.ts\",\"48fbf2591ff001105.ts\",\"48fbf2591ff001106.ts\",\"48fbf2591ff001107.ts\",\"48fbf2591ff001108.ts\",\"48fbf2591ff001109.ts\",\"48fbf2591ff001110.ts\",\"48fbf2591ff001111.ts\",\"48fbf2591ff001112.ts\",\"48fbf2591ff001113.ts\",\"48fbf2591ff001114.ts\",\"48fbf2591ff001115.ts\",\"48fbf2591ff001116.ts\",\"48fbf2591ff001117.ts\",\"48fbf2591ff001118.ts\",\"48fbf2591ff001119.ts\",\"48fbf2591ff001120.ts\",\"48fbf2591ff001121.ts\",\"48fbf2591ff001122.ts\",\"48fbf2591ff001123.ts\",\"48fbf2591ff001124.ts\",\"48fbf2591ff001125.ts\",\"48fbf2591ff001126.ts\",\"48fbf2591ff001127.ts\",\"48fbf2591ff001128.ts\",\"48fbf2591ff001129.ts\",\"48fbf2591ff001130.ts\",\"48fbf2591ff001131.ts\",\"48fbf2591ff001132.ts\",\"48fbf2591ff001133.ts\",\"48fbf2591ff001134.ts\",\"48fbf2591ff001135.ts\",\"48fbf2591ff001136.ts\",\"48fbf2591ff001137.ts\",\"48fbf2591ff001138.ts\",\"48fbf2591ff001139.ts\",\"48fbf2591ff001140.ts\",\"48fbf2591ff001141.ts\",\"48fbf2591ff001142.ts\",\"48fbf2591ff001143.ts\",\"48fbf2591ff001144.ts\",\"48fbf2591ff001145.ts\",\"48fbf2591ff001146.ts\",\"48fbf2591ff001147.ts\",\"48fbf2591ff001148.ts\",\"48fbf2591ff001149.ts\",\"48fbf2591ff001150.ts\",\"48fbf2591ff001151.ts\",\"48fbf2591ff001152.ts\",\"48fbf2591ff001153.ts\",\"48fbf2591ff001154.ts\",\"48fbf2591ff001155.ts\",\"48fbf2591ff001156.ts\",\"48fbf2591ff001157.ts\",\"48fbf2591ff001158.ts\",\"48fbf2591ff001159.ts\",\"48fbf2591ff001160.ts\",\"48fbf2591ff001161.ts\",\"48fbf2591ff001162.ts\",\"48fbf2591ff001163.ts\",\"48fbf2591ff001164.ts\",\"48fbf2591ff001165.ts\",\"48fbf2591ff001166.ts\",\"48fbf2591ff001167.ts\",\"48fbf2591ff001168.ts\",\"48fbf2591ff001169.ts\",\"48fbf2591ff001170.ts\",\"48fbf2591ff001171.ts\",\"48fbf2591ff001172.ts\",\"48fbf2591ff001173.ts\",\"48fbf2591ff001174.ts\",\"48fbf2591ff001175.ts\",\"48fbf2591ff001176.ts\",\"48fbf2591ff001177.ts\",\"48fbf2591ff001178.ts\",\"48fbf2591ff001179.ts\",\"48fbf2591ff001180.ts\",\"48fbf2591ff001181.ts\",\"48fbf2591ff001182.ts\",\"48fbf2591ff001183.ts\",\"48fbf2591ff001184.ts\",\"48fbf2591ff001185.ts\",\"48fbf2591ff001186.ts\",\"48fbf2591ff001187.ts\",\"48fbf2591ff001188.ts\",\"48fbf2591ff001189.ts\",\"48fbf2591ff001190.ts\",\"48fbf2591ff001191.ts\",\"48fbf2591ff001192.ts\",\"48fbf2591ff001193.ts\",\"48fbf2591ff001194.ts\",\"48fbf2591ff001195.ts\",\"48fbf2591ff001196.ts\",\"48fbf2591ff001197.ts\",\"48fbf2591ff001198.ts\",\"48fbf2591ff001199.ts\",\"48fbf2591ff001200.ts\",\"48fbf2591ff001201.ts\",\"48fbf2591ff001202.ts\",\"48fbf2591ff001203.ts\",\"48fbf2591ff001204.ts\",\"48fbf2591ff001205.ts\",\"48fbf2591ff001206.ts\",\"48fbf2591ff001207.ts\",\"48fbf2591ff001208.ts\",\"48fbf2591ff001209.ts\",\"48fbf2591ff001210.ts\",\"48fbf2591ff001211.ts\",\"48fbf2591ff001212.ts\",\"48fbf2591ff001213.ts\",\"48fbf2591ff001214.ts\",\"48fbf2591ff001215.ts\",\"48fbf2591ff001216.ts\",\"48fbf2591ff001217.ts\",\"48fbf2591ff001218.ts\",\"48fbf2591ff001219.ts\",\"48fbf2591ff001220.ts\",\"48fbf2591ff001221.ts\",\"48fbf2591ff001222.ts\",\"48fbf2591ff001223.ts\",\"48fbf2591ff001224.ts\",\"48fbf2591ff001225.ts\",\"48fbf2591ff001226.ts\",\"48fbf2591ff001227.ts\",\"48fbf2591ff001228.ts\",\"48fbf2591ff001229.ts\",\"48fbf2591ff001230.ts\",\"48fbf2591ff001231.ts\",\"48fbf2591ff001232.ts\",\"48fbf2591ff001233.ts\",\"48fbf2591ff001234.ts\",\"48fbf2591ff001235.ts\",\"48fbf2591ff001236.ts\",\"48fbf2591ff001237.ts\",\"48fbf2591ff001238.ts\",\"48fbf2591ff001239.ts\",\"48fbf2591ff001240.ts\",\"48fbf2591ff001241.ts\",\"48fbf2591ff001242.ts\",\"48fbf2591ff001243.ts\",\"48fbf2591ff001244.ts\",\"48fbf2591ff001245.ts\",\"48fbf2591ff001246.ts\",\"48fbf2591ff001247.ts\"],\"base\":\"https://56.com-t-56.com/20190705/23595_0bc79dd8/1000k/hls/\"}";
//        JSONObject object = JsonUtils.readValue(json, JSONObject.class);
//        String base = object.getString("base");
//        JSONArray url = object.getJSONArray("list");
//        String type = "ts";
//        for (int i = 0 ;i < url.size() ; i++){
//            String uri  = (String)url.get(i);
//            String urlPath  = base+uri;
//
//            byte[] datas = FileToByteArrayUtils.readNetInputStream(urlPath);
//            String mdPath = CryptUtil.md5(new String(datas));
//    //
//            map.put("blockSize", datas);
//            map.put("fileSize", datas.length);
//            map.put("fileMd5", mdPath);
//            map.put("fileType", type);
//            map.put("urlPath", basePath);
//            map.put("prefixName", "_"+uri.replace(".ts",""));

//            HttpClientTest.postURL(URLCommand.upload_post_file_slave_byte, map);
        }
    }

