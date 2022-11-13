package com.nelioalves.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nelioalves.workshopmongo.domain.Post;
import com.nelioalves.workshopmongo.domain.User;
import com.nelioalves.workshopmongo.dto.UserDTO;
import com.nelioalves.workshopmongo.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserResources {
	
	@Autowired
	private UserService service;
	
	@GetMapping
 	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = service.findAll();
		// transform list User to UserDTO using Map and lambda expression
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/{id}")
 	public ResponseEntity< UserDTO> findById(@PathVariable String id) {
		User obj = service.findById(id);
  		return ResponseEntity.ok().body(new UserDTO(obj));
	}
	
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody UserDTO objDTO){
		User obj = service.UserFromDTO(objDTO);
		obj = service.insert(obj);
		// http 201 response expects a header with the address (URI) where you added the new 
		// row
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}	
	
	// @DeleteMapping is a DELETE HTTP request annotation
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id){
		service.delete(id);
		return ResponseEntity.noContent().build();   // response with no content HTTP code 204
	}	
	
	// @PutMapping is a PUT HTTP request annotation
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody UserDTO objDTO, @PathVariable String id){
		User obj = service.UserFromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();   // response with no content HTTP code 204

	}	
	
	@GetMapping(value = "/{id}/posts")
 	public ResponseEntity< List<Post>> findPosts(@PathVariable String id) {
		User obj = service.findById(id);
  		return ResponseEntity.ok().body(obj.getPosts());
	}	

}
