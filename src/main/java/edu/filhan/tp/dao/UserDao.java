package edu.filhan.tp.dao;

import edu.filhan.tp.models.Tache;
import edu.filhan.tp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    Optional<User> findByPseudo(String pseudoRecherche);

}
