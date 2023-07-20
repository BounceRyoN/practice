package com.example.demo.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "report")
@Getter
@Setter
public class Report {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String content;
	
	@Column(name = "like_count")
	private String like_count;

	@Column(name = "created_date", insertable = false, updatable = false)
	private Date createdDate;

	@Column(name = "updated_date", insertable = false, updatable = true)
	private Date updatedDate;

	@PreUpdate
	public void onPrePersist() {
		Date date = new Date();
		this.updatedDate = date;
	}

}

