
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

public class TaskController {
    
    public void save(Task task){
        String sql = "INSERT INTO tasks (idProject,"
                + " name,"
                + " description,"
                + " completed,"
                + " notes,"
                + " deadline,"
                + " createdAt,"
                + " updateAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdateAt().getTime()));
            statement.execute();            
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa " 
                    +ex.getMessage(), ex);
            
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
            
            
        }
        
    }
    
    public void update(Task task){
    
        String sql = "UPDATE tasks SET "
                + "idProject = ?, "
                + "name = ?, "
                + "description = ?, "
                + "notes = ?, "
                + "completed = ?, "
                + "deadline = ?, "
                + "createdAt = ?, "
                + "updateAt = ?, "
                + "WHERE id = ?";
                //sempre coloque o WHERE NO UPDATE se n�o vai atualizar dodos os cadastros do banco.
                
                Connection connection = null;
                PreparedStatement statement = null;
                
        try {
            //Estabelecendo a conexao com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdateAt().getTime()));
            statement.setInt(9, task.getId());
            
            //Executando a query
            statement.execute();           
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa " 
                    + ex.getMessage(), ex);
        }
        
    }
    
    public void removeById(int taskId) throws SQLException{
        String sql = "DELETE tasks WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //cria��o da conex�o com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //preparando a query
            statement = connection.prepareStatement(sql);
            
            //substituir o ? do id pelo n�mero do registro
            //setando os valores
            statement.setInt(1, taskId);
                        
            //Executando a query
            statement.execute();
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Task> getAll(int idProject){
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        //Lista de tarefas que ser� devolvida quando a chamada do m�todo acontercer        
        List<Task> tasks = new ArrayList<Task>();
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            //setando o valor que corresponde ao filtro de busca
            statement.setInt(1, idProject);
            
            //valor retornado pela execu��o da query
            resultSet = statement.executeQuery();
            
            //Enquanto houverem valores a serem percorridos no meu result
            while(resultSet.next()){
            
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdateAt(resultSet.getDate("updateAt"));
                
                tasks.add(task);
                
            }
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir a tarefa " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }   
        
        //devolver a lista de tarefes que foi criada e carregada do bando de dados
        return tasks;
    }
    
}
