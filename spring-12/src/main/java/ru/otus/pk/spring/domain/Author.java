package ru.otus.pk.spring.domain;

import lombok.*;

import javax.persistence.*;

import static java.lang.String.format;
import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    public String getFullName() {
        return format("%s %s", firstName, lastName);
    }
}
