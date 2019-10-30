package net.le.tourism.mp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author hanle
 * @version v1.0
 * @date 2019/10/15
 * @modify
 * @copyright zhishoubao
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatMPTest {

    /**
     * 微信浏览器User-Agent
     */
    public static final String UA = "Mozilla/5.0 (Linux; Android 5.0; SM-N9100 Build/LRX21V) > AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 > Chrome/37.0.0.0 Mobile Safari/537.36 > MicroMessenger/6.0.2.56_r958800.520 NetType/WIFI";

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test() throws Exception {
        HttpHeaders headers = new HttpHeaders();

        headers.set(HttpHeaders.USER_AGENT, UA);
        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity responseEntity = restTemplate.exchange(new URL("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx10f0681bb417a814&redirect_uri=http%3A%2F%2Fnba.bluewebgame.com%2Foauth_response.php&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect").toString(), HttpMethod.GET, httpEntity, String.class);
        System.out.println(responseEntity);
    }
}
