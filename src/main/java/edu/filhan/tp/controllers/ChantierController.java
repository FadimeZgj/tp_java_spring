package edu.filhan.tp.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import edu.filhan.tp.Views.ChantierView;
import edu.filhan.tp.dao.ChantierDao;
import edu.filhan.tp.dao.TacheDao;
import edu.filhan.tp.models.Chantier;
import edu.filhan.tp.models.Operation;
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
public class ChantierController {

    @Autowired
    ChantierDao chantierDao;

    @Autowired
    TacheDao tacheDao;

    @GetMapping("/chantier/list")
    @Secured({"ROLE_ADMIN", "ROLE_CHEF"})
    @JsonView({ChantierView.class})
    public List<Chantier> list(){
        List<Chantier> chantierList = chantierDao.findAll();
        return chantierList;
    }

    @GetMapping("/chantier/{id}")
    @Secured({"ROLE_CHEF"})
    @JsonView({ChantierView.class})
    public ResponseEntity<Chantier> get(@PathVariable int id){

        Optional<Chantier> optionalChantier = chantierDao.findById(id);
        List<Tache> tacheList = tacheDao.findAll();
        int total=0;

            for (Operation operation : optionalChantier.get().getOperationList()) {
                for (Tache tache : tacheList) {
                    if (operation.getTache().getId() == tache.getId()) {
                        total += tache.getTemps();
                    }
                }
                optionalChantier.get().setTemps_total(total);
        }

        if (optionalChantier.isPresent()){
            return new ResponseEntity<>(optionalChantier.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/chantier/{id}")
    @JsonView(ChantierView.class)
    @Secured({"ROLE_ADMIN", "ROLE_CHEF"})
    public ResponseEntity<Chantier> delete(@PathVariable int id) {
        Optional<Chantier> optionalChantier = chantierDao.findById(id);

        if (optionalChantier.isPresent()) {
            chantierDao.deleteById(id);
            return new ResponseEntity<>(optionalChantier.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/chantier")
    @JsonView(ChantierView.class)
    @Secured({"ROLE_ADMIN", "ROLE_CHEF"})
    public Chantier create(@RequestBody @Valid Chantier chantier){
        chantier.setId(null);
        chantierDao.save(chantier);
        return chantier;
    }

    @PutMapping("/chantier/{id}")
    @JsonView(ChantierView.class)
    @Secured({"ROLE_ADMIN", "ROLE_CHEF"})
    public ResponseEntity<Chantier> update(@RequestBody @Valid Chantier chantier, @PathVariable int id){

        chantier.setId(id);
        Optional<Chantier> optionalChantier = chantierDao.findById(id);

        if (optionalChantier.isPresent()) {
            chantierDao.save(chantier);
            return new ResponseEntity<>(chantier, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
