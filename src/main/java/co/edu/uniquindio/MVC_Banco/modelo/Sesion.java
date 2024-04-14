package co.edu.uniquindio.MVC_Banco.modelo;

import lombok.Getter;
import lombok.Setter;

public class Sesion {

    public static Sesion INSTANCIA;

    @Getter @Setter
    private Usuario usuario;
    @Getter @Setter
    private CuentaAhorros cuentaAhorros;

    private Sesion() {
    }

    public static Sesion getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new Sesion();
        }
        return INSTANCIA;
    }

    public void cerrarSesion() {
        usuario = null;
    }
}