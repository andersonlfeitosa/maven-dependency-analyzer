package com.andersonlfeitosa.mavendependencyanalyzer.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DependencyEntityTest {

	@Test
	public void testToJSON() {
		// test1
		assertEquals("", new DependencyEntity().toJSON());
		
		// test2
		ArtifactEntity app2 = new ArtifactEntity();
		DependencyEntity dApp1 = new DependencyEntity();
		dApp1.setDependency(app2);
		app2.setArtifactId("app2");
		assertEquals("\"app2\"", dApp1.toJSON());
		
		// test3
		ArtifactEntity app3 = new ArtifactEntity();
		DependencyEntity dApp2 = new DependencyEntity();
		dApp2.setDependency(app3);
		assertEquals("", dApp2.toJSON());
	}

}
