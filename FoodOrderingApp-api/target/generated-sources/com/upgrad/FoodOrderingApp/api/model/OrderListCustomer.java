package com.upgrad.FoodOrderingApp.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * OrderListCustomer
 */

@JsonTypeName("OrderList_customer")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-11T22:00:22.305950+05:30[Asia/Kolkata]")
public class OrderListCustomer {

  private UUID id;

  private String firstName;

  private String lastName;

  private String emailAddress;

  private String contactNumber;

  public OrderListCustomer id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the customer in a standard UUID format
   * @return id
  */
  @Valid 
  @Schema(name = "id", description = "Unique identifier of the customer in a standard UUID format", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public OrderListCustomer firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * First name of the new customer
   * @return firstName
  */
  
  @Schema(name = "first_name", description = "First name of the new customer", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("first_name")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public OrderListCustomer lastName(String lastName) {
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

  public OrderListCustomer emailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

  /**
   * Email address of the new customer
   * @return emailAddress
  */
  
  @Schema(name = "email_address", description = "Email address of the new customer", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email_address")
  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public OrderListCustomer contactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
    return this;
  }

  /**
   * Contact Number of the new customer
   * @return contactNumber
  */
  
  @Schema(name = "contact_number", description = "Contact Number of the new customer", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
    OrderListCustomer orderListCustomer = (OrderListCustomer) o;
    return Objects.equals(this.id, orderListCustomer.id) &&
        Objects.equals(this.firstName, orderListCustomer.firstName) &&
        Objects.equals(this.lastName, orderListCustomer.lastName) &&
        Objects.equals(this.emailAddress, orderListCustomer.emailAddress) &&
        Objects.equals(this.contactNumber, orderListCustomer.contactNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, emailAddress, contactNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderListCustomer {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

