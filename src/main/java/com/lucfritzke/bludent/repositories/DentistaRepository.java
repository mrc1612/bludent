package com.lucfritzke.bludent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucfritzke.bludent.domain.Dentista;

public interface DentistaRepository extends JpaRepository<Dentista, Long >{
    
}
