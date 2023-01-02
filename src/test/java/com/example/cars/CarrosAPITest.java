package com.example.cars;

import com.example.cars.domain.Car;
import com.example.cars.domain.CarDTO.CarDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CarsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarrosAPITest {

	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<CarDTO> getCarro(String url){
		return rest.getForEntity(url, CarDTO.class);
	}

	private ResponseEntity<List<CarDTO>> getCarros(String url) {
		return rest.withBasicAuth("user","123").exchange(
				url,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<CarDTO>>() {
				});
	}

	@Test
	public void testSave() {
		Car car = new Car();
		car.setName("Porshe");
		car.setType("esportivos");

		ResponseEntity response = rest.withBasicAuth("admin","123").postForEntity("/api/v1/carros", car, null);
		System.out.println(response);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		String location = response.getHeaders().get("location").get(0);
		CarDTO c = getCarro(location).getBody();

		assertNotNull(c);
		assertEquals("Porshe", c.getName());
		assertEquals("esportivos", c.getType());

		rest.withBasicAuth("user","123").delete(location);

		assertEquals(HttpStatus.NOT_FOUND, getCarro(location).getStatusCode());
	}

	@Test
	public void testLista() {
		List<CarDTO> carros = getCarros("/api/v1/carros").getBody();
		assertNotNull(carros);
		assertEquals(30, carros.size());
	}

	@Test
	public void testListaPorTipo() {

		assertEquals(10, getCarros("/api/v1/carros/tipo/classicos").getBody().size());
		assertEquals(10, getCarros("/api/v1/carros/tipo/esportivos").getBody().size());
		assertEquals(10, getCarros("/api/v1/carros/tipo/luxo").getBody().size());

		assertEquals(HttpStatus.NO_CONTENT, getCarros("/api/v1/carros/tipo/xxx").getStatusCode());
	}

	@Test
	public void testGetOk() {

		ResponseEntity<CarDTO> response = getCarro("/api/v1/carros/11");
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		CarDTO c = response.getBody();
		assertEquals("Ferrari FF", c.getName());
	}

	@Test
	public void testGetNotFound() {

		ResponseEntity response = getCarro("/api/v1/carros/1100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

}
