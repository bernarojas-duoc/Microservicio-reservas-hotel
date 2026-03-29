package cl.duoc.evaluacion.reservas_hotel;

// Entidad secundaria que se inyecta dentro de la Reserva"
public class Pasajero {
  private String rut;
  private String nombre;

  public Pasajero(String rut, String nombre) {
    this.rut = rut;
    this.nombre = nombre;
  }

  public String getRut() {
    return rut;
  }

  public String getNombre() {
    return nombre;
  }
}
