package br.com.zenon;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FraudAnalyzer {
    private final List<Transaction> lista;


    public FraudAnalyzer(List<Transaction> lista) {
        this.lista = lista;
    }

    public int totalFraudes()
    {
        return lista.stream().filter(v -> {return v.isFraud();}).collect(Collectors.toList()).size();
    }
//        return lista.stream().filter(v -> {return v.isFraud();}).sorted((v1,v2) -> v1.amount().compareTo(v2.amount())).collect(Collectors.toList());
    public List<Transaction> maioresFraudes(int numero_itens)
    {
        return lista.stream().filter(v -> {return v.isFraud();}).sorted(Comparator.comparing(Transaction::amount).reversed()).limit(numero_itens).collect(Collectors.toList());
    }

    public Map<String, Double> totalPorInfrato()
    {
        return lista.stream()
                .filter(v -> {return v.isFraud();})
                .collect(Collectors.groupingBy(Transaction::nameOrig,Collectors.summingDouble(v -> v.amount().doubleValue())))
                ;
    }
    public Map<String, Double> maioresInfratores(int numero_itens)
    {
        return totalPorInfrato()
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String,Double>comparingByValue().reversed())
                .limit(numero_itens)
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(a,b)->a, LinkedHashMap::new));
    }
}
