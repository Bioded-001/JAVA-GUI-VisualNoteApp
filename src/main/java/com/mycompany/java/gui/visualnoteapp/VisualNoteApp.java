/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java.gui.visualnoteapp;

/**
 *
 * @author User
 */

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.FileOutputStream;
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
import javax.swing.SwingUtilities;

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
    private JPopupMenu popupMenu;
    private JMenuItem cutMenuItem;
    private JMenuItem copyMenuItem;
    private JMenuItem pasteMenuItem;
    private JPanel panel;
    private File currentFile;
    public VisualNote() {
        setTitle("VisualNote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        
        noteTextArea = new JTextArea();
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

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(newButton);
        panel.add(openButton);
        panel.add(saveButton);
        toolBar.add(panel);

        
        popupMenu = new JPopupMenu();
        cutMenuItem = new JMenuItem("Cut");
        copyMenuItem = new JMenuItem("Copy");
        pasteMenuItem = new JMenuItem("Paste");
        popupMenu.add(cutMenuItem);
        popupMenu.add(copyMenuItem);
        popupMenu.add(pasteMenuItem);
        
        noteTextArea.setComponentPopupMenu(popupMenu);        
        
        setJMenuBar(menuBar);
        getContentPane().add(toolBar, BorderLayout.PAGE_START);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
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
                System.exit(0);
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
        setVisible(true);
    }
    
    public ImageIcon resizeIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void createNewNote() {
        noteTextArea.setText("");
        currentFile = null;
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
                String content = new String(bytes);
                noteTextArea.setText(content);
                currentFile = selectedFile;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error opening the file.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveNote() {
        if (currentFile != null) {
            try (FileOutputStream fos = new FileOutputStream(currentFile)) {
                byte[] bytes = noteTextArea.getText().getBytes();
                fos.write(bytes);
                JOptionPane.showMessageDialog(null, "Note saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving the file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            saveNoteAs();
        }
    }

    private void saveNoteAs() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                if (selectedFile == null) {
                    throw new Exception("No file selected.");
                }
                try (FileOutputStream fos = new FileOutputStream(selectedFile)) {
                    byte[] bytes = noteTextArea.getText().getBytes();
                    fos.write(bytes);
                    currentFile = selectedFile;
                    JOptionPane.showMessageDialog(null, "Note saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error saving the file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}

public class VisualNoteApp extends VisualNote {
    public static void main(String[] args) {
        VisualNote NF = new VisualNote();
    }
}