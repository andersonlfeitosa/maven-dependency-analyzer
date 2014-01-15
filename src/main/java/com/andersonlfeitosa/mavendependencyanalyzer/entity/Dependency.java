package com.andersonlfeitosa.mavendependencyanalyzer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DEPENDENCY")
public class Dependency implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idDependency")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "idArtifact", referencedColumnName = "idArtifact")
	private Artifact artifact;

	@ManyToOne
	@JoinColumn(name = "idArtifactDependentOn", referencedColumnName = "idArtifact")
	private Artifact dependency;

	@Column(nullable = false)
	private String scope;

	@Column(nullable = false)
	private String classifier;

	@Column(nullable = false)
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getClassifier() {
		return classifier;
	}

	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Artifact getDependency() {
		return dependency;
	}

	public void setDependency(Artifact dependency) {
		this.dependency = dependency;
	}

	public Artifact getArtifact() {
		return artifact;
	}

	public void setArtifact(Artifact artifact) {
		this.artifact = artifact;
	}

}
