package com.bocobi2.rencontre.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bocobi2.rencontre.model.Administrator;
import com.bocobi2.rencontre.model.Borough;
import com.bocobi2.rencontre.model.Concession;
import com.bocobi2.rencontre.model.Country;
import com.bocobi2.rencontre.model.Department;
import com.bocobi2.rencontre.model.Member;
import com.bocobi2.rencontre.model.MemberErrorType;
import com.bocobi2.rencontre.model.Region;
import com.bocobi2.rencontre.model.Role;
import com.bocobi2.rencontre.model.Status;
import com.bocobi2.rencontre.model.Town;
import com.bocobi2.rencontre.model.TypeMeeting;
import com.bocobi2.rencontre.model.UserDetailsServices;
import com.bocobi2.rencontre.repositories.AdministratorRepository;
import com.bocobi2.rencontre.repositories.BoroughRepository;
import com.bocobi2.rencontre.repositories.ConcessionRepository;
import com.bocobi2.rencontre.repositories.CountryRepository;
import com.bocobi2.rencontre.repositories.DepartmentRepository;
import com.bocobi2.rencontre.repositories.LocalityRepository;
import com.bocobi2.rencontre.repositories.MemberRepository;
import com.bocobi2.rencontre.repositories.RegionRepository;
import com.bocobi2.rencontre.repositories.RoleRepository;
import com.bocobi2.rencontre.repositories.StatusRepository;
import com.bocobi2.rencontre.repositories.TownRepository;
import com.bocobi2.rencontre.repositories.TypeMeetingRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rencontre/Administrator")
public class AdministratorController {

	public static final Logger logger = LoggerFactory.getLogger(AdministratorController.class);

	// injection des interfaces de depots mongodb

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

	@Autowired
	StatusRepository statusRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserDetailsServices use;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	/*
	 * Methode de chiffrement des second mot de passe
	 */
	public static String cryptographe(String name) {

		String crypte = "";
		for (int i = 0; i < name.length(); i++) {
			int c = name.charAt(i) ^ 48;
			char crypteC = (char) c;
			crypte = crypte + crypteC;
		}
		return crypte;
	}

	/*
	 * Methode de dechiffrement des mots de passes
	 */
	public static String decryptographe(String password) {
		String aCrypter = "";
		for (int i = 0; i < password.length(); i++) {
			int c = password.charAt(i) ^ 48;
			aCrypter = aCrypter + (char) c;
		}

		return aCrypter;
	}

	// Methodes pour ajouter un administrateur

	@RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
	public ResponseEntity<?> addAdminPost(HttpServletRequest request) {

		String login = request.getParameter("login");
		String password = request.getParameter("password");
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Set<Role> setRole = new HashSet<>();
		setRole.add(role);

		Administrator admin = new Administrator();
		admin.setLoginAdmin(login);
		admin.setPasswordAdmin(bCryptPasswordEncoder.encode(password));
		admin.setPasswordSec(cryptographe(password));
		admin.setRoles(setRole);
		administratorRepository.save(admin);

		return new ResponseEntity<Administrator>(admin, HttpStatus.OK);
	}

	@RequestMapping(value = "/addAdmin", method = RequestMethod.GET)
	public ResponseEntity<?> addAdmin(HttpServletRequest request) {

		String login = request.getParameter("login");
		String password = request.getParameter("password");
		Role role = roleRepository.findByName("ROLE_ADMIN");
		Set<Role> setRole = new HashSet<>();
		setRole.add(role);

		Administrator admin = new Administrator();
		admin.setLoginAdmin(login);
		admin.setPasswordAdmin(bCryptPasswordEncoder.encode(password));
		admin.setPasswordSec(cryptographe(password));
		admin.setRoles(setRole);
		administratorRepository.save(admin);

		return new ResponseEntity<Administrator>(admin, HttpStatus.OK);
	}

	/**
	 *
	 * 
	 * cette methode permet de connecter un membre dans une session
	 */

