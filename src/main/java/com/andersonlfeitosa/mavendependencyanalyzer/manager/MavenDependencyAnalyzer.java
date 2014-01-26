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
import com.andersonlfeitosa.mavendependencyanalyzer.graphviz.Digraph;
import com.andersonlfeitosa.mavendependencyanalyzer.graphviz.Graph;
import com.andersonlfeitosa.mavendependencyanalyzer.graphviz.GraphvizEngine;
import com.andersonlfeitosa.mavendependencyanalyzer.graphviz.Node;
import com.andersonlfeitosa.mavendependencyanalyzer.log.Log;
import com.andersonlfeitosa.mavendependencyanalyzer.strategy.IPomReader;
import com.andersonlfeitosa.mavendependencyanalyzer.strategy.impl.DirectoryPomReaderStrategy;
import com.andersonlfeitosa.mavendependencyanalyzer.strategy.impl.PomRootReaderStrategy;
import com.andersonlfeitosa.mavendependencyanalyzer.util.GAVFormatter;
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
//		Project project = reader.getRoot();
//		plot(project, poms);
		persistObjects(poms);
	}

	private void plot(Project project, Map<String, Project> poms) {
		Graph graph = doChart(null, null, project, poms);

		GraphvizEngine engine = new GraphvizEngine(graph);
		engine.addType("png");
		engine.toFilePath("helloworld.png");
		engine.output();
		
	}

	private Graph doChart(Graph graph, Node node, Project project, Map<String, Project> poms) {
		if (graph == null) {
			graph = new Digraph("chart");
		}
		
		if (node == null) {
			node = graph.addNode(project.getArtifactId());
		}
		
		if (project.getModules() != null) {
			for (String module : project.getModules()) {
				Node nodeModule = graph.addNode(module);
				graph.addEdge(node, nodeModule);
			}
		}
		
		if (project.getDependencies() != null) {
			for (Dependency dependency : project.getDependencies()) {
				Node nodeDependency = graph.addNode(GAVFormatter.gavToString(dependency));
				graph.addEdge(node, nodeDependency);
				doChart(graph, node, project, poms);
			}
		}
		
		return graph;
	}

	private IPomReader createPomReader(boolean directory) {
		return directory ? new DirectoryPomReaderStrategy() : new PomRootReaderStrategy();
	}

	private void persistObjects(Map<String, Project> poms) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("dmpu");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		for (Project project : poms.values()) {
			ArtifactEntity artifact = new ArtifactEntity();
			artifact.setArtifactId(project.getArtifactId());
			artifact.setGroupId(project.getGroupId());
			artifact.setPackaging(Packaging.getPackaging(project.getPackaging()));
			artifact.setVersion(project.getVersion());

			// TODO delete, in future
//			if (artifact.getVersion() != null) {
				logger.debug(artifact);
				entityManager.persist(artifact);
//			}
			
			if (project.getDependencies() != null) {
				for (Dependency dep : project.getDependencies()) {
					DependencyEntity dependency = new DependencyEntity();
					dependency.setArtifact(artifact);
					dependency.setClassifier(dep.getClassifier());
					dependency.setDependency(findArtifactOrCreate(entityManager, dep));
					dependency.setScope(Scope.getScope(dep.getScope()));
					dependency.setType(Type.getType(dep.getType()));
					artifact.getDependencies().add(dependency);
					
//					if (dependency.getDependency().getVersion() != null) {
						logger.debug(dependency);
						entityManager.persist(dependency);
//					}
				}
			}

//			if (artifact.getVersion() != null) {
				logger.debug(artifact);
				entityManager.persist(artifact);
//			}
		}
		
		transaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}

	private ArtifactEntity findArtifactOrCreate(EntityManager entityManager, Dependency dependency) {
		ArtifactEntity result = null;
		
		Query query = entityManager.createQuery(
				"from com.andersonlfeitosa.mavendependencyanalyzer.entity.ArtifactEntity a "
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
