package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartamentoDao;
import model.entities.Departamento;

public class DepartamentoServico {

	DepartamentoDao dao = DaoFactory.criarDepartamentoDAO();
	
	public List<Departamento> encontrarTodos(){
		return dao.buscarTodos();
	}
	
	public void salvarOuAtualizar(Departamento dep) {
		if(dep.getId() == null) {
			dao.inserir(dep);
		}
		else {
			dao.atualizar(dep);
		}
	}
	
	public void deletar(Departamento dep) {
		dao.excluir(dep.getId());
	}
}
