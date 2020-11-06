package com.github.pedron98.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {
	
	private static EntityManagerFactory emf;
	
	static {
		emf = Persistence.createEntityManagerFactory("projeto_ambulatorio");
	}
	
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	public static void close() {
		emf.close();
	}
}
