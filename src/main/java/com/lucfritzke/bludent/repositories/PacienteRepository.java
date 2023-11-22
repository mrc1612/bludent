package com.lucfritzke.bludent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucfritzke.bludent.domain.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    boolean existsByCpf(String cpf);

}
