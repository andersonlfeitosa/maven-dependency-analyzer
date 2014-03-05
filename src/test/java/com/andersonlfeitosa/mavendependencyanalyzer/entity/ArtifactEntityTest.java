package com.andersonlfeitosa.mavendependencyanalyzer.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ArtifactEntityTest {

	@Test
	public void testToJSON() {
		// test1
		assertEquals("", new ArtifactEntity().toJSON());
		
		// test2
		ArtifactEntity app1 = new ArtifactEntity();
		app1.setArtifactId("app1");
		assertEquals("{\"name\":\"app1\",\"size\":0,\"imports\":[]}", app1.toJSON());
		
		//test3
		ArtifactEntity app2 = new ArtifactEntity();
		app2.setArtifactId("app2");
		DependencyEntity dApp1 = new DependencyEntity();
		dApp1.setDependency(app1);
		app2.getDependencies().add(dApp1);
		assertEquals("{\"name\":\"app2\",\"size\":1,\"imports\":[\"app1\"]}", app2.toJSON());

		//test4
		ArtifactEntity app3 = new ArtifactEntity();
		app3.setArtifactId("app3");
		DependencyEntity dApp2 = new DependencyEntity();
		dApp2.setDependency(app2);
		app3.getDependencies().add(dApp1);
		app3.getDependencies().add(dApp2);
		assertEquals("{\"name\":\"app3\",\"size\":2,\"imports\":[\"app1\",\"app2\"]}", app3.toJSON());
		
	}

}
