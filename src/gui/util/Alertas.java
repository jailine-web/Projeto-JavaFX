package gui.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alertas {

	public static void showAlert(String titulo, String cabecalho, String conteudo, AlertType tipo) {

		Alert alerta = new Alert(tipo);
		alerta.setTitle(titulo);
		alerta.setHeaderText(cabecalho);
		alerta.setContentText(conteudo);
		alerta.show();

	}

	public static Optional<ButtonType> showConfirmation(String title, String content) {
		
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle(title);
		alerta.setHeaderText(null);
		alerta.setContentText(content);
		return alerta.showAndWait();
	}
}
