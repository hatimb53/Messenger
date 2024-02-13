package com.hb.messenger.repositories;

import com.hb.messenger.models.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo,String> {
}
