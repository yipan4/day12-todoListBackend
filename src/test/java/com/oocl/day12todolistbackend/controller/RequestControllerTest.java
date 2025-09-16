package com.oocl.day12todolistbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RequestController requestController;

    @BeforeEach
    void resetData() {
        requestController.resetData();
    }

    private ResultActions mockMvcPerformPost(String requestBody) throws Exception {
        return mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));
    }

    private int extractMockPostId(ResultActions resultActions) throws Exception {
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        return new ObjectMapper().readTree(contentAsString).get("id").asInt();
    }

    private int mockAddTodo(String requestBody) throws Exception {
        ResultActions resultActions = mockMvcPerformPost(requestBody);
        return extractMockPostId(resultActions);
    }

    String requestBodyConstructor(String text, boolean done) {
        return """
                {
                    "text": "%s",
                    "done": %b
                }
                """.formatted(text, done);
    }

    @Test
    void should_add_todo_when_post_given_new_todo() throws Exception {
        String text = "task1";
        boolean done = false;
        String requestBody = requestBodyConstructor(text, done);
        ResultActions resultActions = mockMvcPerformPost(requestBody);
        int id = extractMockPostId(resultActions);

        resultActions.andExpect((status().isOk()))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.text").value(text))
                .andExpect(jsonPath("$.done").value(done));
    }

//    @Test
//    void get_all_todos_when_get_given_request() throws Exception {
//        mockMvc.perform(get("/api/todos")
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(3));
//    }

}
