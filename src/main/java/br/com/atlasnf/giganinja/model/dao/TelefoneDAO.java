package br.com.atlasnf.giganinja.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.atlasnf.giganinja.model.Telefone;

@Named
@RequestScoped
public class TelefoneDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Telefone> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Telefone>(this.em, Telefone.class);
	}

	@Inject
	private EntityManager em;

	public void adiciona(Telefone t) {
		dao.adiciona(t);
	}

	public void atualiza(Telefone t) {
		dao.atualiza(t);
	}

	public Telefone buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public boolean equals(Object obj) {
		return dao.equals(obj);
	}

	public int hashCode() {
		return dao.hashCode();
	}

	public void remove(Telefone t) {
		dao.remove(t);
	}

	public List<Telefone> listaTodos() {
		return dao.listaTodos();
	}

	public List<Telefone> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	public String toString() {
		return dao.toString();
	}
}