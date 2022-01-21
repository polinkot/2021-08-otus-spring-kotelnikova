package ru.otus.pk.spring.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "authority")
public class Authority implements GrantedAuthority {
    @Id
    private Long id;
    private String authority;
}
