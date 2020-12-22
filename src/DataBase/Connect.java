package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Connect {
	private static Connection con = null;
	private static String userName = "root";
	private static String passWord = "thuY3012$";
	private static String connectionURL = "jdbc:mysql://localhost:3306/csdl_ailatrieuphu";

	public static Connection connectSQL() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(connectionURL, userName, passWord);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Kết nối CSDL thất bại", "Thông báo", 1);
		}
		return con;
	}

	public ResultSet LoadData(String sql) {
		ResultSet result = null;
		try {
			con = Connect.connectSQL();
			System.out.println(con);
			Statement statement = con.createStatement();
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static ResultSet resultSet(String sql) {
		ResultSet resultSet = null;
		try {
			PreparedStatement statement = query().prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultSet = statement.executeQuery();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultSet;
	}

	public void UpdateData(String sql) {
		try {
			Statement statement = con.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection query() {
		return con;
	}

	public static void main(String[] args) throws SQLException {
		Connection connection = connectSQL();

		if (connection != null) {
			System.out.println("thanh cong");
		} else {
			System.out.println("that bai");
		}

		System.out.println(connectSQL());
	}

}

//https://www.youtube.com/watch?v=QfaSzmcxD5M
// may chu bat dau phuc vu
