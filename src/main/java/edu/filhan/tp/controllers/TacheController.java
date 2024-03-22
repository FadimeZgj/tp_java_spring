package edu.filhan.tp.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import edu.filhan.tp.Views.TacheView;
import edu.filhan.tp.dao.TacheDao;
import edu.filhan.tp.models.Tache;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TacheController {
    @Autowired
    TacheDao tacheDao;

    @GetMapping("/tache/list")
    @JsonView({TacheView.class})
    public List<Tache> list(){
        List<Tache> tacheList = tacheDao.findAll();

        return tacheList;
    }

    @GetMapping("/tache/{id}")
    @JsonView(TacheView.class)
    public ResponseEntity<Tache> get(@PathVariable int id) {
        Optional<Tache> optionalTache = tacheDao.findById(id);
        if (optionalTache.isPresent()){
            return new ResponseEntity<>(optionalTache.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/tache/{id}")
    @Secured({"ROLE_ADMIN","ROLE_CHEF"})
    @JsonView(TacheView.class)
    public ResponseEntity<Tache> delete(@PathVariable int id) {
        Optional<Tache> optionalTache = tacheDao.findById(id);

        if (optionalTache.isPresent()) {
            tacheDao.deleteById(id);
            return new ResponseEntity<>(optionalTache.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/tache")
    @Secured({"ROLE_ADMIN","ROLE_CHEF"})
    @JsonView(TacheView.class)
    public Tache create(@RequestBody @Valid Tache tache){
        tache.setId(null);
        tacheDao.save(tache);
        return tache;
    }

    @PutMapping("/tache/{id}")
    @Secured({"ROLE_ADMIN","ROLE_CHEF"})
    @JsonView(TacheView.class)
    public ResponseEntity<Tache> update(@RequestBody @Valid Tache tache, @PathVariable int id){

        tache.setId(id);
        Optional<Tache> optionalTache = tacheDao.findById(id);

        if (optionalTache.isPresent()) {
            tacheDao.save(tache);
            return new ResponseEntity<>(tache, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
