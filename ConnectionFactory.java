package util;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionFactory {
    
    //tipo de banco que será conectado
    //essa biblioteca JDBC do java serve para todos os bancos, a unica coisa que modifica é o mysql, q poderia ser sql ou oracle.
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    
    //o endereço do banco de dados
    //se o banco estiver em um servidor específico no lugar da palavra localhost coloca o ip do servidor onde está o banco.
    public static final String URL = "jdbc:mysql://localhost:3306/minhaslista";
    
    //usuario padrão do XAMPP é root
    //se for um servidor real é o usuário adm do servidor
    public static final String USER = "root";
    
    //senha padrão do XAMPP é sem senha
    //se for um servidor real é a senha do usuário adm
    public static final String PASS = "";
    
    //precisa criar 3 métodos, um para conectar e dois para fechar a conexão
    public static Connection getConnection(){
        try{
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception ex){
            throw new RuntimeException("Erro na conexão com o banco de dados", ex);
        }
    }
    
    public static void closeConnection(Connection connection, PreparedStatement statement){
        try {
            if(connection != null){
                connection.close();
            }
            
            if(statement != null){
                statement.close();
            }
            
        } catch (Exception ex){
            throw new RuntimeException("Erro ao fechar a conexão com o banco de dados");
        }
    }
    
    public static void closeConnection(Connection connection,
            PreparedStatement statement, ResultSet resultSet){
        try {
            if(connection != null){
                connection.close();
            }
            
            if(statement != null){
                statement.close();
            }
            
            if(resultSet != null){
                resultSet.close();
            }
            
        } catch (Exception ex){
            throw new RuntimeException("Erro ao fechar a conexão com o banco de dados");
        }
    }
    
    
    
}
