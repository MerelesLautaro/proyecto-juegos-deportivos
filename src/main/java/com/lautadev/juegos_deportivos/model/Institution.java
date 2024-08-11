package com.lautadev.juegos_deportivos.model;

import com.lautadev.juegos_deportivos.model.enums.InstitutionType;
import com.lautadev.juegos_deportivos.model.enums.Municipality;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String name;
    private String domicile;
    @Enumerated(EnumType.STRING)
    private Municipality municipality;
    private String cel;
    @Enumerated(EnumType.STRING)
    private InstitutionType institutionType;
}
