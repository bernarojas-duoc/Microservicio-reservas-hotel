package cl.duoc.evaluacion.reservas_hotel;
import java.util.List;
import java.util.Map;

// Objetos Java simples. No tienen lógica, solo atributos y getters.
public class Reserva {
  private int idReserva;
  private String hotel;
  private int noches;
  private String estado;
  // Se utilizan Listas para los pasajeros.
  private List<Pasajero> pasajeros;
  //Mapas para almacenar los gastos por servicio.
  private Map<String, List<Double>> gastosPorServicio;

  public Reserva(int idReserva, String hotel, int noches, String estado,
    List<Pasajero> pasajeros, Map<String, List<Double>> gastosPorServicio
  ) {
    this.idReserva = idReserva;
    this.hotel = hotel;
    this.noches = noches;
    this.estado = estado;
    this.pasajeros = pasajeros;
    this.gastosPorServicio = gastosPorServicio;
  }

  // Getters
  public int getIdReserva() { return idReserva; }
  public String getHotel() { return hotel; }
  public int getNoches() { return noches; }
  public String getEstado() { return estado;}
  public List<Pasajero> getPasajeros() {
    return pasajeros;
  }
  public Map<String, List<Double>> getGastosPorServicio() {
    return gastosPorServicio;
  }
}