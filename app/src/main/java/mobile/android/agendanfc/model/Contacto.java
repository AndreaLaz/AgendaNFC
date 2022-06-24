package mobile.android.agendanfc.model;

public class Contacto {
    String AppMensajeria,Nombre,Telefono;

    public Contacto(){}

    public Contacto(String appMensajeria, String nombre, String telefono) {
        AppMensajeria = appMensajeria;
        Nombre = nombre;
        Telefono = telefono;
    }

    public String getAppMensajeria() {
        return AppMensajeria;
    }

    public void setAppMensajeria(String appMensajeria) {
        AppMensajeria = appMensajeria;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
}
