package model.dao;

import java.util.List;

import model.entities.Departamento;

public interface DepartamentoDao {

	void insert(Departamento departamento);
	void update(Departamento departamento);
	void delete(Integer id);
	Departamento findById(Integer id);
	List<Departamento> findAll();
}
