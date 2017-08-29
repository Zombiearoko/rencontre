
package com.bocobi2.rencontre.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bocobi2.rencontre.model.Testimony;
import com.bocobi2.rencontre.repositories.TestimonyRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/InternetSurfer")
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
	public ResponseEntity<List<Testimony>>  visualizeVideoTestimonyPost(HttpServletRequest request){
		//JSONObject listOfTestimony = new JSONObject();
		List<Testimony> listOfTestimony = testimonyRepository.findByTestimonyType("videos");
		
		if(listOfTestimony.isEmpty()){
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		 return new ResponseEntity<List<Testimony>>(listOfTestimony, HttpStatus.OK);
	}
	/*
	 * Version Get
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/visualizeVideoTestimony", method=RequestMethod.GET)
	public ResponseEntity<List<Testimony>>  visualizeVideoTestimonyGet(HttpServletRequest request){
		//JSONObject listOfTestimony = new JSONObject();
		List<Testimony> listOfTestimony = testimonyRepository.findByTestimonyType("videos");
		
		if(listOfTestimony.isEmpty()){
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		 return new ResponseEntity<List<Testimony>>(listOfTestimony, HttpStatus.OK);
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
	public ResponseEntity<List<Testimony>>  visualizeWriteTestimonyPost(HttpServletRequest request){
		
List<Testimony> listOfTestimony = testimonyRepository.findByTestimonyType("write");
		
		if(listOfTestimony.isEmpty()){
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		 return new ResponseEntity<List<Testimony>>(listOfTestimony, HttpStatus.OK);
	}
	/*
	 * Version GET
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/visualizeWriteTestimony", method=RequestMethod.GET)
	public ResponseEntity<List<Testimony>>  visualizeWriteTestimonyGet(HttpServletRequest request){
		
		List<Testimony> listOfTestimony = testimonyRepository.findByTestimonyType("write");
		
		if(listOfTestimony.isEmpty()){
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		 return new ResponseEntity<List<Testimony>>(listOfTestimony, HttpStatus.OK);
	}
	}
	/*
	 * end visualize testimony write
	 */

