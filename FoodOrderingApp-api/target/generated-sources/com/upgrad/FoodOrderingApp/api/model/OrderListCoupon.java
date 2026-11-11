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
 * OrderListCoupon
 */

@JsonTypeName("OrderList_coupon")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-11-11T22:00:22.305950+05:30[Asia/Kolkata]")
public class OrderListCoupon {

  private UUID id;

  private String couponName;

  private Integer percent;

  public OrderListCoupon id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier of the coupon in a standard UUID format
   * @return id
  */
  @Valid 
  @Schema(name = "id", description = "Unique identifier of the coupon in a standard UUID format", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public OrderListCoupon couponName(String couponName) {
    this.couponName = couponName;
    return this;
  }

  /**
   * Name of the coupon
   * @return couponName
  */
  
  @Schema(name = "coupon_name", description = "Name of the coupon", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("coupon_name")
  public String getCouponName() {
    return couponName;
  }

  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public OrderListCoupon percent(Integer percent) {
    this.percent = percent;
    return this;
  }

  /**
   * Discount percentage of the coupon
   * @return percent
  */
  
  @Schema(name = "percent", description = "Discount percentage of the coupon", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("percent")
  public Integer getPercent() {
    return percent;
  }

  public void setPercent(Integer percent) {
    this.percent = percent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderListCoupon orderListCoupon = (OrderListCoupon) o;
    return Objects.equals(this.id, orderListCoupon.id) &&
        Objects.equals(this.couponName, orderListCoupon.couponName) &&
        Objects.equals(this.percent, orderListCoupon.percent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, couponName, percent);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderListCoupon {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    couponName: ").append(toIndentedString(couponName)).append("\n");
    sb.append("    percent: ").append(toIndentedString(percent)).append("\n");
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

