package com.carapi.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface CarroRepository extends CrudRepository<Carro, Long> {
    List<Carro> findBytipo(String tipo);
}
