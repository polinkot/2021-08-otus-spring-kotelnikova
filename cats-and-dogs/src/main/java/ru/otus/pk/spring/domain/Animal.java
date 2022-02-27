package ru.otus.pk.spring.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static ru.otus.pk.spring.domain.Animal.AnimalStatus.NOT_ADOPTED;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "animal")
public class Animal {
    public enum AnimalStatus {NOT_ADOPTED, ADOPTED}

    public enum AnimalType {CAT, DOG}

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "sterilized")
    private Boolean sterilized;

    @Column(name = "vaccinated")
    private Boolean vaccinated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AnimalStatus status = NOT_ADOPTED;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AnimalType type;
}
