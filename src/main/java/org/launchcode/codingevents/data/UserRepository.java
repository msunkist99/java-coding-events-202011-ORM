package org.launchcode.codingevents.data;

import org.launchcode.codingevents.models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Chris Bay
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    // this method is intended to take a username, and return the given user with that username.
    User findByUsername(String username);

}
