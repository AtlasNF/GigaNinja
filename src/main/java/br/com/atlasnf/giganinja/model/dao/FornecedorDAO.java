package br.com.atlasnf.giganinja.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.atlasnf.giganinja.model.Fornecedor;


@Named
@RequestScoped
public class FornecedorDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DAO<Fornecedor> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Fornecedor>(this.em, Fornecedor.class);
	}

	@Inject
	private EntityManager em;

	public void adiciona(Fornecedor t) {
		dao.adiciona(t);
	}

	public void atualiza(Fornecedor t) {
		dao.atualiza(t);
	}

	public Fornecedor buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public boolean equals(Object obj) {
		return dao.equals(obj);
	}

	public int hashCode() {
		return dao.hashCode();
	}

	public void remove(Fornecedor t) {
		dao.remove(t);
	}

	public List<Fornecedor> listaTodos() {
		return dao.listaTodos();
	}

	public List<Fornecedor> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	public String toString() {
		return dao.toString();
	}
}
