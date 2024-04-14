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
    private String nombre;
    private String direccion;
    private String correoElectronico;
    private String contrasena;
}