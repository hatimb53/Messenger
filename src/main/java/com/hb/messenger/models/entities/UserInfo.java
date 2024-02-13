package com.hb.messenger.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user_info")
public class UserInfo {

  @Id
  @Column(name = "username", nullable = false)
  private String username;

  @JsonIgnore
  @Column(name = "passcode", nullable = false)
  private String passcode;

  @ManyToMany(mappedBy = "users")
  private Set<GroupInfo> groups = new HashSet<>();


}
