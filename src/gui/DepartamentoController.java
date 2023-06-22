package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Limitacoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Departamento;

public class DepartamentoController implements Initializable{

	private Departamento departamento;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Label labelNomeErro;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
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
		
		Limitacoes.setTextFieldInteger(txtId);
		Limitacoes.setTextFieldMaxLength(txtNome, 40);
	}
	
	public void atualizaDadosFormulários() {
		if(departamento == null) {
			throw new IllegalStateException("O departamento estava nulo");
		}
		txtId.setText(String.valueOf(departamento.getId()));
		txtNome.setText(departamento.getNome());
	}
}
