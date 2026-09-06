package com.example.bootcamp.db;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DbUserNotFoundException extends RuntimeException {

    public DbUserNotFoundException(long id) {
        super("用户不存在，id=" + id);
    }
}
