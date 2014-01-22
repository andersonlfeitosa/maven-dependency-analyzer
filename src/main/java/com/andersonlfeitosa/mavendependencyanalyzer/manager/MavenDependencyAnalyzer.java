package com.andersonlfeitosa.mavendependencyanalyzer.manager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.io.FileUtils;

import com.andersonlfeitosa.mavendependencyanalyzer.entity.ArtifactEntity;
import com.andersonlfeitosa.mavendependencyanalyzer.entity.DependencyEntity;
import com.andersonlfeitosa.mavendependencyanalyzer.log.Log;
import com.andersonlfeitosa.mavendependencyanalyzer.util.GAVFormatter;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.Dependency;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.Parent;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.Project;
import com.thoughtworks.xstream.XStream;

public class MavenDependencyAnalyzer {

	private static final MavenDependencyAnalyzer instance = new MavenDependencyAnalyzer();

	private static final Log logger = Log.getInstance();

	private Map<String, Project> poms = new HashMap<String, Project>();
	
	private MavenDependencyAnalyzer() {
	}

	public static MavenDependencyAnalyzer getInstance() {
		return instance;
	}

	public void execute(String directory) {
		readPom(createXStream(), null, createFile(directory, "/pom.xml"));
		persistObjects();
	}

	private void persistObjects() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("dmpu");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		for (Project project : poms.values()) {
			ArtifactEntity artifact = new ArtifactEntity();
			artifact.setArtifactId(project.getArtifactId());
			artifact.setGroupId(project.getGroupId());
			artifact.setPackaging(project.getPackaging());
			artifact.setVersion(project.getVersion());

			entityManager.persist(artifact);
			
			if (project.getDependencies() != null) {
				for (Dependency dep : project.getDependencies()) {
					DependencyEntity dependency = new DependencyEntity();
					dependency.setArtifact(artifact);
					dependency.setClassifier(dep.getClassifier());
					dependency.setDependency(findArtifactOrCreate(entityManager, dep));
					dependency.setScope(dep.getScope());
					dependency.setType(dep.getType());					
					artifact.getDependencies().add(dependency);
					
					entityManager.persist(dependency);
				}
			}
			
			entityManager.persist(artifact);
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

	private File createFile(String... path) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < path.length; i++) {
			sb.append(path[i]);
			sb.append("/");
		}

		File f = new File(sb.toString());
		logger.debug("open file " + f.getAbsolutePath());
		return f;
	}

	private XStream createXStream() {
		XStream xstream = new XStream();
		xstream.alias("project", Project.class);
		xstream.alias("parent", Parent.class);
		xstream.alias("module", String.class);
		xstream.alias("dependency", Dependency.class);

		xstream.omitField(Project.class, "scm");
		xstream.omitField(Project.class, "properties");
		xstream.omitField(Project.class, "dependencyManagement");
		xstream.omitField(Project.class, "build");
		xstream.omitField(Parent.class, "relativePath");
		xstream.omitField(Dependency.class, "exclusions");

		return xstream;
	}

	private void readPom(XStream xstream, Project parent, File file) {
		logger.info("processing file " + file.getAbsolutePath());
		try {
			String sfile = FileUtils.readFileToString(file);
			Project project = (Project) xstream.fromXML(sfile);
			setGroupId(project, parent);
			setVersion(project, parent);
			poms.put(GAVFormatter.gavToString(project), project);
			if (project.getModules() != null && !project.getModules().isEmpty()) {
				for (String module : project.getModules()) {
					readPom(xstream, project, createFile(file.getParent(), "/", module, "/pom.xml"));
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void setVersion(Project project, Project parent) {
		if (project.getVersion() == null) {
			if (parent != null && project.getParent().getVersion() == null) {
				project.setVersion(parent.getVersion());
			} else {
				project.setVersion(project.getParent().getVersion());
			}
		}
	}

	private void setGroupId(Project project, Project parent) {
		if (project.getGroupId() == null) {
			if (parent != null && project.getParent().getGroupId() == null) {
				project.setGroupId(parent.getGroupId());
			} else {
				project.setGroupId(project.getParent().getGroupId());
			}
		}
	}

}
