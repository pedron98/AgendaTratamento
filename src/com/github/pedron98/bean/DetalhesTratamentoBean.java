package com.github.pedron98.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.pedron98.dao.MedicamentoDAO;
import com.github.pedron98.dao.TratamentoDAO;
import com.github.pedron98.exception.SessionException;
import com.github.pedron98.model.Medicamento;
import com.github.pedron98.model.Tratamento;
import com.github.pedron98.model.Usuario;

@Named
@ViewScoped
public class DetalhesTratamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MedicamentoDAO medicamentoDAO;
	@Inject
	private TratamentoDAO tratamentoDAO;
	@Inject
	private Tratamento tratamento;

	private Long usuarioId;
	private Long tratamentoId;

	private List<Medicamento> medicamentos;

	public void pegarTratamentoeMedicamentos() {
		Usuario u = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

		if (u == null) {
			throw new SessionException("Erro ao tentar buscar o usuário da sessão! Faça o login novamente.");
		}
		
		tratamento = tratamentoDAO.findTratamentoFetchMedicamentos(tratamentoId);
		medicamentos = tratamento.getMedicamentos();

		usuarioId = tratamento.getUsuario().getId();
	}

	public void removerMedicamento(Medicamento m) {
		medicamentos.remove(m);
		medicamentoDAO.remove(m);
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
