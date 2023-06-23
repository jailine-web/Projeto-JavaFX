package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.EscutandoMudancaDeDados;
import gui.util.Alertas;
import gui.util.Limitacoes;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Departamento;
import model.services.DepartamentoServico;

public class DepartamentoController implements Initializable{

	private Departamento departamento;
	
	private DepartamentoServico servico;
	
	private List<EscutandoMudancaDeDados> dadosAlterados = new ArrayList<>();
	
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
	
	public void setDepartamentoServico(DepartamentoServico servico) {
		this.servico = servico;
	}
	
	public void subscreverDadosAlterados(EscutandoMudancaDeDados e) {
		dadosAlterados.add(e);
	}
	
	public void onBtSaveAction(ActionEvent evento) {
		
		if(departamento == null) {
			throw new IllegalStateException("O departamento estava nulo");
		}
		if(servico == null) {
			throw new IllegalStateException("O serviço estava nulo");
		}
		try {
			
			departamento = getDadosFormulario();
			servico.saveOrUpdate(departamento);
			notificacaoDeDadosAtualizados();
			Utils.PalcoAtual(evento).close();
		}
		catch(DbException e) {
			Alertas.showAlert("Erro ao salvar o objeto", null, e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	private void notificacaoDeDadosAtualizados() {
		
		for(EscutandoMudancaDeDados e: dadosAlterados) {
			e.onDadosAlterados();
		}
	}

	private Departamento getDadosFormulario() {
		Departamento dep = new Departamento();
		
		dep.setId(Utils.tentarCoverterParaInt(txtId.getText()));
		dep.setNome(txtNome.getText());
		
		return dep;
	}

	public void onBtCancelarAction(ActionEvent evento) {
		Utils.PalcoAtual(evento).close();;
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
