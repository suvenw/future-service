package com.suven.framework.http.message;


import com.suven.framework.common.data.BaseEntity;
import com.suven.framework.common.enums.NumberEnum;
import com.suven.framework.util.random.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.suven.framework.common.api.IAesData;
import com.suven.framework.util.crypt.CryptUtil;

import java.util.Arrays;
import java.util.List;

public  class ResponseAesData extends BaseEntity implements IAesData {

    private List<String> list;

    private Logger logger = LoggerFactory.getLogger(getClass());
    {

    /** aes 对数据返回 data 进行加密功能实现**/
//        String randomStr = 	RandomUtils.verifyCode(32, -1);
//        String key = randomStr.substring(0,16);
////        String value = randomStr.substring(16);
        String key =  RandomUtils.verifyCode(16,-1);
        String value = RandomUtils.verifyCode(16,0);
        String sign = CryptUtil.aesPassEncrypt( value,key);
        list = Arrays.asList(key,value,sign);
        logger.info("===== ResponseAesData ========: " +list.toString());
    }


    public String conventAesUrl(String url){
        boolean isurl = ParamMessage.getRequestRemote().isAesUrl();
        if(isurl && null != list && list.size() == NumberEnum.THREE.id()){
            String aesEncryptKey = findAesValue();
            String sign = CryptUtil.aesPassEncrypt( url,aesEncryptKey);
            return sign;
        }
        return url;
    }


    @Override
    public String findAesKey(){
        return list.get(NumberEnum.ZERO.id());
    }
    @Override
    public String findAesValue(){
        return list.get(NumberEnum.ONE.id());
    }
    @Override
    public String findAesSign(){
        return list.get(NumberEnum.TWO.id());
    }

    public static void main(String[] args) throws Exception{
       String aesKey =  CryptUtil.aesDecrypt("C5B0F80FBBD0F53A30B5A1B06EC9DF2540F8669C44D58A8D6FFCBE0A4870FB91A445FCFB8F334FE72EDC0BA331C4BBB2809A61641970072C113323FEA28A69B9B6BCCCA3A5169120093D32B17086A22644DFDD183BBF9320F0755FFAFFA2CF4BDF5ABFD80ABA1EE8ECCDBACB078912756F5B5E20B1379AEABCF0741ED291D73FB82135E7EC2B1F9A07D9438B50391A2DFE42C93D830A7DD3894798FBE7BC0E2FFF03D2C5014A7AE064DBCF445D5B0B00237A354F0E10846B651AA9665BE4C0A4AFFB12D980C5859C1CC61C1499CA9151BBFD602B39723A9B371D3A2B2E069C67A3AED43E698905A134DC961E453016A5914A8A013BB57173C5014CBACEF95F57EA04E7E55CF2FFD93A927092C630360FC562C116D5A3B0ADC038B4FE74A1718EAF331E84C0DC5D59810B3B885C6FCD92088F30FA79AA9C930ECF165187143F7C5A09FECC26D57B22B54FB4C5C731C2BD038BB20AC8B394876E9E1757426A5C641B1D252A38F4036E6A821202D074CA99F443B7F6936F38194E87D7343D9FC26BE466632487FA8EF9664349E4D2E82500" ,"3328173901515813");
       System.out.println("=========:" +aesKey);
        aesKey = CryptUtil.aesDecrypt("435211D07C50FF02F1FBD45726834869C39CCC13BC2F2760F8C600E622C2FE4F","garpxxvhamplormg");

        System.out.println("-----------:" +aesKey);
    }

}
