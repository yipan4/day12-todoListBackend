package com.oocl.day12todolistbackend.config;

import com.oocl.day12todolistbackend.service.TodoListService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MCPConfig {
    @Bean
    public ToolCallbackProvider myTools(TodoListService todoListService) {
        return MethodToolCallbackProvider.builder().toolObjects(todoListService).build();
    }
}
