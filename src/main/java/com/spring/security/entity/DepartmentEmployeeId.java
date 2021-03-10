package com.spring.security.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class DepartmentEmployeeId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String department;
	private int employee;
}
