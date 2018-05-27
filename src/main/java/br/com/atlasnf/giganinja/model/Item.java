package br.com.atlasnf.giganinja.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private float quantidade;
	private float valor;
	@ManyToOne
	private Produto produto;
	@ManyToOne
	private Pedido pedido;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(float quantidade) {
		this.quantidade = quantidade;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public String toString() {
		return "Item " + id + ";" + produto.getNome() + ", Qtd.: " + quantidade + ", Valor: R$" + valor;
	}

	public Item(Long id, float quantidade, float valor, Produto produto, Pedido pedido) {
		super();
		this.id = id;
		this.quantidade = quantidade;
		this.valor = valor;
		this.produto = produto;
		this.pedido = pedido;
	}

	public Item() {
		super();
	}

	
	
}
