package banking;

import java.sql.*;
public class AccountRep {

    String fileName;
    private static Connection conn;
    AccountRep (String fileName) throws SQLException {

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);

            String createTableSql = "CREATE TABLE IF NOT EXISTS card (\n"
                    + "    id INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                    + "    number TEXT NOT NULL, \n"
                    + "    pin TEXT NOT NULL, \n"
                    + "    balance INTEGER DEFAULT 0\n"
                    + ");";

            Statement stmt = conn.createStatement();
            stmt.execute(createTableSql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insert (Account newAccount) throws SQLException {

        PreparedStatement pstmt = conn.prepareStatement("CREATE INTO card (number, pin, balance) values (?,?,?)");
        try {
            pstmt.setString(1, newAccount.getCardNumber());
            pstmt.setString(2,  newAccount.getPin());
            pstmt.setInt(3, (int) newAccount.getBalance());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static void update (int newBalance, String cardNumber) {
        String sql = "UPDATE card SET balance = ? WHERE number = ?";
    }
}