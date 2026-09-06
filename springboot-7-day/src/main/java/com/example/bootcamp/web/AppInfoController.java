package com.example.bootcamp.web;

import com.example.bootcamp.config.AppProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/info")
public class AppInfoController {

    private final AppProperties properties;

    public AppInfoController(AppProperties properties) {
        this.properties = properties;
    }

    @GetMapping
    public Map<String, String> info() {
        return Map.of("application", properties.name());
    }
}
