package edu.filhan.tp.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import edu.filhan.tp.Views.OperationView;
import edu.filhan.tp.dao.OperationDao;
import edu.filhan.tp.models.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@JsonView(OperationView.class)
public class OperationController {
    @Autowired
    OperationDao operationDao;

    @GetMapping("/operation/list")
    @JsonView({OperationView.class})
    public List<Operation> list(){
        List<Operation> operationList = operationDao.findAll();
        return operationList;
    }

    @GetMapping("/operation/{id}")
    @JsonView(OperationView.class)
    public ResponseEntity<Operation> get(@PathVariable int id) {
        Optional<Operation> optionalOperation = operationDao.findById(id);
        if (optionalOperation.isPresent()) {
            return new ResponseEntity<>(optionalOperation.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/operation/{id}")
    @JsonView(OperationView.class)
    public ResponseEntity<Operation> delete(@PathVariable int id) {
        Optional<Operation> optionalOperation = operationDao.findById(id);

        if (optionalOperation.isPresent()) {
            operationDao.deleteById(id);
            return new ResponseEntity<>(optionalOperation.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/operation")
    @Secured({"ROLE_ADMIN", "ROLE_CHEF"})
    @JsonView(OperationView.class)
//    via l'extension vscode thunder = postman on passe un json dans le body que l'on l'on récupère dans la fonction
    public Operation create(@RequestBody @Valid Operation operation){
//        pour éviter de faire un update d'un produit ayant déjà id existant on setid null
        operation.setId(null);
        operationDao.save(operation);
        return operation;
    }

    @PutMapping("/operation/{id}")
    @Secured({"ROLE_ADMIN","ROLE_CHEF"})
    @JsonView(OperationView.class)
    public ResponseEntity<Operation> update(@RequestBody @Valid Operation operation, @PathVariable int id){

        operation.setId(id);
//        si l'id n'existe pas on fait
        Optional<Operation> optionalOperation = operationDao.findById(id);

        if (optionalOperation.isPresent()) {
            operationDao.save(operation);
            return new ResponseEntity<>(operation, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
