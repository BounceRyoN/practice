package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.Report;
import com.example.demo.Service.TopService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class TopController {

	@Autowired
	TopService topService;

	@GetMapping
	public ModelAndView top() {
		ModelAndView mav = new ModelAndView();
		List<Report> contentData = topService.findAllReport();
		mav.setViewName("/top");
		mav.addObject("contents", contentData);

		return mav;
	}
	
	@PostMapping("/add")
	@ResponseBody
	public String addContent(@RequestParam String content) {

		Report report = new Report();
		report.setContent(content);
		topService.saveReport(report);
		Report LatestPost = topService.findSelectedPost();
		return GetJson(LatestPost);
	}

    private String GetJson(Report LatestPost){
        String retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            retVal = objectMapper.writeValueAsString(LatestPost);
        } catch (JsonProcessingException e) {
            System.err.println(e);
        }
        return retVal;
    }
	@GetMapping("/Edit/{id}")
	@ResponseBody
	public String editContent(@PathVariable Integer id) {
		Report report = topService.GetReport(id);
		return getEditJson(report);
	}

	private String getEditJson(Report editContent){
		String retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try{
			retVal = objectMapper.writeValueAsString(editContent);
		} catch (JsonProcessingException e) {
			System.err.println(e);
		}
		return retVal;
	}
	@PutMapping("/Updata/{id}")
	@ResponseBody
	public String updateContent (@PathVariable Integer id,@RequestParam  String content) {

		Report report = new Report();

		report.setContent(content);
		report.setId(id);

		topService.saveReport(report);

		Report UsersPost = topService.GetReport(id);

		return UpdateContentJson(UsersPost);
	}

	private String UpdateContentJson(Report UsersPost) {
		String retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try{
			retVal = objectMapper.writeValueAsString(UsersPost);
		} catch (JsonProcessingException e) {
			System.err.println(e);
		}
		return retVal;
	}
	@DeleteMapping("/Delete/{id}")
	@ResponseBody
	public void deleteContent(@PathVariable Integer id) {
		topService.deleteReport(id);
	}
	@GetMapping("/Count/{id}")
	@ResponseBody
	public String likeCount(@PathVariable Integer id, @RequestParam(required = false) String like_count) {
		Report report = topService.GetReport(id);
		
		return CountJson(report);
	}
	
	private String CountJson(Report LikeCount) {
		String nowCount = "1";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			nowCount = objectMapper.writeValueAsString(LikeCount);
		} catch (JsonProcessingException e) {
			System.err.println(e);
		}
		return nowCount;
	}
}