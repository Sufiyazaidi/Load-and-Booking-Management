package com.example.Load_Management.Controller;
//import com.example.Load_Management.Entity.Booking;
import com.example.Load_Management.Entity.Load;
import com.example.Load_Management.Service.LoadService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/load")
public class LoadController {
    private final LoadService loadService;

    public LoadController(LoadService loadService) {
        this.loadService = loadService;
    }

    @PostMapping
    public Load createLoad(@Valid @RequestBody Load load) {
        log.info("Received request to create load");
        return loadService.createLoad(load);
    }

    @GetMapping
    public List<Load> getAllLoads() {
        log.info("Fetching all loads");
        return loadService.getAllLoads();
    }
    @GetMapping("/{loadId}")
    public Optional<Load> getLoadById(@PathVariable UUID loadId) {
        return loadService.getLoadById(loadId);
    }

    @PutMapping("/{loadId}")
public ResponseEntity<Load> updateLoad(
    @PathVariable UUID loadId, 
    @RequestBody Load updatedLoad
) {
    Load updated = loadService.updateLoad(loadId, updatedLoad);
    return ResponseEntity.ok(updated);
}
    @DeleteMapping("/{loadId}")
    public ResponseEntity<String> deleteLoad(@PathVariable UUID loadId) {
        loadService.deleteLoad(loadId);
        return ResponseEntity.ok("Load deleted successfully");
    }

}
