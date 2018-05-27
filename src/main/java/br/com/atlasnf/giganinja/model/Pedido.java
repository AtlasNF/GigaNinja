package br.com.atlasnf.giganinja.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date datahora;
	private String notafiscal;
	private float valorfrete;
	private float desconto;
	private float valortotal;
	@OneToMany (mappedBy ="pedido")
	private List<Item> itens;
	@ManyToOne
	private Transportadora transportadora;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNotafiscal() {
		return notafiscal;
	}



	public Date getDatahora() {
		return datahora;
	}

	public void setDatahora(Date datahora) {
		this.datahora = datahora;
	}

	public Transportadora getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}

	public void setNotafiscal(String notafiscal) {
		this.notafiscal = notafiscal;
	}

	public float getValorfrete() {
		return valorfrete;
	}

	public void setValorfrete(float valorfrete) {
		this.valorfrete = valorfrete;
	}

	public float getDesconto() {
		return desconto;
	}

	public void setDesconto(float desconto) {
		this.desconto = desconto;
	}

	public float getValortotal() {
		return valortotal;
	}

	public void setValortotal(float valortotal) {
		this.valortotal = valortotal;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public Pedido(Long id, Date datahora, String notafiscal, float valorfrete, float desconto, float valortotal,
			List<Item> itens) {
		super();
		this.id = id;
		this.datahora = datahora;
		this.notafiscal = notafiscal;
		this.valorfrete = valorfrete;
		this.desconto = desconto;
		this.valortotal = valortotal;
		this.itens = itens;
	}

	public Pedido() {
		super();
	}
	 

}
