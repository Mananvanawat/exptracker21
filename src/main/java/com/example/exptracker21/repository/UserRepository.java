package com.example.exptracker21.repository;

import com.example.exptracker21.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
}
