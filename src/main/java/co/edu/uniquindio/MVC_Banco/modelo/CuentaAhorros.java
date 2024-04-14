package co.edu.uniquindio.MVC_Banco.modelo;

import co.edu.uniquindio.MVC_Banco.modelo.enums.TipoTransaccion;
import co.edu.uniquindio.MVC_Banco.modelo.enums.CategoriaTransaccion;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una cuenta de ahorros y sus operaciones.
 */
@Getter
@ToString
public class CuentaAhorros {

    private final String numeroCuenta;
    private final Usuario propietario;
    private float saldo;
    private final List<Transaccion> transacciones;
    public static CuentaAhorros INSTANCIA;

    private CuentaAhorros(String numeroCuenta, Usuario propietario, float saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.propietario = propietario;
        this.saldo = saldoInicial;
        this.transacciones = new ArrayList<>();
    }

    /**
     * Método que realiza un depósito en la cuenta de ahorros y registra la transacción.
     * @param cantidad cantidad a retirar.
     * @param emisor usuario que realiza el retiro.
     * @param categoria categoría de la transacción.
     */
    private void depositar(float cantidad, Usuario emisor, CategoriaTransaccion categoria) {
        // Se realiza el depósito
        saldo += cantidad;

        // Se crea la transacción de depósito
        Transaccion transaccion = Transaccion.builder()
                .tipo(TipoTransaccion.DEPOSITO)
                .monto(cantidad)
                .usuario(emisor)
                .fecha(LocalDateTime.now())
                .categoria(categoria)
                .build();

        // Se registra la transacción de depósito
        transacciones.add(transaccion);
    }

    /**
     * Método que realiza un retiro en la cuenta de ahorros y registra la transacción.
     * @param cantidad cantidad a retirar.
     * @param cuentaDestino cuenta de destino de la transferencia.
     * @param categoria categoría de la transacción.
     */
    public void transferir(float cantidad, CuentaAhorros cuentaDestino, CategoriaTransaccion categoria) throws Exception{

        // Se cobra 200 por cada transferencia
        cantidad += 200;

        // Se valida que el saldo sea suficiente
        if (saldo >= cantidad) {

            // Se realiza el retiro
            saldo -= cantidad;

            // Se registra la transacción de depósito en la cuenta de destino
            cuentaDestino.depositar(cantidad, propietario, categoria);

            // Se crea la transacción de retiro
            Transaccion transaccion = Transaccion.builder()
                    .tipo(TipoTransaccion.RETIRO)
                    .monto(cantidad)
                    .usuario(cuentaDestino.getPropietario())
                    .fecha(LocalDateTime.now())
                    .categoria(categoria)
                    .build();

            // Se registra la transacción de retiro en la cuenta de origen
            transacciones.add( transaccion );

        } else {
            throw new Exception("Saldo insuficiente");
        }
    }

    /**
     * Método que obtiene las transacciones de un mes y año específico de la cuenta de ahorros.
     * @param mes mes de las transacciones.
     * @param anio año de las transacciones.
     * @return lista de transacciones del mes y año.
     */
    public List<Transaccion> obtenerTransaccionesPeriodo(int mes, int anio) {

        List<Transaccion> transaccionesMes = new ArrayList<>();

        for (int i = 0; i < transacciones.size(); i++) {
            if(transacciones.get(i).getFecha().getMonthValue() == mes && transacciones.get(i).getFecha().getYear()
                    == anio){
                transaccionesMes.add(transacciones.get(i));
            }
        }
        return transaccionesMes;
    }
}