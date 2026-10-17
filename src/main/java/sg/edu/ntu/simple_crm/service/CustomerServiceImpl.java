package sg.edu.ntu.simple_crm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.ntu.simple_crm.exceptions.CustomerNotFoundException;
import sg.edu.ntu.simple_crm.model.Customer;
import sg.edu.ntu.simple_crm.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Create
    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Get one customer
    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    // Get All Customer
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // updateCustomer
    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        // Retrieve customer to be updated
        Customer customerToUpdate = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        // Update the fileds
        customerToUpdate.setFirstName(customer.getFirstName());
        customerToUpdate.setLastName(customer.getLastName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setStatus(customer.getStatus());
        customerToUpdate.setTags(customer.getTags());
        customerToUpdate.setCompany(customer.getCompany());
        customerToUpdate.setNotes(customer.getNotes());
        customerToUpdate.setCreatedAt(customer.getCreatedAt());

        // save and return the updated customer
        return customerRepository.save(customerToUpdate);
    }

    // delete
    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

}
