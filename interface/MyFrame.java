import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

public class MyFrame extends JFrame implements ActionListener{


    JComboBox comboBox;
    String connection;

    MyFrame(String connectionUrl){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout()); 
        
        this.connection = connectionUrl;


        //Our queries

        String[] queries = {"Please select the query from the drop-down menu ->.","School divisions sorted by size.","Number of election candidates per school division.",
        "The top 5 candidates with the highest votes across all divisions.",
        "All speed limits in Winnipeg with the number of streets within each speed limit","The top 5 School Divisions with the highest number of students (assuming votes as a proxy):",
        "All parks with no associated assets.","Number of School Zone Signs at corresponding schools and streets.","The top 5 Electoral Wards with the highest number of neighbourhoods",
        "Enter the speed limit in the console and display all streets with that max speed limit.","Enter your postal code, and display all schools and their information that are in that area."};


        //creating combobox
        comboBox = new JComboBox(queries);

        comboBox.addActionListener(this);


        this.add(comboBox);


        this.pack();
        this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){


        
        if(e.getSource() == comboBox) {
            if(comboBox.getSelectedIndex() == 0) {
                
            }
            if(comboBox.getSelectedIndex() == 1) {
                
                schDivBySize(connection);
                
            }
            if(comboBox.getSelectedIndex() == 2) {
                
                numElecCandidates(connection);
            }
            if(comboBox.getSelectedIndex() == 3) {
                
                top5cand(connection);
            }
            if(comboBox.getSelectedIndex() == 4) {
                
                countSpeedLmt(connection);
            }
            if(comboBox.getSelectedIndex() == 5) {
                highestStudents(connection);
            }
            if(comboBox.getSelectedIndex() == 6) {
                noAssets(connection);
            }
            if(comboBox.getSelectedIndex() == 7) {
                zoneSigns(connection);
            }
            if(comboBox.getSelectedIndex() == 8) {
                highestNeighbourhoods(connection);
            }
            if(comboBox.getSelectedIndex() == 9) {


               
                try{
                    System.out.println("Enter the speed limit");
                    Scanner scanner = new Scanner(System.in);
                    String speed = scanner.nextLine();
                    getStreetsBySpeedLimit(connection, speed);
                }

                catch(Exception Exception){
                    System.out.println("ERROR: TRY AGAIN");
                }
                
            }
            if(comboBox.getSelectedIndex() == 10) {


                System.out.println("Enter postal code for eg- R5G 1J4, R0E 1A0, R2X 2G2, R0E 1P0 etc. ");
                try{
                    Scanner scanner = new Scanner(System.in);
                    String code = scanner.nextLine(); 
                    getSchoolsByPostalCode(connection, code);
                }
                catch(Exception Exception){
                    System.out.println("ERROR: TRY AGAIN");
                }
                
                
            }
        }

    }

    public void schDivBySize(String connectionUrl){
        ResultSet resultSet = null;
    
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {
            

            System.out.println(comboBox.getSelectedItem());
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT divisionName, divisionSize FROM SchoolDivision ORDER BY divisionSize;";
            resultSet = statement.executeQuery(selectSql);
    
            ArrayList<String> large = new ArrayList<String>();
            ArrayList<String> medium = new ArrayList<String>();
            ArrayList<String> small = new ArrayList<String>();
    
            // Retrieve divisions based on size
            while (resultSet.next()) {
                String divisionName = resultSet.getString(1);
                String divisionSize = resultSet.getString(2);
    
                if (divisionSize.equals("Large")) {
                    large.add(divisionName);
                } else if (divisionSize.equals("Medium")) {
                    medium.add(divisionName);
                } else if (divisionSize.equals("Small")) {
                    small.add(divisionName);
                }
            }
    
            // Print divisions after retrieving them
            System.out.println("LARGE DIVISIONS:");
            System.out.println();
            for (String word : large) {
                System.out.println(word);
            }
            System.out.println();
            System.out.println("MEDIUM DIVISIONS:");
            System.out.println();
            for (String word : medium) {
                System.out.println(word);
            }
            System.out.println();
            System.out.println("SMALL DIVISIONS:");
            System.out.println();
            for (String word : small) {
                System.out.println(word);
            }
            
            System.out.println(); 

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void numElecCandidates(String connectionUrl){

        ResultSet resultSet = null;
        System.out.println(comboBox.getSelectedItem());
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {
    
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT divisionName, COUNT(*) AS CandidateCount " + 
                    "FROM ElectionCandidates " + 
                    "WHERE divisionName IS NOT NULL " + 
                      "GROUP BY divisionName;";

            resultSet = statement.executeQuery(selectSql);

             while (resultSet.next()) {
            
            System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));

             }
             System.out.println();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

       public void top5cand(String connectionUrl){


        System.out.println(comboBox.getSelectedItem());
        ResultSet resultSet = null;
    
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {
    
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT TOP 5 EC.name AS CandidateName, EC.divisionName AS DivisionName, SUM(ER.votes) AS TotalVotes " + //
                    "FROM ElectionCandidates EC " + //
                    "JOIN ElectionResult ER ON EC.candidateID = ER.candidateID " + //
                    "WHERE EC.divisionName IS NOT NULL " + //
                    "GROUP BY EC.name, EC.divisionName " + //
                    "ORDER BY TotalVotes DESC; ";

            resultSet = statement.executeQuery(selectSql);

             while (resultSet.next()) {
            
            System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2)+ " - " + resultSet.getString(3));
            
             }

             System.out.println();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void countSpeedLmt(String connectionUrl){


        System.out.println(comboBox.getSelectedItem());
        ResultSet resultSet = null;
    
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {
    
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT limit , COUNT(*) AS NumberOfStreets " + 
                    "FROM SpeedLimit " + 
                    "WHERE limit IN (30, 50, 60, 70, 80, 90) " + 
                    "GROUP BY limit;";

            resultSet = statement.executeQuery(selectSql);

             while (resultSet.next()) {
            
            System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2));
            
             }

             System.out.println();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    
    public void highestStudents(String connectionUrl){


        System.out.println(comboBox.getSelectedItem());
        ResultSet resultSet = null;
    
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {
    
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT TOP 5 sc.divisionName, COUNT(er.votes) AS TotalVotes\r\n" + //
                    "FROM ElectionResult er\r\n" + //
                    "JOIN SchoolDivision sc ON er.divisionName = sc.divisionName\r\n" + //
                    "GROUP BY sc.divisionName\r\n" + //
                    "ORDER BY COUNT(er.votes) DESC;";

            resultSet = statement.executeQuery(selectSql);

             while (resultSet.next()) {
            
            System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2));
            
             }

             System.out.println();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



        public void noAssets(String connectionUrl){


        System.out.println(comboBox.getSelectedItem());
        ResultSet resultSet = null;
    
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {
    
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT p.parkName FROM Park p LEFT JOIN ParkAsset pa ON p.ParkID = pa.parkID WHERE pa.assetID IS NULL;";

            resultSet = statement.executeQuery(selectSql);

             while (resultSet.next()) {
            
            System.out.println(resultSet.getString(1));
            
             }

             System.out.println();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    
        public void zoneSigns(String connectionUrl){


        System.out.println(comboBox.getSelectedItem());
        ResultSet resultSet = null;
    
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {
    
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT  s.schoolName, st.FullName, COUNT(szs.signID) " +
                    "FROM SchoolZoneSign szs " + 
                    "INNER JOIN Schools s ON szs.schoolName = s.schoolName " +
                    "INNER JOIN Street st ON szs.StreetID = st.StreetID " +
                    "group by s.schoolName, st.FullName;";

            resultSet = statement.executeQuery(selectSql);

             while (resultSet.next()) {
            
            System.out.println(resultSet.getString(1) + " " +  resultSet.getString(2) +  " " + resultSet.getString(3));
            
             }

             System.out.println();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


        public void highestNeighbourhoods(String connectionUrl){


        System.out.println(comboBox.getSelectedItem());
        ResultSet resultSet = null;
    
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {
    
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT top 5 electoralWard, COUNT(neighbourhoodName) AS NeighbourhoodCount FROM Neighbourhood GROUP BY electoralWard ORDER BY NeighbourhoodCount desc;";

            resultSet = statement.executeQuery(selectSql);

             while (resultSet.next()) {
            
            System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2));
            
             }

             System.out.println();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    

    public void getStreetsBySpeedLimit(String connectionUrl,String speed) {

        
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT FullName FROM Street WHERE StreetID IN (SELECT StreetID FROM SpeedLimit WHERE limit = ?)")) {
    
            // Create and execute a SELECT SQL statement.
            
            preparedStatement.setString(1, speed);

           ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Streets with a speed limit of " + speed + ":");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("FullName"));
            }
    

             System.out.println();
            }
    
         catch (SQLException e) {
            e.printStackTrace();
        }

    }


        public void getSchoolsByPostalCode(String connectionUrl,String code) {

        
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT s.schoolName, si.phoneNumber " +
                "FROM Schools s " +
                "JOIN SchoolInformation si ON s.phoneNumber = si.phoneNumber " +
                "WHERE lower(si.postalCode) like lower(?);")) {
    
            // Create and execute a SELECT SQL statement.
            
            preparedStatement.setString(1,"%" + code + "%");

           ResultSet resultSet = preparedStatement.executeQuery();

           System.out.println("Schools in the area with postal code " + code + ":");
           
           while (resultSet.next()) {
            System.out.println("School Name: " + resultSet.getString("schoolName") +
                    ", Phone Number: " + resultSet.getString("phoneNumber"));
            }
    

             System.out.println();
            }
    
         catch (SQLException e) {
            e.printStackTrace();
        }

    }
}


