package edu.filhan.tp.models;

import com.fasterxml.jackson.annotation.JsonView;
import edu.filhan.tp.Views.OperationView;
import edu.filhan.tp.Views.TacheView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(TacheView.class)
    protected Integer id;

    @Column
    @Length(min = 3, max = 50, message = "Le nom doit avoir entre 3 et 50 caractères")
    @NotNull(message = "Le nom est obligtoire")
    @JsonView({TacheView.class, OperationView.class})
    protected String nom;

    @JsonView({TacheView.class, OperationView.class})
    protected int temps;

    @OneToMany(mappedBy = "tache") // on dit de quel many on parle
    @JsonView(TacheView.class)
    protected List<Operation> operationList = new ArrayList<>();
}
