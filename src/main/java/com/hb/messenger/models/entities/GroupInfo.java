package com.hb.messenger.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "group_info")
public class GroupInfo {

    @Id
    @Column(name="name")
    private String name;

    @ManyToMany
    @JoinTable(name = "user_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserInfo> users = new HashSet<>();


}
