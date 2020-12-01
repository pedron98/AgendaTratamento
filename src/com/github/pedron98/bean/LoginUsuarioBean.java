package com.github.pedron98.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.pedron98.exception.ServiceException;
import com.github.pedron98.model.Usuario;
import com.github.pedron98.service.UsuarioService;

@Named
@ViewScoped
public class LoginUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuario usuario;
	@Inject
	private UsuarioService usuarioService;
	
	private String emailUsuario;
	
	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	} 

	public Usuario getUsuario() {
		return usuario;
	}

	public String login() {
		try {
			usuario = usuarioService.findUsuarioByEmail(emailUsuario);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
			return "dashboardUsuario?faces-redirect=true";
		}
		catch(ServiceException ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex.getMessage(), ""));
			return null;
		}
	}
}
