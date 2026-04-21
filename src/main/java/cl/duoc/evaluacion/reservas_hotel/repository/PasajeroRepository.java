package cl.duoc.evaluacion.reservas_hotel.repository;

import cl.duoc.evaluacion.reservas_hotel.model.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, String> {
}
