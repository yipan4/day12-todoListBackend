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

    String requestBodyWithIdConstructor(int id, String text, boolean done) {
        return """
                {
                    "id": %d,
                    "text": "%s",
                    "done": %b
                }
                """.formatted(id, text, done);
    }

    @Test
    void should_return_empty_list_when_get_given_empty_no_todos() throws Exception {
        mockMvc.perform(get("/todos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void should_return_2_todos_when_get_given_2_todos() throws Exception {
        String text1 = "task1";
        boolean done1 = false;
        String requestBody1 = requestBodyConstructor(text1, done1);
        int id1 = mockAddTodo(requestBody1);
        String text2 = "task2";
        boolean done2 = true;
        String requestBody2 = requestBodyConstructor(text2, done2);
        int id2 = mockAddTodo(requestBody2);

        mockMvc.perform(get("/todos")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].text").value(text1))
                .andExpect(jsonPath("$[0].done").value(done1))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].text").value(text2))
                .andExpect(jsonPath("$[1].done").value(done2));
    }

    @Test
    void should_return_422_when_post_given_todo_empty_text() throws Exception {
        String text = "";
        boolean done = false;
        String requestBody = requestBodyConstructor(text, done);
        mockMvcPerformPost(requestBody)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_add_todo_when_post_given_new_todo() throws Exception {
        String text = "task1";
        boolean done = false;
        String requestBody = requestBodyConstructor(text, done);
        ResultActions resultActions = mockMvcPerformPost(requestBody);
        int id = extractMockPostId(resultActions);

        resultActions.andExpect((status().isCreated()))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.text").value(text))
                .andExpect(jsonPath("$.done").value(done));
    }

    @Test
    void should_return_422_when_post_given_no_text() throws Exception {
        boolean done = false;
        String requestBody = """
                {
                    "done": %b
                }
                """.formatted(done);
        mockMvcPerformPost(requestBody)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_create_when_post_given_user_provides_id() throws Exception {
        String requestBody = """
                {
                    "id": 100,
                    "text": "task1",
                    "done": false
                }
                """;
        ResultActions resultActions = mockMvcPerformPost(requestBody);
        int id = extractMockPostId(resultActions);

        resultActions.andExpect((status().isCreated()))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.text").value("task1"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void should_update_text_done_when_put_given_updated_text_done() throws Exception {
        String text = "task1";
        boolean done = false;
        String requestBody = requestBodyConstructor(text, done);
        int id = mockAddTodo(requestBody);
        String text2 = "updated task1";
        boolean done2 = true;
        String requestBody2 = requestBodyWithIdConstructor(id, text2, done2);
        mockMvc.perform(put("/todos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.text").value(text2))
                .andExpect(jsonPath("$.done").value(done2));
    }

    @Test
    void should_only_update_path_id_todo_when_put_given_different_path_id_and_body_id() throws Exception {
        String text1 = "task1";
        boolean done1 = false;
        String requestBody1 = requestBodyConstructor(text1, done1);
        int id1 = mockAddTodo(requestBody1);
        String text2 = "task2";
        boolean done2 = false;
        String requestBody2 = requestBodyConstructor(text2, done2);
        int id2 = mockAddTodo(requestBody2);
        String updatedText = "updated task1";
        boolean updatedDone = true;
        String updateRequestBody = requestBodyConstructor(updatedText, updatedDone);
        mockMvc.perform(put("/todos/{id}", id1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(updateRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id1))
                .andExpect(jsonPath("$.text").value(updatedText))
                .andExpect(jsonPath("$.done").value(updatedDone));
        mockMvc.perform(get("/todos/search/{id}", id2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(text2))
                .andExpect(jsonPath("$.done").value(done2));
    }

    @Test
    void should_reject_update_when_put_given_id_not_exist() throws Exception {
        int nonExistentId = 999;
        String updatedText = "updated task";
        boolean updatedDone = true;
        String updateRequestBody = requestBodyConstructor(updatedText, updatedDone);
        mockMvc.perform(put("/todos/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(updateRequestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_reject_when_put_given_incomplete_payload() throws Exception {
        String text = "task1";
        boolean done = false;
        String requestBody = requestBodyConstructor(text, done);
        int id = mockAddTodo(requestBody);
        String incompletePayload = """
                {}
                """;
        mockMvc.perform(put("/todos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(incompletePayload))
                .andExpect(status().isUnprocessableEntity());

    }
}
