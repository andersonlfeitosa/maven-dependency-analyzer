package com.andersonlfeitosa.mavendependencyanalyzer.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ARTIFACT", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"artifactId", "groupId", "version" }) })
public class ArtifactEntity implements Serializable, JSONable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idArtifact")
	private Long id;

	@Column(nullable = false)
	private String groupId;

	@Column(nullable = false)
	private String artifactId;

	@Enumerated(EnumType.STRING)
	private Packaging packaging;

	@Column(nullable = false)
	private String version;

	@OneToMany(mappedBy = "dependency", fetch = FetchType.EAGER)
	private List<DependencyEntity> dependencies = new ArrayList<DependencyEntity>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<DependencyEntity> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<DependencyEntity> dependencies) {
		this.dependencies = dependencies;
	}

	public Packaging getPackaging() {
		return packaging;
	}

	public void setPackaging(Packaging packaging) {
		this.packaging = packaging;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((artifactId == null) ? 0 : artifactId.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArtifactEntity other = (ArtifactEntity) obj;
		if (artifactId == null) {
			if (other.artifactId != null)
				return false;
		} else if (!artifactId.equals(other.artifactId))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ArtifactEntity [groupId=" + groupId + ", artifactId="
				+ artifactId + ", packaging=" + packaging + ", version="
				+ version + "]";
	}

	public String toJSON() {
		StringBuilder sb = new StringBuilder();

		if (getArtifactId() != null && getDependencies() != null) {
			sb.append("{\"name\":");
			sb.append("\"");
			sb.append(getArtifactId());
			sb.append("\",\"size\":");
			sb.append(getDependencies().size());
			sb.append(",\"imports\":[");

			Iterator<DependencyEntity> it = getDependencies().iterator();
			while (it.hasNext()) {
				sb.append(it.next().toJSON());
				if (it.hasNext()) {
					sb.append(",");
				}
			}
			
			sb.append("]}");
		}
		
		return sb.toString();
	}

}
