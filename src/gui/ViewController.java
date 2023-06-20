package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class ViewController implements Initializable {

	@FXML
	private MenuItem menuItemVendedor;

	@FXML
	private MenuItem menuItemDepartamento;

	@FXML
	private MenuItem menuItemSobre;

	@FXML
	public void onMenuItemVendedorAction() {
		System.out.println("Vendedor");
	}

	@FXML
	public void onMenuItemDepartamentoAction() {
		carregarOutraTela2("/gui/ListaDeDepartamento.fxml");
		
	}

	@FXML
	public void onMenuItemSobreAction() {
		carregarOutraTela("/gui/ViewSobre.fxml");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {

	}

	private synchronized void carregarOutraTela(String nomeCompletoDaTela) {
		
		try {
			FXMLLoader carregar = new FXMLLoader(getClass().getResource(nomeCompletoDaTela));
			VBox vboxNovo = carregar.load();

			Scene cenaPrin = Main.getCenaPrincipal();
			VBox principalVBox = (VBox) ((ScrollPane) cenaPrin.getRoot()).getContent();

			Node menuPrincipal = principalVBox.getChildren().get(0);
			principalVBox.getChildren().clear();
			principalVBox.getChildren().add(menuPrincipal);
			principalVBox.getChildren().addAll(vboxNovo.getChildren());

		} 
		catch (IOException e) {
			Alertas.showAlert("IO Exception", "Erro ao carregar a página", e.getMessage(), AlertType.ERROR);
		}
	}
	
	private synchronized void carregarOutraTela2(String nomeCompletoDaTela) {
		
		try {
			FXMLLoader carregar = new FXMLLoader(getClass().getResource(nomeCompletoDaTela));
			VBox vboxNovo = carregar.load();
			
			Scene cenaPrin = Main.getCenaPrincipal();
			VBox principalVBox = (VBox) ((ScrollPane) cenaPrin.getRoot()).getContent();
			
			Node menuPrincipal = principalVBox.getChildren().get(0);
			principalVBox.getChildren().clear();
			principalVBox.getChildren().add(menuPrincipal);
			principalVBox.getChildren().addAll(vboxNovo.getChildren());
			
			ListaDepartamentoController controle = carregar.getController();
			controle.setDepartamentoServico(new DepartamentoServico());
			controle.atualizarTelaTabela();
			
		} 
		catch (IOException e) {
			//Alertas.showAlert("IO Exception", "Erro ao carregar a página", 
			//e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}
	}

}
