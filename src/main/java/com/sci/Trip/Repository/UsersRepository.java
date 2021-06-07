package com.sci.Trip.Repository;


import com.sci.Trip.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User,Integer>{

    User findByEmailAndPassword(String email, String password);

    Optional<User> findByName(String name);

    User findByEmail(String email);


}
