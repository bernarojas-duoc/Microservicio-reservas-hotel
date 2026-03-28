package cl.duoc.evaluacion.reservas_hotel;

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
