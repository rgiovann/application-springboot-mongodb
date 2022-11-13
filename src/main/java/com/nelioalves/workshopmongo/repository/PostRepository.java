package com.nelioalves.workshopmongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.nelioalves.workshopmongo.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
	
	// Query method (mongodb springboot)
	List<Post> findByTitleContainingIgnoreCase(String text);
	
	//alternative query using annotation
	// Query annotation to declare finder queries directly on repository methods. 
	// Both attributes allow using a placeholdernotation of ?0, ?1 and so on.
	@Query("{ 'title': { $regex: ?0, $options: 'i' } }")
	List<Post> searchTitle(String text);

}
