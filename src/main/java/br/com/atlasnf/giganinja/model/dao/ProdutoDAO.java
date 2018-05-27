package br.com.atlasnf.giganinja.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.atlasnf.giganinja.model.Produto;

@Named
@RequestScoped
public class ProdutoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Produto> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Produto>(this.em, Produto.class);
	}

	@Inject
	private EntityManager em;

	public void adiciona(Produto t) {
		dao.adiciona(t);
	}

	public void atualiza(Produto t) {
		dao.atualiza(t);
	}

	public Produto buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public boolean equals(Object obj) {
		return dao.equals(obj);
	}

	public int hashCode() {
		return dao.hashCode();
	}

	public void remove(Produto t) {
		dao.remove(t);
	}

	public List<Produto> listaTodos() {
		return dao.listaTodos();
	}

	public List<Produto> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	public String toString() {
		return dao.toString();
	}
}
