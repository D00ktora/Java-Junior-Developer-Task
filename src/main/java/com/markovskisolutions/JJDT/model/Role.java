package com.markovskisolutions.JJDT.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{
    @Enumerated(value = EnumType.STRING)
    private Roles roleEnum;


    public Roles getRoleEnum() {
        return roleEnum;
    }

    public Role setRoleEnum(Roles roleEnum) {
        this.roleEnum = roleEnum;
        return this;
    }
}
