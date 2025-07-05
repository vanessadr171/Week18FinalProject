package crochet.project.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import crochet.project.entity.Artist;

public interface ArtistDao extends JpaRepository<Artist, Long> {

	Optional<Artist> findByEmail(String email);

}
