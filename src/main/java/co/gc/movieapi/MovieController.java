package co.gc.movieapi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.gc.movieapi.dao.MovieRepository;
import co.gc.movieapi.entity.Movies;

@RestController
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepo;
	
	@GetMapping("/movies")
	public List<Movies> list(@RequestParam(value="category", required=false) String category,
			@RequestParam(value="random", required=false) Integer random,
			@RequestParam(value="title", required=false) String title) {
		
		List<Movies> allMovies = movieRepo.findAll();
		
		if ((category == null || category.isEmpty()) && (random == null) && (title == null || title.isEmpty())) {
		return allMovies;
		} else if (random == null && (title==null || title.isEmpty())) {
		return movieRepo.findByCategoryIgnoreCase(category);
		} else if (title ==null || title.isEmpty()){
			List<Movies> randomMovies = new ArrayList<>();
			for (int i=0; i<random; i++) {
				randomMovies.add(allMovies.get((int) (Math.random() * (allMovies.size()-1) + 1)));
			}
			return randomMovies;
		} else {
			return movieRepo.findByTitleContainingIgnoreCase(title);
		}
		
	}
	
	@GetMapping("/movies/random")
	public Movies random(@RequestParam(value="category", required=false) String category) {
		if (category == null || category.isEmpty()) {
			List<Movies> allMovies = movieRepo.findAll();
			int randomNumberAll = (int) (Math.random() * allMovies.size() + 1);
			return allMovies.get(randomNumberAll);
		} else  {
			List<Movies> moviesInCategory = movieRepo.findByCategoryIgnoreCase(category);
			int randomNumberCat = (int) (Math.random() * (moviesInCategory.size()-1) + 1);
			return moviesInCategory.get(randomNumberCat);
		}
	}
	
	
	@GetMapping("/categories")
	public Set<String> listCategories(){
		Set<String> categories = new HashSet<>();
		List<Movies> allMovies = movieRepo.findAll();
		for (Movies movie : allMovies) {
			categories.add(movie.getCategory());
		}
		return categories;
	}


}
