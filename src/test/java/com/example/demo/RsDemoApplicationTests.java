package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RsDemoApplicationTests {
	
	@Autowired  
    private WebApplicationContext wac;  
    private MockMvc mockMvc;  
  
    @Before  
    public void setUp() {  
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  
    } 

    @Test
	public void responseWithCountingId() throws Exception {
		//1.id自增显示
        String firstResult = mockMvc.perform(MockMvcRequestBuilders.get("/greeting"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))  
                .andReturn().getResponse().getContentAsString();
        System.out.println("----------id自增查询----------\n" +firstResult);
	}
    
	@Test
	public void responseWithoutName() throws Exception {
		//2.不指定姓名查询
        String secondResult = mockMvc.perform(MockMvcRequestBuilders.get("/greeting"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))  
                .andReturn().getResponse().getContentAsString();
        System.out.println("----------不指定姓名查询----------\n" + secondResult);
	}

	@Test
	public void responseWithName() throws Exception {
		//3.指定姓名查询
        String thirdResult = mockMvc.perform(MockMvcRequestBuilders.get("/greeting?name=User"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))  
                .andReturn().getResponse().getContentAsString();
        System.out.println("----------指定姓名查询----------\n" +thirdResult);
	}
	
	@Test
	public void responseWrong() throws Exception {
		//4.错误结果
        String fourthResult = mockMvc.perform(MockMvcRequestBuilders.get("/greeting"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/String;charset=UTF-8"))  
                .andReturn().getResponse().getContentAsString();
        System.out.println("----------错误结果----------\n" +fourthResult);
	}
	
	
}
