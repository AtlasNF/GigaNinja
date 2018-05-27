package br.com.atlasnf.giganinja.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.atlasnf.giganinja.model.Item;
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
public class PedidoCadastroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	ProdutoDAO produtoDAO;
	@Inject
	PedidoDAO pedidoDAO;
	@Inject
	TransportadoraDAO transportadoraDAO;
	@Inject
	ItemDAO itemDAO;

	private List<Transportadora> transportadoras;
	private List<Produto> produtos;
	private List<Transportadora> filtedTransportadoras;
	private List<Produto> filtedProdutos;
	private List<Item> itens;
	private Item selItem;
	private Item item;
	private Produto selProduto;
	private Transportadora selTransportadora;
	private Pedido pedido;

	@PostConstruct
	@Transacional
	public void init() {
		selProduto = new Produto();
		transportadoras = transportadoraDAO.listaTodos();
		produtos = produtoDAO.listaTodos();
		pedido = new Pedido();
		itens = new ArrayList<Item>();
		item = new Item();
	}

	public void deleteItem() {
		pedido.getItens().remove(selItem);
	}

	
	@Transacional
	public void adicionarPedido() {
			if(pedido.getTransportadora() != null) {
				if(!pedido.getItens().isEmpty() || pedido.getItens() !=null) {
					pedido.setDatahora(Date.from(java.time.Instant.now()));
					pedidoDAO.adiciona(pedido);
					for(int i = 0; i < pedido.getItens().size(); i++) {
						pedido.getItens().get(i).setPedido(pedido);
						itemDAO.adiciona(pedido.getItens().get(i));
						Produto auxItem = produtoDAO.buscaPorId(pedido.getItens().get(i).getProduto().getId());
						auxItem.getItens().add(pedido.getItens().get(i));
						produtoDAO.atualiza(auxItem);		
					}
					
					Transportadora aux = transportadoraDAO.buscaPorId(pedido.getTransportadora().getId());
					aux.getPedidos().add(pedido);
					transportadoraDAO.atualiza(aux);
					enviarMessage("Pedido efetuado com sucesso!", "Sucesso", FacesMessage.SEVERITY_INFO);
					pedido = new Pedido();
					item  = new Item();
					selProduto = new Produto();
					transportadoras = transportadoraDAO.listaTodos();
					produtos = produtoDAO.listaTodos();
					
				}
			}
	}

	@SuppressWarnings("deprecation")
	public void selecionarTransportadora() {
		enviarMessage("Transportadora selecionado com sucesso!", "Sucesso", FacesMessage.SEVERITY_INFO);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('transportadoraDialog').hide();");
	}

	@SuppressWarnings("deprecation")
	public void addItem() {
		if(item.getProduto() != null) {
		if (pedido.getItens() == null) {
			pedido.setItens(new ArrayList<Item>());
		}
		pedido.getItens().add(item);
		selItem = item;
		item = new Item();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('itemDialog').hide();");
		}else {
			
		}
	}

	@SuppressWarnings("deprecation")
	public void selecionarProduto() {
		enviarMessage("Produto selecionado com sucesso!", "Sucesso", FacesMessage.SEVERITY_INFO);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('produtoDialog').hide();");
	}

	public void enviarMessage(String mensagem, String header, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, header, mensagem));
	}

	public List<Transportadora> getTransportadoras() {
		return transportadoras;
	}

	public void setTransportadoras(List<Transportadora> transportadoras) {
		this.transportadoras = transportadoras;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Item getSelItem() {
		return selItem;
	}

	public void setSelItem(Item selItem) {
		this.selItem = selItem;
	}

	public Produto getSelProduto() {
		return selProduto;
	}

	public void setSelProduto(Produto selProduto) {
		this.selProduto = selProduto;
	}

	public Transportadora getSelTransportadora() {
		return selTransportadora;
	}

	public void setSelTransportadora(Transportadora selTransportadora) {
		this.selTransportadora = selTransportadora;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<Transportadora> getFiltedTransportadoras() {
		return filtedTransportadoras;
	}

	public void setFiltedTransportadoras(List<Transportadora> filtedTransportadoras) {
		this.filtedTransportadoras = filtedTransportadoras;
	}

	public List<Produto> getFiltedProdutos() {
		return filtedProdutos;
	}

	public void setFiltedProdutos(List<Produto> filtedProdutos) {
		this.filtedProdutos = filtedProdutos;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

}
