package com.kh.fitness.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
//@EqualsAndHashCode(exclude = "users")
//@ToString(exclude = "users")
@Entity
public class Role implements GrantedAuthority, BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users = new HashSet<>();

    @Override
    public String getAuthority() {
        return getName();
    }
}

