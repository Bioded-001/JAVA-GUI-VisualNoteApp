/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java.gui.visualnoteapp;

/**
 *
 * @author User
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class VisualNoteApp {
    private JTextArea noteTextArea;
    private File currentFile;
    
    public VisualNoteApp() {
        JFrame frame = new JFrame("VisualNote");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        
        noteTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(noteTextArea);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        
        JToolBar toolBar = new JToolBar();
        JButton newButton = new JButton(new ImageIcon("new.png"));
        JButton openButton = new JButton(new ImageIcon("open.png"));
        JButton saveButton = new JButton(new ImageIcon("save.png"));
        
        toolBar.add(newButton);
        toolBar.add(openButton);
        toolBar.add(saveButton);
        
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        popupMenu.add(cutMenuItem);
        popupMenu.add(copyMenuItem);
        popupMenu.add(pasteMenuItem);
        
        noteTextArea.setComponentPopupMenu(popupMenu);
        
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
        
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(toolBar, BorderLayout.PAGE_START);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        frame.setVisible(true);
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
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                noteTextArea.setText(content.toString());
                currentFile = selectedFile;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error opening the file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void saveNote() {
        if (currentFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
                writer.write(noteTextArea.getText());
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
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                writer.write(noteTextArea.getText());
                currentFile = selectedFile;
                JOptionPane.showMessageDialog(null, "Note saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving the file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new VisualNoteApp();
            }
        });
    }
}
