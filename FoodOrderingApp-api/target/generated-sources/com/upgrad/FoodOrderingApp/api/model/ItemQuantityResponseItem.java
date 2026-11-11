package com.upgrad.FoodOrderingApp.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ItemQuantityResponseItem
 */

@JsonTypeName("ItemQuantityResponse_item")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-11T22:00:22.305950+05:30[Asia/Kolkata]")
public class ItemQuantityResponseItem {

  private UUID id;

  private String itemName;

  private Integer itemPrice;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    VEG("VEG"),
    
    NON_VEG("NON_VEG");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TypeEnum type;

  public ItemQuantityResponseItem id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the item in a standard UUID format
   * @return id
  */
  @Valid 
  @Schema(name = "id", description = "Unique identifier of the item in a standard UUID format", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public ItemQuantityResponseItem itemName(String itemName) {
    this.itemName = itemName;
    return this;
  }

  /**
   * Name of the item
   * @return itemName
  */
  
  @Schema(name = "item_name", description = "Name of the item", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("item_name")
  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public ItemQuantityResponseItem itemPrice(Integer itemPrice) {
    this.itemPrice = itemPrice;
    return this;
  }

  /**
   * Price of the item
   * @return itemPrice
  */
  
  @Schema(name = "item_price", description = "Price of the item", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("item_price")
  public Integer getItemPrice() {
    return itemPrice;
  }

  public void setItemPrice(Integer itemPrice) {
    this.itemPrice = itemPrice;
  }

  public ItemQuantityResponseItem type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  */
  
  @Schema(name = "type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemQuantityResponseItem itemQuantityResponseItem = (ItemQuantityResponseItem) o;
    return Objects.equals(this.id, itemQuantityResponseItem.id) &&
        Objects.equals(this.itemName, itemQuantityResponseItem.itemName) &&
        Objects.equals(this.itemPrice, itemQuantityResponseItem.itemPrice) &&
        Objects.equals(this.type, itemQuantityResponseItem.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, itemName, itemPrice, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemQuantityResponseItem {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    itemPrice: ").append(toIndentedString(itemPrice)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

