package cl.duoc.evaluacion.reservas_hotel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservasHotelApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void contextLoads() {
		assertNotNull(applicationContext);
	}

	@Test
	void testBeanReservaControllerExiste() {
		assertTrue(applicationContext.containsBean("reservaController"));
	}

	@Test
	void testBeanReservaServiceExiste() {
		assertTrue(applicationContext.containsBean("reservaService"));
	}

	@Test
	void testMain() {
		assertDoesNotThrow(() ->
				ReservasHotelApplication.main(new String[]{"--server.port=0"}));
	}
}
