package co.edu.uniquindio.MVC_Banco.controlador;

import co.edu.uniquindio.MVC_Banco.controlador.observador.Observable;
import co.edu.uniquindio.MVC_Banco.modelo.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de gestionar las acciones de la interfaz gráfica del panel del cliente.
 * @author caflorezvi
 */

public class PanelClienteControlador implements Initializable, Observable {
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelCuenta;
    @FXML
    private TableView<Transaccion> tablaTransacciones;
    @FXML
    private TableColumn <Transaccion, String> tipoTr;
    @FXML
    private TableColumn <Transaccion, String> fechaTr;
    @FXML
    private TableColumn <Transaccion, String> valorTr;
    @FXML
    private TableColumn <Transaccion, String> usuarioTr;
    @FXML
    private TableColumn <Transaccion, String> categoriaTr;
    private final Banco banco = Banco.getInstancia();
    private final Sesion sesion = Sesion.getInstancia();
    private CuentaAhorros cuenta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario usuario = sesion.getUsuario();
        inicializarValores(usuario);

        tipoTr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo().toString()));
        fechaTr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        valorTr.setCellValueFactory(cellData -> new SimpleStringProperty("" + cellData.getValue().getMonto()));
        usuarioTr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getNombre()));
        categoriaTr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().toString()));
    }

    public void inicializarValores(Usuario usuario){
        try {
            if(usuario != null){
                cuenta = banco.consultarCuenta(usuario.getNumeroIdentificacion());
                sesion.setCuenta(cuenta);

                labelNombre.setText(usuario.getNombre());
                labelCuenta.setText(cuenta.getNumeroCuenta());
                consultarTransacciones();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void consultarTransacciones() {
        tablaTransacciones.setItems(FXCollections.observableArrayList(cuenta.getTransacciones()));
    }

    public void cerrarSesion(ActionEvent event) throws Exception {
        crearAlerta("Se ha cerrado la sesión correctamente", Alert.AlertType.INFORMATION);
        Stage stage = (Stage) tablaTransacciones.getScene().getWindow();
        sesion.cerrarSesion();
        stage.close();

        navegarVentana("/login.fxml", "Banco - Iniciar Sesión");
    }

    public void irTransferencia(ActionEvent event) throws Exception {
        FXMLLoader loader = navegarVentana("/transferencia.fxml", "Banco - Transferencia");

        TransferenciaControlador controlador = loader.getController();
        controlador.inicializarObservable(this);
    }

    public void consultarSaldo(ActionEvent event){
        String saldo = banco.consultarSaldo(sesion.getUsuario().getNumeroIdentificacion());
        crearAlerta("El saldo actual de su cuenta es de: " + saldo, Alert.AlertType.INFORMATION);
    }

    private void crearAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private FXMLLoader navegarVentana(String nombreArchivoFxml, String tituloVentana) throws Exception{
        // Cargar la vista
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
        Parent root = loader.load();

        // Crear la escena
        Scene scene = new Scene(root);

        // Crear un nuevo escenario (ventana)
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(tituloVentana);

        // Mostrar la nueva ventana
        stage.show();

        return loader;
    }

    @Override
    public void notificar() {
        consultarTransacciones();
    }

    public void actualizarDatos(ActionEvent actionEvent) throws Exception{
        navegarVentana("/actualizar.fxml", "Banco - Actualizar Cliente");
    }
}