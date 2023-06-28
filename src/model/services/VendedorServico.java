package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Vendedor;

public class VendedorServico {

	VendedorDao dao = DaoFactory.criarVendedorDao();
	
	public List<Vendedor> encontrarTodos(){
		return dao.buscarTodos();
	}
	
	public void salvarOuAtualizar(Vendedor ven) {
		if(ven.getId() == null) {
			dao.inserir(ven);
		}
		else {
			dao.atualizar(ven);
		}
	}
	
	public void deletar(Vendedor ven) {
		dao.excluir(ven.getId());
	}
}
