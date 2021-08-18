package com.carapi.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public Iterable<Carro> getCarros() {
        return rep.findAll();
    }

    public Optional<Carro> getById(Long id) {
        return rep.findById(id);
    }

    public List<Carro> getByTipo(String tipo) {
        return rep.findBytipo(tipo);
    }

    public Carro save(Carro carro) {
        return rep.save(carro);
    }

    public Carro update(Carro carro, Long id) {
        Assert.notNull(id, "Carro não encontrado");

        //Buscar carro no db
        // carro_do_db !== carro do intput, um vem do json outor do db, com foreyng key muda
        Optional<Carro> carro_do_db = getById(id);
        if(carro_do_db.isPresent()) {
            Carro db = carro_do_db.get();
            
            //copiar propiedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());

            rep.save(db);
            return db;
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public void delete(Long id) {
        Assert.notNull(id, "Carro não encontrado");

        Optional<Carro> carro_no_db = getById(id);
        if(carro_no_db.isPresent()) {
            rep.deleteById(id);
        } else {
            throw new RuntimeException("Não foi possível deletar o item");
        }
    }

}