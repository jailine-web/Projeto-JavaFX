package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.EscutandoMudancaDeDados;
import gui.util.Alertas;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Departamento;
import model.services.DepartamentoServico;

public class ListaDepartamentoController implements Initializable, EscutandoMudancaDeDados {

	private DepartamentoServico departamentoServico;

	@FXML
	private Button btNovo;

	@FXML
	private TableView<Departamento> tableViewDepartamento;

	@FXML
	private TableColumn<Departamento, Integer> tableColumnId;

	@FXML
	private TableColumn<Departamento, String> tableColumnNome;

	@FXML
	private TableColumn<Departamento, Departamento> tableColumnEdicao;

	private ObservableList<Departamento> obsLista;

	public void setDepartamentoServico(DepartamentoServico departamentoServico) {
		this.departamentoServico = departamentoServico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializaNos();
	}

	private void inicializaNos() {

		// Inicia o comportamento das colunas (padrão do javafx)
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

		if (departamentoServico == null) {
			throw new IllegalStateException("O serviço está nulo");
		}

		List<Departamento> lista = departamentoServico.encontrarTodos();
		obsLista = FXCollections.observableArrayList(lista);
		tableViewDepartamento.setItems(obsLista);
		iniciaEdicaoBotoes();
	}

	private void criarFormularioDialogo(Departamento dep, String nomeCompletoDaTela, Stage palco) {

		try {

			FXMLLoader carregar = new FXMLLoader(getClass().getResource(nomeCompletoDaTela));
			Pane pane = carregar.load();

			DepartamentoController controler = carregar.getController();
			controler.setDepartamento(dep);
			controler.setDepartamentoServico(new DepartamentoServico());
			controler.subscreverDadosAlterados(this);
			controler.atualizaDadosFormulários();

			Stage palcoDialogo = new Stage();
			palcoDialogo.setTitle("Entre com os dados do departamento");
			palcoDialogo.setScene(new Scene(pane));
			palcoDialogo.setResizable(false);
			palcoDialogo.initOwner(palco);
			palcoDialogo.initModality(Modality.WINDOW_MODAL);
			palcoDialogo.showAndWait();

		} catch (IOException e) {
			Alertas.showAlert("IO Exceção", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDadosAlterados() {
		atualizarTelaTabela();
	}

	private void iniciaEdicaoBotoes() {

		tableColumnEdicao.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdicao.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
		
			private final Button button = new Button("editar");

			@Override
			protected void updateItem(Departamento obj, boolean empty) {
				super.updateItem(obj, empty);
				
				if (obj == null) {
					setGraphic(null);
					return;
				}
				
				setGraphic(button);
				button.setOnAction(
						evento ->  criarFormularioDialogo(obj, "/gui/DepartamentoFormulario.fxml", Utils.PalcoAtual(evento)));
			}
		});
	}

}
