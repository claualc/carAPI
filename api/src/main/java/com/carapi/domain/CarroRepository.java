package com.carapi.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CarroRepository extends JpaRepository<Carro, Long> {
    List<Carro> findBytipo(String tipo);
}
