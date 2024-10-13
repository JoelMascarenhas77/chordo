import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class find_box extends JDialog {

    JComboBox<String> comboBox;
    JButton done;
    JLabel errorLabel;
    Connection conn;

    public find_box(edit edit_ref) {

        setTitle("Find box");
        setSize(300, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setLayout(new FlowLayout());

        comboBox = new JComboBox<>();
        comboBox.setEditable(true);
        add(comboBox);

        done = new JButton("Done");
        add(done);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        add(errorLabel);

        setLocationRelativeTo(null);

        // Get the text field from the comboBox's editor
        JTextField textField = (JTextField) comboBox.getEditor().getEditorComponent();
        // Add a KeyListener to detect key released events
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String input = textField.getText(); 
                ResultSet rs = null;

                try {
                    Connection conn = DriverManager.getConnection(creden.url, creden.user, creden.password);
                    PreparedStatement stmt = conn.prepareStatement("SELECT name FROM song_strings WHERE lower(name) LIKE ?");
                    stmt.setString(1, "%" + input.toLowerCase() + "%");
                    rs = stmt.executeQuery();
                    
                    comboBox.removeAllItems();
                     
                    while (rs.next()) {
                        comboBox.addItem(rs.getString("name"));
                    }
                    textField.setText(input);

                    rs.close();
                    stmt.close();

                } catch (SQLException ex) {
 
                }
            }
        });

        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connection conn = DriverManager.getConnection(creden.url, creden.user, creden.password);
                PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(1) FROM song_strings WHERE name = ?");
                

                edit_ref.editArea.setText("");
                edit_ref.setTitle("");
                int count;
                if (rs.next()) {
                    count = rs.getInt(1);

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
        });

        setVisible(true);
    }
}
