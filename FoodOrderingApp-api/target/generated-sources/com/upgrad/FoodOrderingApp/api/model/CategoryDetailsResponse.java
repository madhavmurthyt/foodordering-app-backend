package com.upgrad.FoodOrderingApp.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.upgrad.FoodOrderingApp.api.model.ItemList;
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
 * CategoryDetailsResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-08T17:53:58.904725+05:30[Asia/Kolkata]")
public class CategoryDetailsResponse {

  private UUID id;

  private String categoryName;

  @Valid
  private List<@Valid ItemList> itemList;

  public CategoryDetailsResponse id(UUID id) {
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

  public CategoryDetailsResponse categoryName(String categoryName) {
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

  public CategoryDetailsResponse itemList(List<@Valid ItemList> itemList) {
    this.itemList = itemList;
    return this;
  }

  public CategoryDetailsResponse addItemListItem(ItemList itemListItem) {
    if (this.itemList == null) {
      this.itemList = new ArrayList<>();
    }
    this.itemList.add(itemListItem);
    return this;
  }

  /**
   * List of items
   * @return itemList
  */
  @Valid 
  @Schema(name = "item_list", description = "List of items", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("item_list")
  public List<@Valid ItemList> getItemList() {
    return itemList;
  }

  public void setItemList(List<@Valid ItemList> itemList) {
    this.itemList = itemList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CategoryDetailsResponse categoryDetailsResponse = (CategoryDetailsResponse) o;
    return Objects.equals(this.id, categoryDetailsResponse.id) &&
        Objects.equals(this.categoryName, categoryDetailsResponse.categoryName) &&
        Objects.equals(this.itemList, categoryDetailsResponse.itemList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, categoryName, itemList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CategoryDetailsResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    categoryName: ").append(toIndentedString(categoryName)).append("\n");
    sb.append("    itemList: ").append(toIndentedString(itemList)).append("\n");
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

