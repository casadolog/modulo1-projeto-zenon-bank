package br.com.zenon;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Optional;

public class DBMain {
    public static void main(String[] args) throws FileNotFoundException {
        TransactionIngestor transactionIngestor = new TransactionIngestor("dados.csv", 10000);
        TransactionSQLRepository transactionSQLRepository = null;
        long tempo_inicio = 0;
        try {
            transactionSQLRepository = new TransactionSQLRepository(transactionIngestor);

            tempo_inicio = System.nanoTime();
            //transactionSQLRepository.salvarTodosRegistros();
            System.out.println("Gastou "+(System.nanoTime() - tempo_inicio)+" para salvar");

            String clientes[] = {"C1231006815","C12345"};
            for(String cliente : clientes) {
                Optional<Transaction> optionalTransaction = transactionSQLRepository.buscaByNomeCliente(cliente);
                System.out.println(optionalTransaction.isEmpty() ? "Transação não encontrada para cliente " + cliente :optionalTransaction.get());
            }
            System.out.println("Demorou: "+(System.nanoTime() - tempo_inicio));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(transactionSQLRepository != null)
                transactionSQLRepository.fechar();
        }
    }
}
