package com.lautadev.juegos_deportivos.model;

import com.lautadev.juegos_deportivos.model.enums.Departament;
import com.lautadev.juegos_deportivos.model.enums.Municipality;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

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
    @NotBlank(message = "DNI must not be blank")
    @Size(min = 8, max = 20, message = "DNI must be between 5 and 20 characters long")
    private String dni;
    @NotBlank(message = "Name must not be blank")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;
    @NotBlank(message = "Lastname must not be blank")
    @Size(max = 50, message = "Lastname must not exceed 50 characters")
    private String lastname;
    @Temporal(TemporalType.DATE)
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    private String cel;
    private String email;
    @Enumerated(EnumType.STRING)
    private Departament departament;
    @Enumerated(EnumType.STRING)
    private Municipality municipality;
    private String domicile;
}