package com.lti.eurekaProducer.controller;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.lti.eureka.producer.exception.ResourceNotFoundException;
import com.lti.eurekaProducer.dao.CustomerRepository;
import com.lti.eurekaProducer.model.Customer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
public class TestController {
		
	@Autowired
	CustomerRepository repository;
	
	//ADD Customer
	@RequestMapping(value = "/customer", method = RequestMethod.POST)
	public Customer addCustomer(@RequestBody Customer customer) {

		return repository.save(customer);
	}	
	//GET All Customer
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public List<Customer>  getCustomerdetails() {		
		 List<Customer> customerList= repository.findAll();
		 return customerList;	
	}
	//GET Customer by ID
	@RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
	public Customer getCustomerdetailbyId(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {		
	 return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("customer id was not found for the given ID :: "+ id));
	
	}
	//DELETE Customer
	@RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
	public Map<String, Boolean> deleteCustomerById(@PathVariable(value = "id") Integer id) {
		
		 repository.deleteById(id);
		 Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return response;	 
	}   
	//UPDATE Customer
	@RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT)
	public Customer updateEmployee(@PathVariable(value = "id") Integer Id,
		 @RequestBody Customer customerDetails) throws ResourceNotFoundException {
		
		Customer customers = repository.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer was not found for the given ID :: " + Id));

		customers.setAge(customerDetails.getAge());
		customers.setAddress(customerDetails.getAddress());
		customers.setName(customerDetails.getName());
		customers.setAccType(customerDetails.getAccType());
		return repository.save(customers);
	}
	//GET Customer
	@RequestMapping(value = "/getcustomer", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "getDataFallBack")
	public List<Customer>  getCustomer() {		
		 List<Customer> customerList= repository.findAll();
	
		 if(customerList==null) {
			  new RuntimeException();
		 }
		 return customerList;	
	}
	public List<Customer> getDataFallBack() {
		Customer cust = new Customer();
		cust.setName("Fallback-name");
		cust.setAddress("Fallback-address");
		cust.setAccType("Fallback-acc type");
		cust.setAge(31);
		List<Customer> customerlist = new ArrayList<Customer>();
		customerlist.add(cust);
		return customerlist; 
	}

}
