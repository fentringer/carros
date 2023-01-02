package com.example.cars.domain;

import com.example.cars.api.exception.ObjectNotFoundException;
import com.example.cars.domain.CarDTO.CarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository rep;

    public List<CarDTO> getCars(){
        return rep.findAll().stream().
                map(CarDTO::create).
                collect(Collectors.toList());
    }

    public CarDTO getCarById(Long id){
        return rep.findById(id).map(CarDTO::create).orElseThrow(()->new ObjectNotFoundException("Car not found"));
    }

    public List<CarDTO> getCarsByType(String type){
        return rep.findByType(type).stream().
                map(CarDTO::create).collect(Collectors.toList());
    }

    public CarDTO insert(Car car) {
        Assert.isNull(car.getId(), "Unable to insert a new car.");

        return CarDTO.create(rep.save(car));
    }

    public CarDTO update(Car car, Long id) {
        Assert.notNull(id,"Unable to update.");

        Optional<Car> optional = rep.findById(id);
        if (optional.isPresent()){
            Car db = optional.get();
            db.setName(car.getName());
            db.setType(car.getType());
            return  CarDTO.create(rep.save(car));
        } else {
            return null;
        }
    }

    public void delete(Long id) {
            rep.deleteById(id);
    }
}
