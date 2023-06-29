package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Vendedor;
import model.exceptions.ValidacaoExcecao;
import model.services.VendedorServico;

public class VendedorController implements Initializable{

	private Vendedor vendedor;
	
	private VendedorServico servico;
	
	private List<EscutandoMudancaDeDados> dadosAlterados = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private DatePicker dpNascimento;
	
	@FXML
	private TextField txtSalarioBase;
	
	@FXML
	private Label labelNomeErro;
	
	@FXML
	private Label labelEmailErro;
	
	@FXML
	private Label labelNascimentoErro;
	
	@FXML
	private Label labelSalarioBaseErro;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}
	
	public void setVendedorServico(VendedorServico servico) {
		this.servico = servico;
	}
	
	public void subscreverDadosAlterados(EscutandoMudancaDeDados e) {
		dadosAlterados.add(e);
	}
	
	public void onBtSaveAction(ActionEvent evento) {
		
		if(vendedor == null) {
			throw new IllegalStateException("O vendedor estava nulo");
		}
		if(servico == null) {
			throw new IllegalStateException("O serviço estava nulo");
		}
		try {
			
			vendedor = getDadosFormulario();
			servico.salvarOuAtualizar(vendedor);
			notificacaoDeDadosAtualizados();
			Utils.PalcoAtual(evento).close();
		}
		catch(DbException e) {
			Alertas.mostrarAlerta("Erro ao salvar o objeto", null, e.getMessage(), AlertType.ERROR);
		}
		catch(ValidacaoExcecao e) {
			setMensagemDeErro(e.getErros());
		}
		
	}
	
	private void notificacaoDeDadosAtualizados() {
		
		for(EscutandoMudancaDeDados e: dadosAlterados) {
			e.onDadosAlterados();
		}
	}

	private Vendedor getDadosFormulario() {
		
		Vendedor ven = new Vendedor();
		
		ValidacaoExcecao excecao = new ValidacaoExcecao("Erro de validação");
		
		ven.setId(Utils.tentarCoverterParaInt(txtId.getText()));
		
		if(txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			excecao.addErros("nome", "O campo: nome não pode ser vazio");
		}
		
		ven.setName(txtNome.getText());
		
		if(excecao.getErros().size()> 0) {
			throw excecao;
		}
		
		return ven;
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
		Limitacoes.setTextFieldMaxLength(txtNome, 70);
		Limitacoes.setTextFieldMaxLength(txtEmail, 60);
		Limitacoes.setTextFieldDouble(txtSalarioBase);
		Utils.formatDatePicker(dpNascimento, "dd/MM/yyyy");
	}
	
	public void atualizaDadosFormulários() {
		if(vendedor == null) {
			throw new IllegalStateException("O vendedor estava nulo");
		}
		txtId.setText(String.valueOf(vendedor.getId()));
		txtNome.setText(vendedor.getName());
		txtEmail.setText(vendedor.getEmail());
		Locale.setDefault(Locale.US);
		txtSalarioBase.setText(String.format("%.2f", vendedor.getBaseSalary()));
		if(vendedor.getBirthDate() != null) {
			dpNascimento.setValue(LocalDate.ofInstant(vendedor.getBirthDate().toInstant(), ZoneId.systemDefault()) );
			
		}
	}
	
	private void setMensagemDeErro (Map<String,String> erros) {
		
		Set<String> campos = erros.keySet();
		
		if(campos.contains("nome")) {
			labelNomeErro.setText(erros.get("nome"));
		}
	}
}
