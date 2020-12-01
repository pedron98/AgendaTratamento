package com.github.pedron98.bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class LogoutUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext()
			.getSessionMap().remove("usuario");
		
		return "index.html?faces-redirect=true";
	}

}
