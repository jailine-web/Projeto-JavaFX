package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartamentoDao;
import model.entities.Departamento;

public class DepartamentoServico {

	public List<Departamento> encontrarTodos(){
		
		DepartamentoDao dao = DaoFactory.criarDepartamentoDAO();
		
		//List<Departamento> lista = new ArrayList<>();
		//lista.add(new Departamento(1, "Livros"));
		//lista.add(new Departamento(2, "Computadores"));
		//lista.add(new Departamento(3, "eletronicos"));
		
		
		return dao.findAll();
	}
}
