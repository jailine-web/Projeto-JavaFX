package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Vendedor implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private String email;
	private Date nascimento;
	private Double salarioBase;

	private Departamento departamento;

	public Vendedor() {

	}

	public Vendedor(Integer id, String nome, String email, Date nascimento, 
			Double salarioBase, Departamento departamento) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.nascimento = nascimento;
		this.salarioBase = salarioBase;
		this.departamento = departamento;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return nome;
	}

	public void setName(String name) {
		this.nome = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return nascimento;
	}

	public void setBirthDate(Date data) {
		this.nascimento = data;
	}

	public Double getBaseSalary() {
		return salarioBase;
	}

	public void setBaseSalary(Double salarioBase) {
		this.salarioBase = salarioBase;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vendedor other = (Vendedor) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Seller id: " + id + ", nome: " + nome + ", email: " + email + ", birthdate: " + nascimento
				+ ", baseSalary: " + salarioBase + " Departamento: " + departamento;
	}

}
