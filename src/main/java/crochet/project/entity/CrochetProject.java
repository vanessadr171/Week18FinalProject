package crochet.project.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class CrochetProject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long crochetProjectId;
	
	private String projectName;
	private String projectType;
	private String experienceLvl;
	private String completionTime;
	private String pattern;
	private String notes;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "crochet_project_materials", 
	joinColumns = @JoinColumn(name = "crochet_project_id"), 
	inverseJoinColumns = @JoinColumn(name = "material_ id"))
	private Set<Materials> materials = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "artist_id")
	private Artist artist;

}
