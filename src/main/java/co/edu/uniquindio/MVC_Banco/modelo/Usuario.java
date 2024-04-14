package co.edu.uniquindio.MVC_Banco.modelo;

import lombok.*;

/**
 * Clase que representa un usuario del banco.
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @EqualsAndHashCode.Include
    private final String numeroIdentificacion;
    private final String nombre;
    private final String direccion;
    private final String correoElectronico;
    private final String contrasena;
}