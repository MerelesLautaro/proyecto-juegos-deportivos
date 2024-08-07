package com.lautadev.juegos_deportivos.model;

import com.lautadev.juegos_deportivos.model.enums.Extract;
import com.lautadev.juegos_deportivos.model.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inscriptions")
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime inscriptionDate;
    private Gender gender;
    private Extract extract;
    @ManyToMany
    @JoinTable(
            name = "participant_inscription",
            joinColumns = @JoinColumn(name = "inscription_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<Participant> participants;
    @ManyToOne
    private Discipline discipline;
}
