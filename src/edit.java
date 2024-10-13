import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class edit extends JFrame {
    public JTextArea editArea;
    private JScrollPane scrollPane ;
    public edit() {
        
        setTitle("");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Text Area setup
        editArea = new JTextArea();
        editArea.setBackground(Color.BLACK);
        editArea.setForeground(Color.LIGHT_GRAY);
        editArea.setCaretColor(Color.LIGHT_GRAY);
        
        // ScrollPane to handle large text content
        scrollPane = new JScrollPane(editArea);
        add(scrollPane, BorderLayout.CENTER);
       
        
        editArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {
                    new find_box(edit.this); 

                } else if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_S) {
                    new saveAs_box(edit.this); 
                    
                }
                else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S){
                        save.saveFile(edit.this);
                }


            }
        });
        
        
        editArea.requestFocusInWindow();

    }


}




