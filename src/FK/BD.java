package FK;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BD {
    private final String host = "localhost";
    private final String port = "5432";
    private final String dbName = "Admin";
    private final String login = "postgres";
    private final String password = "123";

    private Connection dbCon; // подключение к бд

    private Connection getDBConnection() {
        String str = "jdbc:postgresql://" + host + ":"
                + port + "/" + dbName;
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("соединение установлено");
        } catch (ClassNotFoundException e) {
            System.out.println("соединение не установлено");
        }
        try {
            dbCon = DriverManager.getConnection(str, login, password);
        } catch (SQLException e) {
            System.out.println("неверный путь (логин и пароль)");
        }
        return dbCon;
    }

    public void isConnection() throws SQLException, ClassNotFoundException {
        dbCon = getDBConnection();
        System.out.println(dbCon.isValid(1000));
    }
    // метод для добавления клиента
    public void addClient(String name, String phone) throws SQLException {
        String sql = "INSERT INTO clients (name, phone) VALUES (?, ?)";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.executeUpdate();
        }
    }

    // метод для получения списка тренеров
    public List<String> getTrainers() throws SQLException {
        List<String> trainers = new ArrayList<>();
        String sql = "SELECT name FROM trainers";
        try (Connection conn = getDBConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                trainers.add(rs.getString("name"));
            }
        }
        return trainers;
    }

    // метод для получения айди тренера по имени
    public int getTrainerId(String trainerName) throws SQLException {
        String sql = "SELECT id FROM trainers WHERE name = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trainerName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1; //тренера нет
    }

    // метод для получения айди клиента по имени и телефону
    public int getClientId(String clientName, String clientPhone) throws SQLException {
        String sql = "SELECT id FROM clients WHERE name = ? AND phone = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, clientName);
            pstmt.setString(2, clientPhone);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1; // клиента нет
    }

    // метод для добавления тренировки
    public void addSession(int trainerId, int clientId, Timestamp sessionTime) throws SQLException {
        String sql = "INSERT INTO sessions (trainer_id, client_id, session_time) VALUES (?, ?, ?)";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            pstmt.setInt(2, clientId);
            pstmt.setTimestamp(3, sessionTime);
            pstmt.executeUpdate();
        }
    }
}