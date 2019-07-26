package net.le.tourism.authority.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.le.tourism.authority.constant.Constants;
import net.le.tourism.authority.pojo.dto.AdminLoginInfo;
import net.le.tourism.authority.pojo.dto.EditAdminInfoDto;
import net.le.tourism.authority.pojo.dto.InsertAdminInfoDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author hanle
 * @version v1.0
 * @date 2019-06-24
 * @modify
 *
 * 编程千万条, 规范第一条, 注释不规范, 接盘泪两行!
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminInfoControllerTest {

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
        System.out.println(json.getJSONObject("body").get("token").toString());
    }

    @Test
    public void insertAdminInfoTest() throws Exception {
        InsertAdminInfoDto insertAdminInfoDto = new InsertAdminInfoDto();
        insertAdminInfoDto.setAdminName("测试");
        insertAdminInfoDto.setLoginName("ceshi");
        insertAdminInfoDto.setAdminPwd("ceshi!@#");
        insertAdminInfoDto.setAdminMobile("15098102029");
        insertAdminInfoDto.setAdminEmail("501946@163.com");
        List<Integer> roleIds = new ArrayList<>();
        roleIds.add(1);
        roleIds.add(2);
        roleIds.add(3);
        insertAdminInfoDto.setRoleIds(roleIds);
        insertAdminInfoDto.setOrgId(1);
        String requestJson = JSON.toJSONString(insertAdminInfoDto);
        String responseString = mockMvc.perform(post("/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie(Constants.AUTHORITY_KEY, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZG1pbk5hbWUiOiJhZG1pbiIsInRpbWVTdGFtcCI6MTU2MTM2NTY4MCwiaXNzIjoic2VydmljZSIsImFkbWluSWQiOjF9.3_CQeyoIIYbQ3sWxRQy1RQ3VaO4zXhcU3-MXg6mPNSk"))
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println(responseString);
    }

    @Test
    public void editAdminInfoTest() throws Exception {
        EditAdminInfoDto editAdminInfoDto = new EditAdminInfoDto();
        editAdminInfoDto.setAdminId(12);
        editAdminInfoDto.setAdminName("测试");
        editAdminInfoDto.setLoginName("ceshi");
        editAdminInfoDto.setAdminPwd("ceshi123");
        editAdminInfoDto.setAdminMobile("15098102029");
        editAdminInfoDto.setAdminEmail("501946@163.com");
        List<Integer> roleIds = new ArrayList<>();
        roleIds.add(1);
        roleIds.add(2);
        roleIds.add(3);
        editAdminInfoDto.setRoleIds(roleIds);
        editAdminInfoDto.setOrgId(2);
        String requestJson = JSON.toJSONString(editAdminInfoDto);
        String responseString = mockMvc.perform(put("/admins")
                .cookie(new Cookie(Constants.AUTHORITY_KEY, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZG1pbk5hbWUiOiJhZG1pbiIsInRpbWVTdGFtcCI6MTU2MTM2NTY4MCwiaXNzIjoic2VydmljZSIsImFkbWluSWQiOjF9.3_CQeyoIIYbQ3sWxRQy1RQ3VaO4zXhcU3-MXg6mPNSk"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println(responseString);
    }
}
