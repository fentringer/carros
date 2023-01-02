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

    public List<CarDTO> getCarros(){
        return rep.findAll().stream().
                map(CarDTO::create).
                collect(Collectors.toList());
    }

    public CarDTO getCarroById(Long id){
        return rep.findById(id).map(CarDTO::create).orElseThrow(()->new ObjectNotFoundException("Carro nao encontrado"));
    }

    public List<CarDTO> getCarrosByTipo(String tipo){
        return rep.findByType(tipo).stream().
                map(CarDTO::create).collect(Collectors.toList());
    }

    public CarDTO insert(Car car) {
        Assert.isNull(car.getId(), "Nao foi possivel inserir o registro.");

        return CarDTO.create(rep.save(car));
    }

    public CarDTO update(Car car, Long id) {
        Assert.notNull(id,"Nao foi possivel atualizar o registro.");

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
