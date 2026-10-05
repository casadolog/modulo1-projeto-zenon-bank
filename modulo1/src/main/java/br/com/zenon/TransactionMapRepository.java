package br.com.zenon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TransactionMapRepository extends TransactionRespository{

    private Map<String,Transaction> respositorio;
    public TransactionMapRepository(TransactionIngestor transactionIngestor) {
        super(transactionIngestor);
        carregarMap();
    }

    @Override
    public Optional<Transaction> buscaUltimaByNomeCliente(String nome) {
        return buscaByNomeCliente(nome);
    }

    @Override
    public Optional<Transaction> buscaByNomeCliente(String nome) {
        if(respositorio.containsKey(nome))
            return Optional.of(respositorio.get(nome));

        return Optional.empty();
    }

    private void carregarMap()
    {
        List<Transaction> lista = transactionIngestor.getLista();

        respositorio = new HashMap<>();
        for(Transaction transaction: lista)
        {
            respositorio.put(transaction.nameOrig(),transaction);
        }
    }
}
