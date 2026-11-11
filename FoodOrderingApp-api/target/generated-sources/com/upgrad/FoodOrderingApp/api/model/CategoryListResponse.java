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
 * CategoryListResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-11T22:00:22.414014+05:30[Asia/Kolkata]")
public class CategoryListResponse {

  private UUID id;

  private String categoryName;

  public CategoryListResponse id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the category in a standard UUID format
   * @return id
  */
  @Valid 
  @Schema(name = "id", description = "Unique identifier of the category in a standard UUID format", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public CategoryListResponse categoryName(String categoryName) {
    this.categoryName = categoryName;
    return this;
  }

  /**
   * Name of the category
   * @return categoryName
  */
  
  @Schema(name = "category_name", description = "Name of the category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("category_name")
  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CategoryListResponse categoryListResponse = (CategoryListResponse) o;
    return Objects.equals(this.id, categoryListResponse.id) &&
        Objects.equals(this.categoryName, categoryListResponse.categoryName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, categoryName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CategoryListResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    categoryName: ").append(toIndentedString(categoryName)).append("\n");
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

