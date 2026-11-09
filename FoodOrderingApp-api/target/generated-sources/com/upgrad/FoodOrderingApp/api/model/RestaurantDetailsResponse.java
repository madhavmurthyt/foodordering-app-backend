package com.upgrad.FoodOrderingApp.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.upgrad.FoodOrderingApp.api.model.CategoryList;
import com.upgrad.FoodOrderingApp.api.model.RestaurantDetailsResponseAddress;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Restaurant details
 */

@Schema(name = "RestaurantDetailsResponse", description = "Restaurant details")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-08T17:53:58.734447+05:30[Asia/Kolkata]")
public class RestaurantDetailsResponse {

  private UUID id;

  private String restaurantName;

  private String photoURL;

  private BigDecimal customerRating;

  private Integer averagePrice;

  private Integer numberCustomersRated;

  private RestaurantDetailsResponseAddress address;

  @Valid
  private List<@Valid CategoryList> categories;

  public RestaurantDetailsResponse id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the restaurant in a standard UUID format
   * @return id
  */
  @Valid 
  @Schema(name = "id", description = "Unique identifier of the restaurant in a standard UUID format", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public RestaurantDetailsResponse restaurantName(String restaurantName) {
    this.restaurantName = restaurantName;
    return this;
  }

  /**
   * Name of the restaurant
   * @return restaurantName
  */
  
  @Schema(name = "restaurant_name", description = "Name of the restaurant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("restaurant_name")
  public String getRestaurantName() {
    return restaurantName;
  }

  public void setRestaurantName(String restaurantName) {
    this.restaurantName = restaurantName;
  }

  public RestaurantDetailsResponse photoURL(String photoURL) {
    this.photoURL = photoURL;
    return this;
  }

  /**
   * URL for the picture of the restaurant
   * @return photoURL
  */
  
  @Schema(name = "photo_URL", description = "URL for the picture of the restaurant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("photo_URL")
  public String getPhotoURL() {
    return photoURL;
  }

  public void setPhotoURL(String photoURL) {
    this.photoURL = photoURL;
  }

  public RestaurantDetailsResponse customerRating(BigDecimal customerRating) {
    this.customerRating = customerRating;
    return this;
  }

  /**
   * Rating of the restaurant
   * @return customerRating
  */
  @Valid 
  @Schema(name = "customer_rating", description = "Rating of the restaurant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("customer_rating")
  public BigDecimal getCustomerRating() {
    return customerRating;
  }

  public void setCustomerRating(BigDecimal customerRating) {
    this.customerRating = customerRating;
  }

  public RestaurantDetailsResponse averagePrice(Integer averagePrice) {
    this.averagePrice = averagePrice;
    return this;
  }

  /**
   * Average price for two people
   * @return averagePrice
  */
  
  @Schema(name = "average_price", description = "Average price for two people", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("average_price")
  public Integer getAveragePrice() {
    return averagePrice;
  }

  public void setAveragePrice(Integer averagePrice) {
    this.averagePrice = averagePrice;
  }

  public RestaurantDetailsResponse numberCustomersRated(Integer numberCustomersRated) {
    this.numberCustomersRated = numberCustomersRated;
    return this;
  }

  /**
   * Number of customers rated the restaurant
   * @return numberCustomersRated
  */
  
  @Schema(name = "number_customers_rated", description = "Number of customers rated the restaurant", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("number_customers_rated")
  public Integer getNumberCustomersRated() {
    return numberCustomersRated;
  }

  public void setNumberCustomersRated(Integer numberCustomersRated) {
    this.numberCustomersRated = numberCustomersRated;
  }

  public RestaurantDetailsResponse address(RestaurantDetailsResponseAddress address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
  */
  @Valid 
  @Schema(name = "address", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("address")
  public RestaurantDetailsResponseAddress getAddress() {
    return address;
  }

  public void setAddress(RestaurantDetailsResponseAddress address) {
    this.address = address;
  }

  public RestaurantDetailsResponse categories(List<@Valid CategoryList> categories) {
    this.categories = categories;
    return this;
  }

  public RestaurantDetailsResponse addCategoriesItem(CategoryList categoriesItem) {
    if (this.categories == null) {
      this.categories = new ArrayList<>();
    }
    this.categories.add(categoriesItem);
    return this;
  }

  /**
   * List of categories
   * @return categories
  */
  @Valid 
  @Schema(name = "categories", description = "List of categories", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("categories")
  public List<@Valid CategoryList> getCategories() {
    return categories;
  }

  public void setCategories(List<@Valid CategoryList> categories) {
    this.categories = categories;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RestaurantDetailsResponse restaurantDetailsResponse = (RestaurantDetailsResponse) o;
    return Objects.equals(this.id, restaurantDetailsResponse.id) &&
        Objects.equals(this.restaurantName, restaurantDetailsResponse.restaurantName) &&
        Objects.equals(this.photoURL, restaurantDetailsResponse.photoURL) &&
        Objects.equals(this.customerRating, restaurantDetailsResponse.customerRating) &&
        Objects.equals(this.averagePrice, restaurantDetailsResponse.averagePrice) &&
        Objects.equals(this.numberCustomersRated, restaurantDetailsResponse.numberCustomersRated) &&
        Objects.equals(this.address, restaurantDetailsResponse.address) &&
        Objects.equals(this.categories, restaurantDetailsResponse.categories);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, restaurantName, photoURL, customerRating, averagePrice, numberCustomersRated, address, categories);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RestaurantDetailsResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    restaurantName: ").append(toIndentedString(restaurantName)).append("\n");
    sb.append("    photoURL: ").append(toIndentedString(photoURL)).append("\n");
    sb.append("    customerRating: ").append(toIndentedString(customerRating)).append("\n");
    sb.append("    averagePrice: ").append(toIndentedString(averagePrice)).append("\n");
    sb.append("    numberCustomersRated: ").append(toIndentedString(numberCustomersRated)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    categories: ").append(toIndentedString(categories)).append("\n");
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

