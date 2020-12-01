package com.github.pedron98.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {
	
	private static EntityManagerFactory emf;
	
	static {
		emf = Persistence.createEntityManagerFactory("projeto_ambulatorio");
	}
	
	@Produces
	@RequestScoped
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	public static void close(EntityManager em) {
		if (em.isOpen()) {
			em.close();
		}
	}
}
