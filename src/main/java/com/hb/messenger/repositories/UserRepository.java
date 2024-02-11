package com.hb.messenger.repositories;

import com.hb.messenger.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,String> {
    UserEntity findByUsername(String username);
}
