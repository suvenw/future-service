package com.suven.framework.core.baidu;

import com.baidu.aip.ocr.AipOcr;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


/**
 * @ClassName BaiduAipSpeech
 * @Author suven.wang
 * @Description //TODO ${END}$
 * @CreateDate 2018-11-29  11:19
 * @WeeK 星期四
 * @Version v2.0
 **/
public class BaiduAipSpeech extends BaiduConstants{

   private static Logger logger = LoggerFactory.getLogger(BaiduAipSpeech.class);

    private static AipSpeech client = null;
    public static AipSpeech build(){
        if(client != null){
            logger.info("Baidu AipSpeech init client build is not null");
            return client;
        }
        client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(CONNECTION_TIMEOUT_IN_MILLIS);
        client.setSocketTimeoutInMillis(SOCKET_TIMEOUT_IN_MILLIS);
        logger.info("Baidu AipSpeech init client build success");
        return client;
    }

    public static AipOcr buildAipOcr(){
        AipOcr aipOcr =  new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        aipOcr.setConnectionTimeoutInMillis(CONNECTION_TIMEOUT_IN_MILLIS);
        aipOcr.setSocketTimeoutInMillis(SOCKET_TIMEOUT_IN_MILLIS);
        return aipOcr;
    }

    public static byte[] getSpeechData(String content ) {
        HashMap<String,Object> map = BaiduOptions.build().getOptionsMap();
        return  getSpeechData(content,map);

    }
    public static byte[] getSpeechData(String content, HashMap<String,Object> map) {
       try {
           TtsResponse tts = BaiduAipSpeech.build().synthesis(content,"zh",
                   1, BaiduOptions.build().getOptionsMap());
           if(tts == null){
               return null;
           }
           byte[] data =  tts.getData();
           return data;
       }catch (Exception e){
           logger.error("Baidu Aip Speech Exception [{}]",e);
       }
       return null;
    }

    public static boolean writeSpeechData(String content,String wavPath){
        byte[] data =  getSpeechData(content);
        if(data == null){return false;}
        try {
            Util.writeBytesToFileSystem(data, wavPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        JSONObject object = BaiduAipSpeech.build().asr(wavPath,"wav", 8000, BaiduOptions.build().getOptionsMap());
//        System.out.println(object.toString());
        return true;
    }

    public static void main(String[] args) {
        String content = "吾乡前辈多风谊，教子读书全盛事。|刘斯立在庆历间，二子同升俱上第。|后来嘉祐孔长源，三子联登相后先。|元丰四谢更同榜，一家科第光烨然。|直到淳熙徐思叔，亦与二子连芳躅。|又报年来萧定夫，绍定两科庭下玉。|二百年间能几家，刘家孔家无以加。|才名学行总名世，不但官爵称高华。|谢家独有民师著，徐家大无鸣诗句。|人道萧室父子三，前辈流风无让处。|萧公要是真醇儒，家无他物惟有书。|自家读了教儿读，夜半书声喧里闾。|造物负公还不贫，嘉定庚辰汉庭右。|此时二子已崭然，公曰吾心汝其懋。|果然相继取高科，二哥饶著与三哥。|乃翁笑向月中问，吾家桂树何其多。|月娥报道萧家桂，生满月中香满世。|移过东山能几年，今日方教翁满意。|祝公此意令深长，香名当使百世芳。|前贤事业远且大，三刘三孔尤辉光。|世科膴仕公家有，实声莫落谢徐后。|要知渝上一萧家，突过四家名宇宙。|我曾与公同荐名，服公文行识公真。|更与贤郎共文社，情谊凛凛如霜筠。|翻笑传家三父子，草亭老人空厉志。|曾不得名于荐书，仅有大儿前擢第。|小儿四度试春官，尚期他日修征鞍。|如今饥卧空山里，慨想前事悲汍澜。|喜闻令子归锦里，夜不能眠推枕起。|瓣香寓作三萧行，寄贺萧公当双鲤。";
       content = "床前明月光，疑是地上霜。举头望明月，低头思故乡。";
        String path = "/Users/suven/project/workspace/openSource/top-suven/静夜思.wav";
//        path ="output3.wav";

        BaiduAipSpeech.writeSpeechData(content,path);
        System.out.print("ddddddddd");
    }
}
