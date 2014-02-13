package com.andersonlfeitosa.mavendependencyanalyzer.manager;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.andersonlfeitosa.mavendependencyanalyzer.entity.ArtifactEntity;
import com.andersonlfeitosa.mavendependencyanalyzer.entity.DependencyEntity;
import com.andersonlfeitosa.mavendependencyanalyzer.entity.Packaging;
import com.andersonlfeitosa.mavendependencyanalyzer.entity.Scope;
import com.andersonlfeitosa.mavendependencyanalyzer.entity.Type;
import com.andersonlfeitosa.mavendependencyanalyzer.log.Log;
import com.andersonlfeitosa.mavendependencyanalyzer.strategy.IPomReader;
import com.andersonlfeitosa.mavendependencyanalyzer.strategy.impl.DirectoryPomReaderStrategy;
import com.andersonlfeitosa.mavendependencyanalyzer.strategy.impl.PomRootReaderStrategy;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.object.Dependency;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.object.Project;

public class MavenDependencyAnalyzer {

	private static final MavenDependencyAnalyzer instance = new MavenDependencyAnalyzer();

	private static final Log logger = Log.getInstance();

	private MavenDependencyAnalyzer() {
	}

	public static MavenDependencyAnalyzer getInstance() {
		return instance;
	}

	public void execute(String fileOrDirectory) {
		File file = new File(fileOrDirectory);
		IPomReader reader = createPomReader(file.isDirectory());
		Map<String, Project> poms = reader.read(file);
		persistObjects(poms);
		
		plotGraph(poms);
		
	}

	private void plotGraph(Map<String, Project> poms) {
		StringBuilder sb = new StringBuilder();
		
		for (Project project : poms.values()) {
			
			sb.append("{\"name\":");
			sb.append("\"");
			sb.append(project.getArtifactId());
			sb.append("\",\"size\":3938,\"imports\":[");
			
			if (project.getDependencies() != null) {
				for (int i = 0; i < project.getDependencies().size(); i++) {
					Dependency dependency = project.getDependencies().get(i);
					sb.append("\"");
					sb.append(dependency.getArtifactId());
					sb.append("\"");
					
					if (i < project.getDependencies().size()-1) {
						sb.append(",");
					}
				}
			}
			
			sb.append("]},");
			sb.append("\n");
		}
		
		String data = sb.toString();
		logger.debug(data.substring(0, data.length()-2));
		
	}

	private IPomReader createPomReader(boolean directory) {
		return directory ? new DirectoryPomReaderStrategy()
				: new PomRootReaderStrategy();
	}

	private void persistObjects(Map<String, Project> poms) {
		EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("dmpu");
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		for (Project project : poms.values()) {
			ArtifactEntity artifact = findArtifactOrCreate(
					entityManager, 
					new Dependency(project.getGroupId(),
							project.getArtifactId(),
							project.getVersion()));
			artifact.setPackaging(Packaging.getPackaging(project.getPackaging()));

			logger.debug(artifact);
			entityManager.persist(artifact);

			if (project.getDependencies() != null) {
				for (Dependency dep : project.getDependencies()) {
					DependencyEntity dependency = new DependencyEntity();
					dependency.setArtifact(artifact);
					dependency.setClassifier(dep.getClassifier());
					dependency.setDependency(findArtifactOrCreate(
							entityManager, dep));
					dependency.setScope(Scope.getScope(dep.getScope()));
					dependency.setType(Type.getType(dep.getType()));
					artifact.getDependencies().add(dependency);

					logger.debug(dependency);
					entityManager.persist(dependency);
				}
			}

			logger.debug(artifact);
			entityManager.persist(artifact);
		}

		transaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}

	private ArtifactEntity findArtifactOrCreate(EntityManager entityManager,
			Dependency dependency) {
		ArtifactEntity result = null;

		Query query = entityManager
				.createQuery("from com.andersonlfeitosa.mavendependencyanalyzer.entity.ArtifactEntity a "
						+ "where a.groupId = :groupId "
						+ "and a.artifactId = :artifactId "
						+ "and a.version = :version");

		query.setParameter("groupId", dependency.getGroupId());
		query.setParameter("artifactId", dependency.getArtifactId());
		query.setParameter("version", dependency.getVersion());

		@SuppressWarnings("unchecked")
		List<ArtifactEntity> artifacts = query.getResultList();

		if (artifacts != null && !artifacts.isEmpty()) {
			result = artifacts.get(0);
		}

		if (result == null) {
			result = new ArtifactEntity();
			result.setArtifactId(dependency.getArtifactId());
			result.setGroupId(dependency.getGroupId());
			result.setVersion(dependency.getVersion());
		}

		return result;
	}

}
