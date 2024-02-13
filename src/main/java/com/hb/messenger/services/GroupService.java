package com.hb.messenger.services;

import com.hb.messenger.exceptions.ErrorCode;
import com.hb.messenger.exceptions.MessengerException;
import com.hb.messenger.models.entities.GroupInfo;
import com.hb.messenger.models.entities.UserInfo;
import com.hb.messenger.repositories.GroupRepository;
import com.hb.messenger.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

  private final GroupRepository groupRepository;

  private final UserRepository userRepository;

  public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
    this.groupRepository = groupRepository;
    this.userRepository = userRepository;
  }

  public void createGroup(String createdBy, String name) {
    Optional<GroupInfo> group = groupRepository.findById(name);
    Optional<UserInfo> userInfo = userRepository.findById(createdBy);
    if (userInfo.isEmpty()) {
      throw MessengerException.error(ErrorCode.USER_NOT_FOUND);
    }
    if (group.isEmpty()) {
      groupRepository.save(GroupInfo.builder().name(name).users(Set.of(userInfo.get())).build());
    } else {
      throw MessengerException.error(ErrorCode.DUPLICATE_GROUP, name);
    }
  }

  public void addMember(String addedBy, String name, String username) {
    Optional<GroupInfo> group = groupRepository.findById(name);
    if (group.isEmpty()) {
      throw MessengerException.error(ErrorCode.GROUP_NOT_FOUND, name);
    }
    Optional<UserInfo> userProfile = userRepository.findById(username);
    if (userProfile.isEmpty()) {
      throw MessengerException.error(ErrorCode.USER_NOT_FOUND, username);
    }

    Set<String> users = group.get().getUsers().stream().map(UserInfo::getUsername)
        .collect(Collectors.toSet());

    if (!users.contains(addedBy)) {
      throw MessengerException.error(ErrorCode.AUTHORIZATION_ERROR);
    }
    if (users.contains(username)) {
      throw MessengerException.error(ErrorCode.USER_ALREADY_EXIST);
    }

    group.get().getUsers().add(userProfile.get());
    groupRepository.save(group.get());

  }

  public void removeMember(String removedBy,String name, String username) {
    Optional<GroupInfo> group = groupRepository.findById(name);
    if (group.isEmpty()) {
      throw MessengerException.error(ErrorCode.GROUP_NOT_FOUND, name);
    }
    Optional<UserInfo> userProfile = userRepository.findById(username);
    if (userProfile.isEmpty()) {
      throw MessengerException.error(ErrorCode.USER_NOT_FOUND, username);
    }

    Set<String> users = group.get().getUsers().stream().map(UserInfo::getUsername)
        .collect(Collectors.toSet());

    if (!users.contains(removedBy)) {
      throw MessengerException.error(ErrorCode.AUTHORIZATION_ERROR);
    }

    if (!users.contains(username)) {
      throw MessengerException.error(ErrorCode.USER_NOT_EXIST);
    }

    group.get().getUsers().remove(userProfile.get());
    groupRepository.save(group.get());
  }

  public List<String> fetchAllGroups() {
    return groupRepository.findAll().stream().map(GroupInfo::getName).collect(Collectors.toList());
  }

  public List<String> fetchAllMembers(String name) {
    Optional<GroupInfo> group = groupRepository.findById(name);
    if (group.isPresent()) {
      return group.get().getUsers().stream().map(UserInfo::getUsername)
          .collect(Collectors.toList());
    } else {
      throw MessengerException.error(ErrorCode.GROUP_NOT_FOUND, name);
    }
  }


}