	/*
	 * Methodes pour la connexion d'un administrateur
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/administratorConnexion", method = RequestMethod.POST)
	public ResponseEntity<?> administratorConnexionPost(HttpServletRequest requestConnexion) {

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
				String pass = decryptographe(administrator.getPasswordSec());
				if (pass.equals(passwordAdmin)) {

					UserDetails users = use.loadUserByUsernameA(loginAdmin);
					System.out.println("Humm tu as reussi a me mettre en session tu es forte ma petite " + users);
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(users, null,
							users.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authToken);

					return new ResponseEntity<Administrator>(administrator, HttpStatus.OK);
				} else {
					logger.error("Administrator with password {} not found.", passwordAdmin);
					return new ResponseEntity(
							new MemberErrorType("Administrator with " + "password " + passwordAdmin + " not found."),
							HttpStatus.NOT_FOUND);
				}
			} else {
				logger.error("Administrator with password {} not found.", loginAdmin);
				return new ResponseEntity(
						new MemberErrorType(
								"Administrator with " + "pseudonym " + "" + "" + loginAdmin + " not found."),
						HttpStatus.NOT_FOUND);

			}
		} catch (Exception ex) {
			logger.error("Administrator with pseudonym {} not found.", loginAdmin);
			return new ResponseEntity(
					new MemberErrorType(
							"Administrator with " + "pseudonym" + " " + "" + "" + loginAdmin + " not found."),
					HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * Version Get
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/administratorConnexion", method = RequestMethod.GET)
	public ResponseEntity<?> administratorConnexionGet(HttpServletRequest requestConnexion) {

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
				String pass = decryptographe(administrator.getPasswordSec());
				if (pass.equals(passwordAdmin)) {

					UserDetails users = use.loadUserByUsernameA(loginAdmin);
					System.out.println("Humm tu as reussi a me mettre en session tu es forte ma petite " + users);
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(users, null,
							users.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authToken);

					return new ResponseEntity<Administrator>(administrator, HttpStatus.OK);
				} else {
					logger.error("Administrator with password {} not found.", passwordAdmin);
					return new ResponseEntity(
							new MemberErrorType("Administrator with " + "password " + passwordAdmin + " not found."),
							HttpStatus.NOT_FOUND);
				}
			} else {
				logger.error("Administrator with password {} not found.", loginAdmin);
				return new ResponseEntity(
						new MemberErrorType(
								"Administrator with " + "pseudonym " + "" + "" + loginAdmin + " not found."),
						HttpStatus.NOT_FOUND);

			}
		} catch (Exception ex) {
			logger.error("Administrator with pseudonym {} not found.", loginAdmin);
			return new ResponseEntity(
					new MemberErrorType(
							"Administrator with " + "pseudonym" + " " + "" + "" + loginAdmin + " not found."),
					HttpStatus.NOT_FOUND);
		}

	}

	// deconnexion de l'administrateur
	@RequestMapping(value = "/logoutAdministrator", method = RequestMethod.POST)
	public Map<String, String> logoutAdministratorPost(HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> message = new HashMap<>();
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {

				new SecurityContextLogoutHandler().logout(request, response, auth);

				message.put("Message", "succes");
				logger.debug("Disconnection succes");
				return message;
			}
		} catch (Exception ex) {
			message.put("Message", "failed");
			logger.error("Disconnection failed");
			return message;
		}
		message.put("Message", "failed");
		logger.error("Disconnection failed");
		return message;
	}

	@RequestMapping(value = "/logoutAdministrator", method = RequestMethod.GET)
	public Map<String, String> logoutAdministratorGet(HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> message = new HashMap<>();
		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {

				new SecurityContextLogoutHandler().logout(request, response, auth);

				message.put("Message", "succes");
				logger.debug("Disconnection succes");
				return message;
			}
		} catch (Exception ex) {
			message.put("Message", "failed");
			logger.error("Disconnection failed");
			return message;
		}
		message.put("Message", "failed");
		logger.error("Disconnection failed");
		return message;
	}

	/*
	 * Methode d'ajout de status
	 */

