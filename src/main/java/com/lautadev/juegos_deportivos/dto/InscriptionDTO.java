package com.lautadev.juegos_deportivos.dto;

import com.lautadev.juegos_deportivos.model.Discipline;
import com.lautadev.juegos_deportivos.model.Enroller;
import com.lautadev.juegos_deportivos.model.Inscription;
import com.lautadev.juegos_deportivos.model.Institution;
import com.lautadev.juegos_deportivos.model.enums.Extract;
import com.lautadev.juegos_deportivos.model.enums.Gender;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class InscriptionDTO {
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime inscriptionDate;
    private Gender gender;
    private Extract extract;
    private Discipline discipline;
    private Enroller enroller;
    private Institution institution;
    private List<ParticipantDTO> participantDTOS;

    public static InscriptionDTO fromInscription(Inscription inscription) {
        if (inscription == null) {
            return null;
        }

        List<ParticipantDTO> participantDTOs = Optional.ofNullable(inscription.getParticipants())
                .orElse(Collections.emptyList()) // Si es null, devuelve una lista vacía
                .stream()
                .map(ParticipantDTO::fromParticipant)
                .collect(Collectors.toList());

        return new InscriptionDTO(
                inscription.getId(),
                inscription.getInscriptionDate(),
                inscription.getGender(),
                inscription.getExtract(),
                inscription.getDiscipline(),
                inscription.getEnroller(),
                inscription.getInstitution(),
                participantDTOs
        );
    }
}
