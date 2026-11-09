package com.upgrad.FoodOrderingApp.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SignupCustomerRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-08T17:53:58.610447+05:30[Asia/Kolkata]")
public class SignupCustomerRequest {

  private String firstName;

  private String lastName;

  private String emailAddress;

  private String contactNumber;

  private String password;

  public SignupCustomerRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SignupCustomerRequest(String firstName, String emailAddress, String contactNumber, String password) {
    this.firstName = firstName;
    this.emailAddress = emailAddress;
    this.contactNumber = contactNumber;
    this.password = password;
  }

  public SignupCustomerRequest firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * First name of the new customer
   * @return firstName
  */
  @NotNull 
  @Schema(name = "first_name", description = "First name of the new customer", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("first_name")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public SignupCustomerRequest lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Last name of the new customer
   * @return lastName
  */
  
  @Schema(name = "last_name", description = "Last name of the new customer", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("last_name")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public SignupCustomerRequest emailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  /**
   * Email address of the new customer
   * @return emailAddress
  */
  @NotNull 
  @Schema(name = "email_address", description = "Email address of the new customer", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email_address")
  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public SignupCustomerRequest contactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
    return this;
  }

  /**
   * Contact Number of the new customer
   * @return contactNumber
  */
  @NotNull 
  @Schema(name = "contact_number", description = "Contact Number of the new customer", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("contact_number")
  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public SignupCustomerRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Password of the new customer
   * @return password
  */
  @NotNull 
  @Schema(name = "password", description = "Password of the new customer", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SignupCustomerRequest signupCustomerRequest = (SignupCustomerRequest) o;
    return Objects.equals(this.firstName, signupCustomerRequest.firstName) &&
        Objects.equals(this.lastName, signupCustomerRequest.lastName) &&
        Objects.equals(this.emailAddress, signupCustomerRequest.emailAddress) &&
        Objects.equals(this.contactNumber, signupCustomerRequest.contactNumber) &&
        Objects.equals(this.password, signupCustomerRequest.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, emailAddress, contactNumber, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SignupCustomerRequest {\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    emailAddress: ").append(toIndentedString(emailAddress)).append("\n");
    sb.append("    contactNumber: ").append(toIndentedString(contactNumber)).append("\n");
    sb.append("    password: ").append("*").append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

