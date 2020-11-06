package com.github.pedron98.dao;

import com.github.pedron98.model.Usuario;

public interface UsuarioDAOIF extends GenericDAOIF<Usuario> {
	
	Usuario findUsuarioFetchTratamentos(Long id);
	Usuario findUsuarioByEmail(String email);
}
