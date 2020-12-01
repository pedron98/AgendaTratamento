package com.github.pedron98.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.github.pedron98.dao.UsuarioDAO;
import com.github.pedron98.exception.SessionException;
import com.github.pedron98.model.Usuario;

@Named
@ViewScoped
public class PerfilUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private UsuarioDAO usuarioDAO;
	
	public void alterar() {
		usuarioDAO.update(usuario);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Perfil atualizado com sucesso!", ""));
	}
	
	public String remover() {
		usuarioDAO.remove(usuario);
		return "index?faces-redirect=true";
	}
	
	@PostConstruct
	public void init() {
		usuarioDAO = new UsuarioDAO();
		usuario = new Usuario();
		
		Usuario u = (Usuario) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("usuario");
		
		if (u == null) {
			throw new SessionException("Erro ao tentar buscar o usuário da sessão! Faça o login novamente.");
		}
		else {
			usuario = usuarioDAO.findUsuarioFetchTratamentos(u.getId());
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	} 

}
