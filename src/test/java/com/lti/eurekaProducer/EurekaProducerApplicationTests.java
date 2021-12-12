package com.lti.eurekaProducer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import com.lti.eurekaProducer.dao.CustomerRepository;
import com.lti.eurekaProducer.model.Customer;

@SpringBootTest
class EurekaProducerApplicationTests {

	@Autowired
	private CustomerRepository customerrepo;
	
	@Autowired
    private TestEntityManager testEntityManager;
	
	@Test
	 void createnewcustomer() {
		Customer customer= new Customer();
		Customer saveindb=testEntityManager.persist(customer);
		Customer getfromdb=customerrepo.findById(saveindb.getId()).get();
		assertThat(getfromdb).isEqualTo(saveindb);
	}
	
	@Test
	 void getcustomerbyid() {
		Customer customer=new Customer();
		customer.setName("Harsh");
		customer.setAge(18);
		customer.setAddress("Allahabad");
		customer.setAccType("Savings");
		Customer saveindb=testEntityManager.persist(customer);
		Customer getfromdb=customerrepo.findById(customer.getId()).get();
		assertThat(getfromdb).isEqualTo(saveindb);
	}
	
	@Test
	 void getallcustomer() {
		Customer customer1=new Customer();
		customer1.setName("Harsh");
		customer1.setAge(18);
		customer1.setAddress("Allahabad");
		customer1.setAccType("Savings");
		
		Customer customer2=new Customer();
		customer2.setName("Ram");
		customer2.setAge(18);
		customer2.setAddress("Vellore");
		customer2.setAccType("Savings");
		
		testEntityManager.persist(customer1);
		testEntityManager.persist(customer2);
		
		List<Customer> customerlist=customerrepo.findAll();
		assertEquals(2, customerlist.size());
		
	}
	
	@Test
	 void deletebyid() {
		Customer customer1=new Customer();
		customer1.setName("Harsh");
		customer1.setAge(18);
		customer1.setAddress("Allahabad");
		customer1.setAccType("Savings");
		
		Customer customer2=new Customer();
		customer2.setName("Ram");
		customer2.setAge(18);
		customer2.setAddress("Vellore");
		customer2.setAccType("Savings");
		
		Customer customer=testEntityManager.persist(customer1);
		testEntityManager.persist(customer2);
		
		testEntityManager.remove(customer);
		
		List<Customer> customerlist=customerrepo.findAll();
		assertEquals(1, customerlist.size());
	}
	
}
