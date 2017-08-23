package com.bocobi2.rencontre.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.servlet4preview.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.bocobi2.rencontre.model.Customer;
import com.bocobi2.rencontre.repositories.CustomerRepository;


@RestController
public class TestController {
	@Autowired
	private CustomerRepository customerRepository;

	@RequestMapping(value = "/hello1", method = RequestMethod.GET, params = { "name" })
	public String print(HttpServletRequest request) {
		String name= request.getParameter("name");
		System.out.println("{name: Hi "+name+ " , your are the Best}");
		return "{name: Hi "+name+ " , your are the Best}";
	}

	@RequestMapping(value = "/1", method = RequestMethod.GET)
	//public ModelAndView helloWorld( ModelMap model ) {
	public String helloWorld( HttpServletRequestWrapper model ) {
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
