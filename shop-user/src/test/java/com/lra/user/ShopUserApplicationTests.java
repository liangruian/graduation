package com.lra.user;
//
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.internal.util.AlipaySignature;
//import com.alipay.api.request.AlipayTradePagePayRequest;
import org.junit.jupiter.api.Test;

//@SpringBootTest
class ShopUserApplicationTests {

    final String URL = "https://openapi.alipaydev.com/gateway.do";

    final String APP_ID = "2016101700707308";

    final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDIivfPWxYWGsVTg5iwyIEtLG5By+Z6g43a2hCtCLrT8wmmbtwjjikp1TQAHV8csbUKjRz4at0uw/Bkk7oMTn1StJMX30VuED7N6G4hgsmTeTlRMxn4K7lPta7JvVYOmvWNW2fgiM3+3W3GoRxKdmpwrEo2QA5JYhaYezWtaLcK+YPf0Y3/xgGB/qVwxuiyfk+lFVF4Hi6sZGtT8/6r9PsfK1hKNl1IZYlIx0OPVjDauYYsIgfdXYmHyft1ikrXWNjeQrkcyGfprwyGxz9h60JKOVcqAZqFnZgm6LkfAlytWj1KnjrIpQcyh+ZEIuMku+LoKgzWdB+xjRh3R7xBrOK7AgMBAAECggEAe2/mVpCXSKXTvbLrgiz4LekCOKebxt9Aqmu9BfSwopMe5n2oLI5tUd10c3mguJW9fLAdG98TI5zhU9cGFlsdkpGpVHFOWA/RyIX/e8r37jkYiZ1tp66qEqEe4/gRh03kRMz1ZGRwfyamUjOKqV6354B08W1+Hbctd0+nW4Rv3mVdd2qlyuGoNeMddYidaAcru444pNuaVNFJ9gY2xkcHaYWuO4wktdMhNLvv00Xy1ck3QIXcC3/NEv8hX847OLKVO0pLgc6JMtRHoupWRIy4FSwcl25fvNIDAo3vLiubjq3U+b155GxTx3VLb1JKsHQCtxf2umw+EB0d7JrCLWOQCQKBgQDuWTd5mRYLdbmjxQMcyZvV8cGKP8S6jEHHl82awJlUO5Z23ug9WyatZi5rpnwzpyUEj+GN1ztw5sJ7TmZcc3o0ezcChVG0Epouh64p3SbaWz1r+zqNt2lfFCCgP2DzxYC+g7KfJxQZH3AU8j1YMF4HDpomNVJ1OPOAEc+FiduH7wKBgQDXZQEYs1Pm/2J55VmUjc50YLTHYnYbB0ET04C/vvlM26rQiwpLcwS4lp6Gq5sKTt/2agO24cOVsFdnSFCFAVvPGqUQgrLW1lRiLJm7vursavZmIyBIeKvZpegDkzKyCAJ+dMJl33p5zPKmB0kyhYtpLe5FL4JQ0NsMyAjgirXl9QKBgQDYdVG6LtskYS/MmVkwRbAu5jV8VllViZcbqrNgvVUCeHM3VUzYm/VOOKQjw7i8OCmYMQsIg9VJvWgWD6dKgt0S2Gum8Gi4/sPjSflTr7JfT8lCTZBpr44UePUAU067VDyNyJDplNXd0v05KJ2pf5OFGS/JT1epeaU6Q/KRNheXJQKBgBE8J8ZOMxx8uuh+DZjxb9+oDU8W9dRb1D0940N0iIY53YUqWvyGi44pny2vdYxxm0IgYYO0tO25WoUNKEQ8Q5hizWpwytad98qTPwni0PFx7Z1mmiKyZS3ZNKN0l83fDjQIK7XzF92/JHtcgeLlBK652iL3FjpZO+3d47fz4XIxAoGASi3j2k3s733MfaNlg8rT3BqOAdr9mN/Ath5BLZoopqkhhCQ8xJa+aGHx39EBgXnfOMgI5Kx4FP2vxldV9t7ITPTmS6n2hBf3pQRZp+T/CvS9lmpi3FAYf38AVgy80IBZlRUbk0EAvtNcqGTKzH2bOPgs8lebHlPd21BNHDFl6XI=";

    final String FORMAT = "json";

    final String CHARSET = "UTF-8";

    final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjQJM7KRvFJ+6JtW2p8AlZ/uvOTTc9GdmHIHgiQ/4aUlCd49kdgtwCoMNt6LOT26v0G+/OUuoeTPn3wNfSM3/vkHcSlN8gL2hpu2PZfcq8QggE9x1hiTU3HxTKjyyl/UPkruoBKJpJDwEx/ZwMCvrBat1yJqHxWqCLw4+lcBzleQyIm/f7VNLi9B8J9djbGeqqTR+gGuHUoAy//vSNojAl4Vq1EvFtQ+MMlEJHijfJHz1799wnUEtRUMVDBV91lFXPHvAzaIstTNFtMb5iEDsTPR55nzf4m35pIv/dLfiuhqeXuCKYSuhK9IWI3cZeDLadfZXJUKEL/eL/g6YbfyvqQIDAQAB";

    final String SIGN_TYPE = "RSA2";

    @Test
    void contextLoads() {
//        AlipayClient alipayClient = new DefaultAlipayClient(URL,APP_ID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);
//        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
//        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
//        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
//        alipayRequest.setBizContent("{" +
//                "    \"out_trade_no\":\"20150320010101003\"," +
//                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
//                "    \"total_amount\":88888.88," +
//                "    \"subject\":\"Iphone12 64G\"," +
//                "    \"body\":\"Iphone12 64G\"," +
//                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
//                "    \"extend_params\":{" +
//                "    \"sys_service_provider_id\":\"2088511833207846\"" +
//                "    }"+
//                "  }");//填充业务参数
//        String form="";
//        try {
//            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        System.out.println(form);
    }

//    @Test
//    public void test2() throws AlipayApiException {
//        Map<String, String> paramsMap = new HashedMap(); //将异步通知中收到的所有参数都存放到 map 中
//        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE); //调用SDK验证签名
//        if(signVerified){
//            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
//            System.out.println();
//        }else{
//            // TODO 验签失败则记录异常日志，并在response中返回failure.
//            System.out.println();
//        }
//    }
//

    public void skipTest(){
    }


}
