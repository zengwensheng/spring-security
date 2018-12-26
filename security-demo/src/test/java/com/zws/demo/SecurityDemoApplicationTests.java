package com.zws.demo;

import com.zws.core.support.SecurityEnum;
import com.zws.core.support.SimpleResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityDemoApplicationTests {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception{
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void userInfo() throws Exception{
       String result = mvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println("result = " + result);
    }

    @Test
    public void createUser() throws Exception{
        String result = mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"username\":\"zws\",\"password\":\"123456\",\"age\":\"20\",\"birthDay\":1538119366341}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void test() throws Exception{
        System.out.println(new SimpleResponse(SecurityEnum.SOCIAL_QQ_USER_INFO_ERROR).toString());
    }
}
