import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

 class Databaseconectivity {
    private Connection connection;
    String url = "jdbc:mysql://localhost:3306/student";
    String user = "root";
    String password = "12345";

    public Databaseconectivity() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public class Main {
        public static void main(String[] args) {
            SwingUtilities.invokeLater(Main::createUI);
            Databaseconectivity s = new Databaseconectivity();

        }

        static void createUI() {
            JFrame frame = new JFrame("🌿 Biotech Smart Health Assistant");
            frame.setSize(750, 700);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel title = new JLabel("🌿 Biotech Smart Health Assistant", JLabel.CENTER);
            title.setFont(new Font("Verdana", Font.BOLD, 26));
            title.setForeground(new Color(0, 153, 76));
            JTextField nameField = new JTextField(20);
            JTextField phoneField = new JTextField(15);
            JTextField emailField = new JTextField(20);
            JTextField cityField = new JTextField(15);

            JLabel nameLabel = new JLabel("👤 Full Name:");
            JLabel phoneLabel = new JLabel("📞 Contact No:");
            JLabel emailLabel = new JLabel("📧 Email:");
            JLabel cityLabel = new JLabel("🏠 City:");

            String[] symptomsList = {
                    "Fever", "Cough", "Headache", "Nausea", "Fatigue", "Chest Pain", "Blurred Vision",
                    "Sneezing", "Sore Throat", "Body Ache"
            };
            JCheckBox[] symptomBoxes = new JCheckBox[symptomsList.length];
            JPanel symptomPanel = new JPanel(new GridLayout(5, 2, 10, 5));
            symptomPanel.setBackground(new Color(245, 255, 250));
            for (int i = 0; i < symptomsList.length; i++) {
                symptomBoxes[i] = new JCheckBox(symptomsList[i]);
                symptomBoxes[i].setBackground(new Color(255, 255, 255));
                symptomBoxes[i].setFont(new Font("Arial", Font.PLAIN, 14));
                symptomPanel.add(symptomBoxes[i]);
            }

            JComboBox<String> ageBox = new JComboBox<>(new String[]{"Select Age", "Under 18", "18-40", "40+"});
            JComboBox<String> genderBox = new JComboBox<>(new String[]{"Select Gender", "Male", "Female", "Other"});
            ageBox.setBackground(Color.WHITE);
            genderBox.setBackground(Color.WHITE);

            JButton checkButton = new JButton("🔍 Diagnose");
            checkButton.setBackground(new Color(0, 204, 153));
            checkButton.setForeground(Color.WHITE);
            checkButton.setFont(new Font("Arial", Font.BOLD, 16));

            JTextArea resultArea = new JTextArea(10, 50);
            resultArea.setEditable(false);
            resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
            resultArea.setBackground(new Color(255, 253, 240));
            resultArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

            JLabel tipLabel = new JLabel("💡 Health Tip:");
            tipLabel.setFont(new Font("Arial", Font.BOLD, 14));
            JTextArea tipArea = new JTextArea(2, 50);
            tipArea.setEditable(false);
            tipArea.setBackground(new Color(240, 255, 240));
            tipArea.setFont(new Font("Arial", Font.ITALIC, 13));
            tipArea.setForeground(new Color(0, 102, 0));

            JLabel goodbyeLabel = new JLabel("Thank you for using Smart Biotech Health Assistant 🌱 Stay Safe & Healthy!", JLabel.CENTER);
            goodbyeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            goodbyeLabel.setForeground(new Color(0, 102, 51));

            checkButton.addActionListener((ActionEvent e) -> {
                ArrayList<String> selectedSymptoms = new ArrayList<>();
                for (JCheckBox box : symptomBoxes) {
                    if (box.isSelected()) selectedSymptoms.add(box.getText().toLowerCase());
                }

                String age = ageBox.getSelectedItem().toString();
                String gender = genderBox.getSelectedItem().toString();

                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String city = cityField.getText();

                StringBuilder personalDetails = new StringBuilder();
                personalDetails.append("👤 Name: ").append(name).append("\n")
                        .append("📍 City: ").append(city).append("\n")
                        .append("📞 Phone: ").append(phone).append("\n")
                        .append("📧 Email: ").append(email).append("\n\n");

                String diagnosis = analyze(selectedSymptoms, age, gender);
                String tip = getTip(age);

                resultArea.setText(personalDetails + diagnosis);
                tipArea.setText(tip);
            });

            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
            formPanel.setBackground(new Color(255, 255, 255));
            formPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

            formPanel.add(title);
            formPanel.add(Box.createVerticalStrut(10));
            formPanel.add(nameLabel);
            formPanel.add(nameField);
            formPanel.add(phoneLabel);
            formPanel.add(phoneField);
            formPanel.add(emailLabel);
            formPanel.add(emailField);
            formPanel.add(cityLabel);
            formPanel.add(cityField);
            formPanel.add(Box.createVerticalStrut(10));
            formPanel.add(new JLabel("✔️ Select Symptoms:"));
            formPanel.add(symptomPanel);
            formPanel.add(Box.createVerticalStrut(10));
            formPanel.add(new JLabel("👤 Select Gender:"));
            formPanel.add(genderBox);
            formPanel.add(Box.createVerticalStrut(10));
            formPanel.add(new JLabel("🎂 Select Age Group:"));
            formPanel.add(ageBox);
            formPanel.add(Box.createVerticalStrut(10));
            formPanel.add(checkButton);
            formPanel.add(Box.createVerticalStrut(10));
            formPanel.add(new JLabel("🧾 Diagnosis Result:"));
            formPanel.add(new JScrollPane(resultArea));
            formPanel.add(tipLabel);
            formPanel.add(tipArea);
            formPanel.add(Box.createVerticalStrut(10));
            formPanel.add(goodbyeLabel);

            frame.getContentPane().add(formPanel);
            frame.setVisible(true);
        }

        static String analyze(ArrayList<String> symptoms, String ageGroup, String gender) {
            StringBuilder result = new StringBuilder();

            if (symptoms.contains("fever") && symptoms.contains("cough")) {
                result.append("🦠 Possible Condition: Flu or COVID-19\n");
                result.append("💊 Medicine: Paracetamol, Dolo-650, Benadryl\n");
                result.append("🫖 Home Remedy: Steam, warm water, ginger tea\n");
            } else if (symptoms.contains("headache") && symptoms.contains("blurred vision")) {
                result.append("🧠 Possible Condition: Migraine or Eye Strain\n");
                result.append("💊 Medicine: Ibuprofen, consult eye specialist\n");
                result.append("🫖 Home Remedy: Cold compress, rest in dark room\n");
            } else if (symptoms.contains("fatigue") && ageGroup.equals("40+")) {
                result.append("🔋 Possible Condition: BP/Diabetes or Thyroid\n");
                result.append("💊 Medicine: Check-up needed. Avoid sugar/salt\n");
                result.append("🫖 Home Remedy: Walk daily, hydrate, eat balanced diet\n");
            } else if (symptoms.contains("chest pain")) {
                result.append("🚨 Emergency: Could be cardiac. Seek help now!\n");
            } else if (symptoms.isEmpty()) {
                result.append("❗ No symptoms selected. Please choose symptoms.");
            } else {
                result.append("❓ Diagnosis unclear. General care advised.\n");
                result.append("🫖 Remedy: Sleep well, eat light, monitor symptoms.\n");
            }

            return result.toString();
        }

        static String getTip(String ageGroup) {
            return switch (ageGroup) {
                case "Under 18" -> "🏃‍♂️ Run, play, and limit screen time!";
                case "18-40" -> "💪 Exercise, hydrate, and meditate daily.";
                case "30-44" -> "🧘‍♀️ Walk daily and monitor BP/sugar regularly.";
                case "45-55+" -> "🧘‍♀️ Take rest , eat fruits and take milk daily.";
                default -> "🌿 Eat fresh, sleep enough, and smile often!";
            };
        }
    } }



