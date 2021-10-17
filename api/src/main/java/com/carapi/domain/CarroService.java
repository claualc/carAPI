package com.carapi.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.carapi.domain.dto.CarroDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros() {
        List<Carro> carros = rep.findAll();

        // percorre carro por carro gerando uma lista de carroDTO
        System.out.println(carros);
        List<CarroDTO> list = carros.stream().map(CarroDTO::new)
            .collect(Collectors.toList());

        return list;
    }

    public Optional<CarroDTO> getById(Long id) {
        return rep.findById(id).map(CarroDTO::new);
    }

    public List<CarroDTO> getByTipo(String tipo) {
        List<Carro> carros = rep.findBytipo(tipo);

        List<CarroDTO> list = carros.stream().map(CarroDTO::new).collect(Collectors.toList());
        return list;
    }

    public CarroDTO save(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível inserir o novo registro");
        return new CarroDTO(rep.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id, "Carro não encontrado");

        //Buscar carro no db
        // carro_do_db !== carro do intput, um vem do json outor do db, com foreyng key muda
        Optional<Carro> carro_do_db = rep.findById(id);
        if(carro_do_db.isPresent()) {

            rep.update(id, carro);
            return new CarroDTO(carro);
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public Boolean delete(Long id) {
        Assert.notNull(id, "Carro não encontrado");

        Optional<CarroDTO> carro_no_db = getById(id);
        if(carro_no_db.isPresent()) {
            rep.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}