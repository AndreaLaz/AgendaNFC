package mobile.android.agendanfc.modelo;

public class Contacto {

    private String nombre;
    private Double numTelefono;
    private Double numMensajeria;

    public Contacto(String nombre,Double numTelefono, Double numMensajeria){
        this.nombre = nombre;
        this.numTelefono = numTelefono;
        this.numMensajeria = numMensajeria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(Double numTelefono) {
        this.numTelefono = numTelefono;
    }

    public Double getNumMensajeria() {
        return numMensajeria;
    }

    public void setNumMensajeria(Double numMensajeria) {
        this.numMensajeria = numMensajeria;
    }
}
