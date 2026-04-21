package cl.duoc.evaluacion.reservas_hotel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PASAJERO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pasajero {
    @Id
    private String rut;
    private String nombre;
    private String email;
}
