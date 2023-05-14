package com.incedo.smart_inventory.controllers;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incedo.smart_inventory.controllers.models.ChangePasswordData;
import com.incedo.smart_inventory.controllers.models.ErrorData;
import com.incedo.smart_inventory.entities.Employee;
import com.incedo.smart_inventory.entities.EmployeeRoles;
import com.incedo.smart_inventory.entities.Godown;
import com.incedo.smart_inventory.repositories.EmployeeRepository;
import com.incedo.smart_inventory.repositories.EmployeeRolesRepository;
import com.incedo.smart_inventory.repositories.GodownRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	
	private static final String PATH = "/employees";
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EmployeeRolesRepository employeeRolesRepository;
	
	@Autowired
	GodownRepository godownRepository;
	
	@PostMapping(path=PATH)
	public ResponseEntity addEmployees(@RequestBody Employee employee) {
		if(employee.getRole() != null && employee.getRole().getId() > 0) {
			Optional<EmployeeRoles> employeeRoleFound = employeeRolesRepository.findById(employee.getRole().getId());
			
			if(employeeRoleFound.isEmpty()) {
				return new ResponseEntity<String>("Employee role with the given id is not found", HttpStatus.NOT_FOUND);
			}
			employee.setRole(employeeRoleFound.get());
		}
		
		employee.setPassword(BCrypt.hashpw(employee.getUsername(), BCrypt.gensalt()));
		
		if (employee.getGodown() != null && employee.getGodown().getId() > 0) {
			Optional<Godown> godownFound = godownRepository.findById(employee.getGodown().getId());
			
			if (godownFound.isEmpty()) {
				return new ResponseEntity<String>("Godown with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			employee.setGodown(godownFound.get());
		}

		employee.setIsLocked(false);
		
		Employee saved = employeeRepository.save(employee);
		return new ResponseEntity<Employee>(saved, HttpStatus.CREATED);
	}
	
	@PatchMapping(path=PATH + "/{id}/password")
	public ResponseEntity<ErrorData> changePassword(@PathVariable int id, @RequestBody ChangePasswordData changePasswordData) {
		Optional<Employee> employeeFound = employeeRepository.findById(id);
        
		if (employeeFound.isEmpty()) {
        	return new ResponseEntity<ErrorData>(new ErrorData("EMPID_NOT_FOUND", "Employee with the given id not found"), HttpStatus.NOT_FOUND);
        }
		
		if (!BCrypt.checkpw(changePasswordData.getCurrentPassword(), employeeFound.get().getPassword())) {
			return new ResponseEntity<ErrorData>(new ErrorData("WRONG_PASSWORD", "The current password provided is incorrect"), HttpStatus.UNAUTHORIZED);
		}
        
        employeeFound.get().setPassword(BCrypt.hashpw(changePasswordData.getNewPassword(), BCrypt.gensalt()));
        
        employeeRepository.save(employeeFound.get());
        
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping(path=PATH + "/{id}/unlock")
	public ResponseEntity<String> unlockEmployee(@PathVariable int id) {
		Optional<Employee> employeeFound = employeeRepository.findById(id);
		
		if(employeeFound.isEmpty()) {
			return new ResponseEntity<String>("Employee with the given id not found.", HttpStatus.NOT_FOUND);
		}
        
		employeeFound.get().setIsLocked(false);
    	employeeRepository.save(employeeFound.get());
        
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	@GetMapping(path=PATH)
	public ResponseEntity<List<Employee>> fetchAllEmployees(@RequestParam(name = "godownId", required = false) Integer godownId) {
		if (godownId == null) {
			return new ResponseEntity<List<Employee>>(employeeRepository.findAllByIsLocked(false), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Employee>>(employeeRepository.findAllByGodownIdAndIsLocked(godownId, false), HttpStatus.OK);
		}
	}
	
	@GetMapping(path=PATH + "/locked")
	public ResponseEntity<List<Employee>> fetchAllLockedEmployees(@RequestParam(name = "godownId", required = false) Integer godownId) {
		if (godownId == null) {
			return new ResponseEntity<List<Employee>>(employeeRepository.findAllByIsLocked(true), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Employee>>(employeeRepository.findAllByGodownIdAndIsLocked(godownId, true), HttpStatus.OK);
		}
	}
	

	@GetMapping(path=PATH + "/{id}")
    public ResponseEntity getById(@PathVariable int id) {

        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return new ResponseEntity<Employee>(employee.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<String>("Employee with the given id not found.", HttpStatus.NOT_FOUND);
        }
    }
	
	@DeleteMapping(path=PATH + "/{id}")
	public ResponseEntity deleteEmployee(@PathVariable int id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		
		if (employee.isEmpty()) {
            return new ResponseEntity<String>("Employee with the given id not found.", HttpStatus.NOT_FOUND);
        }
		
		if (employee.get().getGodown() != null && employee.get().getId() == employee.get().getGodown().getManager().getId()) {
			return new ResponseEntity<ErrorData>(new ErrorData("RESOURCE_STILL_REFERENCED", "This employee is a manager of a godown"), HttpStatus.BAD_REQUEST);
		}
		
		employeeRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	
		
	@PutMapping(path=PATH + "/{id}")
	public ResponseEntity updateEntity(@PathVariable int id, @RequestBody Employee employee) {
		Optional<Employee> employeeFound = employeeRepository.findById(id);
		
		if(employeeFound.isEmpty()) {
			return new ResponseEntity<String>("Employee with the given id not found.", HttpStatus.NOT_FOUND);
		}
		
		employee.setId(id);
		
		if(employee.getRole() != null && employee.getRole().getId() > 0) {
			Optional<EmployeeRoles> employeeRoleFound = employeeRolesRepository.findById(employee.getRole().getId());
			
			if(employeeRoleFound.isEmpty()) {
				return new ResponseEntity<String>("Employee role with the given id is not found", HttpStatus.NOT_FOUND);
			}
			employee.setRole(employeeRoleFound.get());
		}
		
		if (employee.getPassword() == employee.getUsername()) {
			return new ResponseEntity<String>("Password cannot be the same as username.", HttpStatus.BAD_REQUEST);
		}
		else if (employee.getPassword() != null) {
			employee.setPassword(BCrypt.hashpw(employee.getPassword(), BCrypt.gensalt()));			
		}
		else {
			employee.setPassword(employeeFound.get().getPassword());
		}
		
		employee.setIsLocked(employeeFound.get().getIsLocked());

		if (employee.getGodown() != null && employee.getGodown().getId() > 0) {
			Optional<Godown> godownFound = godownRepository.findById(employee.getGodown().getId());
			
			if (godownFound.isEmpty()) {
				return new ResponseEntity<String>("Godown with the given id not found.", HttpStatus.NOT_FOUND);
			}
			
			if (employee.getId() == employeeFound.get().getGodown().getManager().getId() && employeeFound.get().getGodown().getId() != employee.getGodown().getId()) {
				return new ResponseEntity<String>("This employee is a manager of a godown.", HttpStatus.BAD_REQUEST);
			}
			
			employee.setGodown(godownFound.get());
			
			if (id == godownFound.get().getManager().getId()) {
				employee.getGodown().setManager(employee);
			}
		}
		
		Employee saved = employeeRepository.save(employee);
		return new ResponseEntity<Employee>(saved, HttpStatus.OK);
	}
	
}
