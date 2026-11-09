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
 * UpdateCustomerResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-08T17:53:58.610447+05:30[Asia/Kolkata]")
public class UpdateCustomerResponse {

  private String id;

  private String status;

  private String firstName;

  private String lastName;

  public UpdateCustomerResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UpdateCustomerResponse(String id, String status, String firstName) {
    this.id = id;
    this.status = status;
    this.firstName = firstName;
  }

  public UpdateCustomerResponse id(String id) {
    this.id = id;
    return this;
  }

  /**
   * uuid of the customer with updated details
   * @return id
  */
  @NotNull 
  @Schema(name = "id", description = "uuid of the customer with updated details", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UpdateCustomerResponse status(String status) {
    this.status = status;
    return this;
  }

  /**
   * message showing the status of the customer
   * @return status
  */
  @NotNull 
  @Schema(name = "status", description = "message showing the status of the customer", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public UpdateCustomerResponse firstName(String firstName) {
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

  public UpdateCustomerResponse lastName(String lastName) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateCustomerResponse updateCustomerResponse = (UpdateCustomerResponse) o;
    return Objects.equals(this.id, updateCustomerResponse.id) &&
        Objects.equals(this.status, updateCustomerResponse.status) &&
        Objects.equals(this.firstName, updateCustomerResponse.firstName) &&
        Objects.equals(this.lastName, updateCustomerResponse.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status, firstName, lastName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateCustomerResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
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

