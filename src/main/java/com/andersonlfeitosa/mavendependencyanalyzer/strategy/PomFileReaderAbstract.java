package com.andersonlfeitosa.mavendependencyanalyzer.strategy;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import com.andersonlfeitosa.mavendependencyanalyzer.entity.ArtifactEntity;
import com.andersonlfeitosa.mavendependencyanalyzer.entity.DependencyEntity;

public abstract class PomFileReaderAbstract implements IPomReader {
	
	protected Map<String, ArtifactEntity> artifacts = new HashMap<String, ArtifactEntity>();

	@SuppressWarnings("unchecked")
	protected void execute(File baseDirectory) {
		Collection<File> fileList = FileUtils.listFiles(baseDirectory,
				FileFilterUtils.nameFileFilter("pom.xml"),
				TrueFileFilter.INSTANCE);

		for (File pomFile : fileList) {
			retrieveArtifacts(pomFile);
		}
	}

	@SuppressWarnings("unchecked")
	private void retrieveArtifacts(File pomFile) {
		SAXBuilder builder = new SAXBuilder();
		Document docPom = null;

		try {
			docPom = builder.build(pomFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Namespace nsPom = Namespace.getNamespace("http://maven.apache.org/POM/4.0.0");
		Element elRoot = docPom.getRootElement();
		String packaging = elRoot.getChildText("packaging", nsPom);
		if (packaging != null && !"pom".equalsIgnoreCase(packaging)) {
			
			ArtifactEntity artifact = new ArtifactEntity();
			artifact.setGroupId(elRoot.getChildText("groupId", nsPom));
			artifact.setArtifactId(elRoot.getChildText("artifactId", nsPom));
			artifact.setVersion(elRoot.getChildText("version", nsPom) == null ? "1.0.0.0-SNAPSHOT" : elRoot.getChildText("version", nsPom));
			artifact.setPackaging(packaging);

			Element elDependencies = elRoot.getChild("dependencies", nsPom);
			if (elDependencies != null) {
				List<Element> dependenciesList = elDependencies.getChildren();
				if (dependenciesList != null) {
					for (Element elDependency : dependenciesList) {
						DependencyEntity dependency = new DependencyEntity();
						dependency.setArtifact(artifact);
						dependency.setClassifier(elDependency.getChildText("classifier", nsPom));
						dependency.setScope(elDependency.getChildText("scope", nsPom));
						dependency.setType(elDependency.getChildText("type", nsPom));
						
						String groupId = elDependency.getChildText("groupId", nsPom);
						String artifactId = elDependency.getChildText("artifactId", nsPom);
						String version = elDependency.getChildText("version", nsPom);
						
						ArtifactEntity artifactDependent = artifacts.get(groupId + ":" + artifactId + ":" + version);
						if (artifactDependent == null) {
							artifactDependent = new ArtifactEntity();
							artifactDependent.setArtifactId(artifactId);
							artifactDependent.setGroupId(groupId);
							artifactDependent.setVersion(version);
							artifactDependent.setPackaging(getPackaging(dependency.getType(), dependency.getClassifier()));
							artifacts.put(groupId + ":" + artifactId + ":" + version, artifactDependent);
						}
						
						dependency.setDependency(artifactDependent);
						artifact.getDependencies().add(dependency);
					}
				}
			}
			artifacts.put(artifact.getGroupId() + ":" + artifact.getArtifactId() + ":" + artifact.getVersion(), artifact);
		}
	}

	private String getPackaging(String type, String classifier) {
		if (type == null || "jar".equalsIgnoreCase(type)) {
			return "jar";
		} else if ("ejb-client".equalsIgnoreCase(type) || "client".equalsIgnoreCase(classifier)) {
			return "ejb";
		}
		
		return type;
	}

}
