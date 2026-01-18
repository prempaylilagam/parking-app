package com.parking.controller;

import com.parking.entity.ParkingEntry;
import com.parking.repository.ParkingEntryRepository;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/parking")
public class AddParkingEntryController {

    private final ParkingEntryRepository repository;

    public AddParkingEntryController(ParkingEntryRepository repository) {
        this.repository = repository;
    }

    // ✅ ADD ENTRY (Security / Supervisor / Manager)
    @PostMapping("/add")
    public ParkingEntry addEntry(@RequestBody ParkingEntry entry) {

        // Auto fields (no edit / safe)
        entry.setEntryDate(LocalDate.now());
        entry.setEntryDay(LocalDate.now().getDayOfWeek().toString());
        entry.setInTime(LocalDateTime.now());
        entry.setCreatedAt(LocalDateTime.now());

        // OUT fields should be empty at entry time
        entry.setOutTime(null);
        entry.setOutMarkedBy(null);

        return repository.save(entry);
    }
    
    @PutMapping("/out/{id}")
    public ResponseEntity<String> markOut(
            @PathVariable Long id,
            @RequestParam String securityName) {

        int updated = repository.markOut(
                id,
                LocalDateTime.now(),
                securityName
        );

        if (updated == 1) {
            return ResponseEntity.ok("OUT marked successfully");
        } else {
            return ResponseEntity.badRequest()
                    .body("OUT already marked or invalid entry");
        }
    }
    
    @GetMapping("/today")
    public List<ParkingEntry> getTodayEntries() {
        return repository.findByEntryDate(LocalDate.now());
    }
    
    @GetMapping("/by-date")
    public List<ParkingEntry> getByDate(@RequestParam String date) {
        return repository.findByEntryDate(LocalDate.parse(date));
    }
    
    @GetMapping("/search")
    public List<ParkingEntry> searchByVehicle(@RequestParam String vehicle) {
        return repository.findByVehicleNumberIgnoreCase(vehicle);
    }


    

}
