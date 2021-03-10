package com.spring.security.entity;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class TitleId implements Serializable {
	private static final long serialVersionUID = 1L;
	private int employee;
	private String title;
	private Instant fromDate;
}
