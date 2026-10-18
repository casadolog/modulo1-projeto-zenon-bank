package br.com.zenon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;

public class IngestionMain {

    public static void main(String[] args) throws Exception
    {
        EfficientTransactionIngestor efficientTransactionIngestor = new EfficientTransactionIngestor();
       // efficientTransactionIngestor.readAsStream("data/dados.csv", TransactionSQLRepository::salvarUmaTrasacao);
        efficientTransactionIngestor.readBatch("data/dados.csv", list -> TransactionSQLRepository.salvarRegistrosComBatched(list));

    }



}
