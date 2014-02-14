package com.andersonlfeitosa.mavendependencyanalyzer.report;

import java.util.List;

import com.andersonlfeitosa.mavendependencyanalyzer.entity.ArtifactEntity;

public interface IReport {
	
	public void print(List<ArtifactEntity> artifacts);

}
