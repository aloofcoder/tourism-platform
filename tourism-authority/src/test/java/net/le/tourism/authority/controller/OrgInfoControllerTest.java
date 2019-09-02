package net.le.tourism.authority.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.le.tourism.authority.common.constant.Constants;
import net.le.tourism.authority.pojo.dto.AdminLoginInfo;
import net.le.tourism.authority.pojo.dto.InsertOrgInfoDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-26
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrgInfoControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void loginTest() throws Exception {
        // 模拟登录
        AdminLoginInfo adminLoginInfo = new AdminLoginInfo();
        adminLoginInfo.setLoginPwd("admin");
        adminLoginInfo.setLoginNum("admin");
        String requestJson = JSON.toJSONString(adminLoginInfo);
        String responseString = mockMvc.perform(post("/authority").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JSONObject json = JSON.parseObject(responseString);
        System.out.println(json);
    }

    @Test
    public void insertOrgInfoTest() throws Exception {
        InsertOrgInfoDto insertOrgInfoDto = new InsertOrgInfoDto();
        insertOrgInfoDto.setParentId(1);
        insertOrgInfoDto.setOrgName("测试分公司");
        insertOrgInfoDto.setOrgAdmin("测试管理员");
        insertOrgInfoDto.setOrgClass(1);
        insertOrgInfoDto.setOrgSort(2);
        String requestJson = JSON.toJSONString(insertOrgInfoDto);
        String responseStr = mockMvc
                .perform(post("/organizations")
                        .cookie(new Cookie(Constants.AUTHORITY_KEY, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZG1pbk5hbWUiOiJhZG1pbiIsInRpbWVTdGFtcCI6MTU2MTUxMzA0NCwiaXNzIjoic2VydmljZSIsImFkbWluSWQiOjF9.vcKEiazeDKZJDnwDW1QO8H5Wwl6bP-ErNUXHY94rnIU"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println(responseStr);
    }

    @Test
    public void queryOrgInfoTest() throws Exception {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("condition", "西安cs旅行社");
        String responseStr = mockMvc.
                perform(get("/organizations")
                        .params(param)
                        .cookie(new Cookie(Constants.AUTHORITY_KEY, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZG1pbk5hbWUiOiJhZG1pbiIsInRpbWVTdGFtcCI6MTU2MTUxMzA0NCwiaXNzIjoic2VydmljZSIsImFkbWluSWQiOjF9.vcKEiazeDKZJDnwDW1QO8H5Wwl6bP-ErNUXHY94rnIU")))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println(responseStr);
    }
}
