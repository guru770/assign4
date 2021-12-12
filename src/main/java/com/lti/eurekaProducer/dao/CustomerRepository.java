package com.lti.eurekaProducer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.lti.eurekaProducer.model.Customer;
@Repository
@EnableJpaRepositories
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	@Query("select c from Customer c WHERE c.name=?1")
	Customer searchbyName(String name);

}
