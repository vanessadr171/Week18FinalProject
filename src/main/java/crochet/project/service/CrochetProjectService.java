package crochet.project.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crochet.project.controller.model.CrochetProjectData;
import crochet.project.controller.model.CrochetProjectData.ArtistData;
import crochet.project.controller.model.CrochetProjectData.MaterialsData;
import crochet.project.dao.ArtistDao;
import crochet.project.dao.CrochetProjectDao;
import crochet.project.dao.MaterialDao;
import crochet.project.entity.Artist;
import crochet.project.entity.CrochetProject;
import crochet.project.entity.Materials;

@Service
public class CrochetProjectService {
	
	@Autowired
	private CrochetProjectDao crochetProjectDao;
	
	@Autowired
	private ArtistDao artistDao;
	
	@Autowired
	private MaterialDao materialDao;

	@Transactional(readOnly = false)
	public CrochetProjectData saveCrochetProject(Long artistId, CrochetProjectData crochetProjectData) {
		Artist artist = findArtistById(artistId);
		
		CrochetProject crochetProject = findOrCreateCrochetproject(crochetProjectData.getCrochetProjectId());
		
		copyCrochetProjectFields(crochetProject, crochetProjectData);
		
		crochetProject.setArtist(artist);
		artist.getCrochetProject().add(crochetProject);
		
		CrochetProject dbCrochetProject = crochetProjectDao.save(crochetProject);
		
		return new CrochetProjectData(dbCrochetProject);
	}

	private void copyCrochetProjectFields(CrochetProject crochetProject, CrochetProjectData crochetProjectData) {
		crochetProject.setCrochetProjectId(crochetProjectData.getCrochetProjectId());
		crochetProject.setProjectName(crochetProjectData.getProjectName());
		crochetProject.setProjectType(crochetProjectData.getProjectType());
		crochetProject.setExperienceLvl(crochetProjectData.getExperienceLvl());
		crochetProject.setCompletionTime(crochetProjectData.getCompletionTime());
		crochetProject.setPattern(crochetProjectData.getPattern());
		crochetProject.setNotes(crochetProjectData.getNotes());
		
	}

	private CrochetProject findOrCreateCrochetproject(Long crochetProjectId) {
		CrochetProject crochetProject;
		
		if(Objects.isNull(crochetProjectId)) {
			crochetProject = new CrochetProject();
		}else {
			crochetProject = findCrochetProjectById(crochetProjectId);
		}
		
		return crochetProject;
	}

	private CrochetProject findCrochetProjectById(Long crochetProjectId) {
		return crochetProjectDao.findById(crochetProjectId).orElseThrow(() -> new NoSuchElementException("Crochet project with ID=" + crochetProjectId + " does not exist."));
	}

	@Transactional(readOnly = false)
	public ArtistData saveArtist(ArtistData artistData) {
		Long artistId = artistData.getArtistId();
		String email = artistData.getEmail();
		Artist artist = findOrCreateArtist(artistId, email);
		
		setFeildsInArtist(artist, artistData);
		
		return new ArtistData(artistDao.save(artist));
		
	}

	private void setFeildsInArtist(Artist artist, ArtistData artistData) {
		artist.setEmail(artistData.getEmail());
		artist.setArtistId(artistData.getArtistId());
		artist.setInstagram(artistData.getInstagram());
		artist.setUserName(artistData.getUserName());
		artist.setWebsite(artistData.getWebsite());
	}

	private Artist findOrCreateArtist(Long artistId, String email) {
		Artist artist;
		
		if(Objects.isNull(artistId)) {
			Optional<Artist> opArtist = artistDao.findByEmail(email);
			
			if(opArtist.isPresent()) {
				throw new DuplicateKeyException("Artist with email " + email + " already exist.");
			}
			
			artist = new Artist();
		}else {
			artist = findArtistById(artistId);
		}
		return artist;
	}

	private Artist findArtistById(Long artistId) {
		return artistDao.findById(artistId).orElseThrow(() -> new NoSuchElementException("Artist with ID=" + artistId + " was not found."));
	}

	@Transactional(readOnly = false)
	public MaterialsData saveMaterials(Long crochetProjectId, MaterialsData materialData) {
		CrochetProject crochetProject = findCrochetProjectById(crochetProjectId);
		Long materialId = materialData.getMaterialId();
		Materials material = findOrCreateMaterial(crochetProjectId, materialId);
		
		copyMaterialFields(material, materialData);
		
		material.getCrochetProject().add(crochetProject);
		crochetProject.getMaterials().add(material);
		
		Materials dbMaterial = materialDao.save(material);
		
		return new MaterialsData(dbMaterial);
	}

	private void copyMaterialFields(Materials material, MaterialsData materialData) {
		material.setMaterialId(materialData.getMaterialId());
		material.setHookSize(materialData.getHookSize());
		material.setYarnWeight(materialData.getYarnWeight());
		material.setYarnColor(materialData.getYarnColor());
		material.setYarnMaterial(materialData.getYarnMaterial());
		material.setOtherMaterial(materialData.getOtherMaterial());
		
	}

	private Materials findOrCreateMaterial(Long crochetProjectId, Long materialId) {
		Materials material;
		
		if(Objects.isNull(materialId)) {
			material = new Materials();
		}else {
			material = findMaterialById(crochetProjectId, materialId);
		}
		return material;
	}

	private Materials findMaterialById(Long crochetProjectId, Long materialId) {
		return materialDao.findById(materialId).orElseThrow(() -> new NoSuchElementException("Material with ID=" + materialId + " does not Exist."));
	}

	@Transactional(readOnly = true)
	public List<ArtistData> retrieveAllArtist() {
		List<Artist> artist = artistDao.findAll();
		List<ArtistData> response = new LinkedList<>();
		
		for(Artist art : artist) {
			ArtistData ad = new ArtistData(art);
			ad.getCrochetProjects().clear(); 
			
			response.add(ad);
			
		}
		return response;
	}

	@Transactional(readOnly = true)
	public List<CrochetProjectData> retrieveAllProjects() {
		List<CrochetProject> projects  = crochetProjectDao.findAll();
		List<CrochetProjectData> response = new LinkedList<>();
		
		for(CrochetProject project : projects) {
			CrochetProjectData cpd = new CrochetProjectData(project);
			cpd.getMaterials().clear();
			
			response.add(cpd);
		}
		return response;
	}

	@Transactional(readOnly = true)
	public CrochetProjectData retrieveProjectById(Long crochetProjectId) {
		CrochetProject crochetProject = findCrochetProjectById(crochetProjectId);
		return new CrochetProjectData(crochetProject);
	}

	@Transactional(readOnly = false)
	public void deleteProjectById(Long crochetProjectId) {
		CrochetProject crochetProject = findCrochetProjectById(crochetProjectId);
		crochetProjectDao.delete(crochetProject);
		
	}

}
