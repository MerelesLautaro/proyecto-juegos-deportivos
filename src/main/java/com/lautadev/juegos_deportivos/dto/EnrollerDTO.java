package com.lautadev.juegos_deportivos.dto;

import com.lautadev.juegos_deportivos.model.Enroller;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollerDTO {
    private Long id;
    private String dni;
    private String name;
    private String lastname;
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;
    private Municipality municipality;
    private String cel;
    private String email;
    private List<ParticipantDTO> participantDTOList;

    public static EnrollerDTO fromEnroller(Enroller enroller) {
        if (enroller == null) {
            return null;
        }

        List<ParticipantDTO> participantDTOs = new ArrayList<>();
        if (enroller.getParticipantList() != null) {
            participantDTOs = enroller.getParticipantList().stream()
                    .map(ParticipantDTO::fromParticipant)
                    .collect(Collectors.toList());
        }

        return new EnrollerDTO(
                enroller.getId(),
                enroller.getDni(),
                enroller.getName(),
                enroller.getLastname(),
                enroller.getDateOfBirth(),
                enroller.getMunicipality(),
                enroller.getCel(),
                enroller.getEmail(),
                participantDTOs
        );
    }
}
