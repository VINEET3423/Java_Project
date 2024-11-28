import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class LibraryManagementSystem extends JFrame {
    private Library library;
    private JTextArea displayArea;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField isbnField;
    private JTextField searchField;

    public LibraryManagementSystem() {
        library = new Library();
        
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create GUI components
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("ISBN:"));
        isbnField = new JTextField();
        inputPanel.add(isbnField);
        
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(new AddButtonListener());
        inputPanel.add(addButton);
        
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchButtonListener());
        searchPanel.add(searchButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        
        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            if (!title.isEmpty() && !author.isEmpty() && !isbn.isEmpty()) {
                Book book = new Book(title, author, isbn);
                library.addBook(book);
                titleField.setText("");
                authorField.setText("");
                isbnField.setText("");
                displayBooks();
            } else {
                JOptionPane.showMessageDialog(null, "All fields must be filled out.");
            }
        }
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String query = searchField.getText();
            if (!query.isEmpty()) {
                List<Book> results = library.searchBooks(query);
                displayBooks(results);
            } else {
                displayBooks();
            }
        }
    }

    private void displayBooks() {
        displayBooks(library.getBooks());
    }

    private void displayBooks(List<Book> books) {
        displayArea.setText("");
        for (Book book : books) {
            displayArea.append(book.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryManagementSystem frame = new LibraryManagementSystem();
            frame.setVisible(true);
        });
    }
}