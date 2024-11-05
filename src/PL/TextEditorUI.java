package PL;

import javax.swing.*;
import BLL.TextEditorBO;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.sql.SQLException;

public class TextEditorUI {
    private static TextEditorBO fileService;
    private static JTabbedPane tabbedPane;

    public static void main(String[] args) {
        try {
            fileService = new TextEditorBO();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed.");
            return;
        }

        JFrame frame = new JFrame("Text Editor");
        JButton viewButton = new JButton("View Files");
        JButton createButton = new JButton("Create New File");
        JButton saveButton = new JButton("Save File");
        JButton updateButton = new JButton("Update File");
        JButton deleteButton = new JButton("Delete File");
        JButton hashButton = new JButton("Calculate Hash");

        // Layout for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(viewButton);
        buttonPanel.add(createButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(hashButton);

        tabbedPane = new JTabbedPane();

        createButton.addActionListener(e -> createNewFile());
        saveButton.addActionListener(e -> {
			try {
				saveFile();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        updateButton.addActionListener(e -> {
			try {
				updateFile();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        deleteButton.addActionListener(e -> deleteFile());
        hashButton.addActionListener(e -> calculateHash());

        viewButton.addActionListener(e -> {
            ArrayList<String> fileList = fileService.getFileNames();
            if (fileList != null && !fileList.isEmpty()) {
                showFileList(fileList);
            } else {
                JOptionPane.showMessageDialog(null, "No files found in the database.");
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void createNewFile() {
        JTextArea textArea = new JTextArea();
        JPanel tabComponent = new JPanel(new BorderLayout());
        tabComponent.add(new JScrollPane(textArea), BorderLayout.CENTER);
        tabbedPane.addTab("Untitled", tabComponent);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
    }

    private static void saveFile() throws SQLException {
        if (tabbedPane.getTabCount() > 0) {
            String fileName = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
            fileService.saveToDatabase(fileName, getCurrentTextAreaContent());
            JOptionPane.showMessageDialog(null, "File saved successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "No file to save.");
        }
    }

    private static void updateFile() throws SQLException {
        if (tabbedPane.getTabCount() > 0) {
            String fileName = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
            fileService.saveToDatabase(fileName, getCurrentTextAreaContent());
            JOptionPane.showMessageDialog(null, "File updated successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "No file selected to update.");
        }
    }

    private static void deleteFile() {
        if (tabbedPane.getTabCount() > 0) {
            String fileName = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this file: " + fileName + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                try {
                    fileService.deleteFileFromDatabase(fileName);
                    tabbedPane.remove(tabbedPane.getSelectedIndex());
                    JOptionPane.showMessageDialog(null, "File deleted successfully.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error deleting file: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No file selected to delete.");
        }
    }

    private static void calculateHash() {
        if (tabbedPane.getTabCount() > 0) {
            String content = getCurrentTextAreaContent();
            String hash = fileService.calculateHash(content);
            JOptionPane.showMessageDialog(null, "Hash: " + hash);
        } else {
            JOptionPane.showMessageDialog(null, "No file selected to calculate hash.");
        }
    }

    private static String getCurrentTextAreaContent() {
        Component selectedComponent = tabbedPane.getSelectedComponent();
        JTextArea textArea = (JTextArea) ((JScrollPane) ((JPanel) selectedComponent).getComponent(0)).getViewport().getView();
        return textArea.getText();
    }

    private static void showFileList(ArrayList<String> fileList) {
        JFrame listFrame = new JFrame("File List");
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (String file : fileList) {
            listModel.addElement(file);
        }

        JList<String> fileJList = new JList<>(listModel);
        fileJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        fileJList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String selectedFileName = fileJList.getSelectedValue();
                    openFile(selectedFileName);
                    listFrame.dispose();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(fileJList);
        listFrame.add(scrollPane);
        listFrame.setSize(400, 300);
        listFrame.setVisible(true);
    }

    private static void openFile(String fileName) {
        String fileText = fileService.getFileContent(fileName);

        if (fileText != null) {
            JTextArea textArea = new JTextArea(fileText);
            textArea.setEditable(true);
            JPanel tabComponent = new JPanel(new BorderLayout());
            tabComponent.add(new JScrollPane(textArea), BorderLayout.CENTER);

            tabbedPane.addTab(fileName, tabComponent);
            tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        } else {
            JOptionPane.showMessageDialog(null, "File content not found.");
        }
    }
}
