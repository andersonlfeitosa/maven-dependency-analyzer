package com.andersonlfeitosa.mavendependencyanalyzer.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import com.andersonlfeitosa.mavendependencyanalyzer.dao.DaoAbstract;
import com.andersonlfeitosa.mavendependencyanalyzer.entity.DependencyEntity;

public class DependencyDaoImpl extends DaoAbstract<DependencyEntity> {

	@Override
	public void save(DependencyEntity e) {
		getEntityManager().persist(e);
	}

	@Override
	public void update(DependencyEntity e) {
		getEntityManager().merge(e);
	}

	@Override
	public void delete(DependencyEntity e) {
		getEntityManager().remove(e);
	}

	@Override
	public DependencyEntity get(Serializable key) {
		return getEntityManager().find(DependencyEntity.class, key);
	}

	@Override
	public DependencyEntity find(DependencyEntity filter) {
		Query query = getEntityManager().createQuery(
				"from com.andersonlfeitosa.mavendependencyanalyzer.entity.DependencyEntity a "
				+ "where a.dependency.groupId = :groupId "
				+ "and a.dependency.artifactId = :artifactId "
				+ "and a.dependency.version = :version");
		
		query.setParameter("groupId", filter.getDependency().getGroupId());
		query.setParameter("artifactId", filter.getDependency().getArtifactId());
		query.setParameter("version", filter.getDependency().getVersion());
		return (DependencyEntity) query.getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DependencyEntity> list() {
		return getEntityManager().createQuery(
				"from com.andersonlfeitosa.mavendependencyanalyzer.entity.DependencyEntity a").getResultList();
	}

}
