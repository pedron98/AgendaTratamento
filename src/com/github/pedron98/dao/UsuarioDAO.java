package com.github.pedron98.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.pedron98.model.Usuario;

public class UsuarioDAO implements UsuarioDAOIF, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	@Override
	public void save(Usuario usuario) {
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
	}

	@Override
	public void update(Usuario usuario) {
		em.getTransaction().begin();
		em.merge(usuario);
		em.getTransaction().commit();
	}

	@Override
	public void remove(Usuario usuario) {
		Usuario u = em.find(Usuario.class, usuario.getId());
		em.getTransaction().begin();
		em.remove(u);
		em.getTransaction().commit();
	}

	@Override
	public List<Usuario> findAll() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		String jpql = "FROM Usuario"; 
		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
		usuarios = query.getResultList();
		return usuarios;
	}

	@Override
	public Usuario findById(Long id) {
		return em.find(Usuario.class, id);
	}

	@Override
	public Usuario findUsuarioFetchTratamentos(Long id) {
		Usuario u = em.find(Usuario.class, id);
		String jpql = "SELECT u FROM Usuario u LEFT JOIN FETCH u.tratamentos WHERE u.id = :id";
		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
		u = query.setParameter("id", id).getSingleResult();
		return u;
	}

	@Override
	public Usuario findUsuarioByEmail(String email) {
		Usuario u = new Usuario();
		String jpql = "SELECT u FROM Usuario u WHERE u.email = :email";
		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
		u = query.setParameter("email", email).getSingleResult();
		return u;
	}

}
