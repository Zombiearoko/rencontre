package com.bocobi2.rencontre.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bocobi2.rencontre.model.Administrator;
import com.bocobi2.rencontre.model.Country;
import com.bocobi2.rencontre.model.Department;
import com.bocobi2.rencontre.model.Locality;
import com.bocobi2.rencontre.model.Member;
import com.bocobi2.rencontre.model.MemberErrorType;
import com.bocobi2.rencontre.model.Testimony;
import com.bocobi2.rencontre.model.Town;
import com.bocobi2.rencontre.repositories.AdministratorRepository;
import com.bocobi2.rencontre.repositories.CountryRepository;
import com.bocobi2.rencontre.repositories.DepartmentRepository;
import com.bocobi2.rencontre.repositories.LocalityRepository;
import com.bocobi2.rencontre.repositories.TownRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rencontre/Administrator")
public class AdministratorController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	AdministratorRepository administratorRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	TownRepository townRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	LocalityRepository localitytRepository;

	/**
	 * connexion of the member
	 * 
	 * cette methode permet de connecter un membre dans une session
	 */
	/*
	 * Version POST
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/administratorConnexion", method = RequestMethod.POST)
	public ResponseEntity<?> administratorConnexionPost(HttpServletRequest requestConnexion) {

		HttpSession session = requestConnexion.getSession();
		// String connexionResult;
		// recuperation des champs de connexion
		String loginAdmin = requestConnexion.getParameter("loginAdmin");
		String passwordAdmin = requestConnexion.getParameter("passwordAdmin");
		System.out.println("-------------------------------");
		System.out.println(loginAdmin);
		System.out.println("-------------------------------");

		System.out.println("-------------------------------");
		System.out.println(passwordAdmin);
		// recherche du membre dans la base de donnees
		try {
			Administrator administrator = new Administrator();
			administrator = administratorRepository.findByLoginAdmin(loginAdmin);
			if (administrator != null) {
				if (administrator.getPasswordAdmin().equals(passwordAdmin)) {
					session.setAttribute("Administrator", administrator);
					return new ResponseEntity<Administrator>(administrator, HttpStatus.OK);
				} else {
					session.setAttribute("administrator", null);
					logger.error("administrator with password {} not found.", passwordAdmin);
					return new ResponseEntity(
							new MemberErrorType("Member with " + "password " + passwordAdmin + " not found."),
							HttpStatus.NOT_FOUND);
				}
			} else {
				logger.error("administrator with password {} not found.", loginAdmin);
				return new ResponseEntity(
						new MemberErrorType("administrator with " + "pseudonym " + loginAdmin + " not found."),
						HttpStatus.NOT_FOUND);

			}
		} catch (Exception ex) {
			logger.error("Member with pseudonym {} not found.", loginAdmin);
			return new ResponseEntity(
					new MemberErrorType("administrator with " + "pseudonym" + " " + loginAdmin + " not found."),
					HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * Version Get
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/administratorConnexion", method = RequestMethod.GET)
	public ResponseEntity<?> administratorConnexionGet(HttpServletRequest requestConnexion) {

		HttpSession session = requestConnexion.getSession();
		// String connexionResult;
		// recuperation des champs de connexion
		String loginAdmin = requestConnexion.getParameter("loginAdmin");
		String passwordAdmin = requestConnexion.getParameter("passwordAdmin");
		System.out.println("-------------------------------");
		System.out.println(loginAdmin);
		System.out.println("-------------------------------");

		System.out.println("-------------------------------");
		System.out.println(passwordAdmin);
		// recherche du membre dans la base de donnees
		try {
			Administrator administrator = new Administrator();
			administrator = administratorRepository.findByLoginAdmin(loginAdmin);
			if (administrator != null) {
				if (administrator.getPasswordAdmin().equals(passwordAdmin)) {
					session.setAttribute("Administrator", administrator);
					return new ResponseEntity<Administrator>(administrator, HttpStatus.OK);
				} else {
					session.setAttribute("administrator", null);
					logger.error("administrator with password {} not found.", passwordAdmin);
					return new ResponseEntity(
							new MemberErrorType("Member with " + "password " + passwordAdmin + " not found."),
							HttpStatus.NOT_FOUND);
				}
			} else {
				logger.error("administrator with password {} not found.", loginAdmin);
				return new ResponseEntity(
						new MemberErrorType("administrator with " + "pseudonym " + loginAdmin + " not found."),
						HttpStatus.NOT_FOUND);

			}
		} catch (Exception ex) {
			logger.error("Member with pseudonym {} not found.", loginAdmin);
			return new ResponseEntity(
					new MemberErrorType("administrator with " + "pseudonym" + " " + loginAdmin + " not found."),
					HttpStatus.NOT_FOUND);
		}

	}

	/***
	 * method to add country
	 */

	/*
	 * Version Post
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addCountry", method = RequestMethod.POST)
	public ResponseEntity<?> addCountryPost(HttpServletRequest request) {

		String countryName = request.getParameter("countryName");
		Country country = new Country();
		try {
			countryRepository.deleteAll();
			country.setCountryName(countryName);
			countryRepository.save(country);
			return new ResponseEntity<Country>(country, HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("Unable to create. A Country with name {} already exist", countryName);
			return new ResponseEntity(
					new MemberErrorType(
							"Unable to create. " + "A Country with name " + "" + countryName + " already exist"),
					HttpStatus.CONFLICT);
		}

	}

	/*
	 * Version Get
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addCountry", method = RequestMethod.GET)
	public ResponseEntity<?> addCountryGet(HttpServletRequest request) {

		String countryName = request.getParameter("countryName");
		Country country = new Country();
		try {
			country.setCountryName(countryName);
			countryRepository.save(country);
			return new ResponseEntity<Country>(country, HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("Unable to create. A Country with name {} already exist", countryName);
			return new ResponseEntity(
					new MemberErrorType(
							"Unable to create. " + "A Country with name " + "" + countryName + " already exist"),
					HttpStatus.CONFLICT);
		}
	}
	/*
	 * List all country
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllCountry", method = RequestMethod.POST)
	public ResponseEntity<List<Country>> listAllCountry(HttpServletRequest request) {


		List<Country> listOfCountry = countryRepository.findAll();

		if (listOfCountry.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Country>>(listOfCountry, HttpStatus.OK);
	
	}

	/***
	 * Methode d'enregistrement  departement
	 */

	/*
	 * Version Post
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addDepartement", method = RequestMethod.POST)
	public ResponseEntity<?> addDepartmentPost(HttpServletRequest request) {

		String countryName = request.getParameter("countryName");
		String departmentName = request.getParameter("departmentName");
		Country country = countryRepository.findByCountryName(countryName);

		Department department = new Department();
		List<Department> departmentList = new ArrayList<Department>();
		try {
			departmentRepository.deleteAll();
			department.setDepartmentName(departmentName);
			departmentRepository.insert(department);
			departmentList.add(department);
			country.setDepartment(departmentList);
			countryRepository.save(country);
			return new ResponseEntity<Department>(department, HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("Unable to create. A Department with name {} already exist", departmentName);
			return new ResponseEntity(
					new MemberErrorType(
							"Unable to create. " + "A Country with name " + "" + departmentName + " already exist"),
					HttpStatus.CONFLICT);
		}

	}
	/*
	 * Version Get
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addDepartement", method = RequestMethod.GET)
	public ResponseEntity<?> addDepartmentGet(HttpServletRequest request) {
		
		String countryName=request.getParameter("countryName");
		String departmentName=request.getParameter("departmentName");
		Country country =countryRepository.findByCountryName(countryName);
		
		Department department= new Department();
		List<Department> departmentList =new ArrayList<Department>();
		try{
		department.setDepartmentName(departmentName);
		departmentRepository.insert(department);
		departmentList.add(department);
		country.setDepartment(departmentList);
		countryRepository.save(country);
		return new ResponseEntity<Department>(department, HttpStatus.CREATED); 
		}catch(Exception ex){
			logger.error("Unable to create. A Department with name {} already exist", departmentName);
			return new ResponseEntity(
					new MemberErrorType("Unable to create. " + "A Country with name "
							+ "" + departmentName + " already exist"),
					HttpStatus.CONFLICT);
		}
		
	}
	
	/*
	 * List all department
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllDepartment", method = RequestMethod.POST)
	public ResponseEntity<List<Department>> listAllDepartment(HttpServletRequest request) {


		List<Department> listOfDepartment = departmentRepository.findAll();

		if (listOfDepartment.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Department>>(listOfDepartment, HttpStatus.OK);
	
	}
	
	/**
	 * Method add Ville d'un departement
	 */
	/*
	 * VERSION POST
	 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addTown", method = RequestMethod.POST)
	public ResponseEntity<?> addTownPost(HttpServletRequest request) {
		
		String countryName=request.getParameter("countryName");
		String departmentName=request.getParameter("departmentName");
		String townName=request.getParameter("townName");
		Country country =countryRepository.findByCountryName(countryName);
		Department department= departmentRepository.findByDepartmentName(departmentName);
		Town town=new Town();
		
		List<Town> townList =new ArrayList<Town>();
		try{
			townRepository.deleteAll();
			town.setTownName(townName);
			townRepository.insert(town);
			townList.add(town);
			//department.setTown(townList);
			country.setTown(townList);
			//departmentRepository.save(department);
			countryRepository.save(country);
		return new ResponseEntity<Town>(town, HttpStatus.CREATED); 
		}catch(Exception ex){
			logger.error("Unable to create. A Town with name {} already exist", townName);
			return new ResponseEntity(
					new MemberErrorType("Unable to create. " + "A Town with name "
							+ "" + townName + " already exist"),
					HttpStatus.CONFLICT);
		}
		
	}
	
	/*
	 * VERSION Get
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addTown", method = RequestMethod.GET)
	public ResponseEntity<?> addTownGet(HttpServletRequest request) {
		
		String countryName=request.getParameter("countryName");
		String departmentName=request.getParameter("departmentName");
		String townName=request.getParameter("townName");
		Country country =countryRepository.findByCountryName(countryName);
		Department department= departmentRepository.findByDepartmentName(departmentName);
		Town town=new Town();
		
		List<Town> townList =new ArrayList<Town>();
		try{
			town.setTownName(townName);
			townRepository.insert(town);
			townList.add(town);
			department.setTown(townList);
			country.setTown(townList);
			departmentRepository.save(department);
			countryRepository.save(country);
		return new ResponseEntity<Town>(town, HttpStatus.CREATED); 
		}catch(Exception ex){
			logger.error("Unable to create. A Town with name {} already exist", townName);
			return new ResponseEntity(
					new MemberErrorType("Unable to create. " + "A Country with name "
							+ "" + townName + " already exist"),
					HttpStatus.CONFLICT);
		}
		
	}*/
}


