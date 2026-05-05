package cl.duoc.evaluacion.reservas_hotel.service;

import cl.duoc.evaluacion.reservas_hotel.model.Hotel;
import cl.duoc.evaluacion.reservas_hotel.model.Pasajero;
import cl.duoc.evaluacion.reservas_hotel.model.Reserva;
import cl.duoc.evaluacion.reservas_hotel.repository.HotelRepository;
import cl.duoc.evaluacion.reservas_hotel.repository.ReservaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private ReservaService reservaService;

    private Hotel hotel;
    private Pasajero pasajero;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        hotel = new Hotel(1L, "Hotel Mar", "Viña del Mar", 50, 10);
        pasajero = new Pasajero("111-1", "Claudia Gomez", "claudia@mail.com");
        reserva = new Reserva(null, hotel, pasajero, 3, LocalDate.of(2026, 4, 20), "Pendiente", 150000.0);
    }

    @AfterEach
    void tearDown() {
        hotel = null;
        pasajero = null;
        reserva = null;
    }

    @Test
    void testCrearReserva_ConDisponibilidad_EstadoConfirmada() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(inv -> {
            Reserva r = inv.getArgument(0);
            r.setIdReserva(1L);
            return r;
        });

        Reserva resultado = reservaService.crearReserva(reserva);

        assertEquals("Confirmada", resultado.getEstado());
        assertEquals(9, hotel.getDisponibilidad());
        verify(hotelRepository).save(hotel);
        verify(reservaRepository).save(reserva);
    }

    @Test
    void testCrearReserva_SinDisponibilidad_LanzaExcepcion() {
        hotel.setDisponibilidad(0);
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservaService.crearReserva(reserva));

        assertEquals("No hay disponibilidad en el hotel", ex.getMessage());
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void testCrearReserva_HotelNoExiste_LanzaExcepcion() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservaService.crearReserva(reserva));

        assertEquals("Hotel no encontrado", ex.getMessage());
    }

    @Test
    void testCancelarReserva_Confirmada_CambiaEstadoYRestauraDisponibilidad() {
        reserva.setIdReserva(1L);
        reserva.setEstado("Confirmada");
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        Reserva resultado = reservaService.cancelarReserva(1L);

        assertEquals("Cancelada", resultado.getEstado());
        assertEquals(11, hotel.getDisponibilidad());
        verify(hotelRepository).save(hotel);
    }

    @Test
    void testCancelarReserva_YaCancelada_LanzaExcepcion() {
        reserva.setIdReserva(1L);
        reserva.setEstado("Cancelada");
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservaService.cancelarReserva(1L));

        assertEquals("La reserva ya está cancelada", ex.getMessage());
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void testConsultarDisponibilidad_FiltraHotelesSinCupos() {
        Hotel hotelLleno = new Hotel(2L, "Hotel Lleno", "Santiago", 10, 0);
        when(hotelRepository.findAll()).thenReturn(Arrays.asList(hotel, hotelLleno));

        List<Hotel> disponibles = reservaService.consultarDisponibilidad();

        assertEquals(1, disponibles.size());
        assertEquals("Hotel Mar", disponibles.get(0).getNombre());
    }

    @Test
    void testFindAll_RetornaTodasLasReservas() {
        Reserva r2 = new Reserva(2L, hotel, pasajero, 2, LocalDate.of(2026, 5, 1), "Confirmada", 80000.0);
        when(reservaRepository.findAll()).thenReturn(Arrays.asList(reserva, r2));

        List<Reserva> resultado = reservaService.findAll();

        assertEquals(2, resultado.size());
        verify(reservaRepository).findAll();
    }

    @Test
    void testFindById_Existente_RetornaReserva() {
        reserva.setIdReserva(1L);
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        Optional<Reserva> resultado = reservaService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getIdReserva());
    }

    @Test
    void testFindById_NoExistente_RetornaVacio() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Reserva> resultado = reservaService.findById(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testEliminarReserva_Cancelada_NoRestauраDisponibilidad() {
        reserva.setIdReserva(1L);
        reserva.setEstado("Cancelada");
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        reservaService.eliminarReserva(1L);

        verify(hotelRepository, never()).save(any());
        verify(reservaRepository).delete(reserva);
    }

    @Test
    void testEliminarReserva_Confirmada_RestauраDisponibilidad() {
        reserva.setIdReserva(1L);
        reserva.setEstado("Confirmada");
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        reservaService.eliminarReserva(1L);

        assertEquals(11, hotel.getDisponibilidad());
        verify(hotelRepository).save(hotel);
        verify(reservaRepository).delete(reserva);
    }

    @Test
    void testCancelarReserva_NoExiste_LanzaExcepcion() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservaService.cancelarReserva(99L));

        assertEquals("Reserva no encontrada", ex.getMessage());
    }

    @Test
    void testEliminarReserva_NoExiste_LanzaExcepcion() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservaService.eliminarReserva(99L));

        assertEquals("Reserva no encontrada", ex.getMessage());
    }

    @Test
    void testUpdate_NoExiste_LanzaExcepcion() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservaService.update(99L, reserva));

        assertEquals("Reserva no encontrada", ex.getMessage());
    }

    @Test
    void testDelete_EliminaDirectamentePorId() {
        reservaService.delete(1L);
        verify(reservaRepository).deleteById(1L);
    }

    @Test
    void testUpdate_ActualizaCamposPermitidos() {
        reserva.setIdReserva(1L);
        Reserva detalles = new Reserva(null, null, null, 5, LocalDate.of(2026, 6, 1), null, 200000.0);
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(inv -> inv.getArgument(0));

        Reserva resultado = reservaService.update(1L, detalles);

        assertEquals(5, resultado.getNoches());
        assertEquals(LocalDate.of(2026, 6, 1), resultado.getFechaInicio());
        assertEquals(200000.0, resultado.getMontoTotal());
    }
}
