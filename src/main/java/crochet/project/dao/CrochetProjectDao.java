package crochet.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import crochet.project.entity.CrochetProject;

public interface CrochetProjectDao extends JpaRepository<CrochetProject, Long> {

}
