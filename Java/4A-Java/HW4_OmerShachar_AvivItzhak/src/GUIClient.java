import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class GUIClient extends JFrame {
    
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9999;
    private static final String DELIMITER = "|";
    private static final String DISCONNECT_MESSAGE = "DISCONNECT";

    private JTextField businessNameField;
    private JTextField businessNumberField;
    private JComboBox<String> itemComboBox;
    private JSpinner quantitySpinner;
    private JButton sendButton;
    private JButton disconnectButton;
    private JTextArea statusArea;
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connected = false;

    public GUIClient() {
        initializeGUI();
    }

    // Helper methods
    
    private void initializeGUI() {
        setTitle("Order Management Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setSize(500, 400);

        // Input Panel
        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        // Status Area
        statusArea = new JTextArea(5, 30);
        statusArea.setEditable(false);
        statusArea.setWrapStyleWord(true);
        statusArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(statusArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Status"));
        add(scrollPane, BorderLayout.NORTH);

        // Initially hide disconnect button
        disconnectButton.setVisible(false);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Business Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Business Name:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        businessNameField = new JTextField(20);
        panel.add(businessNameField, gbc);

        // Business Number
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("Business Number (5 digits):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        businessNumberField = new JTextField(20);
        panel.add(businessNumberField, gbc);

        // Item Selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("Item:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        String[] items = {"Sunglasses (1)", "Belt (2)", "Scarf (3)"};
        itemComboBox = new JComboBox<>(items);
        panel.add(itemComboBox, gbc);

        // Quantity
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        quantitySpinner = new JSpinner(spinnerModel);
        panel.add(quantitySpinner, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());
        panel.add(sendButton);

        disconnectButton = new JButton("Disconnect");
        disconnectButton.addActionListener(new DisconnectButtonListener());
        panel.add(disconnectButton);

        return panel;
    }

    private boolean connectToServer() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            connected = true;
            appendStatus("Connected to server at " + SERVER_HOST + ":" + SERVER_PORT);
            return true;
        } catch (IOException e) {
            appendStatus("Error connecting to server: " + e.getMessage());
            return false;
        }
    }

    private void appendStatus(String message) {
        SwingUtilities.invokeLater(() -> {
            statusArea.append(message + "\n");
            statusArea.setCaretPosition(statusArea.getDocument().getLength());
        });
    }

    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Connect if not already connected
            if (!connected) {
                if (!connectToServer()) {
                    return;
                }
            }

            // Validate inputs
            String businessName = businessNameField.getText().trim();
            String businessNumber = businessNumberField.getText().trim();
            int itemType = itemComboBox.getSelectedIndex() + 1; // 1, 2, or 3
            int quantity = (Integer) quantitySpinner.getValue();

            if (businessName.isEmpty() || businessNumber.isEmpty()) {
                appendStatus("Error: Please fill in all fields");
                return;
            }

            // Validate business number format
            if (!isFiveDigits(businessNumber)) {
                appendStatus("Error: Business number must be exactly 5 digits");
                return;
            }

            // Send order in background thread
            new Thread(() -> sendOrder(businessName, businessNumber, itemType, quantity)).start();
        }
    }

    private void sendOrder(String businessName, String businessNumber, int itemType, int quantity) {
        try {
            // Format message according to protocol
            String message = businessName + DELIMITER + businessNumber + DELIMITER + 
                           itemType + DELIMITER + quantity;
            
            appendStatus("Sending order: " + businessName + ", " + businessNumber + 
                        ", Item " + itemType + ", Quantity " + quantity);
            
            out.println(message);

            // Read response
            String response = in.readLine();
            if (response != null) {
                handleResponse(response);
            } else {
                appendStatus("Error: No response from server");
            }
        } catch (IOException e) {
            appendStatus("Error sending order: " + e.getMessage());
            connected = false;
        }
    }

    private void handleResponse(String response) {
        SwingUtilities.invokeLater(() -> {
            String[] parts = response.split("\\" + DELIMITER, 2);
            String code = parts[0];
            String message = parts.length > 1 ? parts[1] : "";

            switch (code) {
                case "100":
                    appendStatus("Success: " + message);
                    // Show disconnect button after success
                    disconnectButton.setVisible(true);
                    break;
                case "200":
                    appendStatus("Error 200 - Missing Data: " + message);
                    break;
                case "201":
                    appendStatus("Error 201 - Name/Number Mismatch: " + message);
                    break;
                case "202":
                    appendStatus("Error 202 - Invalid Format: " + message);
                    break;
                default:
                    appendStatus("Unknown response: " + response);
            }
        });
    }

    private class DisconnectButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            disconnect();
        }
    }

    private void disconnect() {
        if (connected && socket != null && !socket.isClosed()) {
            try {
                // Send disconnect message
                if (out != null) {
                    out.println(DISCONNECT_MESSAGE);
                }
                
                // Close resources
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
                
                connected = false;
                appendStatus("Disconnected from server");
                disconnectButton.setVisible(false);
            } catch (IOException e) {
                appendStatus("Error during disconnect: " + e.getMessage());
            }
        }
        
        // Close window
        dispose();
    }

    public boolean isFiveDigits(String s) {
        return (s.length() == 5) && (isFiveDigits(s, 0));
    }

    public boolean isFiveDigits(String s, int i) {
        if (i == 5) {return true;}
        else return (Character.isDigit(s.charAt(i))) && isFiveDigits(s, i+1);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIClient client = new GUIClient();
            client.setVisible(true);
        });
    }
}
