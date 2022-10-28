package edu.miu.cs.cs425.carrentalswe.repository;

import edu.miu.cs.cs425.carrentalswe.model.ExternalUser;
import edu.miu.cs.cs425.carrentalswe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalUserRepository extends JpaRepository<ExternalUser, Long> {

    ExternalUser findByUser(User user);
}
