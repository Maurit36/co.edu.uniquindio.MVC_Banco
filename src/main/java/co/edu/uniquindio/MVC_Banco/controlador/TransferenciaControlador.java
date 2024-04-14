package co.edu.uniquindio.MVC_Banco.controlador;

import co.edu.uniquindio.MVC_Banco.controlador.observador.Observable;
import co.edu.uniquindio.MVC_Banco.modelo.Banco;
import co.edu.uniquindio.MVC_Banco.modelo.Sesion;
import co.edu.uniquindio.MVC_Banco.modelo.enums.CategoriaTransaccion;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de controlar la creación de transferencias entre cuentas.
 */
public class TransferenciaControlador implements Initializable {
    @FXML
    private TextField txtCuenta;
    @FXML
    private TextField txtMonto;
    @FXML
    private ComboBox<CategoriaTransaccion> txtCategoria;
    private final Banco banco = Banco.getInstancia();
    private final Sesion sesion = Sesion.getInstancia();
    private Observable observable;
    private CategoriaTransaccion categoriaTransaccion;

    public void transferir(ActionEvent actionEvent){
        try{
            String numeroCuenta = txtCuenta.getText();
            float monto = Float.parseFloat(txtMonto.getText());
            CategoriaTransaccion categoria = txtCategoria.getValue();
            String numeroCuentaOrigen = sesion.getCuentaAhorros().getNumeroCuenta();

            if(numeroCuenta.isEmpty() || monto <= 0 || categoria == null){
                crearAlerta("Todos los campos son obligatorios", Alert.AlertType.ERROR);
            }else{
                banco.realizarTransferencia(numeroCuentaOrigen, numeroCuenta, monto, categoria);
                observable.notificar();
                cerrarVentana();
            }
        }catch (Exception e){
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtCategoria.setItems(FXCollections.observableArrayList(CategoriaTransaccion.values()));
    }

    public void seleccionar(ActionEvent event) {
        CategoriaTransaccion  categoriaTransaccion = txtCategoria.getSelectionModel().getSelectedItem();
        this.categoriaTransaccion = categoriaTransaccion;
    }

    /**
     * Método que se encarga de mostrar una alerta en pantalla.
     * @param mensaje mensaje a mostrar.
     * @param tipo tipo de alerta.
     */
    public void crearAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Método que se encarga de cerrar la ventana actual.
     */
    public void cerrarVentana(){
        Stage stage = (Stage) txtCuenta.getScene().getWindow();
        stage.close();
    }

    public void inicializarObservable(Observable observable){
        this.observable = observable;
    }
}