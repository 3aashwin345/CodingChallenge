package com.sample.project.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.project.domain.VehicleDetails;


public interface VehicleRepository extends JpaRepository<VehicleDetails, Integer>{

    List<VehicleDetails> findByMake(String make);
}
