package com.lautadev.juegos_deportivos.model;

import com.lautadev.juegos_deportivos.model.enums.SportRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "participants")
public class Participant extends Person{
    @Enumerated(EnumType.STRING)
    private SportRole sportRole;
    @ManyToOne
    @JoinColumn(name = "fk_enroller")
    private Enroller enroller;
    @ManyToOne
    @JoinColumn(name = "fk_institution")
    private Institution institution;
    @ManyToMany(mappedBy = "participants")
    private List<Inscription> inscriptions;
}
