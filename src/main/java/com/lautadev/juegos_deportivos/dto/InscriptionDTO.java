package com.lautadev.juegos_deportivos.dto;

import com.lautadev.juegos_deportivos.model.Discipline;
import com.lautadev.juegos_deportivos.model.Inscription;
import com.lautadev.juegos_deportivos.model.enums.Extract;
import com.lautadev.juegos_deportivos.model.enums.Gender;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private List<ParticipantDTO> participantDTOS;

    // Método de conversión desde Inscription a InscriptionDTO
    public static InscriptionDTO fromInscription(Inscription inscription) {
        if (inscription == null) {
            return null;
        }
        List<ParticipantDTO> participantDTOs = inscription.getParticipants().stream()
                .map(ParticipantDTO::fromParticipant)
                .collect(Collectors.toList());

        return new InscriptionDTO(
                inscription.getId(),
                inscription.getInscriptionDate(),
                inscription.getGender(),
                inscription.getExtract(),
                inscription.getDiscipline(),
                participantDTOs
        );
    }
}
