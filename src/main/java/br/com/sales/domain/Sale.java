package br.com.sales.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Sale {

    private Long id;
    private List<SaleItem> saleItems = new ArrayList<>();
    private SalesMan salesMan;
    private BigDecimal total;

    public BigDecimal getTotal() {
        return saleItems.stream()
                .map(SaleItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
