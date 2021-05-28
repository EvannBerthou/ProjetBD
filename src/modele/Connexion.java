package modele;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connexion {
	static Connection conn;
	
	public Connexion(String addresse, String username, String password) throws SQLException, ClassNotFoundException{
		/*
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		conn = DriverManager.getConnection ("jdbc:oracle:thin:@madere:1521:info", username, password);
		*/
		conn = DriverManager.getConnection(addresse);
    }
	
	public static ResultSet executeQuery(String query) throws SQLException {
		try {
			Statement stmt = conn.createStatement();
	    	ResultSet rset = stmt.executeQuery(query);
	    	return rset;
		} catch (SQLException e) {
			System.out.println("Erreur dans l'execution de la requete: " + e.toString());
		}
		return null;
	}
	
	public static ResultSet executeQuery(String sql, String[] params) throws SQLException {
	    try {
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        for (int i = 1; i <= params.length; ++i) {
	            pstmt.setString(i, params[i - 1]);
	        }
	        ResultSet rset = pstmt.executeQuery();
	        return rset;
	    } catch (SQLException e) {
	        System.out.println("Erreur dans l'execution de la requete: '" + sql + "' \n" + e.toString());
	    }
	    return null;
	}
	
	public static void executeUpdate(String sql) throws SQLException {
		try {
			Statement stmt = conn.createStatement();
	    	stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Erreur dans l'execution de la requete: '" + sql + "' \n" + e.toString());
		}
	}
	
	public static void executeUpdate(String sql, String[] params) throws SQLException {
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 1; i <= params.length; ++i) {
				pstmt.setString(i, params[i - 1]);
			}
			pstmt.execute();
		} catch (SQLException e) {
			System.out.println("Erreur dans l'execution de la requete: '" + sql + "' \n" + e.toString());
		}
	}
}
