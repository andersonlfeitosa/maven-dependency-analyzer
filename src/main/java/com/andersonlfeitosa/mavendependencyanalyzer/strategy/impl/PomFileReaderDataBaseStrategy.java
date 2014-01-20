package com.andersonlfeitosa.mavendependencyanalyzer.strategy.impl;

import java.io.File;
import java.io.IOException;

import com.andersonlfeitosa.mavendependencyanalyzer.dao.DaoFactory;
import com.andersonlfeitosa.mavendependencyanalyzer.dao.impl.ArtifactDaoImpl;
import com.andersonlfeitosa.mavendependencyanalyzer.entity.Artifact;
import com.andersonlfeitosa.mavendependencyanalyzer.strategy.PomFileReaderAbstract;

public class PomFileReaderDataBaseStrategy extends PomFileReaderAbstract {

	@Override
	public void read(File file) {
		execute(file);
		
		ArtifactDaoImpl dao = DaoFactory.getInstance().createArtifactDaoImpl();
		for (Artifact a : artifacts.values()) {
			dao.add(a);
		}
		
		try {
			dao.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
