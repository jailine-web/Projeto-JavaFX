package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartamentoServico;
import model.services.VendedorServico;

public class ViewController implements Initializable {

	@FXML
	private MenuItem menuItemVendedor;

	@FXML
	private MenuItem menuItemDepartamento;

	@FXML
	private MenuItem menuItemSobre;

	@FXML
	public void onMenuItemVendedorAction() {
		carregarOutraTela("/gui/ListaDeVendedores.fxml",(ListaVendedorController controller) -> {
			controller.setVendedorServico(new VendedorServico());
			controller.atualizarTelaTabela();
		});
	}

	@FXML
	public void onMenuItemDepartamentoAction() {
		carregarOutraTela("/gui/ListaDeDepartamento.fxml",(ListaDepartamentoController controller) -> {
			controller.setDepartamentoServico(new DepartamentoServico());
			controller.atualizarTelaTabela();
		});
		
	}

	@FXML
	public void onMenuItemSobreAction() {
		carregarOutraTela("/gui/ViewSobre.fxml", x -> {});
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {

	}

	private synchronized <T> void carregarOutraTela(String nomeCompletoDaTela, Consumer<T> acaoDeInicializacao) {
		
		try {
			FXMLLoader carregar = new FXMLLoader(getClass().getResource(nomeCompletoDaTela));
			VBox vboxNovo = carregar.load();

			Scene cenaPrin = Main.getCenaPrincipal();
			VBox principalVBox = (VBox) ((ScrollPane) cenaPrin.getRoot()).getContent();

			Node menuPrincipal = principalVBox.getChildren().get(0);
			principalVBox.getChildren().clear();
			principalVBox.getChildren().add(menuPrincipal);
			principalVBox.getChildren().addAll(vboxNovo.getChildren());

			//ativar a função passada e retorna o parâmetro atribuido por parâmetro
			T controller = carregar.getController();
			
			//Executa a função por paramero
			acaoDeInicializacao.accept(controller);
			
		} 
		catch (IOException e) {
			Alertas.mostrarAlerta("IO Exception", "Erro ao carregar a página", e.getMessage(), AlertType.ERROR);
		}
	}

}
