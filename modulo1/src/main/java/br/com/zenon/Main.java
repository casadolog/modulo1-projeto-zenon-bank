package br.com.zenon;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception{
        TransactionIngestor transactionIngestor = new TransactionIngestor("dados.csv", 100000); //paysim_with_bad_data.csv | dados.csv

        List<Transaction> lista = transactionIngestor.getLista();

//        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer(lista);
//        System.out.println("total fraudes: "+fraudAnalyzer.totalFraudes());
//        System.out.println("Maiores Fraudes:");
//        fraudAnalyzer.maioresFraudes(3).forEach(v -> System.out.println(v.amount().toPlainString()));
//        System.out.println("Maiores Fraudadores");
//        fraudAnalyzer.maioresInfratores(5).entrySet().stream().forEach(e -> System.out.println(e.getKey()));
//        System.out.println("Valor total das infrações: "+fraudAnalyzer.valorTotalInfracao());
//        System.out.println("fraudes por tipo");
//        fraudAnalyzer.totalPorTipo().entrySet().stream().forEach( e -> System.out.println(e.getKey()+": "+e.getValue()));

        TransactionListRepository transactionListRepository = new TransactionListRepository(transactionIngestor);
        TransactionMapRepository transactionMapRepository = new TransactionMapRepository(transactionIngestor);

        Long tempo_inicio = System.nanoTime();
        String clientes[] = {"C1231006815","C12345"};
        for(String cliente : clientes) {
            Optional<Transaction> optionalTransaction = transactionListRepository.buscaByNomeCliente(cliente);
            System.out.println(optionalTransaction.isEmpty() ? "Transação não encontrada para cliente " + cliente :optionalTransaction.get());
        }
        System.out.println("Demorou: "+(System.nanoTime() - tempo_inicio));
        tempo_inicio = System.nanoTime();
        for(String cliente : clientes) {
            Optional<Transaction> optionalTransaction = transactionMapRepository.buscaByNomeCliente(cliente);
            System.out.println(optionalTransaction.isEmpty() ? "Transação não encontrada para cliente " + cliente :optionalTransaction.get());
        }
        System.out.println("Demorou: "+(System.nanoTime() - tempo_inicio));

    }
}