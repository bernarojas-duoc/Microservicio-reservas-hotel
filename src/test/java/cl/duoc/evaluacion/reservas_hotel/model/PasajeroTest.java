package cl.duoc.evaluacion.reservas_hotel.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasajeroTest {

    private Pasajero pasajero;

    @BeforeEach
    void setUp() {
        pasajero = new Pasajero("111-1", "Claudia Gomez", "claudia@mail.com");
    }

    @AfterEach
    void tearDown() {
        pasajero = null;
    }

    @Test
    void testConstructorConArgumentos_AsignaValoresCorrectamente() {
        assertEquals("111-1", pasajero.getRut());
        assertEquals("Claudia Gomez", pasajero.getNombre());
        assertEquals("claudia@mail.com", pasajero.getEmail());
    }

    @Test
    void testConstructorVacio_CreaObjetoConValoresNulos() {
        Pasajero pasajeroVacio = new Pasajero();
        assertNull(pasajeroVacio.getRut());
        assertNull(pasajeroVacio.getNombre());
        assertNull(pasajeroVacio.getEmail());
    }

    @Test
    void testSetters_ActualizanValoresCorrectamente() {
        pasajero.setNombre("Pedro Jara");
        pasajero.setEmail("pedro@mail.com");

        assertEquals("Pedro Jara", pasajero.getNombre());
        assertEquals("pedro@mail.com", pasajero.getEmail());
    }

    @Test
    void testSetRut_ActualizaRutCorrectamente() {
        pasajero.setRut("222-2");
        assertEquals("222-2", pasajero.getRut());
    }

    @Test
    void testEmail_FormatoValido() {
        pasajero.setEmail("nuevo@dominio.com");
        assertTrue(pasajero.getEmail().contains("@"));
    }
}
