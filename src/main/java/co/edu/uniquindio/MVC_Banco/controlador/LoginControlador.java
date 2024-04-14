package co.edu.uniquindio.MVC_Banco.controlador;

import co.edu.uniquindio.MVC_Banco.modelo.Banco;
import co.edu.uniquindio.MVC_Banco.modelo.Sesion;
import co.edu.uniquindio.MVC_Banco.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Clase que representa el controlador de la vista de login.
 */
public class LoginControlador {

    @FXML
    private TextField txtIdentificacion;
    @FXML
    private PasswordField txtPassword;
    private final Banco banco = Banco.getInstancia();
    private final Sesion sesion = Sesion.getInstancia();

    /**
     * Método que se ejecuta al presionar el botón de iniciar sesión.
     * @param actionEvent evento de acción.
     */
    public void iniciarSesion(ActionEvent actionEvent) {

        try {
            // Se intenta validar el usuario al banco
            Usuario usuario = banco.validarUsuario(txtIdentificacion.getText(), txtPassword.getText() );
            sesion.setUsuario(usuario);

            // Se muestra un mensaje de éxito y se cierra la ventana
            crearAlerta("El usuario ha iniciado sesión correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();

            navegarVentana("/panelCliente.fxml", "Banco - Panel principal", usuario);

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

    /**
     * Método que permite ir a la venana indicada por el nombre del archivo FXML.
     * @param nombreArchivoFxml Nombre del archivo FXML.
     * @param tituloVentana Título de la ventana.
     */
    public void navegarVentana(String nombreArchivoFxml, String tituloVentana, Usuario usuario) {
        try {

            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            PanelClienteControlador controlador = loader.getController();
            controlador.inicializarValores(usuario);

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
}