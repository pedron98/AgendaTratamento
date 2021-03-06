package com.github.pedron98.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.pedron98.exception.ServiceException;
import com.github.pedron98.model.Usuario;
import com.github.pedron98.service.UsuarioService;

@Named
@RequestScoped
public class CadastrarUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuario usuario;
	
	@Inject
	private UsuarioService usuarioService;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String save() {
		try {
			usuarioService.save(usuario);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário cadastrado com sucesso!", ""));
			return "login?faces-redirect=true";
		}
		catch(ServiceException ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex.getMessage(), ""));
			return null;
		}
	}
}
