package edu.filhan.tp.dao;

import edu.filhan.tp.models.Operation;
import edu.filhan.tp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationDao extends JpaRepository<Operation, Integer> {
    Optional<Operation> findByNom(String nomRecherche);

}
