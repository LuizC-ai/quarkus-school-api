package com.example.school.core;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class ModeloIdBase extends ModeloBase {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Setter( AccessLevel.NONE )
    Long id;
}
