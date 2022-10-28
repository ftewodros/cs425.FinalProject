package edu.miu.cs.cs425.carrentalswe.repository;

import edu.miu.cs.cs425.carrentalswe.model.Category;
import edu.miu.cs.cs425.carrentalswe.model.ExternalUser;
import edu.miu.cs.cs425.carrentalswe.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findAllByExternalUser(ExternalUser externalUser);

    List<Vehicle> findAllByStatus(String status);

    List<Vehicle> findAllByStatusAndCategory(String status, Category category);
}
