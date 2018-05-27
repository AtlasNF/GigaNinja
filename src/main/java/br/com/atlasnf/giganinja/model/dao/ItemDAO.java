package br.com.atlasnf.giganinja.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.atlasnf.giganinja.model.Item;

@Named
@RequestScoped
public class ItemDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DAO<Item> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Item>(this.em, Item.class);
	}

	@Inject
	private EntityManager em;

	public void adiciona(Item t) {
		dao.adiciona(t);
	}

	public void atualiza(Item t) {
		dao.atualiza(t);
	}

	public Item buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public boolean equals(Object obj) {
		return dao.equals(obj);
	}

	public int hashCode() {
		return dao.hashCode();
	}

	public void remove(Item t) {
		dao.remove(t);
	}

	public List<Item> listaTodos() {
		return dao.listaTodos();
	}

	public List<Item> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	public String toString() {
		return dao.toString();
	}
}
