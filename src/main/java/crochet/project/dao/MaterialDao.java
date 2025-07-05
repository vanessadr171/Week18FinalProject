package crochet.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import crochet.project.entity.Materials;

public interface MaterialDao extends JpaRepository<Materials, Long> {

}
