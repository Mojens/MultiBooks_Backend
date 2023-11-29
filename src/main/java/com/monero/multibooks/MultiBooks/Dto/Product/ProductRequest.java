package com.monero.multibooks.MultiBooks.Dto.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ProductRequest {
    private Long id;
    private String productName;
    private double productCode;
    private double productAmount;
    private String productUnit;
    @JsonProperty("productPriceExclVAT")
    private double productPriceExclVAT;
    @JsonProperty("productPriceInclVAT")
    private double productPriceInclVAT;
    private String productDescription;
    private String productAccount;
    @JsonProperty("businessCVRNumber")
    private int businessCVRNumber;
}
