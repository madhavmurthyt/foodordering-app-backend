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
 * LoginResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-08T17:53:58.610447+05:30[Asia/Kolkata]")
public class LoginResponse {

  private String id;

  private String message;

  private String firstName;

  private String lastName;

  private String emailAddress;

  private String contactNumber;

  public LoginResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public LoginResponse(String id, String message, String firstName, String lastName, String emailAddress, String contactNumber) {
    this.id = id;
    this.message = message;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.contactNumber = contactNumber;
  }

  public LoginResponse id(String id) {
    this.id = id;
    return this;
  }

  /**
   * uuid for the customer authentication after he signs in.
   * @return id
  */
  @NotNull 
  @Schema(name = "id", description = "uuid for the customer authentication after he signs in.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public LoginResponse message(String message) {
    this.message = message;
    return this;
  }

  /**
   * message to show the status of the signed in customer
   * @return message
  */
  @NotNull 
  @Schema(name = "message", description = "message to show the status of the signed in customer", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public LoginResponse firstName(String firstName) {
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

  public LoginResponse lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Last name of the new customer
   * @return lastName
  */
  @NotNull 
  @Schema(name = "last_name", description = "Last name of the new customer", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("last_name")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LoginResponse emailAddress(String emailAddress) {
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

  public LoginResponse contactNumber(String contactNumber) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoginResponse loginResponse = (LoginResponse) o;
    return Objects.equals(this.id, loginResponse.id) &&
        Objects.equals(this.message, loginResponse.message) &&
        Objects.equals(this.firstName, loginResponse.firstName) &&
        Objects.equals(this.lastName, loginResponse.lastName) &&
        Objects.equals(this.emailAddress, loginResponse.emailAddress) &&
        Objects.equals(this.contactNumber, loginResponse.contactNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, message, firstName, lastName, emailAddress, contactNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoginResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    emailAddress: ").append(toIndentedString(emailAddress)).append("\n");
    sb.append("    contactNumber: ").append(toIndentedString(contactNumber)).append("\n");
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

