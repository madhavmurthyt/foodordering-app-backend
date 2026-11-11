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
 * SaveAddressRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-11T22:00:22.371070+05:30[Asia/Kolkata]")
public class SaveAddressRequest {

  private String flatBuildingName;

  private String locality;

  private String city;

  private String pincode;

  private String stateUuid;

  public SaveAddressRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SaveAddressRequest(String flatBuildingName, String locality, String city, String pincode, String stateUuid) {
    this.flatBuildingName = flatBuildingName;
    this.locality = locality;
    this.city = city;
    this.pincode = pincode;
    this.stateUuid = stateUuid;
  }

  public SaveAddressRequest flatBuildingName(String flatBuildingName) {
    this.flatBuildingName = flatBuildingName;
    return this;
  }

  /**
   * Flat and building address
   * @return flatBuildingName
  */
  @NotNull 
  @Schema(name = "flat_building_name", description = "Flat and building address", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("flat_building_name")
  public String getFlatBuildingName() {
    return flatBuildingName;
  }

  public void setFlatBuildingName(String flatBuildingName) {
    this.flatBuildingName = flatBuildingName;
  }

  public SaveAddressRequest locality(String locality) {
    this.locality = locality;
    return this;
  }

  /**
   * Locality of the address
   * @return locality
  */
  @NotNull 
  @Schema(name = "locality", description = "Locality of the address", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("locality")
  public String getLocality() {
    return locality;
  }

  public void setLocality(String locality) {
    this.locality = locality;
  }

  public SaveAddressRequest city(String city) {
    this.city = city;
    return this;
  }

  /**
   * City of the address
   * @return city
  */
  @NotNull 
  @Schema(name = "city", description = "City of the address", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("city")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public SaveAddressRequest pincode(String pincode) {
    this.pincode = pincode;
    return this;
  }

  /**
   * pincode of the address
   * @return pincode
  */
  @NotNull 
  @Schema(name = "pincode", description = "pincode of the address", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("pincode")
  public String getPincode() {
    return pincode;
  }

  public void setPincode(String pincode) {
    this.pincode = pincode;
  }

  public SaveAddressRequest stateUuid(String stateUuid) {
    this.stateUuid = stateUuid;
    return this;
  }

  /**
   * Id of the state
   * @return stateUuid
  */
  @NotNull 
  @Schema(name = "state_uuid", description = "Id of the state", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("state_uuid")
  public String getStateUuid() {
    return stateUuid;
  }

  public void setStateUuid(String stateUuid) {
    this.stateUuid = stateUuid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SaveAddressRequest saveAddressRequest = (SaveAddressRequest) o;
    return Objects.equals(this.flatBuildingName, saveAddressRequest.flatBuildingName) &&
        Objects.equals(this.locality, saveAddressRequest.locality) &&
        Objects.equals(this.city, saveAddressRequest.city) &&
        Objects.equals(this.pincode, saveAddressRequest.pincode) &&
        Objects.equals(this.stateUuid, saveAddressRequest.stateUuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(flatBuildingName, locality, city, pincode, stateUuid);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SaveAddressRequest {\n");
    sb.append("    flatBuildingName: ").append(toIndentedString(flatBuildingName)).append("\n");
    sb.append("    locality: ").append(toIndentedString(locality)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    pincode: ").append(toIndentedString(pincode)).append("\n");
    sb.append("    stateUuid: ").append(toIndentedString(stateUuid)).append("\n");
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

