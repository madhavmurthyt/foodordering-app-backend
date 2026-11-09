package com.upgrad.FoodOrderingApp.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.upgrad.FoodOrderingApp.api.model.ItemQuantity;
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
 * SaveOrderRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-08T17:53:58.805087+05:30[Asia/Kolkata]")
public class SaveOrderRequest {

  private String addressId;

  private UUID paymentId;

  private BigDecimal bill;

  private BigDecimal discount;

  private UUID couponId;

  private UUID restaurantId;

  @Valid
  private List<@Valid ItemQuantity> itemQuantities;

  public SaveOrderRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SaveOrderRequest(String addressId, UUID paymentId, BigDecimal bill, UUID restaurantId) {
    this.addressId = addressId;
    this.paymentId = paymentId;
    this.bill = bill;
    this.restaurantId = restaurantId;
  }

  public SaveOrderRequest addressId(String addressId) {
    this.addressId = addressId;
    return this;
  }

  /**
   * uuid of the address
   * @return addressId
  */
  @NotNull 
  @Schema(name = "address_id", description = "uuid of the address", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("address_id")
  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public SaveOrderRequest paymentId(UUID paymentId) {
    this.paymentId = paymentId;
    return this;
  }

  /**
   * uuid of the payment
   * @return paymentId
  */
  @NotNull @Valid 
  @Schema(name = "payment_id", description = "uuid of the payment", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("payment_id")
  public UUID getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(UUID paymentId) {
    this.paymentId = paymentId;
  }

  public SaveOrderRequest bill(BigDecimal bill) {
    this.bill = bill;
    return this;
  }

  /**
   * order bill
   * @return bill
  */
  @NotNull @Valid 
  @Schema(name = "bill", description = "order bill", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("bill")
  public BigDecimal getBill() {
    return bill;
  }

  public void setBill(BigDecimal bill) {
    this.bill = bill;
  }

  public SaveOrderRequest discount(BigDecimal discount) {
    this.discount = discount;
    return this;
  }

  /**
   * order discount
   * @return discount
  */
  @Valid 
  @Schema(name = "discount", description = "order discount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("discount")
  public BigDecimal getDiscount() {
    return discount;
  }

  public void setDiscount(BigDecimal discount) {
    this.discount = discount;
  }

  public SaveOrderRequest couponId(UUID couponId) {
    this.couponId = couponId;
    return this;
  }

  /**
   * uuid of the coupon
   * @return couponId
  */
  @Valid 
  @Schema(name = "coupon_id", description = "uuid of the coupon", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("coupon_id")
  public UUID getCouponId() {
    return couponId;
  }

  public void setCouponId(UUID couponId) {
    this.couponId = couponId;
  }

  public SaveOrderRequest restaurantId(UUID restaurantId) {
    this.restaurantId = restaurantId;
    return this;
  }

  /**
   * uuid of the restaurant
   * @return restaurantId
  */
  @NotNull @Valid 
  @Schema(name = "restaurant_id", description = "uuid of the restaurant", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("restaurant_id")
  public UUID getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(UUID restaurantId) {
    this.restaurantId = restaurantId;
  }

  public SaveOrderRequest itemQuantities(List<@Valid ItemQuantity> itemQuantities) {
    this.itemQuantities = itemQuantities;
    return this;
  }

  public SaveOrderRequest addItemQuantitiesItem(ItemQuantity itemQuantitiesItem) {
    if (this.itemQuantities == null) {
      this.itemQuantities = new ArrayList<>();
    }
    this.itemQuantities.add(itemQuantitiesItem);
    return this;
  }

  /**
   * List of item quantities.
   * @return itemQuantities
  */
  @Valid 
  @Schema(name = "item_quantities", description = "List of item quantities.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("item_quantities")
  public List<@Valid ItemQuantity> getItemQuantities() {
    return itemQuantities;
  }

  public void setItemQuantities(List<@Valid ItemQuantity> itemQuantities) {
    this.itemQuantities = itemQuantities;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SaveOrderRequest saveOrderRequest = (SaveOrderRequest) o;
    return Objects.equals(this.addressId, saveOrderRequest.addressId) &&
        Objects.equals(this.paymentId, saveOrderRequest.paymentId) &&
        Objects.equals(this.bill, saveOrderRequest.bill) &&
        Objects.equals(this.discount, saveOrderRequest.discount) &&
        Objects.equals(this.couponId, saveOrderRequest.couponId) &&
        Objects.equals(this.restaurantId, saveOrderRequest.restaurantId) &&
        Objects.equals(this.itemQuantities, saveOrderRequest.itemQuantities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressId, paymentId, bill, discount, couponId, restaurantId, itemQuantities);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SaveOrderRequest {\n");
    sb.append("    addressId: ").append(toIndentedString(addressId)).append("\n");
    sb.append("    paymentId: ").append(toIndentedString(paymentId)).append("\n");
    sb.append("    bill: ").append(toIndentedString(bill)).append("\n");
    sb.append("    discount: ").append(toIndentedString(discount)).append("\n");
    sb.append("    couponId: ").append(toIndentedString(couponId)).append("\n");
    sb.append("    restaurantId: ").append(toIndentedString(restaurantId)).append("\n");
    sb.append("    itemQuantities: ").append(toIndentedString(itemQuantities)).append("\n");
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

