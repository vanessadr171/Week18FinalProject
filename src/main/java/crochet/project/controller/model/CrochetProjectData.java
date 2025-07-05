package crochet.project.controller.model;

import java.util.HashSet;
import java.util.Set;

import crochet.project.entity.Artist;
import crochet.project.entity.CrochetProject;
import crochet.project.entity.Materials;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrochetProjectData {

	private Long crochetProjectId;	
	private String projectName;
	private String projectType;
	private String experienceLvl;
	private String completionTime;
	private String pattern;
	private String notes;
	private Set<MaterialsData> materials = new HashSet<>();


	public CrochetProjectData(CrochetProject crochetProject) {
		crochetProjectId = crochetProject.getCrochetProjectId();
		projectName = crochetProject.getProjectName();
		projectType = crochetProject.getProjectType();
		experienceLvl = crochetProject.getExperienceLvl();
		completionTime = crochetProject.getCompletionTime();
		pattern = crochetProject.getPattern();
		notes = crochetProject.getNotes();
		
		for (Materials material : crochetProject.getMaterials()) {
			materials.add(new MaterialsData(material));
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class MaterialsData {
		private Long materialId;
		private String yarnWeight;
		private String hookSize;
		private String yarnColor;
		private String yarnMaterial;
		private String otherMaterial;
		
		public MaterialsData(Materials material) {
			materialId = material.getMaterialId();
			yarnWeight = material.getYarnWeight();
			hookSize = material.getHookSize();
			yarnColor = material.getYarnColor();
			yarnMaterial = material.getYarnMaterial();
			otherMaterial = material.getOtherMaterial();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class ArtistData{
		private Long artistId;
		private String userName;
		private String website;
		private String email;
		private String instagram;
		private Set<CrochetProjectData> crochetProjects = new HashSet<>();
		
		public ArtistData(Artist artist) {
			artistId = artist.getArtistId();
			userName = artist.getUserName();
			website = artist.getWebsite();
			email = artist.getEmail();
			instagram = artist.getInstagram();
			
			for (CrochetProject proj : artist.getCrochetProject()) {
				crochetProjects.add(new CrochetProjectData(proj));
			}
		}
	}
}
