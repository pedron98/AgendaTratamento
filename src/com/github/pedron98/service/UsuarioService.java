package com.github.pedron98.service;

import java.io.Serializable;

import com.github.pedron98.dao.UsuarioDAO;
import com.github.pedron98.exception.ServiceException;
import com.github.pedron98.model.Usuario;

public class UsuarioService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private UsuarioDAO usuarioDAO;
	
	public UsuarioService() {
		usuarioDAO = new UsuarioDAO();
	}
	
	public void save(Usuario u) throws ServiceException {
		if (u.getNome().length() < 10) {
			throw new ServiceException("O nome tem que ter no mínimo 10 caractéres");
		}
		else {
			usuarioDAO.save(u);
		}
	}
	
	public Usuario findUsuarioByEmail(String email) throws ServiceException {
		Usuario u = new Usuario();
		try {
			u = usuarioDAO.findUsuarioByEmail(email);
		}
		catch(Exception ex) {
			throw new ServiceException("E-mail não cadastrado!");
		}
		return u;
	}
}
