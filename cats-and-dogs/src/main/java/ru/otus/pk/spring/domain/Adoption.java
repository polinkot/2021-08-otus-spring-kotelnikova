package ru.otus.pk.spring.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@NamedEntityGraph(name = "Adoption.Animal.Owner", attributeNodes = {@NamedAttributeNode("animal"), @NamedAttributeNode("owner")})
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

    @ManyToOne(fetch = LAZY)
    private Animal animal;

    @ManyToOne(fetch = LAZY)
    private Owner owner;
}
