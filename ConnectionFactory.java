package util;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionFactory {
    
    //tipo de banco que ser� conectado
    //essa biblioteca JDBC do java serve para todos os bancos, a unica coisa que modifica � o mysql, q poderia ser sql ou oracle.
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    
    //o endere�o do banco de dados
    //se o banco estiver em um servidor espec�fico no lugar da palavra localhost coloca o ip do servidor onde est� o banco.
    public static final String URL = "jdbc:mysql://localhost:3306/minhaslista";
    
    //usuario padr�o do XAMPP � root
    //se for um servidor real � o usu�rio adm do servidor
    public static final String USER = "root";
    
    //senha padr�o do XAMPP � sem senha
    //se for um servidor real � a senha do usu�rio adm
    public static final String PASS = "";
    
    //precisa criar 3 m�todos, um para conectar e dois para fechar a conex�o
    public static Connection getConnection(){
        try{
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception ex){
            throw new RuntimeException("Erro na conex�o com o banco de dados", ex);
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
            throw new RuntimeException("Erro ao fechar a conex�o com o banco de dados");
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
            throw new RuntimeException("Erro ao fechar a conex�o com o banco de dados");
        }
    }
    
    
    
}
