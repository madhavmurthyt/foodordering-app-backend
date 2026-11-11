package com.upgrad.FoodOrderingApp.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.upgrad.FoodOrderingApp.api.model.RestaurantList;
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
 * RestaurantListResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-11T22:00:22.232825+05:30[Asia/Kolkata]")
public class RestaurantListResponse {

  @Valid
  private List<@Valid RestaurantList> restaurants;

  public RestaurantListResponse restaurants(List<@Valid RestaurantList> restaurants) {
    this.restaurants = restaurants;
    return this;
  }

  public RestaurantListResponse addRestaurantsItem(RestaurantList restaurantsItem) {
    if (this.restaurants == null) {
      this.restaurants = new ArrayList<>();
    }
    this.restaurants.add(restaurantsItem);
    return this;
  }

  /**
   * List of restaurants
   * @return restaurants
  */
  @Valid 
  @Schema(name = "restaurants", description = "List of restaurants", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("restaurants")
  public List<@Valid RestaurantList> getRestaurants() {
    return restaurants;
  }

  public void setRestaurants(List<@Valid RestaurantList> restaurants) {
    this.restaurants = restaurants;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RestaurantListResponse restaurantListResponse = (RestaurantListResponse) o;
    return Objects.equals(this.restaurants, restaurantListResponse.restaurants);
  }

  @Override
  public int hashCode() {
    return Objects.hash(restaurants);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestaurantListResponse {\n");
    sb.append("    restaurants: ").append(toIndentedString(restaurants)).append("\n");
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

