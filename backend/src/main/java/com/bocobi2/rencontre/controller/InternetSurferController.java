package com.bocobi2.rencontre.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/InternetSurferController")
public class InternetSurferController {

	@Autowired
	TestimonyRepository testimonyRepository;
	
	//Testimony testimony= new Testimony();
	/**
	 * Start visualize testimony
	 */
	@RequestMapping(value="/visualizeVideoTestimony", method={RequestMethod.GET, RequestMethod.POST})
	public List<JSONObject> visualizeVideoTestimony(HttpServletRequest request){
		//JSONObject listOfTestimony = new JSONObject();
		List<JSONObject> listOfTestimony;
		for(Testimony testimony: testimonyRepository.findByTestimonyType("videos")){
			listOfTestimony.put(testimony);
		}
		
		return listOfTestimony;
	}
	@RequestMapping(value="/visualizeWriteTestimony", method={RequestMethod.GET, RequestMethod.POST})
	public List<JSONObject> visualizeWriteTestimony(HttpServletRequest request){
		
		List<JSONObject> listOfTestimony;
		for(Testimony testimony: testimonyRepository.findByTestimonyType("write")){
			listOfTestimony.put(testimony);
		}
		
		return listOfTestimony;
	}
	/*
	 * end visualize testimony
	 */
}
