package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Report;
import com.example.demo.Repository.TopRepository;

@Service
public class TopService {

	@Autowired
	TopRepository topRepository;

	public List<Report> findAllReport() {
		return topRepository.findAllByOrderByUpdatedDateDesc();
	}
	public void saveReport(Report report) {
		topRepository.save(report);
	}
	public Report findSelectedPost() {
		return topRepository.findlatest();
	}
	public Report GetReport(Integer id) {
		return topRepository.findById(id).orElse(null);
	}
	public void deleteReport(Integer id) {
		topRepository.deleteById(id);
	}
	public void likeCountReport(Integer id) {
		topRepository.getReferenceById(id);
		
	}
}

