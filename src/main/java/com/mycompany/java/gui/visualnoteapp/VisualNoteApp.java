package com.mycompany.java.gui.visualnoteapp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/*
 * Group No: 17
 * Title: MyNotes
 * Group Members:
 * 1. Ooi Jing Hao, ID: 1211202433
 * 2. Lee Way Sean, ID: 1211203818
 * 3. Zachary Tristan Gary, ID: 1211203508
 */
// import necessary packages and classes
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

// the VisualNote class extends JFrame, indicating it's a GUI window
class VisualNote extends JFrame {
    // declare all necessary components
    private JTextArea noteTextArea;    
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem newMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveAsMenuItem;
    private JMenuItem exitMenuItem;
    private JScrollPane scrollPane;
    private JToolBar toolBar;    
    private JButton newButton;
    private JButton openButton;
    private JButton saveButton;
    private JButton addButton;
    private JButton subButton;
    private JPopupMenu popupMenu;
    private JMenuItem cutMenuItem;
    private JMenuItem copyMenuItem;
    private JMenuItem pasteMenuItem;
    private JPanel panel;
    private File currentFile;
    // constructor for VisualNote
    public VisualNote() {
    // set window properties
    // set up components, layout, and event handlers
        setTitle("VisualNote");  // Sets the title of the JFrame to "VisualNote"
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Sets the default close operation for the JFrame (the application will exit when the JFrame is closed)
        setSize(1200, 800);  // Sets the size of the JFrame to 1200 pixels wide and 800 pixels tall

        // JTextArea for writing and editing notes
        noteTextArea = new JTextArea();  // Initializes a new JTextArea for writing and editing notes
        noteTextArea.setText(new String("".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));  // Sets the initial text in the JTextArea to an empty string
        scrollPane = new JScrollPane(noteTextArea);  // Adds the JTextArea to a JScrollPane to allow for scrolling when the text exceeds the size of the JTextArea

        // Menu bar and file menu initialization
        menuBar = new JMenuBar();  // Initializes a new JMenuBar
        fileMenu = new JMenu("File");  // Initializes a new JMenu with the label "File"
        newMenuItem = new JMenuItem("New");  // Initializes a new JMenuItem with the label "New"
        openMenuItem = new JMenuItem("Open");  // Initializes a new JMenuItem with the label "Open"
        saveMenuItem = new JMenuItem("Save");  // Initializes a new JMenuItem with the label "Save"
        saveAsMenuItem = new JMenuItem("Save As");  // Initializes a new JMenuItem with the label "Save As"
        exitMenuItem = new JMenuItem("Exit");  // Initializes a new JMenuItem with the label "Exit"

        // Adding menu items to the file menu and the file menu to the menu bar
        fileMenu.add(newMenuItem);  // Adds the "New" JMenuItem to the "File" JMenu
        fileMenu.add(openMenuItem);  // Adds the "Open" JMenuItem to the "File" JMenu
        fileMenu.add(saveMenuItem);  // Adds the "Save" JMenuItem to the "File" JMenu
        fileMenu.add(saveAsMenuItem);  // Adds the "Save As" JMenuItem to the "File" JMenu
        fileMenu.addSeparator();  // Adds a separator line to the "File" JMenu
        fileMenu.add(exitMenuItem);  // Adds the "Exit" JMenuItem to the "File" JMenu
        menuBar.add(fileMenu);  // Adds the "File" JMenu to the JMenuBar

        // Tool bar and button initialization
        toolBar = new JToolBar();  // Initializes a new JToolBar
        newButton = new JButton(resizeIcon("image/newfileicon.png", 20, 20));  // Initializes a new JButton with an icon from the specified path
        openButton = new JButton(resizeIcon("image/openfileicon.png",20, 20));  // Initializes a new JButton with an icon from the specified path
        saveButton = new JButton(resizeIcon("image/savefileicon.png",20,20));  // Initializes a new JButton with an icon from the specified path
        subButton = new JButton(resizeIcon("image/icons8-subtract-button-50.png",20,20));  // Initializes a new JButton with an icon from the specified path
        addButton = new JButton(resizeIcon("image/icons8-add-button-50.png",20,20));  // Initializes a new JButton with an icon from the specified path

        // Adding buttons to the tool bar
        panel = new JPanel();  // Initializes a new JPanel
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));  // Sets the layout manager for the JPanel to BoxLayout, which arranges the components along the x-axis
        panel.add(newButton);  // Adds the "New" JButton to the JPanel
        panel.add(openButton);  // Adds the "Open" JButton to the JPanel
        panel.add(saveButton);  // Adds the "Save" JButton to the JPanel
        panel.add(subButton);  // Adds the "Subtract" JButton to the JPanel
        toolBar.add(panel);  // Adds the JPanel to the JToolBar

        // Popup menu for text editing
        popupMenu = new JPopupMenu();  // Initializes a new JPopupMenu
        cutMenuItem = new JMenuItem("Cut");  // Initializes a new JMenuItem with the label "Cut"
        copyMenuItem = new JMenuItem("Copy");  // Initializes a new JMenuItem with the label "Copy"
        pasteMenuItem = new JMenuItem("Paste");  // Initializes a new JMenuItem with the label "Paste"
        popupMenu.add(cutMenuItem);  // Adds the "Cut" JMenuItem to the JPopupMenu
        popupMenu.add(copyMenuItem);  // Adds the "Copy" JMenuItem to the JPopupMenu
        popupMenu.add(pasteMenuItem);  // Adds the "Paste" JMenuItem to the JPopupMenu

        // Adds the addButton to the southPanel and sets the popupMenu as the context menu for the noteTextArea
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));  // Initializes a new JPanel with a FlowLayout that aligns its components to the right
        southPanel.add(addButton);  // Adds the "Add" JButton to the southPanel
        noteTextArea.setComponentPopupMenu(popupMenu);  // Sets the JPopupMenu as the context menu for the JTextArea

        // Adding the menu bar, tool bar, JTextArea in JScrollPane, and southPanel to the JFrame
        setJMenuBar(menuBar);  // Adds the JMenuBar to the JFrame
        getContentPane().add(toolBar, BorderLayout.PAGE_START);  // Adds the JToolBar to the top (PAGE_START) of the JFrame
        getContentPane().add(scrollPane, BorderLayout.CENTER);  // Adds the JScrollPane (which contains the JTextArea) to the center of the JFrame
        getContentPane().add(southPanel, BorderLayout.SOUTH);  // Adds the southPanel (which contains the "Add" JButton) to the bottom (SOUTH) of the JFrame

        // Action listeners for the buttons and menu items
        subButton.addActionListener(new ActionListener() {  // Adds an action listener to the "Subtract" JButton
            public void actionPerformed(ActionEvent e) {  // The method to be called when the "Subtract" JButton is clicked
                boolean check = promptSaveBeforeExit();  // Calls the method promptSaveBeforeExit() and stores its return value in the variable check
                if(check){  // If check is true...
                    dispose();  // ...dispose of the JFrame
                }
            }
        });
        
        addButton.addActionListener(new ActionListener() {  // Adds an action listener to the "Add" JButton
            public void actionPerformed(ActionEvent e) {  // The method to be called when the "Add" JButton is clicked
                new VisualNote();  // Creates a new instance of the VisualNote class
            }
        });

        newMenuItem.addActionListener(new ActionListener() {  // Adds an action listener to the "New" JMenuItem
            public void actionPerformed(ActionEvent e) {  // The method to be called when the "New" JMenuItem is selected
                createNewNote();  // Calls the method createNewNote()
            }
        });

        openMenuItem.addActionListener(new ActionListener() {  // Adds an action listener to the "Open" JMenuItem
            public void actionPerformed(ActionEvent e) {  // The method to be called when the "Open" JMenuItem is selected
                openNote();  // Calls the method openNote()
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {  // Adds an action listener to the "Save" JMenuItem
                public void actionPerformed(ActionEvent e) {  // The method to be called when the "Save" JMenuItem is selected
                saveNote();  // Calls the method saveNote()
            }
        });

        saveAsMenuItem.addActionListener(new ActionListener() {  // Adds an action listener to the "Save As" JMenuItem
            public void actionPerformed(ActionEvent e) {  // The method to be called when the "Save As" JMenuItem is selected
                saveNoteAs();  // Calls the method saveNoteAs()
            }
        });

        exitMenuItem.addActionListener(new ActionListener() {  // Adds an action listener to the "Exit" JMenuItem
            public void actionPerformed(ActionEvent e) {  // The method to be called when the "Exit" JMenuItem is selected
                boolean check = promptSaveBeforeExit();  // Calls the method promptSaveBeforeExit() and stores its return value in the variable check
                if(check){  // If check is true...
                    dispose();  // ...dispose of the JFrame
                }
            }
        });

        newButton.addActionListener(new ActionListener() {  // Adds an action listener to the "New" JButton
            public void actionPerformed(ActionEvent e) {  // The method to be called when the "New" JButton is clicked
                createNewNote();  // Calls the method createNewNote()
            }
        });

        openButton.addActionListener(new ActionListener() {  // Adds an action listener to the "Open" JButton
            public void actionPerformed(ActionEvent e) {  // The method to be called when the "Open" JButton is clicked
                openNote();  // Calls the method openNote()
            }
        });

        saveButton.addActionListener(new ActionListener() {  // Adds an action listener to the "Save" JButton
            public void actionPerformed(ActionEvent e) {  // The method to be called when the "Save" JButton is clicked
                saveNote();  // Calls the method saveNote()
            }
        });

        // the window listener handles window close event
        this.addWindowListener(new WindowAdapter() {
            // this method is called when the window is about to close
            @Override
            public void windowClosing(WindowEvent e) {
                // prompt the user to save before exiting
                boolean check = promptSaveBeforeExit();
                // if the user wants to save changes or doesn't care about unsaved changes, close the window
                if(check){
                    dispose();
                }
            }
        });
        // make the window visible
        setVisible(true);
    }
    
     // this method resizes the icon to specified dimensions
    public ImageIcon resizeIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
    
    // this method creates a new note after checking if the current note needs saving
    private void createNewNote() {
        String text = noteTextArea.getText();
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        String content = new String(bytes, StandardCharsets.UTF_8);
        if (content.isEmpty() && currentFile == null) {
            noteTextArea.setText(new String("".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        } else {
            int option = JOptionPane.showConfirmDialog(this,
                    "Do you want to save the current note before creating a new one?",
                    "Save current note?",
                    JOptionPane.YES_NO_OPTION);
            switch (option) {
                case JOptionPane.YES_OPTION:
                    boolean save = saveNote();
                    if (save) {
                        noteTextArea.setText(new String("".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
                        currentFile = null;
                    }
                    break;
                case JOptionPane.NO_OPTION:
                    break;
                case JOptionPane.CLOSED_OPTION:
                    break;
            }
        }
    }

    // this method prompts the user to save changes before exiting
    private boolean promptSaveBeforeExit() {
        String text = noteTextArea.getText();
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        String content = new String(bytes, StandardCharsets.UTF_8);
        if (content.isEmpty() && currentFile == null) {
            dispose();
        } else {
            int option = JOptionPane.showConfirmDialog(this,
                    "Do you want to save your changes before exiting?",
                    "Save changes?",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            switch (option) {
                case JOptionPane.YES_OPTION:
                    boolean save = saveNote();
                    if (save) {
                        return true;
                    }
                    break;
                case JOptionPane.NO_OPTION:
                    return true;
                case JOptionPane.CANCEL_OPTION:
                    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    return false;
                case JOptionPane.CLOSED_OPTION:
                    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    return false;
            }
        }
        return false;
    }

    // this method opens a note from a file
    private void openNote() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                if (selectedFile == null) {
                    throw new Exception("No file selected.");
                }
                byte[] bytes = Files.readAllBytes(selectedFile.toPath());
                String content = new String(bytes, StandardCharsets.UTF_8);
                noteTextArea.setText(content);
                currentFile = selectedFile;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error opening the file.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // this method saves the current note to its current file
    private boolean saveNote() {
        if (currentFile != null) {
            try (FileOutputStream fos = new FileOutputStream(currentFile)) {
                byte[] bytes = noteTextArea.getText().getBytes(StandardCharsets.UTF_8);
                fos.write(bytes);
                JOptionPane.showMessageDialog(null, "Note saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving the file.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            return saveNoteAs();
        }
    }
    
    // this method saves the current note to a new file
    private boolean saveNoteAs() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                if (selectedFile == null) {
                    throw new Exception("No file selected.");
                }
                try (FileOutputStream fos = new FileOutputStream(selectedFile)) {
                    byte[] bytes = noteTextArea.getText().getBytes(StandardCharsets.UTF_8);
                    fos.write(bytes);
                    currentFile = selectedFile;
                    JOptionPane.showMessageDialog(null, "Note saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error saving the file.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            return false;
        }
    }

}

// VisualNoteApp class, which is a subclass of VisualNote
public class VisualNoteApp extends VisualNote {
    // main method, where the program starts
    public static void main(String[] args) {
        // create a new VisualNote object, which will display the window
        VisualNote NF = new VisualNote();
    }
}