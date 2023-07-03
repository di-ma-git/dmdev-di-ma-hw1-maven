package com.dmdev.dima.entity;

import com.dmdev.dima.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class PermissionRole {
    @Id
    @Column(name = "role_id")
    private Integer id;
    @Column(name = "role_title")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "role_description")
    private String description;
}
