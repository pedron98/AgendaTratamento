package com.github.pedron98.dao;

import java.util.List;

public interface GenericDAOIF <T> {
	
	void save(T object);
	void update(T object);
	void remove(T object);
	List<T> findAll();
	T findById(Long id);
}
