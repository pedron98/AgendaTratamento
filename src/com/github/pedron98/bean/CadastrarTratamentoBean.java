package com.github.pedron98.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.pedron98.dao.TratamentoDAO;
import com.github.pedron98.enums.TipoTratamento;
import com.github.pedron98.exception.SessionException;
import com.github.pedron98.model.Tratamento;
import com.github.pedron98.model.Usuario;

@Named
@ViewScoped
public class CadastrarTratamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Tratamento tratamento;
	
	@Inject
	private TratamentoDAO tratamentoDAO;
	
	private String tipoTratamento;
	private Long tratamentoId;
	
	public void pegarTratamento() {
		if (tratamentoId != null) {			
			tratamento = tratamentoDAO.findById(tratamentoId);
		}
		else {
			Usuario u = (Usuario) FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().get("usuario");
			
			if (u == null) {				
				throw new SessionException("Erro ao tentar buscar o usuário da sessão! Faça o login novamente.");
			}
			
			tratamento.setUsuario(u);
		}
	}

	public void pegarTipoTratamento() {
		TipoTratamento tipo = null;
		if (tipoTratamento.equals("MEDICO")) {
			tipo = TipoTratamento.MEDICO;
		} else {
			if (tipoTratamento.equals("CIRURGICO")) {
				tipo = TipoTratamento.CIRURGICO;
			} else {
				if (tipoTratamento.equals("ACTIVO")) {
					tipo = TipoTratamento.ACTIVO;
				} else {
					if (tipoTratamento.equals("PALIATIVO")) {
						tipo = TipoTratamento.PALIATIVO;
					} else {
						if (tipoTratamento.equals("SINTOMATICO")) {
							tipo = TipoTratamento.SINTOMATICO;
						} else {
							if (tipoTratamento.equals("ALTERNATIVO")) {
								tipo = TipoTratamento.ALTERNATIVO;
							}
						}
					}
				}
			}
		}
		tratamento.setTipoTratamento(tipo);
	}

	public Tratamento getTratamento() {
		return tratamento;
	}

	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	}

	public String getTipoTratamento() {
		return tipoTratamento;
	}

	public void setTipoTratamento(String tipoTratamento) {
		this.tipoTratamento = tipoTratamento;
	}

	public Long getTratamentoId() {
		return tratamentoId;
	}

	public void setTratamentoId(Long tratamentoId) {
		this.tratamentoId = tratamentoId;
	}

	public String save() {
		if (tratamentoId != null) {
			tratamentoDAO.update(tratamento);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Tratamento atualizado com sucesso!", ""));
		} else {
			tratamentoDAO.save(tratamento);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Tratamento cadastrado com sucesso!", ""));
			return "dashboardUsuario.xhtml?faces-redirect=true";
		}
		return null;
	}
}
