package br.com.zenon;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class TransactionSQLRepository  extends TransactionRespository{

    private static Connection connection;
    private static int contador = 0;
    private static int quantidade_salva = 0;

    public TransactionSQLRepository(TransactionIngestor transactionIngestor) throws SQLException {
        super(transactionIngestor);
        conectar();
    }
    public void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/transacoes?rewriteBatchedStatements=true";
        connection = DriverManager.getConnection(url,"root","Xulambs/9957");
    }
    public static void salvaUmTransacao(Transaction transaction)
    {

    }

    @Override
    public Optional<Transaction> buscaUltimaByNomeCliente(String nome) {
        return Optional.empty();
    }

    @Override
    public Optional<Transaction> buscaByNomeCliente(String nome) {
        String sql = "SELECT step,type,amount,name_orig,oldbalance_org,newbalance_orig,name_dest,oldbalance_dest,newbalance_dest,is_fraud,is_flagged_fraud FROM transactions WHERE name_orig LIKE ? ;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,nome);
            ResultSet resultSet = preparedStatement.executeQuery();

            boolean tem_proximo = resultSet.next();

            if(!tem_proximo)
                return Optional.empty();

            TransactionType transactionType = TransactionType.valueOf(resultSet.getString("type"));
            Transaction transaction = new Transaction(resultSet.getInt("step"),
                    transactionType, resultSet.getBigDecimal("amount"),
                    resultSet.getString("name_orig"),
                    resultSet.getBigDecimal("oldbalance_org"),
                    resultSet.getBigDecimal("newbalance_orig"),
                    resultSet.getString("name_dest"),
                    resultSet.getBigDecimal("newbalance_dest"),
                    resultSet.getBigDecimal("newbalance_dest"),
            resultSet.getBoolean("is_fraud"),
                    resultSet.getBoolean("is_flagged_fraud"));

            return Optional.of(transaction);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void save(Transaction transaction)  {
        String sql = "INSERT INTO transactions (step,type,amount,name_orig,oldbalance_org,newbalance_orig,name_dest,oldbalance_dest,newbalance_dest,is_fraud,is_flagged_fraud)VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = getConexao().prepareStatement(sql)) {
            preparedStatement.setInt(1, transaction.step());
            preparedStatement.setString(2, transaction.type().toString());
            preparedStatement.setBigDecimal(3, transaction.amount());
            preparedStatement.setString(4, transaction.nameOrig());
            preparedStatement.setBigDecimal(5, transaction.oldbalanceOrg());
            preparedStatement.setBigDecimal(6, transaction.newbalanceOrig());
            preparedStatement.setString(7, transaction.nameDest());
            preparedStatement.setBigDecimal(8, transaction.oldbalanceDest());
            preparedStatement.setBigDecimal(9, transaction.newbalanceDest());
            preparedStatement.setInt(10, transaction.isFraud() ? 1 : 0);
            preparedStatement.setInt(11, transaction.isFlaggedFraud() ? 1 : 0);

            preparedStatement.execute();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }

    public void salvarTodosRegistrosUmAUm() throws SQLException {
        for (Transaction transaction : transactionIngestor.getLista()) {
            save(transaction);
        }
    }
    public void salvarTodosRegistrosComBatched() throws SQLException {
        salvarRegistrosComBatched(transactionIngestor.getLista());
    }
    public static void salvarRegistrosComBatched(List<Transaction> list)
    {
        String sql = "INSERT INTO transactions " +
                "(step,type,amount,name_orig,oldbalance_org,newbalance_orig,name_dest,oldbalance_dest,newbalance_dest,is_fraud,is_flagged_fraud)" +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = getConexao().prepareStatement(sql)) {
            int cont = 0;
            final int TAMANHO_LOTE = 5000;

            for (Transaction transaction : list) {
                //salvarUmaTrasacao(transaction);

                preparedStatement.setInt(1, transaction.step());
                preparedStatement.setString(2, transaction.type().toString());
                preparedStatement.setBigDecimal(3, transaction.amount());
                preparedStatement.setString(4, transaction.nameOrig());
                preparedStatement.setBigDecimal(5, transaction.oldbalanceOrg());
                preparedStatement.setBigDecimal(6, transaction.newbalanceOrig());
                preparedStatement.setString(7, transaction.nameDest());
                preparedStatement.setBigDecimal(8, transaction.oldbalanceDest());
                preparedStatement.setBigDecimal(9, transaction.newbalanceDest());
                preparedStatement.setInt(10, transaction.isFraud() ? 1 : 0);
                preparedStatement.setInt(11, transaction.isFlaggedFraud() ? 1 : 0);
                preparedStatement.addBatch();
                cont++;
                if ((cont % TAMANHO_LOTE) == 0) {
                    System.out.println("x");
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }


            }
            preparedStatement.executeBatch();
            quantidade_salva += list.size();
            System.out.print(quantidade_salva+" ");
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    public void fechar() {
        try {
            connection.close();
        } catch (SQLException e) {
            //throw new RuntimeException(e);
        }
    }
    public static Connection getConexao()
    {
        if (connection == null)
        {
            String url = "jdbc:mysql://localhost:3306/transacoes?rewriteBatchedStatements=true";
            try {
                connection = DriverManager.getConnection(url, "root", "Xulambs/9957");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static Transaction salvarUmaTrasacao(Transaction transaction)  {
        try
        {

            String sql = "INSERT INTO transactions (step,type,amount,name_orig,oldbalance_org,newbalance_orig,name_dest,oldbalance_dest,newbalance_dest,is_fraud,is_flagged_fraud)VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = getConexao().prepareStatement(sql);
            preparedStatement.setInt(1, transaction.step());
            preparedStatement.setString(2, transaction.type().toString());
            preparedStatement.setBigDecimal(3, transaction.amount());
            preparedStatement.setString(4, transaction.nameOrig());
            preparedStatement.setBigDecimal(5, transaction.oldbalanceOrg());
            preparedStatement.setBigDecimal(6, transaction.newbalanceOrig());
            preparedStatement.setString(7, transaction.nameDest());
            preparedStatement.setBigDecimal(8, transaction.oldbalanceDest());
            preparedStatement.setBigDecimal(9, transaction.newbalanceDest());
            preparedStatement.setInt(10, transaction.isFraud() ? 1 : 0);
            preparedStatement.setInt(11, transaction.isFlaggedFraud() ? 1 : 0);

            preparedStatement.execute();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }


        contador++; System.out.print(contador+" ");
        return transaction;
    }
}
