package com.bocobi2.rencontre.controller;

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
import com.bocobi2.rencontre.model.Borough;
import com.bocobi2.rencontre.model.Concession;
import com.bocobi2.rencontre.model.Country;
import com.bocobi2.rencontre.model.Department;
import com.bocobi2.rencontre.model.MemberErrorType;
import com.bocobi2.rencontre.model.Region;
import com.bocobi2.rencontre.model.Town;
import com.bocobi2.rencontre.model.TypeMeeting;
import com.bocobi2.rencontre.repositories.AdministratorRepository;
import com.bocobi2.rencontre.repositories.BoroughRepository;
import com.bocobi2.rencontre.repositories.ConcessionRepository;
import com.bocobi2.rencontre.repositories.CountryRepository;
import com.bocobi2.rencontre.repositories.DepartmentRepository;
import com.bocobi2.rencontre.repositories.LocalityRepository;
import com.bocobi2.rencontre.repositories.RegionRepository;
import com.bocobi2.rencontre.repositories.TownRepository;
import com.bocobi2.rencontre.repositories.TypeMeetingRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rencontre/Administrator")
public class AdministratorController {

	public static final Logger logger = LoggerFactory.getLogger(AdministratorController.class);

	@Autowired
	AdministratorRepository administratorRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	RegionRepository regionRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	BoroughRepository boroughRepository;

	@Autowired
	TownRepository townRepository;

	@Autowired
	ConcessionRepository concessionRepository;

	@Autowired
	LocalityRepository localitytRepository;

	@Autowired
	TypeMeetingRepository typeMeetingRepository;

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
					session.setAttribute("Administrator", null);
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
	 * method add type of meeting
	 */

