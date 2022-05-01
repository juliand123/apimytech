package com.mytech.api.controllers;
import com.mytech.api.dao.IUserDao;
import com.mytech.api.models.User;
import com.mytech.api.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private IUserDao iUserDao;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id){
        User user = new User();
        user.setId(id);
        user.setName("toño");
        user.setLastname("araujo");
        user.setEmail("araujo22@hotmail.com");
        user.setPhone("3007811262");
        user.setPassword("123456");
        return user;
    }

    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public List<User> GetUsers(@RequestHeader(value = "Authorization") String token ) {

          if (!validateToken(token)){
              return null;
          }
        return iUserDao.getUsers();
    }

    private boolean validateToken(String token){

        String userID = jwtUtil.getKey(token);
        return userID != null;

    }

    @RequestMapping(value = "api/users", method = RequestMethod.POST)
    public void RegisterUser(@RequestBody User user) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(3, 1024, 3, user.getPassword());
        user.setPassword(hash);

        iUserDao.RegisterUser(user);
    }

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.DELETE)
    public void Delete( @RequestHeader(value = "Authorization") String token,
                        @PathVariable Long id){
        if (!validateToken(token)){
            return;
        }

        iUserDao.Delete(id);
    }
}
