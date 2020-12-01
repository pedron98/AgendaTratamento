package com.github.pedron98.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.pedron98.annotation.Transacional;
import com.github.pedron98.model.Medicamento;

public class MedicamentoDAO implements MedicamentoDAOIF, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	@Override
	@Transacional
	public void save(Medicamento medicamento) {
		em.persist(medicamento);
	}
	
	@Override
	@Transacional
	public void update(Medicamento medicamento) {
		em.merge(medicamento);
	}
	
	@Override
	@Transacional
	public void remove(Medicamento medicamento) {
		Medicamento m = em.find(Medicamento.class, medicamento.getId());
		em.remove(m);
	}
	
	@Override
	public List<Medicamento> findAll() {
		List<Medicamento> medicamentos = new ArrayList<Medicamento>();
		String jpql = "FROM Medicamento"; 
		TypedQuery<Medicamento> query = em.createQuery(jpql, Medicamento.class);
		medicamentos = query.getResultList();
		return medicamentos;
	}
	@Override
	public Medicamento findById(Long id) {
		return em.find(Medicamento.class, id);
	}
}
