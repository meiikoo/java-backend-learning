package com.example.bootcamp.db;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DbUserService {

    private final DbUserMapper mapper;

    public DbUserService(DbUserMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<DbUser> list() {
        return mapper.selectList(
                Wrappers.<DbUser>lambdaQuery().orderByAsc(DbUser::getId)
        );
    }

    @Transactional(readOnly = true)
    public DbUser get(long id) {
        DbUser user = mapper.selectById(id);
        if (user == null) {
            throw new DbUserNotFoundException(id);
        }
        return user;
    }

    @Transactional
    public DbUser create(String name, String email) {
        return insert(name, email);
    }

    @Transactional
    public DbUser update(long id, String name, String email) {
        DbUser user = get(id);
        user.setName(name);
        user.setEmail(email);
        user.setUpdatedAt(LocalDateTime.now());

        int affectedRows = mapper.updateById(user);
        if (affectedRows != 1) {
            throw new IllegalStateException("更新用户失败");
        }
        return user;
    }

    @Transactional
    public void delete(long id) {
        int affectedRows = mapper.deleteById(id);
        if (affectedRows != 1) {
            throw new DbUserNotFoundException(id);
        }
    }

    @Transactional
    public List<DbUser> createBatch(List<Draft> drafts) {
        List<DbUser> created = new ArrayList<>();
        for (Draft draft : drafts) {
            created.add(insert(draft.name(), draft.email()));
        }
        return created;
    }

    private DbUser insert(String name, String email) {
        LocalDateTime now = LocalDateTime.now();

        DbUser user = new DbUser();
        user.setName(name);
        user.setEmail(email);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        int affectedRows = mapper.insert(user);
        if (affectedRows != 1) {
            throw new IllegalStateException("创建用户失败");
        }
        return user;
    }

    public record Draft(String name, String email) {
    }
}
