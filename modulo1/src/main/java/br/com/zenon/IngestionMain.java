package br.com.zenon;


public class IngestionMain {

    public static void main(String[] args)
    {
        EfficientTransactionIngestor efficientTransactionIngestor = new EfficientTransactionIngestor();
       // efficientTransactionIngestor.readAsStream("data/dados.csv", TransactionSQLRepository::salvarUmaTrasacao);
        efficientTransactionIngestor.readBatch("data/dados.csv", list -> TransactionSQLRepository.salvarRegistrosComBatched(list));

    }



}
