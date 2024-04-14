package co.edu.uniquindio.MVC_Banco.controlador;

import co.edu.uniquindio.MVC_Banco.modelo.Banco;
import co.edu.uniquindio.MVC_Banco.modelo.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class ActualizarControlador implements Initializable {
    @FXML
    private TextField txtIdentificacion;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtDireccion;
    @FXML
    private PasswordField txtPassword;
    private final Banco banco = Banco.getInstancia();
    private final Sesion sesion = Sesion.getInstancia();

    public void actualizarUsuario(ActionEvent actionEvent) {
        try {
            banco.actualizarUsuario(txtNombre.getText(), txtDireccion.getText(), sesion.getUsuario().getNumeroIdentificacion(), txtCorreo.getText(), txtPassword.getText());
            // Se muestra un mensaje de éxito y se cierra la ventana
            crearAlerta("Los datos del usuario han sido actualizados correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();
        }catch (Exception e){
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String nombre = sesion.getUsuario().getNombre();
        txtNombre.setText(nombre);
        String numeroIdentificacion = sesion.getUsuario().getNumeroIdentificacion();
        txtIdentificacion.setText(numeroIdentificacion);
        String correo = sesion.getUsuario().getCorreoElectronico();
        txtCorreo.setText(correo);
        String password = sesion.getUsuario().getContrasena();
        txtPassword.setText(password);
        String direccion = sesion.getUsuario().getDireccion();
        txtDireccion.setText(direccion);
    }

    public void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
        try {
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

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void crearAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void cerrarVentana(){
        Stage stage = (Stage) txtIdentificacion.getScene().getWindow();
        stage.close();
        navegarVentana("/panelCliente.fxml", "Banco - Panel principal");
    }
}