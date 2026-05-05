package cl.duoc.evaluacion.reservas_hotel.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    private Hotel hotel;
    private Pasajero pasajero;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        hotel = new Hotel(1L, "Hotel Mar", "Viña del Mar", 50, 10);
        pasajero = new Pasajero("111-1", "Claudia Gomez", "claudia@mail.com");
        reserva = new Reserva(1L, hotel, pasajero, 3, LocalDate.of(2026, 4, 20), "Confirmada", 150000.0);
    }

    @AfterEach
    void tearDown() {
        reserva = null;
        hotel = null;
        pasajero = null;
    }

    @Test
    void testConstructorConArgumentos_AsignaValoresCorrectamente() {
        assertEquals(1L, reserva.getIdReserva());
        assertEquals(hotel, reserva.getHotel());
        assertEquals(pasajero, reserva.getPasajero());
        assertEquals(3, reserva.getNoches());
        assertEquals(LocalDate.of(2026, 4, 20), reserva.getFechaInicio());
        assertEquals("Confirmada", reserva.getEstado());
        assertEquals(150000.0, reserva.getMontoTotal());
    }

    @Test
    void testConstructorVacio_CreaObjetoConValoresNulos() {
        Reserva reservaVacia = new Reserva();
        assertNull(reservaVacia.getIdReserva());
        assertNull(reservaVacia.getHotel());
        assertNull(reservaVacia.getPasajero());
        assertNull(reservaVacia.getEstado());
    }

    @Test
    void testSetEstado_CambiaEstadoCorrectamente() {
        reserva.setEstado("Cancelada");
        assertEquals("Cancelada", reserva.getEstado());

        reserva.setEstado("Pendiente");
        assertEquals("Pendiente", reserva.getEstado());
    }

    @Test
    void testSetNoches_ActualizaNoches() {
        reserva.setNoches(7);
        assertEquals(7, reserva.getNoches());
    }

    @Test
    void testSetMontoTotal_ActualizaMonto() {
        reserva.setMontoTotal(300000.0);
        assertEquals(300000.0, reserva.getMontoTotal());
    }

    @Test
    void testSetFechaInicio_ActualizaFecha() {
        LocalDate nuevaFecha = LocalDate.of(2026, 6, 15);
        reserva.setFechaInicio(nuevaFecha);
        assertEquals(nuevaFecha, reserva.getFechaInicio());
    }

    @Test
    void testSetHotel_ActualizaHotel() {
        Hotel nuevoHotel = new Hotel(2L, "Resort Palmas", "La Serena", 100, 80);
        reserva.setHotel(nuevoHotel);
        assertEquals("Resort Palmas", reserva.getHotel().getNombre());
    }
}
