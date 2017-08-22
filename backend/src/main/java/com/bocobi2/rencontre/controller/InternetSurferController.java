package com.bocobi2.rencontre.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bocobi2.rencontre.model.Testimony;
import com.bocobi2.rencontre.repositories.TestimonyRepository;

@RestController
@RequestMapping(value="/InternetSurferController")
public class InternetSurferController {

	@Autowired
	TestimonyRepository testimonyRepository;
	
	//Testimony testimony= new Testimony();
	@SuppressWarnings("unchecked")
	/**
	 * Start visualize testimony
	 */
	@RequestMapping(value="/visualizeVideoTestimony", method={RequestMethod.GET, RequestMethod.POST})
	public List<JSONObject> visualizeVideoTestimony(HttpServletRequest request){
		//JSONObject listOfTestimony = new JSONObject();
		List<JSONObject> listOfTestimony = new ArrayList<JSONObject>();
		JSONObject objectTestimony;
		for(Testimony testimony: testimonyRepository.findByTestimonyType("videos")){
			objectTestimony=new JSONObject();
			objectTestimony.put("Content", testimony.getTestimonyContent());
			objectTestimony.put("Type", testimony.getTestimonyType());
			listOfTestimony.add(objectTestimony);
		}
		
		return listOfTestimony;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/visualizeWriteTestimony", method={RequestMethod.GET, RequestMethod.POST})
	public List<JSONObject> visualizeWriteTestimony(HttpServletRequest request){
		
		List<JSONObject> listOfTestimony=new ArrayList<JSONObject>();;
		JSONObject objectTestimony=new JSONObject();
		for(Testimony testimony: testimonyRepository.findByTestimonyType("write")){
			objectTestimony.put("Content", testimony.getTestimonyContent());
			objectTestimony.put("Type", testimony.getTestimonyType());
			 listOfTestimony.add(objectTestimony);
		}
		
		return listOfTestimony;
	}
	/*
	 * end visualize testimony
	 */
}
