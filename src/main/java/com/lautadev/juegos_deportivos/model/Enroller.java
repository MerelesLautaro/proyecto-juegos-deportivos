package com.lautadev.juegos_deportivos.model;

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
@Table(name = "enrollers")
public class Enroller extends Person{
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @OneToMany(mappedBy = "enroller")
    private List<Participant> participantList;
}
