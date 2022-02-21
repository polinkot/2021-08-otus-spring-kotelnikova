package ru.otus.pk.spring.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "adoption")
public class Adoption {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    private Animal animal;

    @ManyToOne
    private Owner owner;

    @ManyToOne
    private Volunteer volunteer;
}
