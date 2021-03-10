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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "titles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(TitleId.class)
@EqualsAndHashCode(callSuper = false)
public class Title {
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "emp_no", nullable = false, columnDefinition = "INT(11)")
	private Employee employee;

	@Id
	@Column(name = "title", nullable = false, columnDefinition = "VARCHAR(50)")
	private String title;

	@Id
	@Column(name = "from_date", nullable = false, columnDefinition = "DATE")
	private Instant fromDate;

	@Column(name = "to_date", nullable = false, columnDefinition = "DATE")
	private Instant toDate;
}
