package cl.duoc.evaluacion.reservas_hotel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservaController {
    
    private List<Reserva> reservas = new ArrayList<>();

    public ReservaController() {
        reservas.add(new Reserva(1, "Hotel Mar", 3, "Confirmada",
                Arrays.asList(new Pasajero("1.111.111-1", "Claudia"), new Pasajero("222-2", "Pedro")),
                llenarListaGastos("minibar", 2500.0, 1500.0)));
                
        reservas.add(new Reserva(2, "Hostal Cordillera", 2, "Cancelada",
                Arrays.asList(new Pasajero("2.222.222-2", "Juan")),
                llenarListaGastos("restaurante", 15000.0, 12000.0)));
                
        reservas.add(new Reserva(3, "Resort Palmas", 5, "Confirmada",
                Arrays.asList(new Pasajero("3.333.333-3", "Maria"), new Pasajero("55.555.555-5", "Ana")),
                llenarListaGastos("spa", 45000.0, 0.0)));
                
        reservas.add(new Reserva(4, "Hotel Centro", 1, "Pendiente",
                Arrays.asList(new Pasajero("4.444.444-4", "Carlos")),
                llenarListaGastos("minibar", 0.0, 0.0)));
                
        reservas.add(new Reserva(5, "Cabañas Bosque", 4, "Confirmada",
                Arrays.asList(new Pasajero("5.555.555-5", "Luis"), new Pasajero("888-8", "Sofia")),
                llenarListaGastos("restaurante", 25000.0, 8000.0)));
                
        reservas.add(new Reserva(6, "Hotel Norte", 2, "Confirmada",
                Arrays.asList(new Pasajero("6.666.666-6", "Diego")),
                llenarListaGastos("lavanderia", 5000.0, 3000.0)));
                
        reservas.add(new Reserva(7, "Hotel Sur", 3, "Pendiente",
                Arrays.asList(new Pasajero("7.777.777-7", "Camila")),
                llenarListaGastos("minibar", 1200.0, 4000.0)));
                
        reservas.add(new Reserva(8, "Hostal Valle", 1, "Confirmada",
                Arrays.asList(new Pasajero("8.888.888-8", "Javier")),
                llenarListaGastos("restaurante", 18000.0, 0.0)));
    }

    private Map<String, List<Double>> llenarListaGastos(String servicio, Double gasto1, Double gasto2) {
        Map<String, List<Double>> retorno = new HashMap<String, List<Double>>();
        List<Double> gastos = Arrays.asList(gasto1, gasto2);
        retorno.put(servicio, gastos);
        return retorno;
    }

    // Endpoint 1: Retorna todas las reservas
    @GetMapping("/reservas")
    public List<Reserva> getReservas() {
        return reservas;
    }

    // Endpoint 2: Retorna una reserva por su ID
    @GetMapping("/reservas/{id}")
    public Reserva getReservaById(@PathVariable int id) {
        for (Reserva reserva : reservas) {
            if (reserva.getIdReserva() == id) {
                return reserva;
            }
        }
        return null;
    }

    // Endpoint 3: Calcula el total gastado en un servicio específico.
    @GetMapping(path = "/reservas/{idReserva}/totalgastos/{nombreServicio}")
    public Double calcularTotalGastos(@PathVariable("idReserva") Integer idReserva,
                                      @PathVariable("nombreServicio") String nombreServicio) {
        Double total = 0.0;
        for (Reserva reserva : reservas) {
            if (reserva.getIdReserva() == idReserva) {
                // Verificamos si existe el servicio para evitar errores nulos
                if(reserva.getGastosPorServicio().containsKey(nombreServicio)) {
                    List<Double> gastos = reserva.getGastosPorServicio().get(nombreServicio);
                    for (Double gasto : gastos) {
                        total = total + gasto;
                    }
                }
            }
        }
        return total;
    }
}