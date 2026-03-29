package cl.duoc.evaluacion.reservas_hotel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// Controlador REST que maneja las reservas. Se inyectan objetos Java simples para simular
@RestController
public class ReservaController {
    private List<Reserva> reservas = new ArrayList<>();

    public ReservaController() {
        // lista en memoria con los 8 registros
        reservas.add(new Reserva(1, "Hotel Mar", 3, "Confirmada",
                Arrays.asList(new Pasajero("111-1", "Claudia"), new Pasajero("222-2", "Pedro")),
                llenarListaGastos("minibar", 2500.0, 1500.0)));
        reservas.add(new Reserva(2, "Hostal Cordillera", 2, "Cancelada",
                Arrays.asList(new Pasajero("333-3", "Juan")),
                new HashMap<>())); 
        reservas.add(new Reserva(3, "Resort Palmas", 5, "Confirmada",
                Arrays.asList(new Pasajero("444-4", "Maria"), new Pasajero("555-5", "Ana")),
                llenarListaGastos("spa", 45000.0, 0.0)));
        reservas.add(new Reserva(4, "Hotel Centro", 1, "Pendiente",
                Arrays.asList(new Pasajero("666-6", "Carlos")),
                new HashMap<>()));
        reservas.add(new Reserva(5, "Cabañas Bosque", 4, "Confirmada",
                Arrays.asList(new Pasajero("777-7", "Luis"), new Pasajero("888-8", "Sofia")),
                llenarListaGastos("restaurante", 25000.0, 8000.0)));
        reservas.add(new Reserva(6, "Hotel Norte", 2, "Confirmada",
                Arrays.asList(new Pasajero("999-9", "Diego")),
                llenarListaGastos("lavanderia", 5000.0, 3000.0)));
        reservas.add(new Reserva(7, "Hotel Sur", 3, "Pendiente",
                Arrays.asList(new Pasajero("101-0", "Camila")),
                new HashMap<>()));
        reservas.add(new Reserva(8, "Hostal Valle", 1, "Confirmada",
                Arrays.asList(new Pasajero("202-0", "Javier")),
                llenarListaGastos("restaurante", 18000.0, 0.0)));
    }

    private Map<String, List<Double>> llenarListaGastos(String servicio, Double gasto1, Double gasto2) {
        Map<String, List<Double>> retorno = new HashMap<String, List<Double>>();
        List<Double> gastos = Arrays.asList(gasto1, gasto2);
        retorno.put(servicio, gastos);
        return retorno;
    }

    // Retorna todas las reservas
    @GetMapping("/reservas")
    public List<Reserva> getReservas() {
        return reservas;
    }

    // Retorna una reserva por su ID
    @GetMapping("/reservas/{id}")
    public Reserva getReservaById(@PathVariable int id) {
        for (Reserva reserva : reservas) {
            if (reserva.getIdReserva() == id) {
                return reserva;
            }
        }
        return null;
    }

    // Calcula la cuenta final sumando los servicios..."
    @GetMapping(path = "/reservas/{idReserva}/total")
    public Double calcularTotalGastosReserva(@PathVariable("idReserva") Integer idReserva) {
        Double total = 0.0;
        
        for (Reserva reserva : reservas) {
            if (reserva.getIdReserva() == idReserva) {
                for (List<Double> gastos : reserva.getGastosPorServicio().values()) {
                    for (Double gasto : gastos) {
                        total += gasto;
                    }
                }
                break;
            }
        }
        return total;
    }
}