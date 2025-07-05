package crochet.project.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Materials {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long materialId;
	
	private String yarnWeight;
	private String hookSize;
	private String yarnColor;
	private String yarnMaterial;
	private String otherMaterial;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "materials", cascade  =CascadeType.PERSIST)
	private Set<CrochetProject> crochetProject = new HashSet<>();

}