	/*
	 * Version Post
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/typeMeeting", method = RequestMethod.POST)
	public ResponseEntity<?> addTypeMeetingPost(HttpServletRequest request) {

		String meetingName = request.getParameter("meetingName");
		// typeMeetingRepository.deleteAll();
		TypeMeeting typeMeeting = new TypeMeeting();
		if (typeMeetingRepository.findByMeetingName(meetingName) != null) {
			logger.error("Unable to create. the type of Meeting with name {} already exist", meetingName);
			return new ResponseEntity(new MemberErrorType(
					"Unable to create. " + " the type of Meeting with name " + "" + meetingName + " already exist"),
					HttpStatus.CONFLICT);
		} else {

			typeMeeting.setMeetingName(meetingName);
			typeMeetingRepository.insert(typeMeeting);
			return new ResponseEntity<TypeMeeting>(typeMeeting, HttpStatus.CREATED);
		}

	}

	/*
	 * Version Get
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/typeMeeting", method = RequestMethod.GET)
	public ResponseEntity<?> addTypeMeetingGet(HttpServletRequest request) {

		String meetingName = request.getParameter("meetingName");
		// typeMeetingRepository.deleteAll();
		TypeMeeting typeMeeting = new TypeMeeting();
		if (typeMeetingRepository.findByMeetingName(meetingName) != null) {
			logger.error("Unable to create. the type of Meeting with name {} already exist", meetingName);
			return new ResponseEntity(new MemberErrorType(
					"Unable to create. " + " the type of Meeting with name " + "" + meetingName + " already exist"),
					HttpStatus.CONFLICT);
		} else {

			typeMeeting.setMeetingName(meetingName);
			typeMeetingRepository.insert(typeMeeting);
			return new ResponseEntity<TypeMeeting>(typeMeeting, HttpStatus.CREATED);
		}

	}

	/*
	 * method add country
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
	 * add region
	 */
	/*
	 * Version Post
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addRegion", method = RequestMethod.POST)
	public ResponseEntity<?> addRegionPost(HttpServletRequest request) {

		String regionName = request.getParameter("regionName");
		String countryName = request.getParameter("countryName");

		Region region = new Region();
		Country country = countryRepository.findByCountryName(countryName);

		try {
			region.setRegionName(regionName);
			region.setCountry(country);
			regionRepository.save(region);

			return new ResponseEntity<Region>(region, HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("Unable to create. A Region with name {} already exist", regionName);
			return new ResponseEntity(
					new MemberErrorType(
							"Unable to create. " + "A Region with name " + "" + regionName + " already exist"),
					HttpStatus.CONFLICT);
		}
	}

	/*
	 * Version Get
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addRegion", method = RequestMethod.GET)
	public ResponseEntity<?> addRegionGet(HttpServletRequest request) {

		String regionName = request.getParameter("regionName");
		String countryName = request.getParameter("countryName");

		Region region = new Region();
		Country country = countryRepository.findByCountryName(countryName);

		try {
			region.setRegionName(regionName);
			region.setCountry(country);
			regionRepository.save(region);

			return new ResponseEntity<Region>(region, HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("Unable to create. A Region with name {} already exist", regionName);
			return new ResponseEntity(
					new MemberErrorType(
							"Unable to create. " + "A Region with name " + "" + regionName + " already exist"),
					HttpStatus.CONFLICT);
		}
	}

	/*
	 * Version Post
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
	public ResponseEntity<?> addDepartmentPost(HttpServletRequest request) {

		String regionName = request.getParameter("regionName");
		String departmentName = request.getParameter("departmentName");

		Region region = regionRepository.findByRegionName(regionName);
		Department department = new Department();

		try {
			department.setDepartmentName(departmentName);
			department.setRegion(region);

			departmentRepository.save(department);

			return new ResponseEntity<Department>(department, HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("Unable to create. A Department with name {} already exist", departmentName);
			return new ResponseEntity(
					new MemberErrorType(
							"Unable to create. " + "A Department with name " + "" + departmentName + " already exist"),
					HttpStatus.CONFLICT);
		}
	}

	/*
	 * Version Get
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addDepartment", method = RequestMethod.GET)
	public ResponseEntity<?> addDepartmentGet(HttpServletRequest request) {

		String regionName = request.getParameter("regionName");
		String departmentName = request.getParameter("departmentName");

		Region region = regionRepository.findByRegionName(regionName);
		Department department = new Department();

		try {
			department.setDepartmentName(departmentName);
			department.setRegion(region);

			departmentRepository.save(department);

			return new ResponseEntity<Department>(department, HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("Unable to create. A Department with name {} already exist", departmentName);
			return new ResponseEntity(
					new MemberErrorType(
							"Unable to create. " + "A Department with name " + "" + departmentName + " already exist"),
					HttpStatus.CONFLICT);
		}
	}

	/*
	 * Version Post
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addBorough", method = RequestMethod.POST)
	public ResponseEntity<?> addBoroughPost(HttpServletRequest request) {

		String departmentName = request.getParameter("departmentName");
		String boroughName = request.getParameter("boroughName");

		Department department = departmentRepository.findByDepartmentName(departmentName);
		Borough borough = new Borough();

		try {
			borough.setBoroughName(boroughName);
			borough.setDepartment(department);

			boroughRepository.save(borough);

			return new ResponseEntity<Borough>(borough, HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("Unable to create. A Borough with name {} already exist", boroughName);
			return new ResponseEntity(
					new MemberErrorType(
							"Unable to create. " + "A Borough with name " + "" + boroughName + " already exist"),
					HttpStatus.CONFLICT);
		}
	}

	/*
	 * Version Get
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addBorough", method = RequestMethod.GET)
	public ResponseEntity<?> addBoroughGet(HttpServletRequest request) {

		String departmentName = request.getParameter("departmentName");
		String boroughName = request.getParameter("boroughName");

		Department department = departmentRepository.findByDepartmentName(departmentName);
		Borough borough = new Borough();

		try {
			borough.setBoroughName(boroughName);
			borough.setDepartment(department);

			boroughRepository.save(borough);

			return new ResponseEntity<Borough>(borough, HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("Unable to create. A Borough with name {} already exist", boroughName);
			return new ResponseEntity(
					new MemberErrorType(
							"Unable to create. " + "A Borough with name " + "" + boroughName + " already exist"),
					HttpStatus.CONFLICT);
		}
	}

	/*
	 * Version Post
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addTown", method = RequestMethod.POST)
	public ResponseEntity<?> addTownPost(HttpServletRequest request) {

		String boroughName = request.getParameter("boroughName");
		String townName = request.getParameter("townName");

		Borough borough = boroughRepository.findByBoroughName(boroughName);
		Town town = new Town();

		try {
			town.setTownName(townName);
			town.setBorough(borough);

			townRepository.save(town);

			return new ResponseEntity<Town>(town, HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("Unable to create. A Town with name {} already exist", townName);
			return new ResponseEntity(
					new MemberErrorType("Unable to create. " + "A Town with name " + "" + townName + " already exist"),
					HttpStatus.CONFLICT);
		}
	}

	/*
	 * Version Get
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addTown", method = RequestMethod.GET)
	public ResponseEntity<?> addTownGet(HttpServletRequest request) {

		String boroughName = request.getParameter("boroughName");
		String townName = request.getParameter("townName");

		Borough borough = boroughRepository.findByBoroughName(boroughName);
		Town town = new Town();

		try {
			town.setTownName(townName);
			town.setBorough(borough);

			townRepository.save(town);

			return new ResponseEntity<Town>(town, HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("Unable to create. A Town with name {} already exist", townName);
			return new ResponseEntity(
					new MemberErrorType("Unable to create. " + "A Town with name " + "" + townName + " already exist"),
					HttpStatus.CONFLICT);
		}
	}

	/*
	 * Version POst
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addConcession", method = RequestMethod.POST)
	public ResponseEntity<?> addConcessionPost(HttpServletRequest request) {

		String townName = request.getParameter("townName");
		String concessionName = request.getParameter("concessionName");

		Town town = townRepository.findByTownName(townName);
		Concession concession = new Concession();

		try {
			concession.setConcessionName(concessionName);
			concession.setTown(town);

			concessionRepository.save(concession);

			return new ResponseEntity<Concession>(concession, HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("Unable to create. A Concession with name {} already exist", concessionName);
			return new ResponseEntity(
					new MemberErrorType(
							"Unable to create. " + "A Concession with name " + "" + concessionName + " already exist"),
					HttpStatus.CONFLICT);
		}
	}

	/*
	 * List all country
	 */
	/*
	 * Version post
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllCountry", method = RequestMethod.POST)
	public ResponseEntity<List<Country>> listAllCountryPost(HttpServletRequest request) {

		List<Country> listOfCountry = countryRepository.findAll();

		if (listOfCountry.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Country>>(listOfCountry, HttpStatus.OK);

	}

	/*
	 * Version Get
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllCountry", method = RequestMethod.GET)
	public ResponseEntity<List<Country>> listAllCountryGet(HttpServletRequest request) {

		List<Country> listOfCountry = countryRepository.findAll();

		if (listOfCountry.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Country>>(listOfCountry, HttpStatus.OK);

	}

	/*
	 * Version Post
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listRegion", method = RequestMethod.POST)
	public ResponseEntity<List<Region>> listRegionPost(HttpServletRequest request) {

		String countryName = request.getParameter("countryName");
		Country country = countryRepository.findByCountryName(countryName);
		List<Region> listOfRegion = regionRepository.findByCountry(country);

		if (listOfRegion.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Region>>(listOfRegion, HttpStatus.OK);

	}

	/*
	 * Version Post
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listDepartment", method = RequestMethod.POST)
	public ResponseEntity<List<Department>> listDepartmentPost(HttpServletRequest request) {

		String regionName = request.getParameter("regionName");
		Region region = regionRepository.findByRegionName(regionName);
		List<Department> listOfDepartment = departmentRepository.findByRegion(region);

		if (listOfDepartment.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Department>>(listOfDepartment, HttpStatus.OK);

	}

	/*
	 * Version Post
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listBorough", method = RequestMethod.POST)
	public ResponseEntity<List<Borough>> listBoroughPost(HttpServletRequest request) {

		String departmentName = request.getParameter("departmentName");
		Department department = departmentRepository.findByDepartmentName(departmentName);
		List<Borough> listOfBorough = boroughRepository.findByDepartment(department);

		if (listOfBorough.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Borough>>(listOfBorough, HttpStatus.OK);

	}

	/*
	 * Version Post
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listTown", method = RequestMethod.POST)
	public ResponseEntity<List<Town>> listTownPost(HttpServletRequest request) {

		String boroughName = request.getParameter("boroughName");
		Borough borough = boroughRepository.findByBoroughName(boroughName);
		List<Town> listOfTown = townRepository.findByBorough(borough);

		if (listOfTown.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Town>>(listOfTown, HttpStatus.OK);

	}

	/*
	 * Version Post
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listConcession", method = RequestMethod.POST)
	public ResponseEntity<List<Concession>> listConcessionPost(HttpServletRequest request) {

		String townName = request.getParameter("townName");
		Town town = townRepository.findByTownName(townName);
		List<Concession> listOfConcession = concessionRepository.findByTown(town);

		if (listOfConcession.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Concession>>(listOfConcession, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listTypeMeeting", method = RequestMethod.POST)
	public ResponseEntity<List<TypeMeeting>> listTypeMeetingPost(HttpServletRequest request) {

		List<TypeMeeting> listOfTypeMeeting = typeMeetingRepository.findAll();

		if (listOfTypeMeeting.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<TypeMeeting>>(listOfTypeMeeting, HttpStatus.OK);

	}

}
