package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

	public static Stage PalcoAtual(ActionEvent evento) {
		return (Stage) ((Node) evento.getSource()).getScene().getWindow();
		
	}
	
	//Facilitar o processo
	public static Integer tentarCoverterParaInt(String str) {
		try {
			
			return Integer.parseInt(str);
		}
		catch(NumberFormatException e) {
			return null;
		}
		
	}
}