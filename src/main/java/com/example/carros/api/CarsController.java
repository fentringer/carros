package com.example.carros.api;

import com.example.carros.domain.Car;
import com.example.carros.domain.CarDTO.CarDTO;
import com.example.carros.domain.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carros")
public class CarsController {
    @Autowired
    private CarService service;

    @GetMapping()
    public ResponseEntity<List<CarDTO>> get() {
        return ResponseEntity.ok(service.getCarros());
    }

    @GetMapping("/{id}")
    public ResponseEntity getCarroById(@PathVariable("id") Long id) {
        CarDTO carro = service.getCarroById(id);

        return ResponseEntity.ok(carro);
    }

    @GetMapping("tipo/{tipo}")
    public ResponseEntity<List<CarDTO>> getCarroByTipo(@PathVariable("tipo") String tipo) {
        List<CarDTO> carros = service.getCarrosByTipo(tipo);

        return carros.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(carros);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Car car) {
        CarDTO c = service.insert(car);
        URI location = getURI(c.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Car car) {
        car.setId(id);
        CarDTO c = service.update(car, id);

        return c != null ? ResponseEntity.ok(c)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    private URI getURI(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

}
