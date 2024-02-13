package com.hb.messenger.repositories;

import com.hb.messenger.models.entities.GroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupInfo, String> {

}
