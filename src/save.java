import java.sql.*;

public class save {

    public static void saveFile(edit edit_ref) {
        String name = edit_ref.getTitle();
        if (name == null) {
            System.out.println("Error: The name cannot be null.");
            return; // Exit the method if name is null
        }
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Establish the database connection (adjust credentials as per your setup)
            conn = DriverManager.getConnection(creden.url, creden.user, creden.password);
            
            // Update the content for the specified name
            pstmt = conn.prepareStatement("UPDATE song_strings SET content = ? WHERE name = ?");
            pstmt.setString(1, edit_ref.editArea.getText()); // Set content to update
            pstmt.setString(2, name);     // Set name to find the record
            
            pstmt.executeUpdate(); // Execute the update


        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL errors
        } 
}


}
