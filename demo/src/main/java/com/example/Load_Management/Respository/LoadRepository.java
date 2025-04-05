package com.example.Load_Management.Respository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Load_Management.Entity.Load;






import java.util.UUID;

public interface LoadRepository extends JpaRepository<Load, UUID> {}
