package br.com.sales.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaleItem {

    private Long id;
    private Integer quantity;
    private BigDecimal price;

}
