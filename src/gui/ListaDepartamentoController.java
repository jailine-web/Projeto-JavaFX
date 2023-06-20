package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Departamento;

public class ListaDepartamentoController implements Initializable{

	@FXML
	private Button btNovo;
	
	@FXML
	private TableView<Departamento> tableViewDepartamento;
	
	@FXML
	private TableColumn<Departamento, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Departamento, String> tableColumnNome;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializaNos();
	}
	
	
	private void inicializaNos() {
		//Inicia o comportamento das colunas (padrão do javafx)
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		Stage palco = (Stage) Main.getCenaPrincipal().getWindow();
		tableViewDepartamento.prefHeightProperty().bind(palco.heightProperty());
		
	}

	public void onBtNovoAction() {
		System.out.println("botão");
	}
	
	
	
	
	
}
