package com.fd.finema.repository;

import com.fd.finema.bom.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor,Long> {
    Optional<Actor> findFirstByFirstNameAndLastName(String firstName, String secondName);
}
