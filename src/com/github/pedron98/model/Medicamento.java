package com.github.pedron98.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.pedron98.enums.TipoMedicamento;

@Entity
@Table(name="medicamento")
public class Medicamento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 150)
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@Column(name="categoria")
	private TipoMedicamento tipoMedicamento;
	
	@Column(length = 250)
	private String dosagem;
	
	@ManyToOne
	@JoinColumn(name="tratamento_id")
	private Tratamento tratamento;

	public Medicamento() {
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoMedicamento getTipoMedicamento() {
		return tipoMedicamento;
	}

	public void setTipoMedicamento(TipoMedicamento tipoMedicamento) {
		this.tipoMedicamento = tipoMedicamento;
	}

	public String getDosagem() {
		return dosagem;
	}

	public void setDosagem(String dosagem) {
		this.dosagem = dosagem;
	}

	public Long getId() {
		return id;
	}

	public Tratamento getTratamento() {
		return tratamento;
	}

	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dosagem == null) ? 0 : dosagem.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((tipoMedicamento == null) ? 0 : tipoMedicamento.hashCode());
		result = prime * result + ((tratamento == null) ? 0 : tratamento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medicamento other = (Medicamento) obj;
		if (dosagem == null) {
			if (other.dosagem != null)
				return false;
		} else if (!dosagem.equals(other.dosagem))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (tipoMedicamento != other.tipoMedicamento)
			return false;
		if (tratamento == null) {
			if (other.tratamento != null)
				return false;
		} else if (!tratamento.equals(other.tratamento))
			return false;
		return true;
	} 
	
}
