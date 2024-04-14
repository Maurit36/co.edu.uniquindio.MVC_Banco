package co.edu.uniquindio.MVC_Banco.controlador;

import co.edu.uniquindio.MVC_Banco.modelo.Banco;
import co.edu.uniquindio.MVC_Banco.modelo.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ActualizarControlador{

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


            banco.actualizarUsuario(txtNombre.getText(), txtDireccion.getText(), txtIdentificacion.getText(), txtCorreo.getText(), txtPassword.getText());

            // Se muestra un mensaje de éxito y se cierra la ventana
            crearAlerta("Los datos del usuario han sido actualizados correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();

            navegarVentana("/panelCliente.fxml", "Banco - Panel principal");

        }catch (Exception e){
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
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
}