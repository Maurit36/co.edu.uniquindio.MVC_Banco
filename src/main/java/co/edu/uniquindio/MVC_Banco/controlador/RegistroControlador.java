package co.edu.uniquindio.MVC_Banco.controlador;

import co.edu.uniquindio.MVC_Banco.modelo.Banco;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Clase que representa el controlador de la ventana de registro de usuario.
 */
public class RegistroControlador {

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

    /**
     * Método que se ejecuta al presionar el botón de registrarse.
     * @param actionEvent evento de acción.
     */
    public void registrarse(ActionEvent actionEvent) {

        try {
            // Se intenta agregar el usuario al banco
            banco.agregarUsuario(
                    txtIdentificacion.getText(),
                    txtNombre.getText(),
                    txtCorreo.getText(),
                    txtDireccion.getText(),
                    txtPassword.getText() );

            // Se intenta agregar una cuenta de ahorros al usuario
            banco.agregarCuentaAhorros(txtIdentificacion.getText(), 0F);

            // Se muestra un mensaje de éxito y se cierra la ventana
            crearAlerta("Usuario registrado correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();

        }catch (Exception e){
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
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
        Stage stage = (Stage) txtIdentificacion.getScene().getWindow();
        stage.close();
    }
}