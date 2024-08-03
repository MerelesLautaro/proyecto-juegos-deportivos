package com.lautadev.juegos_deportivos.model;

import com.lautadev.juegos_deportivos.model.enums.InstitutionType;
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
@Table(name="institutions")
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private InstitutionType institutionType;
    @OneToMany(mappedBy = "institution")
    private List<Participant> participants;
}
