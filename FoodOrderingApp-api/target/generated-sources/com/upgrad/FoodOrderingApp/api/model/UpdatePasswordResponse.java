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
 * UpdatePasswordResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-11T22:00:22.093054+05:30[Asia/Kolkata]")
public class UpdatePasswordResponse {

  private String id;

  private String status;

  public UpdatePasswordResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UpdatePasswordResponse(String id, String status) {
    this.id = id;
    this.status = status;
  }

  public UpdatePasswordResponse id(String id) {
    this.id = id;
    return this;
  }

  /**
   * uuid of the customer with updated password
   * @return id
  */
  @NotNull 
  @Schema(name = "id", description = "uuid of the customer with updated password", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UpdatePasswordResponse status(String status) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdatePasswordResponse updatePasswordResponse = (UpdatePasswordResponse) o;
    return Objects.equals(this.id, updatePasswordResponse.id) &&
        Objects.equals(this.status, updatePasswordResponse.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdatePasswordResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

