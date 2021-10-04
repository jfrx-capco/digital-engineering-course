package com.digital.engineering.course.training.service;

import com.digital.engineering.course.training.exception.CustomerNotFoundException;
import com.digital.engineering.course.training.model.Customer;
import com.digital.engineering.course.training.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void whenSaveUser_shouldReturnUser() {
        Customer user = new Customer("Jack", "Dawson");
        when(customerRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(user);
        Customer created = customerService.saveCustomer(user);
        assertThat(created.getFirstName()).isSameAs(user.getFirstName());
        assertThat(created.getLastName()).isSameAs(user.getLastName());
        verify(customerRepository, times(1)).save(user);
    }

    @Test
    public void whenGetAllUsers_shouldReturnAllUsers() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer("Alice", "Smith"));
        customerList.add(new Customer("Bob", "Smith"));
        given(customerRepository.findAll()).willReturn(customerList);
        List<Customer> expected = customerService.getAllCustomers();
        assertEquals(expected, customerList);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void whenGivenId_shouldDeleteUser_ifFound(){
        Customer user = new Customer();
        user.setFirstName("Dane");
        user.setLastName("Alvares");
        user.setId("111");
        customerService.deleteCustomerById(user.getId());
        verify(customerRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void whenGetUserById_withValidId_shouldReturnUser() throws CustomerNotFoundException {
        Customer user = new Customer("Jay", "Menon");
        given(customerRepository.findById(user.getId())).willReturn(Optional.of(user));
        Customer expected = customerService.getCustomerById(user.getId());
        assertEquals(expected, user);
        verify(customerRepository, times(1)).findById(user.getId());
    }

    @Test(expected = CustomerNotFoundException.class)
    public void whenGetUserById_withInvalidId_shouldReturnException() throws CustomerNotFoundException {
        Customer user = new Customer("Sid", "Daksh");
        String invalidCustomerId = "123";
        Customer expected = customerService.getCustomerById(invalidCustomerId);
        assertNull(expected);
    }
}
