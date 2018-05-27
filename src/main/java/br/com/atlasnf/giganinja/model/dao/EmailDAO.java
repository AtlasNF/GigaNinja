package br.com.atlasnf.giganinja.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.atlasnf.giganinja.model.Email;



@Named
@RequestScoped
public class EmailDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private DAO<Email> dao;

	
	@PostConstruct
	void init() {
		this.dao = new DAO<Email>(this.em, Email.class);
	}

	@Inject
	private EntityManager em;

	public void adiciona(Email t) {
		dao.adiciona(t);
	}

	public void atualiza(Email t) {
		dao.atualiza(t);
	}

	public Email buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public boolean equals(Object obj) {
		return dao.equals(obj);
	}

	public int hashCode() {
		return dao.hashCode();
	}

	public void remove(Email t) {
		dao.remove(t);
	}

	public List<Email> listaTodos() {
		return dao.listaTodos();
	}

	public List<Email> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	public String toString() {
		return dao.toString();
	}
}
