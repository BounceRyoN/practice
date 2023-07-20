package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.Report;


public interface TopRepository extends JpaRepository<Report, Integer> {

	List<Report> findAllByOrderByUpdatedDateDesc();

	 @Query(nativeQuery = true, value = "select * from report order by id asc limit 1 ")
	Report findlatest();
}