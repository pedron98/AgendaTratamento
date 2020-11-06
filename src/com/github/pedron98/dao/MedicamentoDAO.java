package com.github.pedron98.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.pedron98.model.Medicamento;
import com.github.pedron98.util.JPAUtils;

public class MedicamentoDAO implements MedicamentoDAOIF, Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager em;
	
	@Override
	public void save(Medicamento medicamento) {
		em = JPAUtils.getEntityManager();
		em.getTransaction().begin();
		em.persist(medicamento);
		em.getTransaction().commit();
		em.close();
	}
	
	@Override
	public void update(Medicamento medicamento) {
		em = JPAUtils.getEntityManager();
		em.getTransaction().begin();
		em.merge(medicamento);
		em.getTransaction().commit();
		em.close();	
	}
	
	@Override
	public void remove(Medicamento medicamento) {
		em = JPAUtils.getEntityManager();
		Medicamento m = em.find(Medicamento.class, medicamento.getId());
		em.getTransaction().begin();
		em.remove(m);
		em.getTransaction().commit();
		em.close();
	}
	
	@Override
	public List<Medicamento> findAll() {
		List<Medicamento> medicamentos = new ArrayList<Medicamento>();
		em = JPAUtils.getEntityManager();
		String jpql = "FROM Medicamento"; 
		TypedQuery<Medicamento> query = em.createQuery(jpql, Medicamento.class);
		medicamentos = query.getResultList();
		em.close();
		return medicamentos;
	}
	@Override
	public Medicamento findById(Long id) {
		em = JPAUtils.getEntityManager();
		Medicamento m = em.find(Medicamento.class, id);
		em.close();
		return m;
	}
	
}
