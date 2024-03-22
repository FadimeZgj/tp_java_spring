package edu.filhan.tp.models.security;

import edu.filhan.tp.dao.UserDao;

import edu.filhan.tp.models.User;
import edu.filhan.tp.models.security.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {

        Optional<User> optionalUser = userDao.findByPseudo(pseudo);

        if (optionalUser.isEmpty()){
            throw new UsernameNotFoundException("pseudo introuvable");
        }

        return new AppUserDetails(optionalUser.get());
    }
}
