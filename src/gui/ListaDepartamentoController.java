package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alertas;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Departamento;
import model.services.DepartamentoServico;

public class ListaDepartamentoController implements Initializable{

	private DepartamentoServico departamentoServico;
	
	@FXML
	private Button btNovo;
	
	@FXML
	private TableView<Departamento> tableViewDepartamento;
	
	@FXML
	private TableColumn<Departamento, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Departamento, String> tableColumnNome;
	
	private ObservableList<Departamento> obsLista;
	
	public void setDepartamentoServico(DepartamentoServico departamentoServico) {
		this.departamentoServico = departamentoServico;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializaNos();
	}
	
	private void inicializaNos() {
		
		//Inicia o comportamento das colunas (padrão do javafx)
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		
		Stage palco = (Stage) Main.getCenaPrincipal().getWindow();
		tableViewDepartamento.prefHeightProperty().bind(palco.heightProperty());
		
	}

	public void onBtNovoAction(ActionEvent evento) {
		
		Stage palcoPai = Utils.PalcoAtual(evento);
		Departamento dep = new Departamento();
		criarFormularioDialogo(dep, "/gui/DepartamentoFormulario.fxml", palcoPai);
	}
	
	public void atualizarTelaTabela() {
		
		if(departamentoServico == null) {
			throw new IllegalStateException("O serviço está nulo");
		}
		
		List<Departamento> lista = departamentoServico.encontrarTodos();
		obsLista = FXCollections.observableArrayList(lista);
		tableViewDepartamento.setItems(obsLista);
	}
	
	private void criarFormularioDialogo(Departamento dep, String nomeCompletoDaTela, Stage palco) {
		
		try {
			
			FXMLLoader carregar = new FXMLLoader(getClass().getResource(nomeCompletoDaTela));
			Pane pane = carregar.load();
			
			DepartamentoController controler = carregar.getController();
			controler.setDepartamento(dep);
			controler.atualizaDadosFormulários();
			
			Stage palcoDialogo = new Stage();
			palcoDialogo.setTitle("Entre com os dados do departamento");
			palcoDialogo.setScene(new Scene(pane));
			palcoDialogo.setResizable(false);
			palcoDialogo.initOwner(palco);
			palcoDialogo.initModality(Modality.WINDOW_MODAL);
			palcoDialogo.showAndWait();
			
		}
		catch(IOException e) {
			Alertas.showAlert("IO Exceção", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);
		}
	}
	
}
