package com.kh.fitness.entity.user;

import com.kh.fitness.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import static javax.persistence.EnumType.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role implements GrantedAuthority, BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(STRING)
    private Roles name;

    @Override
    public String getAuthority() {
        return getName().name();
    }
}

