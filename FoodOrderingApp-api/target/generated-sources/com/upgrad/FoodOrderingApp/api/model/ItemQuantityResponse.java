package com.upgrad.FoodOrderingApp.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.upgrad.FoodOrderingApp.api.model.ItemQuantityResponseItem;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ItemQuantityResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-11T22:00:22.305950+05:30[Asia/Kolkata]")
public class ItemQuantityResponse {

  private ItemQuantityResponseItem item;

  private Integer quantity;

  private Integer price;

  public ItemQuantityResponse item(ItemQuantityResponseItem item) {
    this.item = item;
    return this;
  }

  /**
   * Get item
   * @return item
  */
  @Valid 
  @Schema(name = "item", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("item")
  public ItemQuantityResponseItem getItem() {
    return item;
  }

  public void setItem(ItemQuantityResponseItem item) {
    this.item = item;
  }

  public ItemQuantityResponse quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Quantity of item ordered
   * @return quantity
  */
  
  @Schema(name = "quantity", description = "Quantity of item ordered", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("quantity")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public ItemQuantityResponse price(Integer price) {
    this.price = price;
    return this;
  }

  /**
   * Total price of the item
   * @return price
  */
  
  @Schema(name = "price", description = "Total price of the item", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("price")
  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemQuantityResponse itemQuantityResponse = (ItemQuantityResponse) o;
    return Objects.equals(this.item, itemQuantityResponse.item) &&
        Objects.equals(this.quantity, itemQuantityResponse.quantity) &&
        Objects.equals(this.price, itemQuantityResponse.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(item, quantity, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ItemQuantityResponse {\n");
    sb.append("    item: ").append(toIndentedString(item)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
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

