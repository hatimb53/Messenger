package com.hb.messenger.repositories;

import com.hb.messenger.models.entities.GroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupInfo,String> {

}
