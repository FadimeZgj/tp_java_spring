package edu.filhan.tp.models;

import com.fasterxml.jackson.annotation.JsonView;
import edu.filhan.tp.Views.ChantierView;
import edu.filhan.tp.Views.OperationView;
import edu.filhan.tp.Views.TacheView;
import edu.filhan.tp.Views.UserView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column
    @Length(min = 3, max = 50, message = "Le nom doit avoir entre 3 et 50 caractères")
    @NotNull(message = "Le nom est obligtoire")
    @JsonView({OperationView.class, ChantierView.class, TacheView.class, UserView.class})
    protected String nom;

    @JsonView({OperationView.class, TacheView.class, UserView.class})
    protected LocalDate date;

    @ManyToOne(optional = false)
    @JsonView(OperationView.class)
    protected Chantier chantier;

    @ManyToOne(optional = false)
    @JsonView(OperationView.class)
    protected Tache tache;

    @ManyToOne(optional = false)
    @JsonView({OperationView.class, TacheView.class, OperationView.class})
    protected User user;

}