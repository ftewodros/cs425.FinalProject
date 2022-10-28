package edu.miu.cs.cs425.carrentalswe.service;

import edu.miu.cs.cs425.carrentalswe.model.Category;
import edu.miu.cs.cs425.carrentalswe.model.ExternalUser;
import edu.miu.cs.cs425.carrentalswe.model.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleService {

    List<Vehicle> getAllVehicles();
    List<Vehicle> getUserVehicles(ExternalUser externalUser);
    List<Vehicle> getAvailableVehicles(String status);
    List<Vehicle> getVehiclesByCategory(String status, Category category);
    Vehicle getOneVehicle(Long vId);
    Vehicle saveVehicle(Vehicle vehicle);

}
