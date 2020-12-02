package com.github.pedron98.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.pedron98.annotation.Transacional;
import com.github.pedron98.enums.ErrorCode;
import com.github.pedron98.exception.DAOException;
import com.github.pedron98.model.Usuario;

public class UsuarioDAO implements UsuarioDAOIF, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	@Override
	public void save(Usuario usuario) {
		try {			
			em.persist(usuario);
		}
		catch(Exception ex) {
			throw new DAOException("Erro ao tentar cadastrar o usuário no banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
	}

	@Override
	@Transacional
	public void update(Usuario usuario) {
		try {
			Usuario u = em.find(Usuario.class, usuario.getId());
			u.setNome(usuario.getNome());
			u.setEmail(usuario.getNome());
			em.merge(usuario);
		}
		catch(NullPointerException ex) {
			throw new DAOException("O usuário informado não foi encontrado: "+ex.getMessage(),
					ErrorCode.NOT_FOUND.getCode());
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar atualizar o usuário no banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
		
	}

	@Override
	@Transacional
	public void remove(Usuario usuario) {
		try {
			Usuario u = em.find(Usuario.class, usuario.getId());
			em.remove(u);
		}
		catch(IllegalArgumentException ex) {
			throw new DAOException("O usuário informado não foi encontrado: "+ex.getMessage(),
					ErrorCode.NOT_FOUND.getCode());
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar remover o usuário do banco do dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
		
	}

	@Override
	public List<Usuario> findAll() {
		try {
			List<Usuario> usuarios = new ArrayList<Usuario>();
			String jpql = "FROM Usuario"; 
			TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
			usuarios = query.getResultList();
			return usuarios;
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar buscar todos os usuários do banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
	}

	@Override
	public Usuario findById(Long id) {
		Usuario u = null;
		if (id <= 0) {
			throw new DAOException("O id deve ser maior do que zero!", ErrorCode.BAD_REQUEST.getCode());
		}
		try {
			u = em.find(Usuario.class, id);
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar buscar o usuário no banco de dados: "+ex.getMessage(),
					ErrorCode.BAD_REQUEST.getCode());
		}
		if(u == null) {
			throw new DAOException("O usuário informado não foi encontrado!", ErrorCode.NOT_FOUND.getCode());
		}
		return u;
	}

	@Override
	public Usuario findUsuarioFetchTratamentos(Long id) {
		if (id <= 0) {
			throw new DAOException("O id deve ser maior do que zero!", ErrorCode.BAD_REQUEST.getCode());
		}
		try {
			String jpql = "SELECT u FROM Usuario u LEFT JOIN FETCH u.tratamentos WHERE u.id = :id";
			TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
			return query.setParameter("id", id).getSingleResult();
		}
		catch(RuntimeException ex) {
			throw new DAOException("Erro ao tentar buscar o usuário com carregamento de tratamentos do banco de dados: "+ex.getMessage(),
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
	}

	@Override
	public Usuario findUsuarioByEmail(String email) {
		try {
			String jpql = "SELECT u FROM Usuario u WHERE u.email = :email";
			TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
			return query.setParameter("email", email).getSingleResult();
		}
		catch(RuntimeException ex) {
			throw new DAOException("Nenhum usuário encontrado com este e-mail :S",
					ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		}
	}

}
