package com.hb.messenger.services;

import com.hb.messenger.exceptions.ErrorCode;
import com.hb.messenger.exceptions.MessengerException;
import com.hb.messenger.models.CustomUserDetails;
import com.hb.messenger.models.entities.UserInfo;
import com.hb.messenger.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void createUser(String username, String password) {

    if (userRepository.findById(username).isPresent()) {
      throw MessengerException.error(ErrorCode.DUPLICATE_USER);
    }
    userRepository.save(UserInfo.builder().
        username(username)
        .passcode(passwordEncoder.encode(password))
        .build());
  }

  public List<String> fetchAllUsers() {
    return userRepository.findAll().stream().map(UserInfo::getUsername)
        .collect(Collectors.toList());
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserInfo> userEntity = userRepository.findById(username);
    if (userEntity.isEmpty()) {
      throw new UsernameNotFoundException("could not found user..!!");
    }
    return new CustomUserDetails(userEntity.get());
  }

}
