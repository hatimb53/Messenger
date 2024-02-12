package com.hb.messenger.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name="userprofile")
public class UserEntity {

    @Id
    String username;

    @JsonIgnore
    @Column(name="passcode",nullable = false)
    String passcode;

}
