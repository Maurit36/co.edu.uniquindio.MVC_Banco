package co.edu.uniquindio.MVC_Banco.modelo;

import co.edu.uniquindio.MVC_Banco.modelo.enums.TipoTransaccion;
import co.edu.uniquindio.MVC_Banco.modelo.enums.CategoriaTransaccion;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Clase que representa una transacción bancaria.
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Transaccion {

    private final TipoTransaccion tipo; // "Depósito" o "Retiro"
    private final float monto;
    private final Usuario usuario; // Usuario emisor o destinatario de la transacción
    private final LocalDateTime fecha;
    private final CategoriaTransaccion categoria;
}