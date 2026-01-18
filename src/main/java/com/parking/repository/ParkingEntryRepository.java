package com.parking.repository;

import com.parking.entity.ParkingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;



@Repository
public interface ParkingEntryRepository extends JpaRepository<ParkingEntry, Long> {

    // Today entries
    List<ParkingEntry> findByEntryDate(LocalDate entryDate);

    // Only IN vehicles (OUT not yet marked)
    List<ParkingEntry> findByOutTimeIsNull();

    // Search by vehicle number
    List<ParkingEntry> findByVehicleNumber(String vehicleNumber);
    
    List<ParkingEntry> findByVehicleNumberIgnoreCase(String vehicleNumber);

    
    @Transactional
    @Modifying
    @Query("""
        UPDATE ParkingEntry p
        SET p.outTime = :outTime,
            p.outMarkedBy = :outMarkedBy
        WHERE p.id = :id
          AND p.outTime IS NULL
    """)
    int markOut(Long id, LocalDateTime outTime, String outMarkedBy);

}
