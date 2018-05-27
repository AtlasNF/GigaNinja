package br.com.atlasnf.giganinja.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.atlasnf.giganinja.model.Pedido;


@Named
@RequestScoped
public class PedidoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Pedido> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Pedido>(this.em, Pedido.class);
	}

	@Inject
	private EntityManager em;

	public void adiciona(Pedido t) {
		dao.adiciona(t);
	}

	public void atualiza(Pedido t) {
		dao.atualiza(t);
	}

	public Pedido buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public boolean equals(Object obj) {
		return dao.equals(obj);
	}

	public int hashCode() {
		return dao.hashCode();
	}

	public void remove(Pedido t) {
		dao.remove(t);
	}

	public List<Pedido> listaTodos() {
		return dao.listaTodos();
	}

	public List<Pedido> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	public String toString() {
		return dao.toString();
	}
}