	/*
	 * Version Post
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addStatus", method = RequestMethod.POST)
	public ResponseEntity<?> addStatusPost(HttpServletRequest request) {

		String statusName = request.getParameter("statusName");
		// typeMeetingRepository.deleteAll();
		Status status = new Status();
		if (statusRepository.findByStatusName(statusName) != null) {
			logger.error("Unable to create. the status with name {} already exist", statusName);
			return new ResponseEntity(new MemberErrorType("Unable to create. " + "" + " the type of status with name "
					+ "" + "" + statusName + " already exist"), HttpStatus.CONFLICT);
		} else {

			status.setStatusName(statusName);
			statusRepository.insert(status);
			return new ResponseEntity<Status>(status, HttpStatus.OK);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addStatus", method = RequestMethod.GET)
	public ResponseEntity<?> addStatusGet(HttpServletRequest request) {

		String statusName = request.getParameter("statusName");
		// typeMeetingRepository.deleteAll();
		Status status = new Status();
		if (statusRepository.findByStatusName(statusName) != null) {
			logger.error("Unable to create. the status with name {} already exist", statusName);
			return new ResponseEntity(new MemberErrorType("Unable to create. " + "" + " the type of status with name "
					+ "" + "" + statusName + " already exist"), HttpStatus.CONFLICT);
		} else {

			status.setStatusName(statusName);
			statusRepository.insert(status);
			return new ResponseEntity<Status>(status, HttpStatus.OK);
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
			return new ResponseEntity(new MemberErrorType("Unable to create. " + "" + " the type of Meeting with name "
					+ "" + "" + meetingName + " already exist"), HttpStatus.CONFLICT);
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
			return new ResponseEntity(new MemberErrorType("Unable to create. " + "" + " the type of Meeting with name "
					+ "" + "" + meetingName + " already exist"), HttpStatus.CONFLICT);
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
							"Unable to create. " + "A Country with name " + "" + "" + countryName + " already exist"),
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
							"Unable to create. " + "A Country with name " + "" + "" + countryName + " already exist"),
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
							"Unable to create. " + "A Region with name " + "" + "" + regionName + " already exist"),
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
	 * ajouter les departement
	 */

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
			return new ResponseEntity(new MemberErrorType("Unable to create. " + "A Department with name " + "" + ""
					+ "" + departmentName + " already exist"), HttpStatus.CONFLICT);
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
			return new ResponseEntity(new MemberErrorType(
					"Unable to create. " + "A Department with name " + "" + "" + departmentName + " already exist"),
					HttpStatus.CONFLICT);
		}
	}

	// ajouter l'arrodissement
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
							"Unable to create. " + "A Borough with name " + "" + "" + boroughName + " already exist"),
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
					new MemberErrorType(
							"Unable to create. " + "A Town with name " + "" + "" + townName + " already exist"),
					HttpStatus.CONFLICT);
		}
	}
	// Ajouter les villages

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
					new MemberErrorType(
							"Unable to create. " + "A Town with name " + "" + "" + townName + " already exist"),
					HttpStatus.CONFLICT);
		}
	}

	// Ajouter les concession
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
			return new ResponseEntity(new MemberErrorType(
					"Unable to create. " + "A Concession with name " + "" + "" + concessionName + " already exist"),
					HttpStatus.CONFLICT);
		}
	}

	// Ajouter les roles

	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public ResponseEntity<?> rolePost(HttpServletRequest request) {

		String name = request.getParameter("nameRole");
		Role role = new Role();

		if (name.equalsIgnoreCase("ROLE_USER")) {
			role.setName(name);
			role.setUsers(new HashSet<>(memberRepository.findAll()));
			roleRepository.save(role);
		} else if (name.equalsIgnoreCase("ROLE_ADMIN")) {
			role.setName(name);
			role.setAdmin(new HashSet<>(administratorRepository.findAll()));
			roleRepository.save(role);
		}

		// roleRepository.deleteAll();

		return new ResponseEntity<Role>(role, HttpStatus.OK);
	}

	@RequestMapping(value = "/addRole", method = RequestMethod.GET)
	public ResponseEntity<?> roleGet(HttpServletRequest request) {

		String name = request.getParameter("nameRole");
		Role role = new Role();

		if (name.equalsIgnoreCase("ROLE_USER")) {
			role.setName(name);
			role.setUsers(new HashSet<>(memberRepository.findAll()));
			roleRepository.save(role);
		} else if (name.equalsIgnoreCase("ROLE_ADMIN")) {
			role.setName(name);
			role.setAdmin(new HashSet<>(administratorRepository.findAll()));
			roleRepository.save(role);
		}

		// roleRepository.deleteAll();

		return new ResponseEntity<Role>(role, HttpStatus.OK);
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
	@RequestMapping(value = "/listRegion", method = RequestMethod.GET)
	public ResponseEntity<List<Region>> listRegionGet(HttpServletRequest request) {

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listDepartment", method = RequestMethod.GET)
	public ResponseEntity<List<Department>> listDepartmentGet(HttpServletRequest request) {

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listBorough", method = RequestMethod.GET)
	public ResponseEntity<List<Borough>> listBoroughGet(HttpServletRequest request) {

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listTown", method = RequestMethod.GET)
	public ResponseEntity<List<Town>> listTownGet(HttpServletRequest request) {

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
	@RequestMapping(value = "/listConcession", method = RequestMethod.GET)
	public ResponseEntity<List<Concession>> listConcessionGet(HttpServletRequest request) {

		String townName = request.getParameter("townName");
		Town town = townRepository.findByTownName(townName);
		List<Concession> listOfConcession = concessionRepository.findByTown(town);

		if (listOfConcession.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Concession>>(listOfConcession, HttpStatus.OK);

	}

	/*
	 * list type meeting with birtdate
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listTypeMeeting", method = RequestMethod.POST)
	public ResponseEntity<List<TypeMeeting>> listTypeMeetingPost(HttpServletRequest request) throws ParseException {

		String birth = request.getParameter("birthDate");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(birth);
			System.out.println("Date parsée : " + date);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);

			Calendar calendarCourante = Calendar.getInstance();

			int yearCourante = calendarCourante.get(Calendar.YEAR);

			int birthDate = yearCourante - year;

			// Integer birthDate = new Integer(birth);
			System.out.println(birthDate);
			List<TypeMeeting> listOfTypeMeeting = typeMeetingRepository.findAll();

			List<TypeMeeting> listMineur = new ArrayList<TypeMeeting>();

			// List<TypeMeeting> listMajeur = new ArrayList<TypeMeeting>();

			if (listOfTypeMeeting.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}

			if (birthDate <= 20) {
				for (TypeMeeting meeting : listOfTypeMeeting) {
					if (meeting.getMeetingName().equals("Amicale")) {
						listMineur.add(meeting);
					} else if (meeting.getMeetingName().equals("Academique")) {
						listMineur.add(meeting);
					}
				}
				return new ResponseEntity<List<TypeMeeting>>(listMineur, HttpStatus.OK);
			} else {

				return new ResponseEntity<List<TypeMeeting>>(listOfTypeMeeting, HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity(new MemberErrorType("Format de date invalide. Usage : yyyy-MM-dd"),
					HttpStatus.CONFLICT);

		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listTypeMeeting", method = RequestMethod.GET)
	public ResponseEntity<List<TypeMeeting>> listTypeMeetingGet(HttpServletRequest request) throws ParseException {

		String birth = request.getParameter("birthDate");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(birth);
			System.out.println("Date parsée : " + date);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);

			Calendar calendarCourante = Calendar.getInstance();

			int yearCourante = calendarCourante.get(Calendar.YEAR);

			int birthDate = yearCourante - year;
			//String birt = birthDate + "";
			// Integer birthDate = new Integer(birth);
			System.out.println(birthDate);
			List<TypeMeeting> listOfTypeMeeting = typeMeetingRepository.findAll();

			List<TypeMeeting> listMineur = new ArrayList<TypeMeeting>();

			// List<TypeMeeting> listMajeur = new ArrayList<TypeMeeting>();

			if (listOfTypeMeeting.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}

			if (birthDate <= 20) {
				for (TypeMeeting meeting : listOfTypeMeeting) {
					if (meeting.getMeetingName().equals("Amicale")) {
						listMineur.add(meeting);
					} else if (meeting.getMeetingName().equals("Academique")) {
						listMineur.add(meeting);
					}
				}
				return new ResponseEntity<List<TypeMeeting>>(listMineur, HttpStatus.OK);
			} else {

				return new ResponseEntity<List<TypeMeeting>>(listOfTypeMeeting, HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity(new MemberErrorType("Format de date invalide. Usage : yyyy-MM-dd"),
					HttpStatus.CONFLICT);

		}

	}

	/*
	 * list all
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllTypeMeeting", method = RequestMethod.POST)
	public ResponseEntity<List<TypeMeeting>> listAllTypeMeetingPost(HttpServletRequest request) {

		List<TypeMeeting> listOfTypeMeeting = typeMeetingRepository.findAll();

		if (listOfTypeMeeting.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<TypeMeeting>>(listOfTypeMeeting, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllTypeMeeting", method = RequestMethod.GET)
	public ResponseEntity<List<TypeMeeting>> listAllTypeMeetingGet(HttpServletRequest request) {

		List<TypeMeeting> listOfTypeMeeting = typeMeetingRepository.findAll();

		if (listOfTypeMeeting.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<TypeMeeting>>(listOfTypeMeeting, HttpStatus.OK);

	}

	/*
	 * list all
	 */

	/*
	 * Version Get et post
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllRegion", method = RequestMethod.POST)
	public ResponseEntity<List<Region>> listAllRegionPost(HttpServletRequest request) {

		List<Region> listOfRegion = regionRepository.findAll();

		if (listOfRegion.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Region>>(listOfRegion, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllRegion", method = RequestMethod.GET)
	public ResponseEntity<List<Region>> listAllRegionGet(HttpServletRequest request) {

		List<Region> listOfRegion = regionRepository.findAll();

		if (listOfRegion.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Region>>(listOfRegion, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllDepartment", method = RequestMethod.POST)
	public ResponseEntity<List<Department>> listAllDepartmentPost(HttpServletRequest request) {

		List<Department> listOfDepartment = departmentRepository.findAll();

		if (listOfDepartment.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Department>>(listOfDepartment, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllDepartment", method = RequestMethod.GET)
	public ResponseEntity<List<Department>> listAllDepartmentGet(HttpServletRequest request) {

		List<Department> listOfDepartment = departmentRepository.findAll();

		if (listOfDepartment.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Department>>(listOfDepartment, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllBorough", method = RequestMethod.POST)
	public ResponseEntity<List<Borough>> listAllBoroughPost(HttpServletRequest request) {

		List<Borough> listOfBorough = boroughRepository.findAll();

		if (listOfBorough.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Borough>>(listOfBorough, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllBorough", method = RequestMethod.GET)
	public ResponseEntity<List<Borough>> listAllBoroughGet(HttpServletRequest request) {

		List<Borough> listOfBorough = boroughRepository.findAll();

		if (listOfBorough.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Borough>>(listOfBorough, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllTown", method = RequestMethod.POST)
	public ResponseEntity<List<Town>> listAllTownPost(HttpServletRequest request) {

		List<Town> listOfTown = townRepository.findAll();

		if (listOfTown.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Town>>(listOfTown, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllStatus", method = RequestMethod.POST)
	public ResponseEntity<List<Status>> listAllStatusPost(HttpServletRequest request) {

		List<Status> listOfStatus = statusRepository.findAll();

		if (listOfStatus.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Status>>(listOfStatus, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllTown", method = RequestMethod.GET)
	public ResponseEntity<List<Town>> listAllTownGet(HttpServletRequest request) {

		List<Town> listOfTown = townRepository.findAll();

		if (listOfTown.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Town>>(listOfTown, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllConcession", method = RequestMethod.POST)
	public ResponseEntity<List<Concession>> listAllConcessionPost(HttpServletRequest request) {

		List<Concession> listOfConcession = concessionRepository.findAll();

		if (listOfConcession.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Concession>>(listOfConcession, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllConcession", method = RequestMethod.GET)
	public ResponseEntity<List<Concession>> listAllConcessionGet(HttpServletRequest request) {

		List<Concession> listOfConcession = concessionRepository.findAll();

		if (listOfConcession.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Concession>>(listOfConcession, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllStatus", method = RequestMethod.GET)
	public ResponseEntity<List<Status>> listAllStatusGet(HttpServletRequest request) {

		List<Status> listOfStatus = statusRepository.findAll();

		if (listOfStatus.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Status>>(listOfStatus, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllMember", method = RequestMethod.POST)
	public ResponseEntity<List<Member>> listAllMemberPost(HttpServletRequest request) {

		List<Member> listOfMember = memberRepository.findAll();

		if (listOfMember.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Member>>(listOfMember, HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/listAllMember", method = RequestMethod.GET)
	public ResponseEntity<List<Member>> listAllMemberGet(HttpServletRequest request) {

		List<Member> listOfMember = memberRepository.findAll();

		if (listOfMember.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Member>>(listOfMember, HttpStatus.OK);

	}

}
