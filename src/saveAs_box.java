import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class saveAs_box extends JDialog {

    JTextField field;
    JButton done;
    JLabel errorLabel;

    // Constructor
    saveAs_box(edit edit_ref) {
        // Set up the dialog properties
        setSize(300, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true); 
        setLayout(new FlowLayout());

        // Create and add the text field
        field = new JTextField(15);
        add(field);

        // Create and add the done button
        done = new JButton("Done");
        add(done);

        // Create and add the hidden error label
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false); // Initially hidden
        add(errorLabel);
        
        // Set up the button event
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = field.getText(); // Get input from the text field
                ResultSet rs = null;
                int count;

                if (!name.isEmpty()) {
                    try {
                        // Establish the connection
                        Connection conn = DriverManager.getConnection(creden.url, creden.user, creden.password);

                        PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(1) FROM song_strings WHERE name = ?");
                        pstmt.setString(1, name); // Set the 'name' parameter
                        rs = pstmt.executeQuery();

                        if (rs.next()) {
                            count = rs.getInt(1); // Get the count

                            if (count == 1) {
                                errorLabel.setText("Name already present");
                                errorLabel.setVisible(true); // Show the error message
                            } else {
                                // Prepare the INSERT query to add the song
                                pstmt = conn.prepareStatement("INSERT INTO song_strings (name, content) VALUES (?, ?)");
                                pstmt.setString(1, name); // Set the 'name' column value
                                pstmt.setString(2, edit_ref.editArea.getText()); // Set the 'song_text' column value
                                pstmt.executeUpdate(); // Execute the insert query
                                edit_ref.setTitle(name);
                                dispose(); // Close the dialog
                            }
                        }

           
                    } catch (SQLException ex) {
                        System.out.println("Database error: " + ex.getMessage());
                    } finally {
                        // Close the ResultSet if it was opened
                        if (rs != null) {
                            try {
                                rs.close();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                } else {
                    errorLabel.setText("Please enter a name.");
                    errorLabel.setVisible(true); // Show the error message if the name is empty
                }
            }
        });

        // Center the dialog on the screen and make it visible
        setLocationRelativeTo(null);
        setTitle("Save as box");
        setVisible(true); // Show the dialog after everything is set up
    }
}
