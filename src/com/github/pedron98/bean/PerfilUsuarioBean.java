package com.github.pedron98.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.github.pedron98.dao.UsuarioDAO;
import com.github.pedron98.model.Usuario;

@Named
@ViewScoped
public class PerfilUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private UsuarioDAO usuarioDAO;
	private int usuarioId;
	
	public void pegarUsuario() {
		usuario = usuarioDAO.findById(new Long(usuarioId));
	}
	
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
		usuario = new Usuario();
		usuarioDAO = new UsuarioDAO();
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	} 

}
