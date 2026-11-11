package com.upgrad.FoodOrderingApp.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * OrderListPayment
 */

@JsonTypeName("OrderList_payment")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-11T22:00:22.305950+05:30[Asia/Kolkata]")
public class OrderListPayment {

  private UUID id;

  private String paymentName;

  public OrderListPayment id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the payment in a standard UUID format
   * @return id
  */
  @Valid 
  @Schema(name = "id", description = "Unique identifier of the payment in a standard UUID format", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public OrderListPayment paymentName(String paymentName) {
    this.paymentName = paymentName;
    return this;
  }

  /**
   * Name of the payment method used
   * @return paymentName
  */
  
  @Schema(name = "payment_name", description = "Name of the payment method used", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("payment_name")
  public String getPaymentName() {
    return paymentName;
  }

  public void setPaymentName(String paymentName) {
    this.paymentName = paymentName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderListPayment orderListPayment = (OrderListPayment) o;
    return Objects.equals(this.id, orderListPayment.id) &&
        Objects.equals(this.paymentName, orderListPayment.paymentName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, paymentName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderListPayment {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    paymentName: ").append(toIndentedString(paymentName)).append("\n");
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

