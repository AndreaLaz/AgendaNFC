package mobile.android.agendanfc.model;

public class Contacto {
    String AppMensajeria, Nombre, Telefono;

    public Contacto(){}

    public Contacto(String AppMensajeria, String Nombre, String Telefono) {
        this.AppMensajeria = AppMensajeria;
        this.Nombre = Nombre;
        this.Telefono = Telefono
    ;
    }

    public String getAppMensajeria() {
        return AppMensajeria;
    }

    public void setAppMensajeria(String appMensajeria) {
        this.AppMensajeria = appMensajeria;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        this.Telefono = telefono;
    }
}
