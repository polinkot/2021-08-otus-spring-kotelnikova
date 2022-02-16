package ru.otus.pk.spring.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cat")
public class Cat implements Animal {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
