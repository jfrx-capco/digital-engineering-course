package com.digital.engineering.course.training.service;

import com.digital.engineering.course.training.exception.CustomerNotFoundException;
import com.digital.engineering.course.training.model.Customer;
import com.digital.engineering.course.training.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        Customer customerAdded = customerRepository.save(customer);
        return customerAdded;
    }

    public void deleteCustomerById(String id) {
        customerRepository.deleteById(id);
    }

    public Customer getCustomerById(String id) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if(!customer.isEmpty()) {
            return customer.get();
        } else {
            throw new CustomerNotFoundException("The user with given Id is not found. Please try again.");
        }
    }
}
