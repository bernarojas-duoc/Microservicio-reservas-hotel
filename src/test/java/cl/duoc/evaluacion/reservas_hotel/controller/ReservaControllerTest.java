package cl.duoc.evaluacion.reservas_hotel.controller;

import cl.duoc.evaluacion.reservas_hotel.model.Hotel;
import cl.duoc.evaluacion.reservas_hotel.model.Pasajero;
import cl.duoc.evaluacion.reservas_hotel.model.Reserva;
import cl.duoc.evaluacion.reservas_hotel.service.ReservaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaControllerTest {

    @Mock
    private ReservaService reservaService;

    @InjectMocks
    private ReservaController reservaController;

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
        hotel = null;
        pasajero = null;
        reserva = null;
    }

    @Test
    void testGetAll_RetornaListaDeReservas() {
        when(reservaService.findAll()).thenReturn(Arrays.asList(reserva));

        List<Reserva> resultado = reservaController.getAll();

        assertEquals(1, resultado.size());
        assertEquals("Confirmada", resultado.get(0).getEstado());
        verify(reservaService).findAll();
    }

    @Test
    void testGetById_Existente_RetornaOk() {
        when(reservaService.findById(1L)).thenReturn(Optional.of(reserva));

        ResponseEntity<Reserva> respuesta = reservaController.getById(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(1L, respuesta.getBody().getIdReserva());
    }

    @Test
    void testGetById_NoExistente_RetornaNotFound() {
        when(reservaService.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Reserva> respuesta = reservaController.getById(99L);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void testCreate_Exitoso_RetornaReservaConfirmada() {
        when(reservaService.crearReserva(any(Reserva.class))).thenReturn(reserva);

        ResponseEntity<?> respuesta = reservaController.create(reserva);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        Reserva cuerpo = (Reserva) respuesta.getBody();
        assertNotNull(cuerpo);
        assertEquals("Confirmada", cuerpo.getEstado());
    }

    @Test
    void testCreate_SinDisponibilidad_RetornaBadRequest() {
        when(reservaService.crearReserva(any(Reserva.class)))
                .thenThrow(new RuntimeException("No hay disponibilidad en el hotel"));

        ResponseEntity<?> respuesta = reservaController.create(reserva);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("No hay disponibilidad en el hotel", respuesta.getBody());
    }

    @Test
    void testUpdate_Exitoso_RetornaReservaActualizada() {
        Reserva actualizada = new Reserva(1L, hotel, pasajero, 5, LocalDate.of(2026, 6, 1), "Confirmada", 200000.0);
        when(reservaService.update(eq(1L), any(Reserva.class))).thenReturn(actualizada);

        ResponseEntity<?> respuesta = reservaController.update(1L, actualizada);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        Reserva cuerpo = (Reserva) respuesta.getBody();
        assertNotNull(cuerpo);
        assertEquals(5, cuerpo.getNoches());
        assertEquals(200000.0, cuerpo.getMontoTotal());
    }

    @Test
    void testUpdate_NoExiste_RetornaNotFound() {
        when(reservaService.update(eq(99L), any(Reserva.class)))
                .thenThrow(new RuntimeException("Reserva no encontrada"));

        ResponseEntity<?> respuesta = reservaController.update(99L, reserva);

        assertEquals(HttpStatus.valueOf(404), respuesta.getStatusCode());
    }

    @Test
    void testDelete_Exitoso_RetornaOk() {
        doNothing().when(reservaService).eliminarReserva(1L);

        ResponseEntity<?> respuesta = reservaController.delete(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Reserva eliminada correctamente", respuesta.getBody());
        verify(reservaService).eliminarReserva(1L);
    }

    @Test
    void testDelete_NoExiste_RetornaNotFound() {
        doThrow(new RuntimeException("Reserva no encontrada"))
                .when(reservaService).eliminarReserva(99L);

        ResponseEntity<?> respuesta = reservaController.delete(99L);

        assertEquals(HttpStatus.valueOf(404), respuesta.getStatusCode());
    }

    @Test
    void testCancelar_Exitoso_RetornaReservaCancelada() {
        reserva.setEstado("Cancelada");
        when(reservaService.cancelarReserva(1L)).thenReturn(reserva);

        ResponseEntity<?> respuesta = reservaController.cancel(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        Reserva cuerpo = (Reserva) respuesta.getBody();
        assertNotNull(cuerpo);
        assertEquals("Cancelada", cuerpo.getEstado());
    }

    @Test
    void testCancelar_YaCancelada_RetornaError() {
        when(reservaService.cancelarReserva(1L))
                .thenThrow(new RuntimeException("La reserva ya está cancelada"));

        ResponseEntity<?> respuesta = reservaController.cancel(1L);

        assertEquals(HttpStatus.valueOf(404), respuesta.getStatusCode());
        assertEquals("La reserva ya está cancelada", respuesta.getBody());
    }

    @Test
    void testGetDisponibilidad_RetornaHotelesConCupos() {
        when(reservaService.consultarDisponibilidad()).thenReturn(Arrays.asList(hotel));

        List<Hotel> resultado = reservaController.getDisponibilidad();

        assertEquals(1, resultado.size());
        assertEquals("Hotel Mar", resultado.get(0).getNombre());
        assertEquals(10, resultado.get(0).getDisponibilidad());
    }
}
