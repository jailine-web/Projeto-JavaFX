package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbException;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Vendedor;
import model.services.VendedorServico;

public class ListaVendedorController implements Initializable, EscutandoMudancaDeDados {

	private VendedorServico vendedorServico;

	@FXML
	private Button btNovo;

	@FXML
	private TableView<Vendedor> tableViewVendedor;

	@FXML
	private TableColumn<Vendedor, Integer> tableColumnId;

	@FXML
	private TableColumn<Vendedor, String> tableColumnNome;
	
	@FXML
	private TableColumn<Vendedor, String> tableColumnEmail;
	
	@FXML
	private TableColumn<Vendedor, Date> tableColumnNascimento;
	
	@FXML
	private TableColumn<Vendedor, Double> tableColumnSalarioBase;

	@FXML
	private TableColumn<Vendedor, Vendedor> tableColumnEdicao;

	@FXML
	private TableColumn<Vendedor, Vendedor> tableColumnRemove;

	private ObservableList<Vendedor> obsLista;

	public void setVendedorServico(VendedorServico vendedorServico) {
		this.vendedorServico = vendedorServico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializaNos();
	}

	private void inicializaNos() {

		// Inicia o comportamento das colunas (padrão do javafx) e instancia os dados do banco de dados
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnNascimento.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		Utils.formatarColunaDataDaTabela(tableColumnNascimento, "dd/MM/yyyy");
		tableColumnSalarioBase.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		Utils.formatarColunaDoubleDaTabela(tableColumnSalarioBase, 2);

		Stage palco = (Stage) Main.getCenaPrincipal().getWindow();
		tableViewVendedor.prefHeightProperty().bind(palco.heightProperty());

	}

	public void onBtNovoAction(ActionEvent evento) {

		Stage palcoPai = Utils.PalcoAtual(evento);
		Vendedor dep = new Vendedor();
		criarFormularioDialogo(dep, "/gui/VendedorFormulario.fxml", palcoPai);
	}

	public void atualizarTelaTabela() {

		if (vendedorServico == null) {
			throw new IllegalStateException("O serviço está nulo");
		}

		List<Vendedor> lista = vendedorServico.encontrarTodos();
		obsLista = FXCollections.observableArrayList(lista);
		tableViewVendedor.setItems(obsLista);
		iniciaEdicaoBotoes();
		BotaoIniciaRemocao();

	}

	private void criarFormularioDialogo(Vendedor ven, String nomeCompletoDaTela, Stage palco) {
//
		try {

			FXMLLoader carregar = new FXMLLoader(getClass().getResource(nomeCompletoDaTela));
			Pane pane = carregar.load();

			VendedorController controler = carregar.getController();
			controler.setVendedor(ven);
			controler.setVendedorServico(new VendedorServico());
			controler.subscreverDadosAlterados(this);
			controler.atualizaDadosFormulários();

			Stage palcoDialogo = new Stage();
			palcoDialogo.setTitle("Entre com os dados do vendedor");
			palcoDialogo.setScene(new Scene(pane));
			palcoDialogo.setResizable(false);
			palcoDialogo.initOwner(palco);
			palcoDialogo.initModality(Modality.WINDOW_MODAL);
			palcoDialogo.showAndWait();

		} catch (IOException e) {
			Alertas.mostrarAlerta("IO Exceção", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDadosAlterados() {
		atualizarTelaTabela();
	}

	private void iniciaEdicaoBotoes() {

		tableColumnEdicao.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdicao.setCellFactory(param -> new TableCell<Vendedor, Vendedor>() {

			private final Button button = new Button("editar");

			@Override
			protected void updateItem(Vendedor obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);

				button.setOnAction(evento -> criarFormularioDialogo(obj, "/gui/VendedorFormulario.fxml",
						Utils.PalcoAtual(evento)));

			}

		});

	}

	private void BotaoIniciaRemocao() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<Vendedor, Vendedor>() {
			private final Button button = new Button("excluir");

			@Override
			protected void updateItem(Vendedor obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeVendedor(obj));
			}
		});
	}

	private void removeVendedor(Vendedor obj) {
		Optional<ButtonType> resultado = Alertas.mostrarConfirmacao("Confirmação",
				"Tem certeza que você quer deletar esse vendedor? ");

		if (resultado.get() == ButtonType.OK) {

			if (resultado.get() == null) {
				throw new IllegalStateException("Serviço estava nulo");
			}
			try {
				
				vendedorServico.deletar(obj);
				atualizarTelaTabela();
			}
			catch(DbException e) {
				Alertas.mostrarAlerta("Erro ao remover o vendedor", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

}
