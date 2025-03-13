import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GrafCalc extends JFrame implements ActionListener {

    static JFrame frame;
    static JTextField result; //текстовое поле
    static String a = "", b = "", operation = "";

    public static void main(String[] arg) {

        GrafCalc listen = new GrafCalc(); //создание объекта класса

        frame = new JFrame("calculator");

        result = new JTextField(16); //колво символов
        result.setEditable(false); //неизменяемое полe

        ArrayList<JButton> buttons = new ArrayList<>(); //список с кнопками
        for (int i = 0; i < 10; i++) { //колво кнопок от 0 до 9
            JButton jTmpButton = new JButton(Integer.toString(i)); //переменная переводится в строку в качестве заголовка
            jTmpButton.addActionListener(listen);
            buttons.add(jTmpButton);
        }

        //список операций:
        List<String> operations = Arrays.asList("+", "-", "/", "*", "c", "=");

        JPanel button = new JPanel();//панель, где все скомпановано
        buttons.forEach(button::add); //все кнопки перетащили на панель
        operations.forEach(it -> {
            JButton jTmpButton = new JButton(it);
            jTmpButton.addActionListener(listen);
            button.add(jTmpButton);
        });

        //располагаем все сеткой:
        GridLayout numsAndOpsLayout = new GridLayout(4, 4);
        button.setLayout(numsAndOpsLayout);

        JPanel mainPanel = new JPanel();
        mainPanel.add(result);
        mainPanel.add(button);

        frame.add(mainPanel);
        frame.setSize(400, 200); //размер окна
        frame.setVisible(true); //видимость окна
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.charAt(0) >= '0' && s.charAt(0) <= '9') {
            if (operation.equals("")) {
                a = a + s;
            } else
                b = b + s;
            result.setText(a + operation + b);
        } else if (s.charAt(0) == 'c') {
            a = operation = b = "";
            result.setText(a + operation + b);
        } else if (s.charAt(0) == '=') {
            int rslt = switch (operation) {
                case "+" -> Integer.parseInt(a) + Integer.parseInt(b);
                case "-" -> Integer.parseInt(a) - Integer.parseInt(b);
                case "/" -> Integer.parseInt(a) / Integer.parseInt(b);
                default -> Integer.parseInt(a) * Integer.parseInt(b);
            };
            a = String.valueOf(rslt);
            result.setText(a);
            operation = b = "";
        } else {
            if (operation.equals(""))
                operation = s;
            result.setText(a + operation + b);
        }
    }
}