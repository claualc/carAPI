package com.carapi.api;

import com.carapi.domain.CarroService;

import java.util.List;
import java.util.Optional;

import com.carapi.domain.Carro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
    @Autowired
    private CarroService service;

    @GetMapping()
    public ResponseEntity<Iterable<Carro>> get() {
        return ResponseEntity.ok(service.getCarros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> show(@PathVariable("id") Long id) {
        Optional<Carro> carro = service.getById(id);

        // return carro.isPresent() 
        //     ? ResponseEntity.ok(carro.get())
        //     : ResponseEntity.notFound().build();

        //c -> ResponseEntity.ok(c) == ResponseEntity::ok
        return carro.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<Iterable<Carro>> getByTipo(@PathVariable("tipo") String tipo ) {
        List<Carro> carros = service.getByTipo(tipo);
        return !carros.isEmpty() 
            ? ResponseEntity.ok(carros)
            : ResponseEntity.noContent().build();
    }

    @PostMapping
    //RequestBody serializa para carro diretamente
    public String post(@RequestBody Carro carro) {
        System.out.println(carro);
        Carro c = service.save(carro);

        return c.getId() + " salvo";
    }

    @PutMapping("/{id}")
    //RequestBody serializa para carro diretamente
    public Carro post(@PathVariable Long id, @RequestBody Carro carro) {
        Carro c = service.update(carro, id);

        return c;
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.delete(id);
        return "Deletado";
    }
}
