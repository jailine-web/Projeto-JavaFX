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
	
	public void saveOrUpdate(Departamento dep) {
		if(dep.getId() == null) {
			dao.inserir(dep);
		}
	}
}
