package com.github.pedron98.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.github.pedron98.dao.MedicamentoDAO;
import com.github.pedron98.dao.TratamentoDAO;
import com.github.pedron98.model.Medicamento;
import com.github.pedron98.model.Tratamento;

@Named
@ViewScoped
public class DetalhesTratamentoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private MedicamentoDAO medicamentoDAO;
	private TratamentoDAO tratamentoDAO;
	private Tratamento tratamento;
	private Long usuarioId;
	private Long tratamentoId;
	
	private List<Medicamento> medicamentos;
	
	public void pegarTratamentoeMedicamentos() {
		tratamento = tratamentoDAO.findTratamentoFetchMedicamentos(tratamentoId);
		medicamentos = tratamento.getMedicamentos();
		usuarioId = tratamento.getUsuario().getId();
	}
	
	public void removerMedicamento(Medicamento m) {
		medicamentos.remove(m);
		medicamentoDAO.remove(m);
	}
	
	@PostConstruct
	public void init() {
		tratamentoDAO = new TratamentoDAO();
		tratamento = new Tratamento();
		medicamentoDAO = new MedicamentoDAO();
	}

	public Long getTratamentoId() {
		return tratamentoId;
	}

	public void setTratamentoId(Long tratamentoId) {
		this.tratamentoId = tratamentoId;
	} 

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Tratamento getTratamento() {
		return tratamento;
	}

	public List<Medicamento> getMedicamentos() {
		return medicamentos;
	} 

}
