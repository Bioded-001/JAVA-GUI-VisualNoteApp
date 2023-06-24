package com.mycompany.java.gui.visualnoteapp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


//
//
//
//
//
//

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

class VisualNote extends JFrame{
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
    public VisualNote() {
        setTitle("VisualNote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        
        noteTextArea = new JTextArea();
        noteTextArea.setText(new String("".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        scrollPane = new JScrollPane(noteTextArea);
        
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        newMenuItem = new JMenuItem("New");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        saveAsMenuItem = new JMenuItem("Save As");
        exitMenuItem = new JMenuItem("Exit");
        
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        
        toolBar = new JToolBar();
        
        newButton = new JButton(resizeIcon("image/newfileicon.png", 20, 20));
        openButton = new JButton(resizeIcon("image/openfileicon.png",20, 20));
        saveButton = new JButton(resizeIcon("image/savefileicon.png",20,20));
        subButton = new JButton(resizeIcon("image/icons8-subtract-button-50.png",20,20));
        addButton = new JButton(resizeIcon("image/icons8-add-button-50.png",20,20));

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(newButton);
        panel.add(openButton);
        panel.add(saveButton);
        panel.add(subButton);
        toolBar.add(panel);
 
        popupMenu = new JPopupMenu();
        cutMenuItem = new JMenuItem("Cut");
        copyMenuItem = new JMenuItem("Copy");
        pasteMenuItem = new JMenuItem("Paste");
        popupMenu.add(cutMenuItem);
        popupMenu.add(copyMenuItem);
        popupMenu.add(pasteMenuItem);
        
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(addButton);
        
        noteTextArea.setComponentPopupMenu(popupMenu);        
        
        setJMenuBar(menuBar);
        getContentPane().add(toolBar, BorderLayout.PAGE_START);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
        
        subButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean check = promptSaveBeforeExit();
                if(check){
                    dispose();
                }
            }
        });
        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VisualNote();
            }
        });

        newMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createNewNote();
            }
        });
        
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openNote();
            }
        });
        
        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveNote();
            }
        });
        
        saveAsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveNoteAs();
            }
        });
        
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean check = promptSaveBeforeExit();
                if(check){
                    dispose();
                }
            }
        });
        
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createNewNote();
            }
        });
        
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openNote();
            }
        });
        
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveNote();
            }
        });
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean check = promptSaveBeforeExit();
                if(check){
                    dispose();
                }
            }
        });
        setVisible(true);
    }
    
    
    public ImageIcon resizeIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

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

public class VisualNoteApp extends VisualNote {
    public static void main(String[] args) {
        VisualNote NF = new VisualNote();
    }
}