package edu.miu.cs.cs425.carrentalswe.repository;

import edu.miu.cs.cs425.carrentalswe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String userName);
}
