package com.example.Load_Management.Service;
import com.example.Load_Management.Entity.Load;
//import com.example.Load_Management.Enum.LoadStatus;
import com.example.Load_Management.Exception.ResourceNotFoundException;
import com.example.Load_Management.Respository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class LoadService {
    private final LoadRepository loadRepository;
   

    public LoadService(LoadRepository loadRepository) {
        this.loadRepository = loadRepository;
    }

    public Load createLoad(Load load) {
        log.info("Creating load: {}", load);
        return loadRepository.save(load);
    }

    public List<Load> getAllLoads() {
        log.info("Fetching all loads");
        return loadRepository.findAll();
    }
    public Optional<Load> getLoadById(UUID LoadId)
    {
        return loadRepository.findById(LoadId);

    }

    public Load updateLoad(UUID loadId, Load updatedLoad) {
        Load existingLoad = loadRepository.findById(loadId)
            .orElseThrow(() -> new ResourceNotFoundException("Load not found with id: " + loadId));
    
        // Only update fields if they are provided in the request
        if (updatedLoad.getShipperId() != null) {
            existingLoad.setShipperId(updatedLoad.getShipperId());
        }
        if (updatedLoad.getFacility() != null) {
            existingLoad.setFacility(updatedLoad.getFacility());
        }
        if (updatedLoad.getProductType() != null) {
            existingLoad.setProductType(updatedLoad.getProductType());
        }
        if (updatedLoad.getTruckType() != null) {
            existingLoad.setTruckType(updatedLoad.getTruckType());
        }
        if (updatedLoad.getNoOfTrucks() > 0) {
            existingLoad.setNoOfTrucks(updatedLoad.getNoOfTrucks());
        }
        if (updatedLoad.getWeight() > 0) {
            existingLoad.setWeight(updatedLoad.getWeight());
        }
        if (updatedLoad.getComment() != null) {
            existingLoad.setComment(updatedLoad.getComment());
        }
        if (updatedLoad.getStatus()!= null){
            existingLoad.setStatus(updatedLoad.getStatus());
        }
    
        return loadRepository.save(existingLoad);
    }
    public void deleteLoad(UUID LoadId){
        if (!loadRepository.existsById(LoadId)) {
            throw new ResourceNotFoundException("Load not found with ID: " + LoadId);
        }
        
        loadRepository.deleteById(LoadId);
    }
}
