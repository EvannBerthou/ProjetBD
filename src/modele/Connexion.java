package modele;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connexion {
	static Connection conn;
	private static Statement lastStmt;
	private static PreparedStatement lastPStmt;

	public Connexion(String addresse, String username, String password) throws SQLException, ClassNotFoundException{
		if (conn == null || conn.isClosed()) {
			conn = DriverManager.getConnection(addresse, username, password);
		}
	}

	public static ResultSet executeQuery(String query) throws SQLException {
		if (lastStmt != null) {
			lastStmt.close();
		}
		try {
			lastStmt = conn.createStatement();
			ResultSet rset = lastStmt.executeQuery(query);
			return rset;
		} catch (SQLException e) {
			System.out.println("Erreur dans l'execution de la requete: " + e.toString());
		}
		return null;
	}

	public static ResultSet executeQuery(String sql, String[] params) throws SQLException {
		if (lastPStmt != null) {
			lastPStmt.close();
		}
	    try {
	    	lastPStmt = conn.prepareStatement(sql);
	        for (int i = 1; i <= params.length; ++i) {
	        	lastPStmt.setString(i, params[i - 1]);
	        }
	        
	        ResultSet rset = lastPStmt.executeQuery();
	        return rset;
	    } catch (SQLException e) {
	        System.out.println("Erreur dans l'execution de la requete: '" + sql + "' \n" + e.toString());
	    }
	    return null;
	}

	public static void executeUpdate(String sql) throws SQLException {
		if (lastStmt != null) {
			lastStmt.close();
		}
		try {
			lastStmt = conn.createStatement();
			lastStmt.executeUpdate(sql);
			conn.commit();
		} catch (SQLException e) {
			System.out.println("Erreur dans l'execution de la requete: '" + sql + "' \n" + e.toString());
		}
	}

	public static void executeUpdate(String sql, String[] params) throws Exception {
		if (lastPStmt != null) {
			lastPStmt.close();
		}
		try {
			lastPStmt = conn.prepareStatement(sql);
			for (int i = 1; i <= params.length; ++i) {
				lastPStmt.setString(i, params[i - 1]);
			}

			lastPStmt.execute();
			conn.commit();
		} catch (SQLException  e) {
			System.out.println("Erreur dans l'execution de la requete: '" + sql + "' \n" + e.toString());
			throw e;
		}
	}
}
