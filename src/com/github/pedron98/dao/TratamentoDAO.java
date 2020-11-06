package com.github.pedron98.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.pedron98.model.Tratamento;
import com.github.pedron98.util.JPAUtils;

public class TratamentoDAO implements TratamentoDAOIF, Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager em;

	@Override
	public void save(Tratamento tratamento) {
		em = JPAUtils.getEntityManager();
		em.getTransaction().begin();
		em.persist(tratamento);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void update(Tratamento tratamento) {
		em = JPAUtils.getEntityManager();
		em.getTransaction().begin();
		em.merge(tratamento);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void remove(Tratamento tratamento) {
		em = JPAUtils.getEntityManager();
		em.getTransaction().begin();
		Tratamento t = em.find(Tratamento.class, tratamento.getId());
		em.remove(t);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public List<Tratamento> findAll() {
		List<Tratamento> tratamentos = new ArrayList<Tratamento>();
		em = JPAUtils.getEntityManager();
		String jpql = "FROM Tratamento";
		TypedQuery<Tratamento> query = em.createQuery(jpql, Tratamento.class);
		tratamentos = query.getResultList();
		em.close();
		return tratamentos;
	}

	@Override
	public Tratamento findById(Long id) {
		em = JPAUtils.getEntityManager();
		Tratamento t = em.find(Tratamento.class, id);
		em.close();
		return t;
	}

	@Override
	public Tratamento findTratamentoFetchMedicamentos(Long id) {
		em = JPAUtils.getEntityManager();
		String jpql = "SELECT t FROM Tratamento t LEFT JOIN FETCH t.medicamentos WHERE t.id = :id";
		TypedQuery<Tratamento> query = em.createQuery(jpql, Tratamento.class);
		return query.setParameter("id", id).getSingleResult();
	}

}
