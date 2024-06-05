/* This program parses all our tables that are stored in their respective CSV files, generates SQL server equivalent insert statements and stores it in the text file insertQueries.txt. */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class generateInsertFromCSV {
    public static void main(String[] args) {


        /* Files from part 1 of project */
        String[] fileNames = {
            "ElectionCandidates.csv",
            "ElectionResult.csv",
            "SchoolDivision.csv",
            "Neighbourhood.csv",
            "Schools.csv",
            "SchoolInformation.csv",
            "SchoolZoneSign.csv",
            "Street.csv",
            "SpeedLimit.csv",
            "Park.csv",
            "ParkAsset.csv",
            "Asset.csv"
        };
        String outputFile = "insertQueries.txt";


        /* Parsing all our files and generating insert statments into the outputFile */

        parseSchoolDivisionCSV(fileNames[2]);


        
        parseElectionCandidatesCSV(fileNames[0]);

        
        parseElectionResultCSV(fileNames[1]);
        
        
        
        
        parseNeighbourhoodCSV(fileNames[3]);

        parseSchoolInformationCSV(fileNames[5]);
        
        parseSchoolsCSV(fileNames[4]);
        
        parseStreetCSV(fileNames[7]);
        
        parseSchoolZoneSignCSV(fileNames[6]);
        
        
        
        parseSpeedLimitCSV(fileNames[8]);
        
        parseParkCSV(fileNames[9]);

        parseAssetCSV(fileNames[11]);

        parseParkAssetCSV(fileNames[10]);
        
       
        
        
    }


    /* parseElectionCandidatesCSV is a method that takes in the file name that it is going to parse and writes
     * into the output file the sql equivalent insert queries for all the records in that csv file
     */

    private static void parseElectionCandidatesCSV(String fileName) {
        String line;
        
        String csvSplitBy = ",";  //delimiter for CSV

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            BufferedWriter writer = new BufferedWriter(new FileWriter("insertQueries.txt", true));

            line = br.readLine();
            
            while ((line = br.readLine()) != null) {

                String[] data = line.split(csvSplitBy, -1); 
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].trim().isEmpty() ? "NULL" :   data[i];  //replacing empty data with NULL
                }

                if (data.length == 6) {
                    String candid = data[0];
                    String name = data[1];
                    String type = data[2] ;
                    String email = data[3];
                    String phone = data[4]; 
                    String divisionName = data[5];

                    //Fixing one of the errors in our input file for the divison name
                    if (divisionName.equals("St. James - Assiniboia School Division School Division School Division School Division School Division")) {
                        divisionName = "St. James - Assiniboia School Division";
                    }


                    //generating insert statements
                    
                    String sqlInsert = "INSERT INTO ElectionCandidates VALUES (" +
                    candid + ", " +
                    (name.equals("NULL") ? name : "'" + name + "'") + ", " +
                    (type.equals("NULL") ? type : "'" + type + "'") + ", " +
                    (email.equals("NULL") ? email : "'" + email + "'") + ", " +
                    (phone.equals("NULL") ? phone : "'" + phone + "'") + ", " +
                    (divisionName.equals("NULL") ? divisionName : "'" + divisionName + "'") +
                    " );";
                    
                    //writing onto the output file
                    writer.write(sqlInsert);
                    writer.newLine();

                    
                } else {
                    System.out.println("Invalid entry");
                }
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    // All of the following methods have the same implementation as the previous method the difference is just
    // between the different file inputs and their parsing according to number of attributes





    
        private static void parseElectionResultCSV(String fileName) {
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            BufferedWriter writer = new BufferedWriter(new FileWriter("insertQueries.txt",true));
            line = br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(csvSplitBy);
                
                if (data.length == 5) {
                    String eid = data[0].trim().replaceAll("'", "''"); 
                    String won = data[1].trim().replaceAll("'", "''");
                    String votes = data[2].trim().replaceAll("'", "''");
                    String cid = data[3].trim().replaceAll("'", "''");
                    String divname = data[4];

                    
                    String sqlInsert = "INSERT INTO ElectionResult VALUES (" + eid + ", '" + won + "', " + votes + ", " + cid + ", '" + divname + "');";


                    writer.write(sqlInsert);
                    writer.newLine();
                } else {
                    System.out.println("Invalid entry");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private static void parseSchoolDivisionCSV(String fileName) {
        String line;
        String csvSplitBy = ",";
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("insertQueries.txt", false));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);

                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].replaceAll("\"", ""); 
                }
    
                if (data.length == 2) {
                    String divname = data[0].trim().replaceAll("'", "''");; 
                    String divsize = data[1].trim().replaceAll("'", "''");;

                    // Generating SQL insert statement for each entry
                    String sqlInsert = "INSERT INTO SchoolDivision VALUES ('" + divname + "', '" + divsize + "');";
                    writer.write(sqlInsert);
                    writer.newLine();

                } else {
                    System.out.println("Invalid entry");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private static void parseNeighbourhoodCSV(String fileName) {
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("insertQueries.txt", true));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length == 3) {
                    String name = data[0].trim().replaceAll("'", "''"); 
                    String ward = data[1].trim().replaceAll("'", "''");
                    String divname = data[2].trim().replaceAll("'", "''");
                    // Generating SQL insert statement for each entry
                    String sqlInsert = "INSERT INTO Neighbourhood VALUES ('" + name + "', '" + ward + "', '" + divname + "');";
                    
                    writer.write(sqlInsert);
                    writer.newLine();
                } else {
                    System.out.println("Invalid entry");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private static void parseSchoolsCSV(String fileName) {

        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("insertQueries.txt", true));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length == 3) {
                    String name = data[0].replaceAll("'", ""); 
                    String pno = data[1];
                    String divname = data[2].replaceAll("'", "");
                    // Generating SQL insert statement for each entry
                    String sqlInsert = "INSERT INTO Schools VALUES ('" + name + "', '" + pno + "', '" + divname + "');";
                    
                    writer.write(sqlInsert);
                    writer.newLine();
                } else {
                    System.out.println("Invalid entry found");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private static void parseSchoolInformationCSV(String fileName) {
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("insertQueries.txt",true));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length == 2) {
                    String pno = data[0].trim().replaceAll("'", "''"); 
                    String pcode = data[1].trim().replaceAll("'", "''");

                    // Generating SQL insert statement for each entry
                    String sqlInsert = "INSERT INTO SchoolInformation VALUES ('" + pno + "', '" + pcode + "');";
                    writer.write(sqlInsert);
                    writer.newLine();
                } else {
                    System.out.println("Invalid entry");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private static void parseSchoolZoneSignCSV(String fileName) {
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("insertQueries.txt", true));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length == 3) {
                    String sid = data[0]; 
                    String sdiv = data[1].trim().replaceAll("'", "''");
                    String stid = data[2];

                    
                    String sqlInsert = "INSERT INTO SchoolZoneSign VALUES (" + sid + ", '" + sdiv + "', " + stid + ");";
                    
                    writer.write(sqlInsert);
                    writer.newLine();
                } else {
                    System.out.println("Invalid entry");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private static void parseStreetCSV(String fileName) {
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("insertQueries.txt", true));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length == 2) {
                    String sid = data[0]; 
                    String name = data[1].trim().replaceAll("'", "''");

                   
                    String sqlInsert = "INSERT INTO Street VALUES (" + sid + ", '" + name + "');";
                    writer.write(sqlInsert);
                    writer.newLine();
                } else {
                    System.out.println("Invalid entry");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private static void parseSpeedLimitCSV(String fileName) {
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("insertQueries.txt", true));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length == 4) {
                    String lid = data[0]; 
                    String l = data[1];
                    String sq = data[2].trim().replaceAll("'", "''"); 
                    String sid = data[3];

                   
                    String sqlInsert = "INSERT INTO SpeedLimit VALUES (" +
                    lid + ", " +
                    (l.equals("NULL") ? l : "'" + l + "'") + ", " +
                    (sq.equals("NULL") ? sq : "'" + sq + "'") + ", " +
                    (sid.equals("NULL") ? sid : "'" + sid + "'")  +
                     " );";
                    
                    writer.write(sqlInsert);
                    writer.newLine();
                } else {
                    System.out.println("Invalid entry" );
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private static void parseParkCSV(String fileName) {
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("insertQueries.txt", true));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length == 3) {
                    String pid = data[0];
                    String pname = data[1].trim().replaceAll("'", "''"); 
                    String nname = data[2].trim().replaceAll("'", "''");

                   
                    String sqlInsert = "INSERT INTO Park VALUES (" +
                    pid + ", " +
                    (pname.equals("NULL") ? pname : "'" + pname + "'") + ", " +
                    (nname.equals("NULL") ? nname : "'" + nname + "'")  +
                    
                     " );";
                    
                    writer.write(sqlInsert);
                    writer.newLine();
                } else {
                    System.out.println("Invalid entry");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private static void parseParkAssetCSV(String fileName) {
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("insertQueries.txt", true));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length == 3) {
                    String aid = data[0];
                    String assetType = data[1].trim().replaceAll("'", "''");
                    String pid = data[2];

                    
                    String sqlInsert = "INSERT INTO ParkAsset VALUES (" + aid + ", '" + assetType + "' , " + pid + ");";
                    
                    writer.write(sqlInsert);
                    writer.newLine();
                } else {
                    System.out.println("Invalid entry " );
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private static void parseAssetCSV(String fileName) {
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            BufferedWriter writer = new BufferedWriter(new FileWriter("insertQueries.txt", true));
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length == 2) {
                    String assetType = data[0].trim().replaceAll("'", "''"); 
                    String assetClass = data[1].trim().replaceAll("'", "''");

                    
                    String sqlInsert = "INSERT INTO asset (AssetType, AssetClass) VALUES ('" + assetType + "', '" + assetClass + "');";
                    
                    writer.write(sqlInsert);
                    writer.newLine();
                } else {
                    System.out.println("Invalid entry" );
                }
                
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
