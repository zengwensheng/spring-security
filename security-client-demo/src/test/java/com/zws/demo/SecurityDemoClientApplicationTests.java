package com.zws.demo;

import org.bouncycastle.crypto.tls.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/11/6
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityDemoClientApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Before
    public void before(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void loginOnSuccess() throws  Exception{
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("authorization","Basic endzOnp3cw==")
                .param("grant_type","password")
                .param("scope","all")
                .param("username","zws")
                .param("password","123456"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
