package com.andersonlfeitosa.mavendependencyanalyzer.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import com.andersonlfeitosa.mavendependencyanalyzer.dao.DaoAbstract;
import com.andersonlfeitosa.mavendependencyanalyzer.entity.ArtifactEntity;

public class ArtifactDaoImpl extends DaoAbstract<ArtifactEntity> {

	@Override
	public void save(ArtifactEntity e) {
		getEntityManager().persist(e);
	}

	@Override
	public void update(ArtifactEntity e) {
		getEntityManager().merge(e);
	}

	@Override
	public void delete(ArtifactEntity e) {
		getEntityManager().remove(e);
	}

	@Override
	public ArtifactEntity get(Serializable key) {
		return getEntityManager().find(ArtifactEntity.class, key);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ArtifactEntity find(ArtifactEntity filter) {
		ArtifactEntity result = null;
		Query query = getEntityManager().createQuery(
				"from com.andersonlfeitosa.mavendependencyanalyzer.entity.ArtifactEntity a "
				+ "where a.groupId = :groupId "
				+ "and a.artifactId = :artifactId "
				+ "and a.version = :version");
		
		query.setParameter("groupId", filter.getGroupId());
		query.setParameter("artifactId", filter.getArtifactId());
		query.setParameter("version", filter.getVersion());
		query.setMaxResults(1);
		
		List<ArtifactEntity> artifacts = query.getResultList();
		if (artifacts != null && !artifacts.isEmpty()) {
			result = artifacts.get(0);
		}
		
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ArtifactEntity> list() {
		return getEntityManager().createQuery(
				"from com.andersonlfeitosa.mavendependencyanalyzer.entity.ArtifactEntity a").getResultList();
	}

}

