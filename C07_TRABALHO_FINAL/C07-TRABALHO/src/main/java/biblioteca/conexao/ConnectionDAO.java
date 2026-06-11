package biblioteca.conexao;

import java.sql.*;

public abstract class ConnectionDAO{

    protected Connection connection;

    protected PreparedStatement pst;
    protected Statement st;
    protected ResultSet rs;

    String user = "usuario_bibliotecario";
    String password = "1234";
    String url = "jdbc:mysql://localhost:3306/biblioteca?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo";

    public Connection connectToDb(){
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
        return connection;
    }

    public void fecharRecursos() {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (st != null) st.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar recursos do banco: " + e.getMessage());
        }
    }

}
