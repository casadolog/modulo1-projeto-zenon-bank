package br.com.zenon;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Scanner;

public class DataBase {

    private File arquivo;

    public DataBase()
    {
        arquivo = getFile();
    }
    private static File getFile()
    {
        return new File(Paths.get("data/dados.csv").toUri());
    }
    private Transaction linhaToTransactiton(String linha)
    {
        String[] dados = linha.split(",");
        
        int step = Integer.parseInt(dados[0]);
        TransactionType type = TransactionType.valueOf(dados[1]);
        BigDecimal amount = new BigDecimal(dados[2]);
        String nameOrig = dados[3];
        BigDecimal oldbalanceOrg = new BigDecimal(dados[4]);
        BigDecimal newbalanceOrig = new BigDecimal(dados[5]);
        String nameDest = dados[6];
        BigDecimal oldbalanceDest = new BigDecimal(dados[7]);
        BigDecimal	newbalanceDest = new BigDecimal(dados[8]);
        boolean isFraud = dados[9].equals("1");
        boolean isFlaggedFraud = dados[10].equals("1");
        
        return new Transaction(step,type, amount, nameOrig, oldbalanceOrg,
            newbalanceOrig, nameDest, oldbalanceDest, newbalanceDest,
        isFraud, isFlaggedFraud);
    }
    public Transaction getTransationByPosicao(int posicao) throws Exception {
        try(Scanner scanner = new Scanner(getFile()))
        {
            int cont = 0;
            while (scanner.hasNext()) {
                String linha = scanner.next();
               // System.out.println(scanner.next());
                if(cont == posicao)
                    return linhaToTransactiton(linha);
                cont++;
            }
        }
        throw new Exception("Linha não concontrada");
    }
}
