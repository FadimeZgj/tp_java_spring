package edu.filhan.tp.models;

import com.fasterxml.jackson.annotation.JsonView;
import edu.filhan.tp.Views.TacheView;
import edu.filhan.tp.Views.UserView;
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
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column
    @Length(min = 3, max = 50, message = "La désignation doit avoir entre 3 et 50 caractères")
    @NotNull(message = "Le nom est obligtoire")
    @JsonView(UserView.class)
    protected String designation;

    @OneToMany(mappedBy = "role") // on dit de quel many on parle
    protected List<User> userList = new ArrayList<>();

}
