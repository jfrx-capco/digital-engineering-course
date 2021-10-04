package com.digital.engineering.course.training.controller;

import com.digital.engineering.course.training.exception.CustomerNotFoundException;
import com.digital.engineering.course.training.model.Customer;
import com.digital.engineering.course.training.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(value="onlinecustomerservice", description="Resources to get and save details of a customer")
public class CustomerController {

    private final CustomerService customerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @ApiOperation(value = "Gets all the users stored in the DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got user details"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public List<Customer> getUsers() {
        List<Customer> allCustomers = customerService.getAllCustomers();
        LOGGER.info("All Customers found successfully. Customer details: "+allCustomers);
        return allCustomers;
    }

    @PostMapping
    @ApiOperation(value = "Saves details of a user in the DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully saved user details"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity saveUser(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.saveCustomer(customer);
        LOGGER.info("Customer created successfully: "+createdCustomer);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/id/{id}")
    @ApiOperation(value = "Deletes a user based on the ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted user details"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public void deleteUserById(@PathVariable String id) {
        LOGGER.info("Customer with ID: " +id+ " deleted successfully.");
        customerService.deleteCustomerById(id);
    }

    @GetMapping("/id/{id}")
    @ApiOperation(value = "Gets a user based on the ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully got user details"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The user with given Id is not found. Please try again")
    })
    public Customer getUserById(@PathVariable String id) {
        Customer customer = null;
        try {
            customer = customerService.getCustomerById(id);
            LOGGER.info("Customer details found: "+customer);
        } catch (CustomerNotFoundException e) {
            e.printStackTrace();
            LOGGER.debug("Customer with given ID: " +id+ " not found");
            LOGGER.error("Customer with given ID: " +id+ " not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        return customer;
    }
}
