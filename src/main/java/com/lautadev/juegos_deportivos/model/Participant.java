package com.lautadev.juegos_deportivos.model;

import com.lautadev.juegos_deportivos.model.enums.Departament;
import com.lautadev.juegos_deportivos.model.enums.Municipality;
import com.lautadev.juegos_deportivos.model.enums.SportRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "participants")
public class Participant {
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
    private SportRole sportRole;
    @ManyToOne
    @JoinColumn(name = "fk_institution")
    private Institution institution;
    private Departament departament;
    private Municipality municipality;
    private String domicile;
    @ManyToMany(mappedBy = "participants")
    private List<Inscription> inscriptions;
    @OneToOne
    private Account account;
}
