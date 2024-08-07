package com.lautadev.juegos_deportivos.dto;

import com.lautadev.juegos_deportivos.model.Institution;
import com.lautadev.juegos_deportivos.model.Participant;
import com.lautadev.juegos_deportivos.model.enums.Municipality;
import com.lautadev.juegos_deportivos.model.enums.SportRole;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDTO {
    private Long id;
    private String dni;
    private String name;
    private String lastname;
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;
    private SportRole sportRole;
    private Municipality municipality;
    private String cel;
    private String email;
    private Institution institution;

    public static ParticipantDTO fromParticipant(Participant participant) {
        if (participant == null) {
            return null;
        }
        return new ParticipantDTO(
                participant.getId(),
                participant.getDni(),
                participant.getName(),
                participant.getLastname(),
                participant.getDateOfBirth(),
                participant.getSportRole(),
                participant.getMunicipality(),
                participant.getCel(),
                participant.getEmail(),
                participant.getInstitution()
        );
    }
}
