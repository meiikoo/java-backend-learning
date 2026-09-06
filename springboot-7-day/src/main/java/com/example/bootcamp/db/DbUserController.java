package com.example.bootcamp.db;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/db-users")
public class DbUserController {

    private final DbUserService service;

    public DbUserController(DbUserService service) {
        this.service = service;
    }

    @GetMapping
    public List<DbUser> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public DbUser get(@PathVariable long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DbUser create(@Valid @RequestBody UserRequest request) {
        return service.create(request.name(), request.email());
    }

    @PutMapping("/{id}")
    public DbUser update(
            @PathVariable long id,
            @Valid @RequestBody UserRequest request) {
        return service.update(id, request.name(), request.email());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public List<DbUser> createBatch(@Valid @RequestBody BatchRequest request) {
        List<DbUserService.Draft> drafts = request.users().stream()
                .map(user -> new DbUserService.Draft(user.name(), user.email()))
                .toList();
        return service.createBatch(drafts);
    }

    public record UserRequest(
            @NotBlank @Size(max = 50) String name,
            @NotBlank @Email @Size(max = 100) String email
    ) {
    }

    public record BatchRequest(
            @NotEmpty @Size(max = 100) List<@Valid UserRequest> users
    ) {
    }
}
