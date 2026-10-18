package sg.edu.ntu.simple_crm.config;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import sg.edu.ntu.simple_crm.model.Customer;
import sg.edu.ntu.simple_crm.repository.CustomerRepository;

@Component
public class DataLoader {

    private CustomerRepository customerRepository;

    public DataLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Load initial customer data into the database
    @PostConstruct
    public void loadData() {
        // Clear the database first
        customerRepository.deleteAll();

        // Load data
        customerRepository.save(new Customer(
                "Sarah",
                "Chen",
                "sarah.chen@email.com",
                "+65 9123 4567",
                "active",
                new ArrayList<>(List.of("VIP", "Referral")),
                "Fintech Pte Ltd",
                "Referred by Marcus. Interested in the enterprise plan.",
                LocalDate.parse("2024-03-12")
        ));
        customerRepository.save(new Customer(
                "James",
                "Tan",
                "james.tan@email.com",
                "+65 9234 5678",
                "active",
                new ArrayList<>(List.of("Lead")),
                "Builders Co",
                "Met at the PropTech conference. Follow up in Q3.",
                LocalDate.parse("2024-06-01")
        ));
        customerRepository.save(new Customer(
                "Nurul",
                "Huda",
                "nurul.huda@email.com",
                "+65 9345 6789",
                "inactive",
                new ArrayList<>(List.of("")),
                "Gov Agency",
                "",
                LocalDate.parse("2024-01-20")
        ));
        customerRepository.save(new Customer(
                "Marcus",
                "Lim",
                "marcus.lim@email.com",
                "+65 9456 7890",
                "active",
                new ArrayList<>(List.of("Lead", "VIP")),
                "RetailX Pte Ltd",
                "High-value account. Has requested a custom SLA.",
                LocalDate.parse("2023-11-05")));
        customerRepository.save(new Customer(
                "Wei Ling",
                "Goh",
                "weiling.goh@email.com",
                "+65 9567 8901",
                "active",
                new ArrayList<>(List.of("Referral")),
                "HealthTech SG",
                "Referred by Sarah Chen.",
                LocalDate.parse("2024-08-19")
        ));
    }

}
