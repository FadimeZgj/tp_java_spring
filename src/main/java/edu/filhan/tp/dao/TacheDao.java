package edu.filhan.tp.dao;

import edu.filhan.tp.models.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TacheDao extends JpaRepository<Tache, Integer> {
    Optional<Tache> findByNom(String nomRecherche);

}
