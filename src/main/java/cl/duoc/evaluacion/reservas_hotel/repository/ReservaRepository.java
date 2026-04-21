package cl.duoc.evaluacion.reservas_hotel.repository;

import cl.duoc.evaluacion.reservas_hotel.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
