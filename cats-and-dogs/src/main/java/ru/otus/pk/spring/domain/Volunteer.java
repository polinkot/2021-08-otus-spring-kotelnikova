package ru.otus.pk.spring.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "volunteer")
public class Volunteer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
