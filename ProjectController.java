 package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

public class ProjectController {

public void save(Project project){
        String sql = "INSERT INTO project (name, "
                + " description, "
                + " createdAt, "
                + " updateAt) "
                + "VALUES (?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdateAt().getTime())); 
            statement.execute();            
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar o projeto ", ex);            
        } finally {
            ConnectionFactory.closeConnection(connection, statement);                        
        }        
    }
    
    public void update(Project project){
    
        String sql = "UPDATE projects SET "
                + "name = ?, "
                + "description = ?, "
                + "createdAt = ?, "
                + "updateAt = ?, "
                + "WHERE id = ?";
                //sempre coloque o WHERE NO UPDATE se não vai atualizar dodos os cadastros do banco.
                
                Connection connection = null;
                PreparedStatement statement = null;
                
        try {
            //Estabelecendo a conexao com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdateAt().getTime()));
            statement.setInt(5, project.getId());
            
            //Executando a query
            statement.execute();           
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar o projeto ", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }       
    }
       
    public List<Project> getAll(){
        String sql = "SELECT * FROM  projects";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        //Lista de tarefas que será devolvida quando a chamada do método acontercer        
        List<Project> projects = new ArrayList<>();
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
                      
            //valor retornado pela execução da query
            resultSet = statement.executeQuery();
            
            //Enquanto houverem valores a serem percorridos no meu result
            while(resultSet.next()){
            
                Project project = new Project();
                
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdateAt(resultSet.getDate("updateAt"));
                
                project.add(project);
                
            }
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar os projetos ", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }   
        
        //devolver a lista de tarefes que foi criada e carregada do bando de dados
        return projects;
    }
    
    public void removeById(int idProject){
        String sql = "DELETE FROM projectss WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idProject);
            statement.execute();
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar o projeto ", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
                
    }
    
}
