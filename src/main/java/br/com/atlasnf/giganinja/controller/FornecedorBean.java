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

import br.com.atlasnf.giganinja.model.Email;
import br.com.atlasnf.giganinja.model.Fornecedor;
import br.com.atlasnf.giganinja.model.Telefone;
import br.com.atlasnf.giganinja.model.dao.EmailDAO;
import br.com.atlasnf.giganinja.model.dao.FornecedorDAO;
import br.com.atlasnf.giganinja.model.dao.TelefoneDAO;
import br.com.atlasnf.giganinja.tx.Transacional;

@Named
@ViewScoped
public class FornecedorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EmailDAO emailDAO;
	@Inject
	FornecedorDAO fornecedorDAO;
	@Inject
	TelefoneDAO telefoneDAO;

	private String nome;
	private String endereco;
	private String numero;
	private String descricao;
	private String bairro;
	private String cidade;
	private String ddd;
	private String numeroTEL;
	private String referenciaTEL;
	private List<Telefone> telefones;
	private List<Telefone> removeTelefones;
	private List<Email> emails;
	private Telefone selTelefone;
	private Email email;
	private Email selEmail;
	private Fornecedor selectedFornecedor;

	public FornecedorBean() {
	}

	@PostConstruct
	public void init() {
		email = new Email();
		telefones = new ArrayList<>();
		removeTelefones = new ArrayList<>();
		emails = new ArrayList<>();
		selTelefone = new Telefone();
	}

	@Transacional
	public List<Fornecedor> getAllForncedores() {
		return fornecedorDAO.listaTodos();

	}

	@Transacional
	public void cadastrarForncedor() {
		if (!telefones.isEmpty()) {
			Fornecedor aux = new Fornecedor(nome, descricao, cidade, endereco, bairro, numero, telefones);
			fornecedorDAO.adiciona(aux);
			for (int i = 0; i < telefones.size(); i++) {
				telefones.get(i).setFornecedor(aux);
				telefoneDAO.adiciona(telefones.get(i));
			}
			if (!emails.isEmpty()) {
				for (int i = 0; i < emails.size(); i++) {
					emails.get(i).setFornecedor(aux);
					emailDAO.adiciona(emails.get(i));
				}
			}
			fornecedorDAO.atualiza(aux);
			nome = new String();
			endereco = new String();
			cidade = new String();
			numero = new String();
			bairro = new String();
			descricao = new String();
			ddd = new String();
			numeroTEL = new String();
			referenciaTEL = new String();
			telefones = new ArrayList<>();
			emails = new ArrayList<>();
			enviarMessage("Cadastro realizado com sucesso!", "Sucesso", FacesMessage.SEVERITY_INFO);
		} else {
			enviarMessage("É necessário um número de telefone", "Erro", FacesMessage.SEVERITY_ERROR);
		}

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumeroTEL() {
		return numeroTEL;
	}

	public void setNumeroTEL(String numeroTEL) {
		this.numeroTEL = numeroTEL;
	}

	public String getReferenciaTEL() {
		return referenciaTEL;
	}

	public void setReferenciaTEL(String referenciaTEL) {
		this.referenciaTEL = referenciaTEL;
	}

	public Telefone getSelTelefone() {
		return selTelefone;
	}

	public void setSelTelefone(Telefone selTelefone) {
		this.selTelefone = selTelefone;
	}

	public void addTelefone() {
		Telefone aux = new Telefone();
		aux.setDdd(ddd);
		aux.setNumero(numeroTEL);
		aux.setReferencia(referenciaTEL);
		System.out.println(aux);
		telefones.add(aux);
		ddd = new String();
		numeroTEL = new String();
		referenciaTEL = new String();
	}

	public void addEmail() {
		emails.add(email);
		System.out.println(email.getEmail());
		email = new Email();
	}

	public void addEmailFornecedor() {
		selectedFornecedor.getEmails().add(email);
		System.out.println(email.getEmail());
		email = new Email();
	}

	public void removerTelefone() {
		telefones.remove(selTelefone);
	}

	public void removerEmail() {
		emails.remove(selEmail);
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Email getSelEmail() {
		return selEmail;
	}

	public void setSelEmail(Email selEmail) {
		this.selEmail = selEmail;
	}

	public void enviarMessage(String mensagem, String header, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, header, mensagem));
	}

	public Fornecedor getSelectedFornecedor() {
		return selectedFornecedor;
	}

	public void setSelectedFornecedor(Fornecedor selectedFornecedor) {
		this.selectedFornecedor = selectedFornecedor;
	}

	public void editTelefone() {
		Telefone aux = new Telefone();
		aux.setDdd(ddd);
		aux.setNumero(numeroTEL);
		aux.setReferencia(referenciaTEL);
		System.out.println(aux);
		selectedFornecedor.getTelefones().add(aux);
		ddd = new String();
		numeroTEL = new String();
		referenciaTEL = new String();
	}

	public void removerTelefoneSelec() {
		selectedFornecedor.getTelefones().remove(selTelefone);
		removeTelefones.add(selTelefone);
		selTelefone = new Telefone();
	}

	public void removerEmailSelec() {
		selectedFornecedor.getEmails().remove(selEmail);
		selEmail = new Email();
	}

	public void teste() {
		System.out.println("Teste");
	}

	public void Lists() {
		telefones = selectedFornecedor.getTelefones();
	}

	@SuppressWarnings("deprecation")
	@Transacional
	public void editarForncedores() {
		if (!selectedFornecedor.getTelefones().isEmpty()) {
			fornecedorDAO.atualiza(selectedFornecedor);
			System.out.println(selectedFornecedor.getId());
			System.out.println("aqui 1");
			for (int i = 0; i < selectedFornecedor.getTelefones().size(); i++) {
				selectedFornecedor.getTelefones().get(i).setFornecedor(selectedFornecedor);
				if (selectedFornecedor.getTelefones().get(i).getId() == null) {
					telefoneDAO.adiciona(selectedFornecedor.getTelefones().get(i));
					System.out.println(selectedFornecedor.getTelefones().get(i).getId());
				} else {
					telefoneDAO.atualiza(selectedFornecedor.getTelefones().get(i));
				}
				System.out.println("aqui" + i);
			}
			if (!selectedFornecedor.getEmails().isEmpty()) {
				for (int i = 0; i < selectedFornecedor.getEmails().size(); i++) {
					selectedFornecedor.getEmails().get(i).setFornecedor(selectedFornecedor);
					if (selectedFornecedor.getEmails().get(i).getId() == null) {
						emailDAO.adiciona(selectedFornecedor.getEmails().get(i));
					} else {
						emailDAO.atualiza(selectedFornecedor.getEmails().get(i));
					}

				}
			}
			for (int i = 0; i < removeTelefones.size(); i++) {
				if (removeTelefones.get(i).getId() != null) {
					telefoneDAO.remove(removeTelefones.get(i));
				}
			}
			fornecedorDAO.atualiza(selectedFornecedor);
			nome = new String();
			endereco = new String();
			cidade = new String();
			numero = new String();
			bairro = new String();
			descricao = new String();
			ddd = new String();
			numeroTEL = new String();
			referenciaTEL = new String();
			telefones = new ArrayList<>();
			emails = new ArrayList<>();
			enviarMessage("Edição realizado com sucesso!", "Sucesso", FacesMessage.SEVERITY_INFO);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('fornecedorDialog').hide();");
			context.update("fornecedoresDT");
		} else {
			enviarMessage("É necessário um número de telefone!", "Erro", FacesMessage.SEVERITY_ERROR);
		}
	}

	@SuppressWarnings("deprecation")
	@Transacional
	public void deletarFornecedor() {
		selectedFornecedor = fornecedorDAO.buscaPorId(selectedFornecedor.getId());
		if (selectedFornecedor.getProdutos().isEmpty()) {
			for (int i = 0; i < selectedFornecedor.getTelefones().size(); i++) {
				selectedFornecedor.getTelefones().get(i).setFornecedor(selectedFornecedor);
				if (selectedFornecedor.getTelefones().get(i).getId() != null) {
					telefoneDAO.remove(selectedFornecedor.getTelefones().get(i));
				}
				System.out.println("aqui" + i);
			}
			if (!selectedFornecedor.getEmails().isEmpty()) {
				for (int i = 0; i < selectedFornecedor.getEmails().size(); i++) {
					selectedFornecedor.getEmails().get(i).setFornecedor(selectedFornecedor);
					if (selectedFornecedor.getEmails().get(i).getId() != null) {
						selectedFornecedor.getEmails().get(i).setFornecedor(null);
						emailDAO.remove(selectedFornecedor.getEmails().get(i));
					}
				}
				fornecedorDAO.remove(selectedFornecedor);
				enviarMessage("Deletado com sucesso!!", "Sucesso", FacesMessage.SEVERITY_INFO);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('fornecedorDialog').hide();");
				context.update("fornecedoresDT");

			}
			enviarMessage("Não é possivel deletar o fornecedor pois ainda existe produtos associados.", "Sucesso", FacesMessage.SEVERITY_INFO);
		}
	}

}
