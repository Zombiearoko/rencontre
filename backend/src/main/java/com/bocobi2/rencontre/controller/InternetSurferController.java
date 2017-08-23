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
	/*
	 * Version Post
	 */
	@RequestMapping(value="/visualizeVideoTestimony", method= RequestMethod.POST)
	public List<JSONObject> visualizeVideoTestimonyPost(HttpServletRequest request){
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
	/*
	 * Version Get
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/visualizeVideoTestimony", method=RequestMethod.GET)
	public List<JSONObject> visualizeVideoTestimonyGet(HttpServletRequest request){
		//JSONObject listOfTestimony = new JSONObject();
		List<JSONObject> listOfTestimony = new ArrayList<JSONObject>();
		JSONObject objectTestimony=new JSONObject();
		for(Testimony testimony: testimonyRepository.findByTestimonyType("videos")){
			objectTestimony.put("Content", testimony.getTestimonyContent());
			objectTestimony.put("Type", testimony.getTestimonyType());
			listOfTestimony.add(objectTestimony);
		}
		
		return listOfTestimony;
	}
	/*
	 * End visualize testimony videos
	 */
	/**
	 * start visualition testimony write
	 * @param request
	 * @return
	 */
	/*
	 * Version POST
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/visualizeWriteTestimony", method= RequestMethod.POST)
	public List<JSONObject> visualizeWriteTestimonyPost(HttpServletRequest request){
		
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
	 * Version GET
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/visualizeWriteTestimony", method=RequestMethod.GET)
	public List<JSONObject> visualizeWriteTestimonyGet(HttpServletRequest request){
		
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
	 * end visualize testimony write
	 */
}
