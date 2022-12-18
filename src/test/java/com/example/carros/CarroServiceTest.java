package com.example.carros;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroDTO.CarroDTO;
import com.example.carros.domain.CarroService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;



@SpringBootTest
class CarroServiceTest {

	@Autowired
	private CarroService service;

	@Test
	void test1() {
		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("esportivos");

		CarroDTO c = service.insert(carro);
		assertNotNull(c);

		Long id = c.getId();
		assertNotNull(id);

		service.delete(id);
	}

	@Test
	void test2() {
		List<CarroDTO> carros = service.getCarros();

		assertEquals(30, carros.size());
	}

	@Test
	void test3() {
		CarroDTO carro = service.getCarroById(11L);
		assertEquals(carro.getNome(), "Ferrari FF");
	}

	@Test
	void test4() {
		List<CarroDTO> carro = service.getCarrosByTipo("esportivos");

		assertEquals(10 , carro.size());
	}

}
