package ru.otus.pk.spring.domain;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@Entity
@DiscriminatorValue("DOG")
public class Dog extends Animal {
    public Dog(Long id, String name, Gender gender, Integer age, Boolean sterilized, Boolean vaccinated) {
        super(id, name, gender, age, sterilized, vaccinated);
    }
}
