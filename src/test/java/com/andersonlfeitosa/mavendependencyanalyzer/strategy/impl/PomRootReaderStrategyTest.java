package com.andersonlfeitosa.mavendependencyanalyzer.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.andersonlfeitosa.mavendependencyanalyzer.strategy.IPomReader;
import com.andersonlfeitosa.mavendependencyanalyzer.xml.object.Project;

public class PomRootReaderStrategyTest {
	
	private IPomReader reader;

	@Before
	public void init() {
		reader = new PomRootReaderStrategy();
	}
	
	@Test
	public void testReadApp1() {
		File file = createFile("poms/app1/pom.xml");
		Map<String, Project> poms = reader.read(file); 
		assertEquals(1, poms.values().size());
		
		Project project = poms.get("com.app1:app1:1.0.0.0-SNAPSHOT");
		
		assertEquals(null, project.getAggregatorProject());
		assertEquals(project.getArtifactId(), "app1");
		assertEquals(null, project.getDependencies());
		assertEquals(null, project.getDependencyManagement());
		assertEquals(project.getGroupId(), "com.app1");
		assertEquals("4.0.0", project.getModelVersion());
		assertEquals(null, project.getModules());
		assertEquals(null, project.getName());
		assertEquals("pom", project.getPackaging());
		
		assertEquals("com.pom", project.getParent().getGroupId());
		assertEquals("super-pom", project.getParent().getArtifactId());
		assertEquals("1.0.0.0", project.getParent().getVersion());
		
		assertEquals(null, project.getParentProject());
		
		assertEquals("com.app1", project.getProperties().get("project.groupId").getValue());
		assertEquals("app1", project.getProperties().get("project.artifactId").getValue());
		assertEquals("1.0.0.0-SNAPSHOT", project.getProperties().get("project.version").getValue());

		assertEquals("1.0.0.0-SNAPSHOT", project.getVersion());
	}
	
	@Test
	public void testReadApp2() {
		File file = createFile("poms/app2/pom.xml");
		Map<String, Project> poms = reader.read(file); 
		assertEquals(3, poms.values().size());
		
		Project project = poms.get("com.app2:app2:1.0.0.0-SNAPSHOT");
		
		assertEquals(null, project.getAggregatorProject());
		assertEquals(project.getArtifactId(), "app2");
		assertEquals(null, project.getDependencies());
		assertEquals(null, project.getDependencyManagement());
		assertEquals(project.getGroupId(), "com.app2");
		assertEquals("4.0.0", project.getModelVersion());
		assertEquals(1, project.getModules().size());
		assertEquals("app2", project.getName());
		assertEquals("pom", project.getPackaging());
		assertEquals("com.pom", project.getParent().getGroupId());
		assertEquals("super-pom", project.getParent().getArtifactId());
		assertEquals("1.0.0.0", project.getParent().getVersion());
		assertEquals(null, project.getParentProject());
		assertEquals("com.app2", project.getProperties().get("project.groupId").getValue());
		assertEquals("app2", project.getProperties().get("project.artifactId").getValue());
		assertEquals("1.0.0.0-SNAPSHOT", project.getProperties().get("project.version").getValue());
		assertEquals("1.0.0.0-SNAPSHOT", project.getVersion());
		
		Project project2 = poms.get("com.app2:mod1:1.0.0.0-SNAPSHOT");
		assertEquals(project, project2.getAggregatorProject());
		assertEquals(project2.getArtifactId(), "mod1");
		assertEquals(null, project2.getDependencies());
		assertEquals(null, project2.getDependencyManagement());
		assertEquals(project2.getGroupId(), "com.app2");
		assertEquals("4.0.0", project2.getModelVersion());
		assertEquals(1, project2.getModules().size());
		assertEquals("mod1", project2.getName());
		assertEquals("pom", project2.getPackaging());
		assertEquals("com.app2", project2.getParent().getGroupId());
		assertEquals("app2", project2.getParent().getArtifactId());
		assertEquals("1.0.0.0-SNAPSHOT", project2.getParent().getVersion());
		assertEquals(project, project2.getParentProject());
		assertEquals("com.app2", project2.getProperties().get("project.groupId").getValue());
		assertEquals("mod1", project2.getProperties().get("project.artifactId").getValue());
		assertEquals("1.0.0.0-SNAPSHOT", project2.getProperties().get("project.version").getValue());
		assertEquals("1.0.0.0-SNAPSHOT", project2.getVersion());
		
	}
	
	@Test
	public void testReadApp3() {
		File file = createFile("poms/app3/pom.xml");
		Map<String, Project> poms = reader.read(file); 
		assertEquals(4, poms.values().size());
		
		Project project = poms.get("com.app3:app3:1.0.0.0-SNAPSHOT");
		
		assertEquals(null, project.getAggregatorProject());
		assertEquals(project.getArtifactId(), "app3");
		assertEquals(null, project.getDependencies());
		assertEquals(null, project.getDependencyManagement());
		assertEquals(project.getGroupId(), "com.app3");
		assertEquals("4.0.0", project.getModelVersion());
		assertEquals(1, project.getModules().size());
		assertEquals("app3", project.getName());
		assertEquals("pom", project.getPackaging());
		assertEquals("com.pom", project.getParent().getGroupId());
		assertEquals("super-pom", project.getParent().getArtifactId());
		assertEquals("1.0.0.0", project.getParent().getVersion());
		assertEquals(null, project.getParentProject());
		assertEquals("com.app3", project.getProperties().get("project.groupId").getValue());
		assertEquals("app3", project.getProperties().get("project.artifactId").getValue());
		assertEquals("1.0.0.0-SNAPSHOT", project.getProperties().get("project.version").getValue());
		assertEquals("1.0.0.0-SNAPSHOT", project.getVersion());
		
		Project project2 = poms.get("com.app3:mod1:1.0.0.0-SNAPSHOT");
		assertEquals(project, project2.getAggregatorProject());
		assertEquals(project2.getArtifactId(), "mod1");
		assertEquals(null, project2.getDependencies());
		assertEquals(null, project2.getDependencyManagement());
		assertEquals(project2.getGroupId(), "com.app3");
		assertEquals("4.0.0", project2.getModelVersion());
		assertEquals(2, project2.getModules().size());
		assertEquals("mod1", project2.getName());
		assertEquals("pom", project2.getPackaging());
		assertEquals("com.app3", project2.getParent().getGroupId());
		assertEquals("app3", project2.getParent().getArtifactId());
		assertEquals("1.0.0.0-SNAPSHOT", project2.getParent().getVersion());
		assertEquals(project, project2.getParentProject());
		assertEquals("com.app3", project2.getProperties().get("project.groupId").getValue());
		assertEquals("mod1", project2.getProperties().get("project.artifactId").getValue());
		assertEquals("1.0.0.0-SNAPSHOT", project2.getProperties().get("project.version").getValue());
		assertEquals("1.0.0.0-SNAPSHOT", project2.getVersion());
		
	}
	

	private File createFile(String path) {
		File file = null;
		
		try {
			file = new File(getClass().getClassLoader().getResource(path).toURI());
		} catch (URISyntaxException e) {
			fail("error");
		}
		
		return file;
	}

}
