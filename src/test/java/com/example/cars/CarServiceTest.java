package com.example.cars;

import com.example.cars.domain.Car;
import com.example.cars.domain.CarDTO.CarDTO;
import com.example.cars.domain.CarService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;



@SpringBootTest
class CarServiceTest {

	@Autowired
	private CarService service;

	@Test
	void test1() {
		Car car = new Car();
		car.setName("Ferrari");
		car.setType("esportivos");

		CarDTO c = service.insert(car);
		assertNotNull(c);

		Long id = c.getId();
		assertNotNull(id);

		service.delete(id);
	}

	@Test
	void test2() {
		List<CarDTO> carros = service.getCarros();

		assertEquals(30, carros.size());
	}

	@Test
	void test3() {
		CarDTO carro = service.getCarroById(11L);

		assertNotNull(carro);
		assertEquals(carro.getName(), "Ferrari FF");
	}

	@Test
	void test4() {
		List<CarDTO> carro = service.getCarrosByTipo("esportivos");

		assertEquals(10 , carro.size());
	}

}
