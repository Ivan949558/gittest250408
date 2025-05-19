package com.itwillbs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "members")
@Getter
@Setter
@ToString
public class Member {
	
	@Id
	@Column(name = "id", length = 50)
	private String id;
	
	@Column(name = "passwd", nullable = false)
	private String passwd;
	
	@Column(name = "name")
	private String name;
}
