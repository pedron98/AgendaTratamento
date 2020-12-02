package com.github.pedron98.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.pedron98.dao.TratamentoDAO;
import com.github.pedron98.dao.UsuarioDAO;
import com.github.pedron98.exception.SessionException;
import com.github.pedron98.model.Tratamento;
import com.github.pedron98.model.Usuario;

@Named
@ViewScoped
public class DashboardUsuarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TratamentoDAO tratamentoDAO;
	@Inject
	private UsuarioDAO usuarioDAO;
	@Inject
	private Usuario usuario;
	
	private List<Tratamento> tratamentos;
	
	public void remover(Tratamento t) {
		tratamentoDAO.remove(t);
		tratamentos.remove(t);
	}

	@PostConstruct
	public void init() {
					
		Usuario u = (Usuario) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("usuario");
		
		if (u == null) {
			throw new SessionException("Erro ao tentar buscar o usuário da sessão! Faça o login novamente.");
		}
		else {			
			usuario = usuarioDAO.findUsuarioFetchTratamentos(u.getId());
			tratamentos = usuario.getTratamentos();
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Tratamento> getTratamentos() {
		return tratamentos;
	}
}
