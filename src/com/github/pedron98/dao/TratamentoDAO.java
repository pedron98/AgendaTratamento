package com.github.pedron98.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.github.pedron98.annotation.Transacional;
import com.github.pedron98.model.Tratamento;

public class TratamentoDAO implements TratamentoDAOIF, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;

	@Override
	@Transacional
	public void save(Tratamento tratamento) {
		em.persist(tratamento);
	}

	@Override
	@Transacional
	public void update(Tratamento tratamento) {
		em.merge(tratamento);
	}

	@Override
	@Transacional
	public void remove(Tratamento tratamento) {
		Tratamento t = em.find(Tratamento.class, tratamento.getId());
		em.remove(t);
	}

	@Override
	public List<Tratamento> findAll() {
		List<Tratamento> tratamentos = new ArrayList<Tratamento>();
		String jpql = "FROM Tratamento";
		TypedQuery<Tratamento> query = em.createQuery(jpql, Tratamento.class);
		tratamentos = query.getResultList();
		return tratamentos;
	}

	@Override
	public Tratamento findById(Long id) {
		return em.find(Tratamento.class, id);
	}

	@Override
	public Tratamento findTratamentoFetchMedicamentos(Long id) {
		String jpql = "SELECT t FROM Tratamento t LEFT JOIN FETCH t.medicamentos WHERE t.id = :id";
		TypedQuery<Tratamento> query = em.createQuery(jpql, Tratamento.class);
		return query.setParameter("id", id).getSingleResult();
	}

}
