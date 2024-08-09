package com.lautadev.juegos_deportivos.model;

import com.lautadev.juegos_deportivos.model.enums.Departament;
import com.lautadev.juegos_deportivos.model.enums.Municipality;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dni;
    private String name;
    private String lastname;
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;
    private String cel;
    private String email;
    @Enumerated(EnumType.STRING)
    private Departament departament;
    @Enumerated(EnumType.STRING)
    private Municipality municipality;
    private String domicile;
}
