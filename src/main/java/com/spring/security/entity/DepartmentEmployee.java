package com.spring.security.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dept_emp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(DepartmentEmployeeId.class)
@EqualsAndHashCode(callSuper = false)
public class DepartmentEmployee {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_no", nullable = false, columnDefinition = "INT(11)")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Employee employee;

	@Id
//	@ManyToOne(fetch = FetchType.LAZY)
	@Column(name = "dept_no", nullable = false, columnDefinition = "CHAR(4)")
	private String department;

	@Column(name = "from_date", nullable = false, columnDefinition = "DATE")
	private Instant fromDate;

	@Column(name = "to_date", nullable = false, columnDefinition = "DATE")
	private Instant toDate;
}
