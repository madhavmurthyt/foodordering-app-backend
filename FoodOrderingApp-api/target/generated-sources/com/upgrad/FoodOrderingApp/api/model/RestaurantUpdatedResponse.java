package com.upgrad.FoodOrderingApp.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * RestaurantUpdatedResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-11T22:00:22.232825+05:30[Asia/Kolkata]")
public class RestaurantUpdatedResponse {

  private UUID id;

  private String status;

  public RestaurantUpdatedResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public RestaurantUpdatedResponse(UUID id, String status) {
    this.id = id;
    this.status = status;
  }

  public RestaurantUpdatedResponse id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the restaurant in a standard UUID format
   * @return id
  */
  @NotNull @Valid 
  @Schema(name = "id", description = "Unique identifier of the restaurant in a standard UUID format", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public RestaurantUpdatedResponse status(String status) {
    this.status = status;
    return this;
  }

  /**
   * status of the updated restaurant
   * @return status
  */
  @NotNull 
  @Schema(name = "status", description = "status of the updated restaurant", requiredMode = Schema.RequiredMode.REQUIRED)
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
    RestaurantUpdatedResponse restaurantUpdatedResponse = (RestaurantUpdatedResponse) o;
    return Objects.equals(this.id, restaurantUpdatedResponse.id) &&
        Objects.equals(this.status, restaurantUpdatedResponse.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestaurantUpdatedResponse {\n");
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

