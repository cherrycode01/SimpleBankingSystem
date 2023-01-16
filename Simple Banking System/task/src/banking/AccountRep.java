package banking;

import java.awt.*;
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

    public void update (Account a) {

        String sql = "UPDATE card SET " +
                "pin = ?," +
                "balance = ?" +
                "WHERE number = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement (sql);
            pstmt.setString (1, a.getPin ());
            pstmt.setDouble (2, a.getBalance ());
            pstmt.setString (3, a.getCardNumber ());

            pstmt.executeUpdate ();

        } catch (SQLException e) {
            throw new RuntimeException (e);

        }
    }
    public void loadAccountsFromDbToList(List accountList){
        String sql = "SELECT * FROM card";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                Account a = new Account();
                a.setCardNumber(rs.getString("number"));
                a.setPin(rs.getString("pin"));
                a.setBalance(rs.getInt("balance"));
                accountList.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(accountsList.toString());
    }

}