package com.upgrad.FoodOrderingApp.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.upgrad.FoodOrderingApp.api.model.StatesList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * StatesListResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-08T17:53:58.864512+05:30[Asia/Kolkata]")
public class StatesListResponse {

  @Valid
  private List<@Valid StatesList> states;

  public StatesListResponse states(List<@Valid StatesList> states) {
    this.states = states;
    return this;
  }

  public StatesListResponse addStatesItem(StatesList statesItem) {
    if (this.states == null) {
      this.states = new ArrayList<>();
    }
    this.states.add(statesItem);
    return this;
  }

  /**
   * List of states
   * @return states
  */
  @Valid 
  @Schema(name = "states", description = "List of states", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("states")
  public List<@Valid StatesList> getStates() {
    return states;
  }

  public void setStates(List<@Valid StatesList> states) {
    this.states = states;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatesListResponse statesListResponse = (StatesListResponse) o;
    return Objects.equals(this.states, statesListResponse.states);
  }

  @Override
  public int hashCode() {
    return Objects.hash(states);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatesListResponse {\n");
    sb.append("    states: ").append(toIndentedString(states)).append("\n");
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

