package br.com.zenon;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception{
        TransactionIngestor transactionIngestor = new TransactionIngestor("dados.csv");

        List<Transaction> lista = transactionIngestor.getLista();

        for(int i = 0; i < 10; i++)
            System.out.println(lista.get(i));
    }
}