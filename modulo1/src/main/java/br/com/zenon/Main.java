package br.com.zenon;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception{
        TransactionIngestor transactionIngestor = new TransactionIngestor("dados.csv", 50000); //paysim_with_bad_data.csv | dados.csv

        List<Transaction> lista = transactionIngestor.getLista();

        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer(lista);

        System.out.println("total fraudes: "+fraudAnalyzer.totalFraudes());
        System.out.println("Maiores Fraudes:");
        fraudAnalyzer.maioresFraudes(3).forEach(v -> System.out.println(v.amount().toPlainString()));
        System.out.println("Maiores Fraudadores");
        fraudAnalyzer.maioresInfratores(5).entrySet().stream().forEach(e -> System.out.println(e.getKey()));

    }
}