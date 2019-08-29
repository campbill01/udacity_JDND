package edu.udacity.java.nano;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class WebLayerTest {

    private static final String INDEX_TEST_USERNAME = "/index?username='frodo'";
    @Autowired
    private MockMvc mockMvc;

    // Test main page
    @Test
    public void shouldReturnLoginPage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Chat Room Login")));
    }
    // Test user login
    @Test
    public void shouldReturnChatPage() throws Exception {
        this.mockMvc.perform(get(INDEX_TEST_USERNAME)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("id=\"username\">&#39;frodo&#39;<")));
    }
}