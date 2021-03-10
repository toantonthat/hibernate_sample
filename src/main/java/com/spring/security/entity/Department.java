package com.spring.security.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity

@Table(name = "departments")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "readOnlyEntityData")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "dept_no", length = 11, nullable = false, columnDefinition = "CHAR(4)")
	private String id;

	@Column(name = "dept_name", updatable = true, nullable = false, columnDefinition = "VARCHAR(40)")
	private String deptName;
	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	@Cacheable
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "department")
//	@Fetch(FetchMode.JOIN)
	private List<DepartmentEmployee> employees;
}
