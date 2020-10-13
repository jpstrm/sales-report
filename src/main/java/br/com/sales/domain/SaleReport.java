package br.com.sales.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SaleReport {

    private Integer customerQty = 0;
    private Integer salesManQty = 0;
    private BigDecimal mostExpensiveSale = BigDecimal.ZERO;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private BigDecimal mostCheapSale = BigDecimal.ZERO;

    private SalesMan worstSalesMan;

    public void incrementCustomer() {
        customerQty++;
    }

    public void incrementSalesMan() {
        salesManQty++;
    }

    public void updateMostExpensiveSale(Sale sale) {
        BigDecimal saleAmount = sale.getSaleItems().stream()
                .map(SaleItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (mostExpensiveSale.compareTo(saleAmount) < 0) {
            this.mostExpensiveSale = saleAmount;
        }
        updateWorstSalesMan(sale, saleAmount);
    }

    public void updateWorstSalesMan(Sale sale, BigDecimal saleAmount) {
        if (mostCheapSale.compareTo(BigDecimal.ZERO) == 0) {
            mostCheapSale = saleAmount;
            this.worstSalesMan = sale.getSalesMan();
        } else if (mostCheapSale.compareTo(saleAmount) > 0) {
            mostCheapSale = saleAmount;
            this.worstSalesMan = sale.getSalesMan();
        }
    }

}
