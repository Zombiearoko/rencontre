package com.bocobi2.rencontre.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bocobi2.rencontre.model.Customer;
import com.bocobi2.rencontre.repositories.CustomerRepository;

@CrossOrigin(origins = "*")
@RestController
public class HelloController {
	@Autowired
	private CustomerRepository customerRepository;

	@RequestMapping(value = "/hello", method = RequestMethod.POST, params = { "name","surname" })
	public JSONObject print(HttpServletRequest request) {
		String name= request.getParameter("name");
		String surname= request.getParameter("surname");
		JSONObject param = new JSONObject();
		param.put("name", name);
		param.put("surname", surname);
		param.put("application_type", "Spring");
		System.out.println(param.toJSONString());
		System.out.println(param);
		return param;
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET, params = { "name","surname" })
	public JSONObject print1(HttpServletRequest request) {
		String name= request.getParameter("name");
		String surname= request.getParameter("surname");
		JSONObject param = new JSONObject();
		param.put("name", name);
		param.put("surname", surname);
		param.put("application_type", "Spring");
		System.out.println(param.toJSONString());
		System.out.println(param);
		return param;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	//public ModelAndView helloWorld( ModelMap model ) {
	public String helloWorld( HttpServletRequest model ) {
		customerRepository.deleteAll();

		// save a couple of customers
		customerRepository.save(new Customer("Alice", "Smith"));
		customerRepository.save(new Customer("Bob", "Smith"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : customerRepository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(customerRepository.findByFirstName("Alice"));

		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (Customer customer : customerRepository.findByLastName("Smith")) {
			System.out.println(customer);
		}
		return "Good";
	}
}
