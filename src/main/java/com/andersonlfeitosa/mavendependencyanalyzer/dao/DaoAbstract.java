package com.andersonlfeitosa.mavendependencyanalyzer.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class DaoAbstract<T> implements IDao<T> {
	
	private EntityManagerFactory entityManagerFactory;
	
	private EntityManager entityManager;
	
	public DaoAbstract() {
		entityManagerFactory = Persistence.createEntityManagerFactory("dmpu");
		entityManager = entityManagerFactory.createEntityManager();
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}
	
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
