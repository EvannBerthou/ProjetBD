package modele;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 
 * @author Evann
 *
 */
public class Connexion {
	
	/**
	 * champs static 
	 */
	static Connection conn;
	/**
	 * dernier curseur 
	 */
	private static Statement lastStmt;
	/**
	 * prochain curseur 
	 */
	private static PreparedStatement lastPStmt;

	/**
	 * initialisation de l'objet de connexion avec la base de donner 
	 * @param addresse addresse de connexion
	 * @param username nom du proprietaire de la base de donné
	 * @param password mot de passe pour acceder a la session du propriétaire
	 * @throws SQLException exception lors de l'execution de commande SQL
	 * @throws ClassNotFoundException 
	 */
	public Connexion(String addresse, String username, String password) throws SQLException, ClassNotFoundException{
		if (conn == null || conn.isClosed()) {
			conn = DriverManager.getConnection(addresse, username, password);
		}
	}

	/**
	 * execute la commande SQL passée en parametre et retourne le resultat  
	 * @param query commande en SQL
	 * @return resultat renvoyer par la base de donnée
	 * @throws SQLException gestion d'erreur SQL
	 */
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

	/**
	 * execute la commande SQL passée en parametre en y integrant les donné  et retourne le resultat
	 * @param sql commande SQL a executer 
	 * @param params parametre a integre a la commende SQL 
	 * @return retultat de la commande SQL
	 * @throws SQLException gestion d'erreur SQL 
	 */
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

	
	/**
	 * réalise les update dans la base de donné
	 * @param sql requete SQL d'update a executer
	 * @throws SQLException gestion d'erreur SQL
	 */
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

	/**
	 * réalise les update dans la base de donné avec les parametre donné
	 * @param sql requete SQL d'update
	 * @param params parametre a integre a la requete 
	 * @throws Exception gestion d'erreur SQL
	 */
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
