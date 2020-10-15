package br.com.sales.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SaleReport {

    private Integer customerQty = 0;
    private Integer salesManQty = 0;
    private Sale mostExpensiveSale;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private BigDecimal mostCheapSale = BigDecimal.ZERO;

    private SalesMan worstSalesMan;
    private final List<Sale> sales = new ArrayList<>();

    public void incrementCustomer() {
        customerQty++;
    }

    public void incrementSalesMan() {
        salesManQty++;
    }

    public void updateMostExpensiveSale(Sale sale) {
        if (this.mostExpensiveSale == null) {
            this.mostExpensiveSale = sale;
        }
        BigDecimal saleAmount = sale.getSaleItems().stream()
                .map(SaleItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (mostExpensiveSale.getTotal().compareTo(saleAmount) < 0) {
            this.mostExpensiveSale = sale;
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
