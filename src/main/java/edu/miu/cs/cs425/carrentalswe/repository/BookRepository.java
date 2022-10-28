package edu.miu.cs.cs425.carrentalswe.repository;


import edu.miu.cs.cs425.carrentalswe.model.Booking;
import edu.miu.cs.cs425.carrentalswe.model.ExternalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Booking,Long> {

    List<Booking> findAllByUser(ExternalUser user);
}
