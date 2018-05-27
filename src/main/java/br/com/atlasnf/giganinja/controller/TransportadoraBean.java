package br.com.atlasnf.giganinja.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.atlasnf.giganinja.model.Transportadora;
import br.com.atlasnf.giganinja.model.dao.TransportadoraDAO;
import br.com.atlasnf.giganinja.tx.Transacional;

@Named
@ViewScoped
public class TransportadoraBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	TransportadoraDAO transportadoraDAO;
	private Transportadora transportadora;
	private Transportadora selTransportadora;
	private List<Transportadora> allTransportadoras;

	@PostConstruct
	@Transacional
	public void init() {
		transportadora = new Transportadora();
		allTransportadoras = transportadoraDAO.listaTodos();
	}

	public Transportadora getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}
	

	public Transportadora getSelTransportadora() {
		return selTransportadora;
	}

	public void setSelTransportadora(Transportadora selTransportadora) {
		this.selTransportadora = selTransportadora;
	}

	
	
	public List<Transportadora> getAllTransportadoras() {
		return allTransportadoras;
	}

	public void setAllTransportadoras(List<Transportadora> allTransportadoras) {
		this.allTransportadoras = allTransportadoras;
	}

	@Transacional
	public void adicionarTransportadora() {
		transportadoraDAO.adiciona(transportadora);
		transportadora = new Transportadora();
		enviarMessage("Transportadora cadastrada com sucesso!", "Sucesso", FacesMessage.SEVERITY_INFO);
	}
	
	@SuppressWarnings("deprecation")
	@Transacional
	public void editarTransportadora() {
		transportadoraDAO.atualiza(selTransportadora);
		enviarMessage("Transportadora editada com sucesso!", "Sucesso", FacesMessage.SEVERITY_INFO);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('transportadoraDialog').hide();");
		
	}
	@SuppressWarnings("deprecation")
	@Transacional
	public void deletarTransportadora() {
		selTransportadora = transportadoraDAO.buscaPorId(selTransportadora.getId());
		if(selTransportadora.getPedidos().isEmpty()) {
			transportadoraDAO.remove(selTransportadora);
			enviarMessage("Transportadora excluía com sucesso!", "Sucesso", FacesMessage.SEVERITY_INFO);
			allTransportadoras = transportadoraDAO.listaTodos();
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('transportadoraDialog').hide();");
			context.update("transportadorasDT");
		}else {
			enviarMessage("Transportadora não pode ser excluída pois ainda possui pedidos associados!", "Erro", FacesMessage.SEVERITY_FATAL);
		}
		
	}

	public void enviarMessage(String mensagem, String header, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, header, mensagem));
	}
}
