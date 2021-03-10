package com.spring.security.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.spring.security.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

	@QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
//	@Cacheable(value = "readOnlyEntityData")
	@Query("select distinct d from Department d join fetch d.employees")
	List<Department> findAll();
}
