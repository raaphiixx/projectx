package com.projectx.repositories;

import com.projectx.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Set<User> findByEmailIgnoreCase(@Param("text") String text);

    UserDetails findByLogin(String login);
}
