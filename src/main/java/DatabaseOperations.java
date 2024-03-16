import java.sql.*;
import java.util.Scanner;
public class DatabaseOperations {
    Connection connection;

    public static void main(String[] args){
        DatabaseOperations operations = new DatabaseOperations();
        Scanner scanner = new Scanner(System.in);
        try{
            while (true){
                System.out.println("Press 0 to quit.\nPress 1 to get all students.\nPress 2 to add a student.\nPress 3 to update a student's email.\nPress 4 to delete a student");
                Integer input = Integer.parseInt(scanner.nextLine());
                if (input == 0) break;
                else if (input == 1) {
                    operations.getAllStudents();
                } else if (input == 2) {
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
                    System.out.print("Enter the student's ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter a new email for the student: ");
                    String email = scanner.nextLine();
                    operations.updateStudentEmail(id, email);
                } else if (input == 4) {
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
        String databaseName = "A3_Q1";

        //Variables used in connection
        String url = "jdbc:postgresql://localhost:" + portNumber + "/" + databaseName;
        String user = "postgres";
        String password = "postgres";

        try{
            //Connecting to database
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            if(connection != null){
                System.out.println("connected");
            } else {
                System.out.println("not connected");
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

        //Executing insertion statement
        statement.execute(String.format("INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES ('%s', '%s', '%s', '%s');", first_name, last_name, email, enrollment_date));

    }

    public void updateStudentEmail(String student_id, String new_email) throws SQLException {
        Statement statement = connection.createStatement();

        //Executing update statement
        statement.execute(String.format("UPDATE students SET email = '%s' WHERE student_id = '%s'", new_email, student_id));
    }

    public void deleteStudent(String student_id) throws SQLException {
            Statement statement = connection.createStatement();

            //Executing delete statement
            statement.execute(String.format("DELETE FROM students WHERE student_id = '%s'", student_id));
    }
}
