package com.carapi.api;

import com.carapi.domain.CarroService;
import com.carapi.domain.dto.CarroDTO;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
    @Autowired
    private CarroService service;

    @GetMapping()
    public ResponseEntity get() {
        return ResponseEntity.ok(service.getCarros());
    }

    @GetMapping("/{id}")
    public ResponseEntity show(@PathVariable("id") Long id) {
        Optional<CarroDTO> carro = service.getById(id);

        // return carro.isPresent() 
        //     ? ResponseEntity.ok(carro.get())
        //     : ResponseEntity.notFound().build();

        //c -> ResponseEntity.ok(c) == ResponseEntity::ok
        return carro.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getByTipo(@PathVariable("tipo") String tipo ) {
        List<CarroDTO> carros = service.getByTipo(tipo);
        return !carros.isEmpty() 
            ? ResponseEntity.ok(carros)
            : ResponseEntity.noContent().build();
    }


    @PostMapping
    //RequestBody serializa para carro diretamente
    public ResponseEntity post(@RequestBody Carro carro) {
        try {
            CarroDTO c = service.save(carro);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(c.getId()).toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    //RequestBody serializa para carro diretamente
    public ResponseEntity post(@PathVariable Long id, @RequestBody Carro carro) {
        CarroDTO c = service.update(carro, id);

        try {
            return c != null 
            ? ResponseEntity.ok(c)
            : ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }

    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {

        Boolean deleted = service.delete(id);

        return deleted 
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }
}
