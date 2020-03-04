package co.gc.movieapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.gc.movieapi.entity.Movies;

public interface MovieRepository extends JpaRepository<Movies, Long>{

	List<Movies> findByCategoryIgnoreCase(String category);
	
	List<Movies> findByTitleContainingIgnoreCase(String title);
	
	
}
