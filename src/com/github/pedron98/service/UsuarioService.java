package com.github.pedron98.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.github.pedron98.annotation.Transacional;
import com.github.pedron98.dao.UsuarioDAO;
import com.github.pedron98.exception.ServiceException;
import com.github.pedron98.model.Usuario;

public class UsuarioService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Transacional
	public void save(Usuario u) throws ServiceException {
		if (u.getNome().length() < 10) {
			throw new ServiceException("O nome tem que ter no mínimo 10 caractéres");
		}
		else {
			usuarioDAO.save(u);
		}
	}
}
