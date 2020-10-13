package br.com.sales.mapper;

import br.com.sales.config.SalesConfiguration;
import br.com.sales.domain.SaleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class SaleItemsMapper {

    @Autowired
    private SalesConfiguration salesConfig;

    public List<SaleItem> mapFieldSet(String itemsStr) {
        String[] items = itemsStr.replace("[", "")
                .replace("]", "")
                .split(salesConfig.getDelimiterItem());
        int count = 0;
        List<SaleItem> saleItems = new ArrayList<>();
        for (int i = count; i < items.length; i++) {
            String[] saleItem = items[i].split(salesConfig.getDelimiterItemField());
            saleItems.add(new SaleItem(Long.valueOf(saleItem[0]), Integer.valueOf(saleItem[1]),
                    new BigDecimal(saleItem[2])));
        }
        return saleItems;
    }

}
