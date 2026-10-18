package sg.edu.ntu.simple_crm.config;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
// import java.util.Arrays;
import sg.edu.ntu.simple_crm.model.Customer;
import sg.edu.ntu.simple_crm.repository.CustomerRepository;

@Component
public class DataLoader {

    private CustomerRepository customerRepository;

    public DataLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Arrays.asList("VIP", "Referral"),
    @PostConstruct
    public void loadData() {
        // Clear the database first
        customerRepository.deleteAll();

        // Load data
        customerRepository.save(Customer.builder()
                .firstName("Sarah")
                .lastName("Chen")
                .email("sarah.chen@email.com")
                .phone("+65 9123 4567")
                .status("active")
                .tags("VIP, Referral")
                .company("Fintech Pte Ltd")
                .notes("Referred by Marcus. Interested in the enterprise plan.")
                .createdAt(LocalDate.parse("2024-03-12"))
                .build());
        customerRepository.save(Customer.builder()
                .firstName("James")
                .lastName("Tan")
                .email("james.tan@email.com")
                .phone("+65 9234 5678")
                .status("active")
                .tags("Lead")
                .company("Builders Co")
                .notes("Met at the PropTech conference. Follow up in Q3.")
                .createdAt(LocalDate.parse("2024-06-01"))
                .build());
        customerRepository.save(Customer.builder()
                .firstName("Nurul")
                .lastName("Huda")
                .email("nurul.huda@email.com")
                .phone("+65 9345 6789")
                .status("inactive")
                .tags("")
                .company("Gov Agency")
                .notes("")
                .createdAt(LocalDate.parse("2024-01-20"))
                .build());
        customerRepository.save(Customer.builder()
                .firstName("Marcus")
                .lastName("Lim")
                .email("marcus.lim@email.com")
                .phone("+65 9456 7890")
                .status("active")
                .tags("Lead, VIP")
                .company("RetailX Pte Ltd")
                .notes("High-value account. Has requested a custom SLA.")
                .createdAt(LocalDate.parse("2023-11-05"))
                .build());
        customerRepository.save(Customer.builder()
                .firstName("Wei Ling")
                .lastName("Goh")
                .email("weiling.goh@email.com")
                .phone("+65 9567 8901")
                .status("active")
                .tags("Referral")
                .company("HealthTech SG")
                .notes("Referred by Sarah Chen.")
                .createdAt(LocalDate.parse("2024-08-19"))
                .build());
    }

}
