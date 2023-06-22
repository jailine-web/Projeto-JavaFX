package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Limitacoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartamentoController implements Initializable{

	@FXML
	private TextField txtID;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Label labelNomeErro;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	public void onBtSaveAction() {
		System.out.println("Departamento salvo com sucesso");
	}
	
	public void onBtCancelarAction() {
		System.out.println("Voltando");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializaNos();
	}

	private void inicializaNos() {
		
		Limitacoes.setTextFieldInteger(txtID);
		Limitacoes.setTextFieldMaxLength(txtNome, 40);
	}
}
