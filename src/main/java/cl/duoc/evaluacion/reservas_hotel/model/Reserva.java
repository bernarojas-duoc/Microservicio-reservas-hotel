package cl.duoc.evaluacion.reservas_hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "RESERVA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    @ManyToOne
    @JoinColumn(name = "id_hotel")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "rut_pasajero")
    private Pasajero pasajero;

    private Integer noches;
    private LocalDate fechaInicio;
    private String estado; // Confirmada, Cancelada, Pendiente
    private Double montoTotal;
}