package es.etg.dam.acs;

public class Caballo {
    private String nombre;
    private int puntos;
    
    public Caballo(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos += puntos;
    }

    @Override
    public String toString() {
        return "El caballo " + getNombre() + " tiene un total de " + getPuntos() + " puntos.";
    }
}
