package edu.filhan.tp.models;

import com.fasterxml.jackson.annotation.JsonView;
import edu.filhan.tp.Views.OperationView;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @JsonView({TacheView.class, OperationView.class, UserView.class})
    protected String pseudo;
    protected String password;

    @OneToMany(mappedBy = "user") // on dit de quel many on parle
    @JsonView({UserView.class, OperationView.class})
    protected List<Operation> operationList = new ArrayList<>();

    @ManyToOne(optional = false)
    @JsonView(UserView.class)
    protected Role role;
}