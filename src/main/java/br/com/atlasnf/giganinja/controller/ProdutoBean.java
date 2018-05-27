package br.com.atlasnf.giganinja.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.atlasnf.giganinja.model.Fornecedor;
import br.com.atlasnf.giganinja.model.Produto;
import br.com.atlasnf.giganinja.model.dao.FornecedorDAO;
import br.com.atlasnf.giganinja.model.dao.ProdutoDAO;
import br.com.atlasnf.giganinja.tx.Transacional;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	ProdutoDAO produtoDAO;
	@Inject
	FornecedorDAO fornecedorDAO;

	private List<Fornecedor> fornecedores;
	private List<Fornecedor> filteredFornecedores;
	private Produto produto;
	private Produto selectedProduto;
	private Fornecedor selFornecedor;
	private Fornecedor oldFornecedor;
	private List<Produto> allProdutos;

	@PostConstruct
	@Transacional
	public void init() {
		allProdutos = produtoDAO.listaTodos();
		fornecedores = fornecedorDAO.listaTodos();
		produto = new Produto();
	}

	public List<Fornecedor> getFilteredFornecedores() {
		return filteredFornecedores;
	}

	public void setFilteredFornecedores(List<Fornecedor> filteredFornecedores) {
		this.filteredFornecedores = filteredFornecedores;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Fornecedor getSelFornecedor() {
		return selFornecedor;
	}

	public void setSelFornecedor(Fornecedor selFornecedor) {
		this.selFornecedor = selFornecedor;
	}

	@Transacional
	public void adicionarProduto() {
		if (selFornecedor != null) {
			selFornecedor = fornecedorDAO.buscaPorId(selFornecedor.getId());
			produto.setFornecedor(selFornecedor);
			produtoDAO.adiciona(produto);
			if (selFornecedor.getProdutos() == null) {
				List<Produto> produtos = new ArrayList<Produto>();
				selFornecedor.setProdutos(produtos);
			}
			selFornecedor.getProdutos().add(produto);
			fornecedorDAO.atualiza(selFornecedor);
			produto = new Produto();
			selFornecedor = null;
			enviarMessage("Produto Cadastrado com sucesso!", "Sucesso", FacesMessage.SEVERITY_INFO);
		} else {
			enviarMessage("É necessário selecionar um fornecedor!", "Erro", FacesMessage.SEVERITY_FATAL);
		}
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	@SuppressWarnings("deprecation")
	public void selecionarFornecedor() {
		enviarMessage("Forncedor selecionado com sucesso!", "Sucesso", FacesMessage.SEVERITY_INFO);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('fornecedoresDialog').hide();");
	}

	public void enviarMessage(String mensagem, String header, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, header, mensagem));
	}

	public Produto getSelectedProduto() {
		return selectedProduto;
	}

	public void setSelectedProduto(Produto selectedProduto) {
		this.selectedProduto = selectedProduto;
		oldFornecedor = selectedProduto.getFornecedor();
	}

	@SuppressWarnings("deprecation")
	@Transacional
	public void editarProduto() {
		if (oldFornecedor.getId() == selectedProduto.getFornecedor().getId()) {
			produtoDAO.atualiza(selectedProduto);
		} else {
			oldFornecedor = fornecedorDAO.buscaPorId(oldFornecedor.getId());
			oldFornecedor.getProdutos().remove(selectedProduto);
			fornecedorDAO.atualiza(oldFornecedor);
			produtoDAO.atualiza(selectedProduto);
			Fornecedor aux = fornecedorDAO.buscaPorId(selectedProduto.getFornecedor().getId());
			aux.getProdutos().add(selectedProduto);
			fornecedorDAO.atualiza(aux);
		}
		enviarMessage("Produto editado com sucesso!", "Sucesso", FacesMessage.SEVERITY_INFO);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('produtoDialog').hide();");

	}

	@SuppressWarnings("deprecation")
	@Transacional
	public void deletarProduto() {
		selectedProduto = produtoDAO.buscaPorId(selectedProduto.getId());
		if (selectedProduto.getItens().isEmpty()) {
			Fornecedor aux = fornecedorDAO.buscaPorId(selectedProduto.getFornecedor().getId());
			aux.getProdutos().remove(selectedProduto);
			fornecedorDAO.atualiza(aux);
			produtoDAO.remove(selectedProduto);
			allProdutos = produtoDAO.listaTodos();
			enviarMessage("Produto removido com sucesso!", "Sucesso", FacesMessage.SEVERITY_INFO);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('produtoDialog').hide();");
		}else {
			enviarMessage("Produto ainda consta em algum pedido!", "Erro", FacesMessage.SEVERITY_FATAL);
		}
	}

	public List<Produto> getAllProdutos() {
		return allProdutos;
	}

	public void setAllProdutos(List<Produto> allProdutos) {
		this.allProdutos = allProdutos;
	}

}
