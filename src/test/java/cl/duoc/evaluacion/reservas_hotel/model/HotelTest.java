package cl.duoc.evaluacion.reservas_hotel.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HotelTest {

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = new Hotel(1L, "Hotel Mar", "Viña del Mar", 50, 10);
    }

    @AfterEach
    void tearDown() {
        hotel = null;
    }

    @Test
    void testConstructorConArgumentos_AsignaValoresCorrectamente() {
        assertEquals(1L, hotel.getId());
        assertEquals("Hotel Mar", hotel.getNombre());
        assertEquals("Viña del Mar", hotel.getUbicacion());
        assertEquals(50, hotel.getCapacidad());
        assertEquals(10, hotel.getDisponibilidad());
    }

    @Test
    void testConstructorVacio_CreaObjetoConValoresNulos() {
        Hotel hotelVacio = new Hotel();
        assertNull(hotelVacio.getId());
        assertNull(hotelVacio.getNombre());
        assertNull(hotelVacio.getUbicacion());
        assertNull(hotelVacio.getCapacidad());
        assertNull(hotelVacio.getDisponibilidad());
    }

    @Test
    void testSetters_ActualizanValoresCorrectamente() {
        hotel.setNombre("Hostal Cordillera");
        hotel.setUbicacion("Santiago");
        hotel.setCapacidad(20);
        hotel.setDisponibilidad(15);

        assertEquals("Hostal Cordillera", hotel.getNombre());
        assertEquals("Santiago", hotel.getUbicacion());
        assertEquals(20, hotel.getCapacidad());
        assertEquals(15, hotel.getDisponibilidad());
    }

    @Test
    void testSetId_ActualizaIdCorrectamente() {
        hotel.setId(99L);
        assertEquals(99L, hotel.getId());
    }

    @Test
    void testDisponibilidad_PermiteModificacion() {
        hotel.setDisponibilidad(hotel.getDisponibilidad() - 1);
        assertEquals(9, hotel.getDisponibilidad());

        hotel.setDisponibilidad(hotel.getDisponibilidad() + 1);
        assertEquals(10, hotel.getDisponibilidad());
    }
}
