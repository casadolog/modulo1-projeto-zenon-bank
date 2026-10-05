package br.com.zenon;

import java.util.Optional;

public abstract class TransactionRespository {
    protected final TransactionIngestor transactionIngestor;
    public TransactionRespository(TransactionIngestor transactionIngestor) {
        this.transactionIngestor = transactionIngestor;
    }
    public abstract Optional<Transaction> buscaUltimaByNomeCliente(String nome);
    public abstract Optional<Transaction> buscaByNomeCliente(String nome);

}
