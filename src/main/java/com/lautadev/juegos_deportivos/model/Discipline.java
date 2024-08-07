package com.lautadev.juegos_deportivos.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "disciplines")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String modality;
    @ManyToMany
    @JoinTable(
            name = "categories_disciplines",
            joinColumns = @JoinColumn(name = "discipline_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @ManyToOne
    private Category category; //esto tiene quer ser una lista, una disciplina puede tener muchas categorias
}
