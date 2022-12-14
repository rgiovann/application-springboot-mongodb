package com.nelioalves.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.workshopmongo.domain.User;
import com.nelioalves.workshopmongo.dto.UserDTO;
import com.nelioalves.workshopmongo.repository.UserRepository;
import com.nelioalves.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public List<User> findAll(){
		return repo.findAll();
	}
	
	public User findById(String id){
		
		// returns a Optional object, a container object which may or may not contain a non-null value.
		Optional<User> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("WARNING: Object " + id + " not found."));		
		
	}
	
	// create method to insert user
    //
	public User insert(User obj) {
		return this.repo.save(obj);
	}
	
	public User UserFromDTO(UserDTO objDTO) {
		return new User(objDTO.getId(), objDTO.getName(),objDTO.getEmail());
	}
	
	// create method to delete user by id
    //	
	public void delete(String id) {
		this.findById(id);
		this.repo.deleteById(id);
		 

	}
	
	// create method to update user
	public User update(User obj) {
		User newObj = this.findById(obj.getId());	 
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}
}
