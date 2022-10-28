package edu.miu.cs.cs425.carrentalswe.service.Impl;

import edu.miu.cs.cs425.carrentalswe.model.Category;
import edu.miu.cs.cs425.carrentalswe.model.ExternalUser;
import edu.miu.cs.cs425.carrentalswe.model.Vehicle;
import edu.miu.cs.cs425.carrentalswe.repository.VehicleRepository;
import edu.miu.cs.cs425.carrentalswe.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public List<Vehicle> getUserVehicles(ExternalUser externalUser) {
        return vehicleRepository.findAllByExternalUser(externalUser);
    }

    @Override
    public Vehicle getOneVehicle(Long vId) {
        return vehicleRepository.getById(vId);
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> getAvailableVehicles(String status) {
        return vehicleRepository.findAllByStatus(status);
    }

    @Override
    public List<Vehicle> getVehiclesByCategory(String status, Category category) {
        return vehicleRepository.findAllByStatusAndCategory(status,category);
    }
}
