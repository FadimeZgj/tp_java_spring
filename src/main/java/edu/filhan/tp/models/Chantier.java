package edu.filhan.tp.models;

import com.fasterxml.jackson.annotation.JsonView;
import edu.filhan.tp.Views.ChantierView;
import edu.filhan.tp.Views.OperationView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
public class Chantier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ChantierView.class, OperationView.class})
    protected Integer id;

    @Column
    @Length(min = 3, max = 50, message = "Le nom doit avoir entre 3 et 50 caractères")
    @NotNull(message = "Le nom est obligtoire")
    @JsonView({ChantierView.class, OperationView.class})
    protected String nom;

    @JsonView(ChantierView.class)
    protected String adresse;

    @OneToMany(mappedBy = "chantier") // on dit de quel many on parle
    @JsonView(ChantierView.class)
    protected List<Operation> operationList = new ArrayList<>();

    @Transient
    @JsonView(ChantierView.class)
    protected Integer temps_total;

}