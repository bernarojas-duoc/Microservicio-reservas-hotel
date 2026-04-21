package cl.duoc.evaluacion.reservas_hotel.service;

import cl.duoc.evaluacion.reservas_hotel.model.Hotel;
import cl.duoc.evaluacion.reservas_hotel.model.Reserva;
import cl.duoc.evaluacion.reservas_hotel.repository.HotelRepository;
import cl.duoc.evaluacion.reservas_hotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> findById(Long id) {
        return reservaRepository.findById(id);
    }

    @Transactional
    public Reserva crearReserva(Reserva reserva) {
        Hotel hotel = hotelRepository.findById(reserva.getHotel().getId())
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));

        if (hotel.getDisponibilidad() <= 0) {
            throw new RuntimeException("No hay disponibilidad en el hotel");
        }

        // Descontar disponibilidad
        hotel.setDisponibilidad(hotel.getDisponibilidad() - 1);
        hotelRepository.save(hotel);

        reserva.setEstado("Confirmada");
        return reservaRepository.save(reserva);
    }

    @Transactional
    public void eliminarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // Restaurar disponibilidad si la reserva no estaba ya cancelada
        if (!"Cancelada".equals(reserva.getEstado())) {
            Hotel hotel = reserva.getHotel();
            hotel.setDisponibilidad(hotel.getDisponibilidad() + 1);
            hotelRepository.save(hotel);
        }

        reservaRepository.delete(reserva);
    }

    @Transactional
    public Reserva cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if ("Cancelada".equals(reserva.getEstado())) {
            throw new RuntimeException("La reserva ya está cancelada");
        }

        reserva.setEstado("Cancelada");
        
        Hotel hotel = reserva.getHotel();
        hotel.setDisponibilidad(hotel.getDisponibilidad() + 1);
        hotelRepository.save(hotel);

        return reservaRepository.save(reserva);
    }

    public List<Hotel> consultarDisponibilidad() {
        return hotelRepository.findAll().stream()
                .filter(h -> h.getDisponibilidad() > 0)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        reservaRepository.deleteById(id);
    }
    
    @Transactional
    public Reserva update(Long id, Reserva reservaDetails) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        reserva.setNoches(reservaDetails.getNoches());
        reserva.setFechaInicio(reservaDetails.getFechaInicio());
        reserva.setMontoTotal(reservaDetails.getMontoTotal());
        // El estado y el hotel se manejan en métodos específicos para asegurar consistencia
        
        return reservaRepository.save(reserva);
    }
}
