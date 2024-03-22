package edu.filhan.tp.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import edu.filhan.tp.Views.OperationView;
import edu.filhan.tp.Views.TacheView;
import edu.filhan.tp.Views.UserView;
import edu.filhan.tp.dao.UserDao;
import edu.filhan.tp.models.Tache;
import edu.filhan.tp.models.User;
import edu.filhan.tp.models.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    UserDao userDao;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/sign-in")
    public void signIn(@RequestBody User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userDao.save(user);

    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        try {

            UserDetails userDetails = (UserDetails) authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getPseudo(),
                    user.getPassword()
            )).getPrincipal();

            return jwtUtils.generateJwt(userDetails);

        } catch (Exception ex) {
            return null;
        }

    }

    @GetMapping("/user/list")
    @Secured({"ROLE_ADMIN,", "ROLE_CHEF"})
    @JsonView({UserView.class})
    public List<User> list() {
        List<User> userList = userDao.findAll();

        return userList;
    }

    @GetMapping("/user/{id}")
    @JsonView(UserView.class)
    public ResponseEntity<User> get(@PathVariable int id) {
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isPresent()) {
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/user/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_CHEF"})
    @JsonView({UserView.class, OperationView.class})
    public ResponseEntity<User> delete(@PathVariable int id) {
        Optional<User> optionalUser = userDao.findById(id);

        if (optionalUser.isPresent()) {
            userDao.deleteById(id);
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/user")
    @Secured({"ROLE_ADMIN", "ROLE_CHEF"})
    @JsonView(UserView.class)
    public User create(@RequestBody @Valid User user) {
        user.setId(null);
        userDao.save(user);
        return user;
    }

    @PutMapping("/user/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_CHEF"})
    @JsonView(UserView.class)
    public ResponseEntity<User> update(@RequestBody @Valid User user, @PathVariable int id) {

        user.setId(id);
        Optional<User> optionalUser = userDao.findById(id);

        if (optionalUser.isPresent()) {
            userDao.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
