package com.upgrad.FoodOrderingApp.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.upgrad.FoodOrderingApp.api.model.AddressListState;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AddressList
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-11T22:00:22.371070+05:30[Asia/Kolkata]")
public class AddressList {

  private UUID id;

  private String flatBuildingName;

  private String locality;

  private String city;

  private String pincode;

  private AddressListState state;

  public AddressList id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the address in a standard UUID format
   * @return id
  */
  @Valid 
  @Schema(name = "id", description = "Unique identifier of the address in a standard UUID format", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public AddressList flatBuildingName(String flatBuildingName) {
    this.flatBuildingName = flatBuildingName;
    return this;
  }

  /**
   * Flat and building address
   * @return flatBuildingName
  */
  
  @Schema(name = "flat_building_name", description = "Flat and building address", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("flat_building_name")
  public String getFlatBuildingName() {
    return flatBuildingName;
  }

  public void setFlatBuildingName(String flatBuildingName) {
    this.flatBuildingName = flatBuildingName;
  }

  public AddressList locality(String locality) {
    this.locality = locality;
    return this;
  }

  /**
   * Locality of the address
   * @return locality
  */
  
  @Schema(name = "locality", description = "Locality of the address", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("locality")
  public String getLocality() {
    return locality;
  }

  public void setLocality(String locality) {
    this.locality = locality;
  }

  public AddressList city(String city) {
    this.city = city;
    return this;
  }

  /**
   * City of the address
   * @return city
  */
  
  @Schema(name = "city", description = "City of the address", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("city")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public AddressList pincode(String pincode) {
    this.pincode = pincode;
    return this;
  }

  /**
   * pincode of the address
   * @return pincode
  */
  
  @Schema(name = "pincode", description = "pincode of the address", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("pincode")
  public String getPincode() {
    return pincode;
  }

  public void setPincode(String pincode) {
    this.pincode = pincode;
  }

  public AddressList state(AddressListState state) {
    this.state = state;
    return this;
  }

  /**
   * Get state
   * @return state
  */
  @Valid 
  @Schema(name = "state", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("state")
  public AddressListState getState() {
    return state;
  }

  public void setState(AddressListState state) {
    this.state = state;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddressList addressList = (AddressList) o;
    return Objects.equals(this.id, addressList.id) &&
        Objects.equals(this.flatBuildingName, addressList.flatBuildingName) &&
        Objects.equals(this.locality, addressList.locality) &&
        Objects.equals(this.city, addressList.city) &&
        Objects.equals(this.pincode, addressList.pincode) &&
        Objects.equals(this.state, addressList.state);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, flatBuildingName, locality, city, pincode, state);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddressList {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    flatBuildingName: ").append(toIndentedString(flatBuildingName)).append("\n");
    sb.append("    locality: ").append(toIndentedString(locality)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    pincode: ").append(toIndentedString(pincode)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
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

