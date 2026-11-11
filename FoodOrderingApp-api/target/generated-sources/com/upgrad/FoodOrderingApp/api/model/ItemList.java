package com.upgrad.FoodOrderingApp.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * ItemList
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-11T22:00:22.458833+05:30[Asia/Kolkata]")
public class ItemList {

  private UUID id;

  private String itemName;

  private Integer price;

  /**
   * Gets or Sets itemType
   */
  public enum ItemTypeEnum {
    VEG("VEG"),
    
    NON_VEG("NON_VEG");

    private String value;

    ItemTypeEnum(String value) {
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
    public static ItemTypeEnum fromValue(String value) {
      for (ItemTypeEnum b : ItemTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private ItemTypeEnum itemType;

  public ItemList id(UUID id) {
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

  public ItemList itemName(String itemName) {
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

  public ItemList price(Integer price) {
    this.price = price;
    return this;
  }

  /**
   * Price of the item
   * @return price
  */
  
  @Schema(name = "price", description = "Price of the item", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("price")
  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public ItemList itemType(ItemTypeEnum itemType) {
    this.itemType = itemType;
    return this;
  }

  /**
   * Get itemType
   * @return itemType
  */
  
  @Schema(name = "item_type", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("item_type")
  public ItemTypeEnum getItemType() {
    return itemType;
  }

  public void setItemType(ItemTypeEnum itemType) {
    this.itemType = itemType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemList itemList = (ItemList) o;
    return Objects.equals(this.id, itemList.id) &&
        Objects.equals(this.itemName, itemList.itemName) &&
        Objects.equals(this.price, itemList.price) &&
        Objects.equals(this.itemType, itemList.itemType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, itemName, price, itemType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemList {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    itemName: ").append(toIndentedString(itemName)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    itemType: ").append(toIndentedString(itemType)).append("\n");
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

