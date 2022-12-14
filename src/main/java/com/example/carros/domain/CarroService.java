package com.example.carros.domain;

import com.example.carros.domain.CarroDTO.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros(){
        return rep.findAll().stream().
                map(CarroDTO::create).
                collect(Collectors.toList());
    }

    public Optional<CarroDTO> getCarroById(Long id){
        return rep.findById(id).map(CarroDTO::create);
    }

    public List<CarroDTO> getCarrosByTipo(String tipo){
        return rep.findByTipo(tipo).stream().
                map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Nao foi possivel inserir o registro.");

        return CarroDTO.create(rep.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id,"Nao foi possivel atualizar o registro.");

        Optional<Carro> optional = rep.findById(id);
        if (optional.isPresent()){
            Carro db = optional.get();
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            return  CarroDTO.create(rep.save(carro));
        } else {
            return null;
        }
    }

    public boolean delete(Long id) {
        if (getCarroById(id).isPresent()){
            rep.deleteById(id);
            return true;
        }
        return false;
    }
}
