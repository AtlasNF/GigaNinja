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

import br.com.atlasnf.giganinja.model.Pedido;
import br.com.atlasnf.giganinja.model.Produto;
import br.com.atlasnf.giganinja.model.Transportadora;
import br.com.atlasnf.giganinja.model.dao.ItemDAO;
import br.com.atlasnf.giganinja.model.dao.PedidoDAO;
import br.com.atlasnf.giganinja.model.dao.ProdutoDAO;
import br.com.atlasnf.giganinja.model.dao.TransportadoraDAO;
import br.com.atlasnf.giganinja.tx.Transacional;

@Named
@ViewScoped
public class PedidoVisualizarBean implements Serializable{

	private static final long serialVersionUID = 1L;
	

	@Inject
	PedidoDAO pedidoDAO;
	@Inject
	ItemDAO itemDAO;
	@Inject
	TransportadoraDAO transportadoraDAO;
	@Inject
	ProdutoDAO produtoDAO;
	private List<Pedido> pedidos;
	private Pedido selPedido;
	
	@PostConstruct
	@Transacional
	public void init() {
	 pedidos = pedidoDAO.listaTodos();
	}
	
	@Transacional
	public void deletePedido() {
		System.out.println(selPedido.getId().toString());
		selPedido = pedidoDAO.buscaPorId(selPedido.getId());
		for(int i = 0; i < selPedido.getItens().size(); i++) {
			Produto auxProd = produtoDAO.buscaPorId(selPedido.getItens().get(i).getProduto().getId());
			auxProd.getItens().remove(selPedido.getItens().get(i));
			produtoDAO.atualiza(auxProd);
			itemDAO.remove(selPedido.getItens().get(i));
		}
		Transportadora auxTrans = transportadoraDAO.buscaPorId(selPedido.getTransportadora().getId());
		auxTrans.getPedidos().remove(selPedido);
		transportadoraDAO.atualiza(auxTrans);
		pedidoDAO.remove(selPedido);
		pedidos = pedidoDAO.listaTodos();
		enviarMessage("Pedido deletado com sucesso!", "Sucesso", FacesMessage.SEVERITY_INFO);
	}
	

	public void enviarMessage(String mensagem, String header, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, header, mensagem));
	}
	
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Pedido getSelPedido() {
		return selPedido;
	}

	public void setSelPedido(Pedido selPedido) {
		this.selPedido = selPedido;
	}
	public void  rowSelect() {
		enviarMessage(selPedido.getId().toString(), "Teste", FacesMessage.SEVERITY_INFO);
	}
	
}
