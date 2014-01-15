package com.andersonlfeitosa.mavendependencyanalyzer.strategy;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import com.andersonlfeitosa.mavendependencyanalyzer.entity.Artifact;
import com.andersonlfeitosa.mavendependencyanalyzer.entity.Dependency;

public abstract class PomFileReaderAbstract implements IPomFileReader {

	@SuppressWarnings("unchecked")
	public static List<Artifact> execute(File baseDirectory) {
		Collection<File> fileList = FileUtils.listFiles(baseDirectory,
				FileFilterUtils.nameFileFilter("pom.xml"),
				TrueFileFilter.INSTANCE);

		List<Artifact> artifacts = new ArrayList<Artifact>();

		for (File pomFile : fileList) {
			artifacts.add(retrieve(pomFile));
		}

		return artifacts;
	}

	private static Artifact retrieve(File pomFile) {
		Artifact artifact = null;
		SAXBuilder builder = new SAXBuilder();
		Document docPom = null;

		try {
			docPom = builder.build(pomFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Namespace nsPom = Namespace
				.getNamespace("http://maven.apache.org/POM/4.0.0");
		Element elementoPomRaiz = docPom.getRootElement();

		String packaging = elementoPomRaiz.getChildText("packaging", nsPom);
		if (packaging != null && !"pom".equalsIgnoreCase(packaging)) {
			artifact = new Artifact();
			artifact.setGroupId(elementoPomRaiz.getChildText("groupId", nsPom));
			artifact.setArtifactId(elementoPomRaiz.getChildText("artifactId",
					nsPom));
			artifact.setVersion(elementoPomRaiz.getChildText("version", nsPom));
			artifact.setPackaging(packaging);

			Element dependencies = elementoPomRaiz.getChild("dependencies",
					nsPom);
			if (dependencies != null) {
				@SuppressWarnings("unchecked")
				List<Element> dependenciesList = dependencies.getChildren();

				if (dependenciesList != null) {
					for (Element dependency : dependenciesList) {
						Dependency d = new Dependency();
						d.setArtifact(artifact);
						d.setClassifier(dependency.getChildText("classifier",
								nsPom));
						// d.set (dependency.getChildText("classifier", nsPom));

						// d.setClassifier(classifier);

					}
				}

			}

		}

		return artifact;
	}

}
