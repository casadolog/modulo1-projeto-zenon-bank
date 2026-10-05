package br.com.zenon;

import java.util.List;
import java.util.Optional;

public class TransactionListRepository extends TransactionRespository {

    public TransactionListRepository(TransactionIngestor transactionIngestor) {
        super(transactionIngestor);
    }

    public Optional<Transaction> buscaByNomeCliente(String nome)
    {
        List lista = transactionIngestor.getLista().stream().filter(v ->{return v.nameOrig().equals(nome);}).toList();
        if(lista.isEmpty())
            return Optional.empty();

        return Optional.of((Transaction) lista.get(0));
    }
    public Optional<Transaction> buscaUltimaByNomeCliente(String nome)
    {
        List lista = transactionIngestor.getLista().stream().filter(v ->{return v.nameOrig().equals(nome);}).toList();
        if(lista.isEmpty())
            return Optional.empty();

        return Optional.of((Transaction) lista.getLast());
    }
}
