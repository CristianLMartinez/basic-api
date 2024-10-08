package com.globant.trainingnewgen.repository;

import com.globant.trainingnewgen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
        SELECT u FROM User u WHERE u.name = :name
     """
    )
    Optional<User> findByName(String name);

}
