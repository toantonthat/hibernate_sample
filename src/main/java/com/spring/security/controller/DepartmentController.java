package com.spring.security.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.spring.security.entity.Department;
import com.spring.security.errors.DepartmentNotFoundException;
import com.spring.security.model.DepartmentForm;
import com.spring.security.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	
	@GetMapping("/test2")
	public ResponseEntity<Void> test2() {
		System.out.println("entityManager " + entityManager);
		SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		System.out.println("sessionFactory " + sessionFactory);
		
		Session curSession1 = sessionFactory.openSession();
        Session curSession2 = sessionFactory.openSession();
        curSession1.beginTransaction();
        curSession2.beginTransaction();

        Department dept1 = curSession1.load(Department.class, "d009");
        System.out.println(dept1.getDeptName());

        Department dept2 = curSession2.load(Department.class, "d009");
        System.out.println(dept2.getDeptName());

        curSession1.disconnect();
        curSession2.disconnect();
        curSession1.close();
        curSession2.close();
        sessionFactory.close();
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public ResponseEntity<Void> test() {
		System.out.println("entityManager " + entityManager);
//		System.out.println("entityManagerFactory " + entityManagerFactory);
//		List<Department> departments = departmentRepository.findAll();
		
		SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		System.out.println("sessionFactory " + sessionFactory);
		
//		List<Department> departments = entityManager.createQuery("select distinct d from Department d join fetch d.employees",
//				Department.class)
//				.setHint(QueryHints.CACHEABLE, true)
//				.getResultList();
		
//		for (Department department : departments) {
//			System.out.printf("Department: %s = %s has %d employees.%n",
//					department.getDeptName(),
//					department.getId(),
//					0
////					department.getEmployees().size()
//			);
//		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("")
	@JacksonFeatures(serializationDisable = {SerializationFeature.FAIL_ON_EMPTY_BEANS})
	public ResponseEntity<Object> getAll() {
		List<Department> departments = departmentRepository.findAll();
		for (Department department : departments) {
			System.out.printf("Department: %s = %s has %d employees.%n",
					department.getDeptName(),
					department.getId(),
					department.getEmployees().size());
		}
		return ResponseEntity.ok(departments);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody DepartmentForm form) {
		Department existed = this.departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException());
		existed.setDeptName(form.getName());
		this.departmentRepository.save(existed);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		Department existed = this.departmentRepository.findById(id)
				.orElseThrow(() -> new DepartmentNotFoundException());
		this.departmentRepository.delete(existed);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
