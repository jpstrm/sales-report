package br.com.sales.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Customer {

    private String cpf;
    private String name;
    private String businessArea;

}
