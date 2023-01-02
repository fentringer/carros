package com.example.cars.api;

import com.example.cars.domain.Car;
import com.example.cars.domain.CarDTO.CarDTO;
import com.example.cars.domain.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
public class CarsController {
    @Autowired
    private CarService service;

    @GetMapping()
    public ResponseEntity<List<CarDTO>> get() {
        return ResponseEntity.ok(service.getCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity getCarById(@PathVariable("id") Long id) {
        CarDTO car = service.getCarById(id);

        return ResponseEntity.ok(car);
    }

    @GetMapping("type/{type}")
    public ResponseEntity<List<CarDTO>> getCarsByType(@PathVariable("type") String type) {
        List<CarDTO> cars = service.getCarsByType(type);

        return cars.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(cars);
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
