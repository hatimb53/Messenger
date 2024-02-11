package com.hb.messenger.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name="userprofile")
public class UserEntity {

    @Id
    String username;

    @Column(name="passcode",nullable = false)
    String passcode;
}
