package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

	public void onBtNovoAction() {
		System.out.println("botão");
	}
	
	public void atualizarTelaTabela() {
		
		if(departamentoServico == null) {
			throw new IllegalStateException("O serviço está nulo");
		}
		
		List<Departamento> lista = departamentoServico.encontrarTodos();
		obsLista = FXCollections.observableArrayList(lista);
		tableViewDepartamento.setItems(obsLista);
	}
	
}
