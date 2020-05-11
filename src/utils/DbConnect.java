package utils;

import java.sql.*;
import java.util.ArrayList;

public class DbConnect {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/pz_miner";
    static final String USER = "root";
    static final String PASSWORD = "";

    private Connection connection;
    private ResultSet resultSet;
    Statement statement;

    private ArrayList<String> nameResults = new ArrayList<>();
    private ArrayList<Integer> pointsResults = new ArrayList<>();
//SET GLOBAL time_zone = '+1:00'

    /**
     * construtior setting connection
     */
    public DbConnect()
    {
        try
        {

        } catch (Exception e) {
                e.printStackTrace();
                System.out.println("No database connection");
        }
    }

    /**
     * method quering top players
     */
    public void queryTopPlayerResults(){
        nameResults = new ArrayList<>();
        pointsResults = new ArrayList<>();
        String query = "SELECT * FROM player ORDER BY points DESC LIMIT 10;";
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pz_miner","root","");
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                //Retrieve by column name
                nameResults.add(resultSet.getString("name"));
                pointsResults.add(resultSet.getInt("points"));
            }
            resultSet.close();
            connection.close();
            statement.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            System.out.println("Cant connect to the database");
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    /**
     * Method Inserting player results
     * @param name player name to be inserted
     * @param points player points to be inserted
     */
    public void insertPlayerResult(String name, int points){
        String query = "INSERT INTO player (name, points) VALUES ('" + name + "'," + points + ");";
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pz_miner","root","");
            statement = connection.createStatement();
            System.out.println(query);
            statement.executeUpdate(query);
            connection.close();
            statement.close();
        } catch(SQLException se){
            //Handle errors for JDBC
            System.out.println("Cant connect to the database");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getNameResults(){
        return nameResults;
    }

    public ArrayList<Integer> getPointsResults(){
        return pointsResults;
    }
}
