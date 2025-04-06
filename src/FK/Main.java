package FK;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class Main {

    private JFrame frame;
    private JTextField clientNameField;
    private JTextField clientPhoneField;
    private JComboBox<String> trainerComboBox;
    private JSpinner sessionTimeSpinner;
    private BD dbManager;


    public Main() {
        dbManager = new BD();
        frame = new JFrame("контроль тренировок");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2));

        // имя клиента
        frame.add(new JLabel("имя клиента:"));
        clientNameField = new JTextField();
        frame.add(clientNameField);

        // телефон клиента
        frame.add(new JLabel("телефон клиента:"));
        clientPhoneField = new JTextField();
        frame.add(clientPhoneField);

        // выбрать тренера
        frame.add(new JLabel("выбрать тренера:"));
        trainerComboBox = new JComboBox<>();
        loadTrainers();
        frame.add(trainerComboBox);

        // время тренировки
        frame.add(new JLabel("время тренировки:"));
        sessionTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(sessionTimeSpinner, "yyyy-MM-dd HH:mm");
        sessionTimeSpinner.setEditor(editor);
        frame.add(sessionTimeSpinner);

        // кнопка
        JButton addButton = new JButton("добавить тренировку");
        addButton.addActionListener(new AddSessionAction());
        frame.add(addButton);

        frame.setVisible(true);
    }

    private void loadTrainers() {
        try {
            List<String> trainers = dbManager.getTrainers();
            for (String trainer : trainers) {
                trainerComboBox.addItem(trainer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "ошибка добавления тренера: " + e.getMessage());
        }
    }

    private class AddSessionAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String clientName = clientNameField.getText();
            String clientPhone = clientPhoneField.getText();
            String trainerName = (String) trainerComboBox.getSelectedItem();
            Timestamp sessionTime = new Timestamp(((java.util.Date) sessionTimeSpinner.getValue()).getTime());

            try {
                // добавление клиента
                dbManager.addClient(clientName, clientPhone);

                // получение айди тренера
                int trainerId = dbManager.getTrainerId(trainerName);
                if (trainerId != -1) {
                    // получение айди клиента
                    int clientId = dbManager.getClientId(clientName, clientPhone);
                    if (clientId != -1) {
                        // добавление сессии
                        dbManager.addSession(trainerId, clientId, sessionTime);
                        JOptionPane.showMessageDialog(frame, "тренировка добавлена!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "клиент не найден.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "тренер не найден.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "ошибка добавления тренировки: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
