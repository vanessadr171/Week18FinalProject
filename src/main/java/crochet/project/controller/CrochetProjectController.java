package crochet.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PatchExchange;

import crochet.project.controller.model.CrochetProjectData;
import crochet.project.controller.model.CrochetProjectData.ArtistData;
import crochet.project.controller.model.CrochetProjectData.MaterialsData;
import crochet.project.service.CrochetProjectService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/crochet_project")
@Slf4j
public class CrochetProjectController {
	
	@Autowired
	private CrochetProjectService crochetProjectService;
	
	@PostMapping("/artist")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ArtistData insertArtist(@RequestBody ArtistData artistData) {
		log.info("Inserting a new artist", artistData);
		return crochetProjectService.saveArtist(artistData);
	}
	@PutMapping("/artist/{artistId}")
	public ArtistData modifyArtist(@RequestBody ArtistData artistData, @PathVariable Long artistId) {
		artistData.setArtistId(artistId);
		log.info("Updating artist with ID=", artistId);
		return crochetProjectService.saveArtist(artistData);
	}
	
	@GetMapping("/artist")
	public List<ArtistData> retrieveAllArtist(){	
		log.info("Retrieving all Artist");
		return crochetProjectService.retrieveAllArtist();
	}
	
	
	@PostMapping("/artist/{artistId}/crochetProject")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CrochetProjectData createCrochetProject(@PathVariable Long artistId, @RequestBody CrochetProjectData crochetProjectData) {
		log.info("Creating a new crochet project {} for artist with ID=", crochetProjectData, artistId);
		return crochetProjectService.saveCrochetProject(artistId, crochetProjectData);
	}
	
	@PutMapping("/artist/{artistId}/crochetProject/{crochetProjectId}")
	public CrochetProjectData modifyCrochetProject(@PathVariable Long artistId, @PathVariable Long crochetProjectId, @RequestBody CrochetProjectData crochetProjectData) {
		crochetProjectData.setCrochetProjectId(crochetProjectId);
		log.info("Updating crochet project {} for artist with ID=", crochetProjectData, artistId);
		return crochetProjectService.saveCrochetProject(artistId, crochetProjectData);
	}
	
	@GetMapping("/crochetProject")
	public List<CrochetProjectData> retrieveAllProjects(){
		log.info("Retrieving all Crochet Projects");
		return crochetProjectService.retrieveAllProjects();
	}
	
	@GetMapping("crochetProject/{crochetProjectId}")
	public CrochetProjectData retrieveProjectById(@PathVariable Long crochetProjectId) {
		log.info("Retrieving Crochet Project with ID=", crochetProjectId);
		return crochetProjectService.retrieveProjectById(crochetProjectId);
	}
	
	@DeleteMapping("/crochetProject")
	public void deleteAllProjects() {
		log.info("Attempting to delete all Crochet Projects");
		throw new UnsupportedOperationException("Deleting all crochet projects is not allowed.");
	}
	
	@DeleteMapping("/{crochetProjectId}")
	public Map<String, String> deleteProjectById(@PathVariable Long crochetProjectId){
		log.info("Deleting crochet Project with ID=", crochetProjectId);
		
		crochetProjectService.deleteProjectById(crochetProjectId);
		
		return Map.of("message", "Deletion of crochet project with ID=" + crochetProjectId + "was successful.");
	}
	
	@PostMapping("crochetProject/{crochetProjectId}/materials")
	@ResponseStatus(code = HttpStatus.CREATED)
	public MaterialsData InsertMaterials(@PathVariable Long crochetProjectId, @RequestBody MaterialsData materialData) {
		log.info("Inserting materials {} for crochet project with ID=", materialData, crochetProjectId);
		return crochetProjectService.saveMaterials(crochetProjectId, materialData);
	}

}
