package sg.edu.ntu.simple_crm.model;

import java.time.LocalDate;
import java.util.List;

// import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder            // fluent object creation in tests
@EqualsAndHashCode  // value-based equality, required by assertEquals()
@NoArgsConstructor  // required by JPA
@AllArgsConstructor // required by @Builder when other constructors exist
@JsonPropertyOrder({ "id", "firstName", "lastName", "email", "phone", "status", "tags", "company", "notes", "createdAt" })
@Entity
@Table(name = "simple_crm")
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotBlank(message = "First name is mandatory")
  @Column(name = "first_name")
  private String firstName;

  @NotBlank(message = "Last name is mandatory")
  @Column(name = "last_name")
  private String lastName;

  @Email(message = "Email should be valid")
  @Column(name = "email")
  private String email;

  @Column(name = "phone")
  private String phone;

  @Column(name = "status")
  private String status;

  @Column(name = "tags")
  private List<String> tags;

  @Column(name = "company")
  private String company;

   @Column(name = "notes")
  private String notes;

   @Column(name = "created_at")
  private LocalDate createdAt;

  public Customer(String firstName, String lastName) {

    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Customer(String firstName, String lastName, String email, String phone, String status, List<String> tags, String company, String notes, LocalDate createdAt) {

    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.status = status;
    this.tags = tags;
    this.company = company;
    this.notes = notes;
    this.createdAt = createdAt;
  }

}