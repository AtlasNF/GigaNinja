package br.com.atlasnf.giganinja.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.atlasnf.giganinja.model.Transportadora;

@Named
@RequestScoped
public class TransportadoraDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Transportadora> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Transportadora>(this.em, Transportadora.class);
	}

	@Inject
	private EntityManager em;

	public void adiciona(Transportadora t) {
		dao.adiciona(t);
	}

	public void atualiza(Transportadora t) {
		dao.atualiza(t);
	}

	public Transportadora buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public boolean equals(Object obj) {
		return dao.equals(obj);
	}

	public int hashCode() {
		return dao.hashCode();
	}

	public void remove(Transportadora t) {
		dao.remove(t);
	}

	public List<Transportadora> listaTodos() {
		return dao.listaTodos();
	}

	public List<Transportadora> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	public String toString() {
		return dao.toString();
	}
}