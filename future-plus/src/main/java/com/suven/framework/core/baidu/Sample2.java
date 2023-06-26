package com.suven.framework.core.baidu;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Sample2 {
    //设置APPID/AK/SK
//    public static final String APP_ID = "11784039";//"你的 App ID";
//    public static final String API_KEY = "nDuA7GxRW3cFc0bU1j6yEZKF";//"你的 Api Key";
//    public static final String SECRET_KEY = "9y8cKbk5jhMueYZdxutM9MX4I984LrDw";//"你的 Secret Key";
    public static final String APP_ID = "32876586";//""11784039";//"你的 App ID";32876586
    public static final String API_KEY ="pe3N6PmgAzKGBEdHLC5xEyNq"; //"nDuA7GxRW3cFc0bU1j6yEZKF";//"你的 Api Key";//pe3N6PmgAzKGBEdHLC5xEyNq
    public static final String SECRET_KEY = "VFkxCGZShi22IsekdF3Q5oKUOXfigh2c"; //"9y8cKbk5jhMueYZdxutM9MX4I984LrDw";//"你的 Secret Key";VFkxCGZShi22IsekdF3Q5oKUOXfigh2c


    public static void main(String[] args) throws Exception{
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
          String str = "字式之，----號石屏，台州黃岩(今屬浙江)人。以詩鳴江湖間，為江湖詩人之重要作家。有《石屏詩集》、《石屏詞》。主要作品有:滿江紅（赤壁磯頭）','(1167－？)字式之，號石屏，台州黃岩(今屬浙江)人。以詩鳴江湖間，為江湖詩人之重要作家。有《石屏詩集》、《石屏詞》。";

//        参数	类型	描述	是否必须
//        tex	String	合成的文本，使用UTF-8编码，
//        请注意文本长度必须小于1024字节	是
//        cuid	String	用户唯一标识，用来区分用户，
//        填写机器 MAC 地址或 IMEI 码，长度为60以内	否
//        spd	String	语速，取值0-9，默认为5中语速	否
//        pit	String	音调，取值0-9，默认为5中语调	否
//        vol	String	音量，取值0-15，默认为5中音量	否
//        per	String	发音人选择, 0为女声，1为男声，
//        3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女	否
// 设置可选参数
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("spd", "3");
        options.put("pit", "5");
        options.put("vol", "8");
        options.put("per", "0");

        str = "关于唐代著名太宗皇帝的简介\n" +
                "太宗（599年－649年），姓李，名世民，唐朝第二位皇帝，他在位期间实行了一系列改革和开拓扩张的政策，开创了唐朝的盛世。\n" +
                "太宗是李渊的第三个儿子，年少时即显得聪明过人，从小受到很好的教育。他深谙儒家经典，熟练掌握文学、历史和哲学等学科，同时也善于武艺。\n" +
                "贞观年间，太宗实行了一系列改革，包括改革政治制度、加强中央集权、减轻赋税、开放州县市场、加强农业生产等。他还提倡科举制度，选拔人才，推崇儒学，提拔和重用许多文学、历史学家和哲学家，他本人也亲自撰写了许多文学和历史著作。\n" +
                "太宗重视武功，自己也是一名出色的将领，多次亲征战场，扩大了唐朝的疆域。他还重视外交，通过外交手段巩固了唐朝的国际地位，与邻国建立了友好关系，达成了一系列重要条约。\n" +
                "太宗在位期间，唐朝经济、文化、艺术、科技等方面都取得了长足的发展，被后人称为“贞观之治”。他是中国历史上杰出的政治家、军事家、文化家，对中国历史和文化产生了深远影响。";
        TtsResponse res = client.synthesis(str, "zh", 1, options);



//          TtsResponse res = client.synthesis(str, "zh", 1, null);
        byte[] data = res.getData();
        JSONObject res1 = res.getResult();
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data, "output.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (res1 != null) {
            System.out.println(res1.toString(2));
        }

    }

    public static void main4(String[] args) throws Exception {
//        main3(args);
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
        //目前rate参数仅提供8000,16000两种
        String str = "/Users/suven/project/workspace/redfinger/hsz/output.wav";
        JSONObject res =  client.asr(str,"wav", 8000, null);
        // 调用接口
//        JSONObject res = client.asr("test.pcm", "pcm", 16000, null);
        System.out.println(res.toString());
        
    }
}