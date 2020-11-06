package com.github.pedron98.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.pedron98.model.Usuario;
import com.github.pedron98.util.JPAUtils;

public class UsuarioDAO implements UsuarioDAOIF, Serializable {
	
	private static final long serialVersionUID = 1L;
	private EntityManager em;
	
	@Override
	public void save(Usuario usuario) {
		em = JPAUtils.getEntityManager();
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void update(Usuario usuario) {
		em = JPAUtils.getEntityManager();
		em.getTransaction().begin();
		em.merge(usuario);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void remove(Usuario usuario) {
		em = JPAUtils.getEntityManager();
		Usuario u = em.find(Usuario.class, usuario.getId());
		em.getTransaction().begin();
		em.remove(u);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public List<Usuario> findAll() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		em = JPAUtils.getEntityManager();
		String jpql = "FROM Usuario"; 
		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
		usuarios = query.getResultList();
		em.close();
		return usuarios;
	}

	@Override
	public Usuario findById(Long id) {
		em = JPAUtils.getEntityManager();
		Usuario u = em.find(Usuario.class, id);
		em.close();
		return u;
	}

	@Override
	public Usuario findUsuarioFetchTratamentos(Long id) {
		em = JPAUtils.getEntityManager();
		Usuario u = em.find(Usuario.class, id);
		String jpql = "SELECT u FROM Usuario u LEFT JOIN FETCH u.tratamentos WHERE u.id = :id";
		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
		u = query.setParameter("id", id).getSingleResult();
		em.close();
		return u;
	}

	@Override
	public Usuario findUsuarioByEmail(String email) {
		Usuario u = new Usuario();
		em = JPAUtils.getEntityManager();
		String jpql = "SELECT u FROM Usuario u WHERE u.email = :email";
		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
		u = query.setParameter("email", email).getSingleResult();
		em.close();
		return u;
	}

}
