package br.com.sales.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class Sale {

    private Long id;
    private List<SaleItem> saleItems;
    private SalesMan salesMan;

}
