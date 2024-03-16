import java.sql.*;
import java.util.Scanner;
public class DatabaseOperations {
    Connection connection;

    public static void main(String[] args){
        //Creates object to connect to database
        DatabaseOperations operations = new DatabaseOperations();

        //Creates scanner for user input
        Scanner scanner = new Scanner(System.in);
        try{
            //Gets user input to decide what operation to do
            while (true){
                System.out.println("Press 0 to quit.\nPress 1 to get all students.\nPress 2 to add a student.\nPress 3 to update a student's email.\nPress 4 to delete a student");
                Integer input = Integer.parseInt(scanner.nextLine());
                if (input == 0) break;
                else if (input == 1) {
                    operations.getAllStudents();
                } else if (input == 2) {
                    //Gets user input to complete addStudent function
                    System.out.print("Enter a first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter a last name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Enter an email name: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter an enrollment date name in the form YYYY-MM-DD: ");
                    String date = scanner.nextLine();
                    operations.addStudent(firstName, lastName, email, date);
                } else if (input == 3) {
                    //Gets user input to complete updateStudentEmail function
                    System.out.print("Enter the student's ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter a new email for the student: ");
                    String email = scanner.nextLine();
                    operations.updateStudentEmail(id, email);
                } else if (input == 4) {
                    //Gets user input to complete deleteStudent function
                    System.out.print("Enter the student's ID: ");
                    String id = scanner.nextLine();
                    operations.deleteStudent(id);
                }
            }
        }
        catch(Exception e) {e.printStackTrace();}
    }

    public DatabaseOperations(){
        //on creation, connect to the database
        connectToDatabase();
    }

    private void connectToDatabase(){
        //update portNumber and databaseName to test
        String portNumber = "5432";
        String databaseName = "A3_Q1_video";

        //Variables used in connection
        String url = "jdbc:postgresql://localhost:" + portNumber + "/" + databaseName;
        String user = "postgres";
        String password = "postgres";

        try{
            //Load driver
            Class.forName("org.postgresql.Driver");
            //Connect to database
            connection = DriverManager.getConnection(url, user, password);
            if(connection != null){
                System.out.println("connected to database");
            } else {
                System.out.println("not connected to database");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getAllStudents() throws SQLException {
        Statement statement = connection.createStatement();

        //Executing selection query
        statement.executeQuery("SELECT * FROM students");
        ResultSet resultSet = statement.getResultSet();

        //Looping through all results and printing them
        while(resultSet.next()){
            System.out.print(resultSet.getString("student_id") + " \t");
            System.out.print(resultSet.getString("first_name") + " \t");
            System.out.print(resultSet.getString("last_name") + " \t");
            System.out.print(resultSet.getString("email") + " \t");
            System.out.println(resultSet.getString("enrollment_date") + " \t");
        }
        System.out.println();
    }

    public void addStudent(String first_name, String last_name, String email, String enrollment_date) throws SQLException{
        Statement statement = connection.createStatement();

        //Executing insertion statement using arguments given
        statement.execute(String.format("INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES ('%s', '%s', '%s', '%s');", first_name, last_name, email, enrollment_date));

    }

    public void updateStudentEmail(String student_id, String new_email) throws SQLException {
        Statement statement = connection.createStatement();

        //Executing update statement using the given arguments
        statement.execute(String.format("UPDATE students SET email = '%s' WHERE student_id = '%s'", new_email, student_id));
    }

    public void deleteStudent(String student_id) throws SQLException {
            Statement statement = connection.createStatement();

            //Executing delete statement using the given argument
            statement.execute(String.format("DELETE FROM students WHERE student_id = '%s'", student_id));
    }
}
