package sg.edu.ntu.simple_crm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import sg.edu.ntu.simple_crm.exceptions.CustomerNotFoundException;
import sg.edu.ntu.simple_crm.model.Customer;
import sg.edu.ntu.simple_crm.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void createCustomer_validCustomer_returnsSavedCustomer() {

        // 1. ARRANGE
        Customer customer = Customer.builder()
                .firstName("Clint").lastName("Barton")
                .email("clint@avengers.com").phone("12345678")
                .status("active").tags(new ArrayList<>(List.of("Lead", "VIP"))).company("Avengers").notes("Test notes").createdAt(LocalDate.now())
                .build();

        // Program the mock. The real repository is never called, no database is
        // touched.
        when(customerRepository.save(customer)).thenReturn(customer);

        // 2. ACT
        Customer savedCustomer = customerService.createCustomer(customer);

        // 3. ASSERT
        assertEquals(customer, savedCustomer, "The saved customer should match the new customer");
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void getCustomer_missingId_throwsCustomerNotFoundException() {
        // 1. ARRANGE
        Long customerId = 1L;

        // Optional.empty() simulates no record found
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // 2. ACT + 3. ASSERT
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(customerId));
    }

    @ParameterizedTest
    @CsvSource({
            "Clint, Barton, clint@avengers.com, 12345678, active, VIP, Avengers, Test notes ABC, 2024-05-06",
            "Tony, Stark, tony@stark.com, 87654321, inactive, Lead, Stark Industries, Test notes, 2025-12-11",
            "Natasha, Romanoff, natasha@avengers.com, 11223344, active, VIP, Avengers, Test notes, 2026-03-08"
    })
    void createCustomers_validCustomers_returnsSavedCustomers(
            String firstName,
            String lastName,
            String email,
            String phone,
            String status,
            String tag,
            String company,
            String notes,
            LocalDate createdAt) {
        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .status(status)
                .tags(new ArrayList<>(List.of(tag)))
                .company(company)
                .notes(notes)
                .createdAt(createdAt)
                .build();

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer savedCustomer = customerService.createCustomer(customer);

        assertEquals(customer, savedCustomer);
        verify(customerRepository, times(1)).save(customer);
    }

}
