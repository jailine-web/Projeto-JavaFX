package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidacaoExcecao extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	//1° String representa o nome do campo na tela de formulário e o 2° indica a mensagem de erro
	private Map<String, String> erros = new HashMap<>();
	
	public ValidacaoExcecao(String msg) {
		super(msg);
	}
	
	public  Map<String, String> getErros(){
		return erros;
	}
	
	public void addErros (String nomeCampo, String mensagemDoErro) {
		erros.put(nomeCampo, mensagemDoErro);
	}
}
