package com.upgrad.FoodOrderingApp.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.upgrad.FoodOrderingApp.api.model.ItemQuantityResponse;
import com.upgrad.FoodOrderingApp.api.model.OrderListAddress;
import com.upgrad.FoodOrderingApp.api.model.OrderListCoupon;
import com.upgrad.FoodOrderingApp.api.model.OrderListCustomer;
import com.upgrad.FoodOrderingApp.api.model.OrderListPayment;
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
 * OrderList
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-08T17:53:58.805087+05:30[Asia/Kolkata]")
public class OrderList {

  private UUID id;

  private BigDecimal bill;

  private OrderListCoupon coupon;

  private BigDecimal discount;

  private String date;

  private OrderListPayment payment;

  private OrderListCustomer customer;

  private OrderListAddress address;

  @Valid
  private List<@Valid ItemQuantityResponse> itemQuantities;

  public OrderList id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the order in a standard UUID format
   * @return id
  */
  @Valid 
  @Schema(name = "id", description = "Unique identifier of the order in a standard UUID format", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public OrderList bill(BigDecimal bill) {
    this.bill = bill;
    return this;
  }

  /**
   * order bill
   * @return bill
  */
  @Valid 
  @Schema(name = "bill", description = "order bill", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("bill")
  public BigDecimal getBill() {
    return bill;
  }

  public void setBill(BigDecimal bill) {
    this.bill = bill;
  }

  public OrderList coupon(OrderListCoupon coupon) {
    this.coupon = coupon;
    return this;
  }

  /**
   * Get coupon
   * @return coupon
  */
  @Valid 
  @Schema(name = "coupon", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("coupon")
  public OrderListCoupon getCoupon() {
    return coupon;
  }

  public void setCoupon(OrderListCoupon coupon) {
    this.coupon = coupon;
  }

  public OrderList discount(BigDecimal discount) {
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

  public OrderList date(String date) {
    this.date = date;
    return this;
  }

  /**
   * Full date-time format (yyyy-MM-dd HH:mm)
   * @return date
  */
  
  @Schema(name = "date", description = "Full date-time format (yyyy-MM-dd HH:mm)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("date")
  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public OrderList payment(OrderListPayment payment) {
    this.payment = payment;
    return this;
  }

  /**
   * Get payment
   * @return payment
  */
  @Valid 
  @Schema(name = "payment", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("payment")
  public OrderListPayment getPayment() {
    return payment;
  }

  public void setPayment(OrderListPayment payment) {
    this.payment = payment;
  }

  public OrderList customer(OrderListCustomer customer) {
    this.customer = customer;
    return this;
  }

  /**
   * Get customer
   * @return customer
  */
  @Valid 
  @Schema(name = "customer", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("customer")
  public OrderListCustomer getCustomer() {
    return customer;
  }

  public void setCustomer(OrderListCustomer customer) {
    this.customer = customer;
  }

  public OrderList address(OrderListAddress address) {
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
  public OrderListAddress getAddress() {
    return address;
  }

  public void setAddress(OrderListAddress address) {
    this.address = address;
  }

  public OrderList itemQuantities(List<@Valid ItemQuantityResponse> itemQuantities) {
    this.itemQuantities = itemQuantities;
    return this;
  }

  public OrderList addItemQuantitiesItem(ItemQuantityResponse itemQuantitiesItem) {
    if (this.itemQuantities == null) {
      this.itemQuantities = new ArrayList<>();
    }
    this.itemQuantities.add(itemQuantitiesItem);
    return this;
  }

  /**
   * List of item quantity
   * @return itemQuantities
  */
  @Valid 
  @Schema(name = "item_quantities", description = "List of item quantity", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("item_quantities")
  public List<@Valid ItemQuantityResponse> getItemQuantities() {
    return itemQuantities;
  }

  public void setItemQuantities(List<@Valid ItemQuantityResponse> itemQuantities) {
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
    OrderList orderList = (OrderList) o;
    return Objects.equals(this.id, orderList.id) &&
        Objects.equals(this.bill, orderList.bill) &&
        Objects.equals(this.coupon, orderList.coupon) &&
        Objects.equals(this.discount, orderList.discount) &&
        Objects.equals(this.date, orderList.date) &&
        Objects.equals(this.payment, orderList.payment) &&
        Objects.equals(this.customer, orderList.customer) &&
        Objects.equals(this.address, orderList.address) &&
        Objects.equals(this.itemQuantities, orderList.itemQuantities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bill, coupon, discount, date, payment, customer, address, itemQuantities);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderList {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    bill: ").append(toIndentedString(bill)).append("\n");
    sb.append("    coupon: ").append(toIndentedString(coupon)).append("\n");
    sb.append("    discount: ").append(toIndentedString(discount)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    payment: ").append(toIndentedString(payment)).append("\n");
    sb.append("    customer: ").append(toIndentedString(customer)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
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

