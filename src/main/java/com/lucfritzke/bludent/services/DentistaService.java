package com.lucfritzke.bludent.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.lucfritzke.bludent.domain.Dentista;
import com.lucfritzke.bludent.repositories.DentistaRepository;


@Service
public class DentistaService extends ServiceImpl<Dentista>{

    @Autowired
    private  DentistaRepository dentistaRepository;


    @Autowired
    public DentistaService(DentistaRepository dentistaRepository) {
        this.dentistaRepository = dentistaRepository;
    }


    @Override
    public JpaRepository getRepository() {
        return dentistaRepository;
        }



}
