package com.andersonlfeitosa.mavendependencyanalyzer.dao;

import com.andersonlfeitosa.mavendependencyanalyzer.dao.impl.ArtifactDaoImpl;
import com.andersonlfeitosa.mavendependencyanalyzer.dao.impl.DependencyDaoImpl;

public class DaoFactory {
	
	private static final DaoFactory instance = new DaoFactory();
	
	private DaoFactory() {
		
	}
	
	public static DaoFactory getInstance() {
		return instance;
	}

	public ArtifactDaoImpl createArtifactDaoImpl() {
		return new ArtifactDaoImpl();
	}
	
	public DependencyDaoImpl createDependencyDaoImpl() {
		return new DependencyDaoImpl();
	}
}
