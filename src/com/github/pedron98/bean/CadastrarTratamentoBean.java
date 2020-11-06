package com.github.pedron98.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.github.pedron98.dao.TratamentoDAO;
import com.github.pedron98.dao.UsuarioDAO;
import com.github.pedron98.enums.TipoTratamento;
import com.github.pedron98.model.Tratamento;
import com.github.pedron98.model.Usuario;

@Named
@ViewScoped
public class CadastrarTratamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Tratamento tratamento;
	private TratamentoDAO tratamentoDAO;
	private Usuario usuario;
	private UsuarioDAO usuarioDAO;
	private String tipoTratamento;
	private int usuarioId;
	private int tratamentoId;

	public void pegarUsuarioESalvarTratamento() {
		usuario = usuarioDAO.findById(new Long(usuarioId));
		if (tratamentoId != 0) {			
			tratamento = tratamentoDAO.findById(new Long(tratamentoId));
		}
		tratamento.setUsuario(usuario);
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
		System.out.println(tipo);
		tratamento.setTipoTratamento(tipo);
	}

	@PostConstruct
	public void init() {
		tratamento = new Tratamento();
		tratamentoDAO = new TratamentoDAO();
		usuario = new Usuario();
		usuarioDAO = new UsuarioDAO();
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

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getTratamentoId() {
		return tratamentoId;
	}

	public void setTratamentoId(int tratamentoId) {
		this.tratamentoId = tratamentoId;
	}

	public String save() {
		if (tratamentoId != 0) {			
			tratamentoDAO.update(tratamento);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Tratamento atualizado com sucesso!", ""));
			return null;
		}
		else {
			tratamentoDAO.save(tratamento);
			return "dashboardUsuario?faces-redirect=true"+"id_usuario="+usuarioId;
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
