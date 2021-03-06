/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import logic.HistoryEntry;

/**
 *
 * @author M
 */
public class HistoryEntryMapper extends AbstractMapper<HistoryEntry> {
    
    public HistoryEntryMapper() {
    }

    @Override
    public int insert(HistoryEntry historyEntry) throws SQLException {
	try (Connection conn = getConnection(); PreparedStatement statement = getInsertStatement(historyEntry, conn)) {
	    statement.executeUpdate();
	    try (ResultSet keys = statement.getGeneratedKeys()) {
		if (keys == null || !keys.next()) {
		    return -1;
		}
		return keys.getInt(1);
	    }
	}
    }

    @Override
    public void update(HistoryEntry historyEntry) throws SQLException {
	try (Connection conn = getConnection(); PreparedStatement statement = getUpdateStatement(historyEntry, conn)) {
	    statement.executeUpdate();
	}
    }

    @Override
    public void delete(HistoryEntry historyEntry) throws SQLException {
	String query = "DELETE FROM SearchHistory WHERE Id = ?";
	
	try (PreparedStatement statement = getConnection().prepareStatement(query)) {
	    statement.setLong(1, historyEntry.getId());
	    statement.executeUpdate();
	}
    }

    public HistoryEntry find(String word) throws SQLException {
	String query = "SELECT * FROM SearchHistory WHERE Query = ?";
	
	try (PreparedStatement statement = getConnection().prepareStatement(query)) {
	    statement.setString(1, word);
	    try (ResultSet rset = statement.executeQuery()) {
		List<HistoryEntry> history = getElementsFromResultSet(rset);
		if (history != null && !history.isEmpty()) {
		    return history.get(0);
		}
		return null;
	    }
	}
    }
    
    @Override
    public HistoryEntry find(long id) throws SQLException {
	String query = "SELECT * FROM SearchHistory WHERE Id = ?";
	try (PreparedStatement statement = getConnection().prepareStatement(query)) {
	    statement.setLong(1, id);
	    try (ResultSet rset = statement.executeQuery()) {
		List<HistoryEntry> history = getElementsFromResultSet(rset);
		if (history != null && !history.isEmpty()) {
		    return history.get(0);
		}
		return null;
	    }
	}
    }
    
    public List<HistoryEntry> getTopQueries(int amount) throws SQLException {
	String query = "SELECT * FROM SearchHistory ORDER BY Rating DESC FETCH FIRST ? ROWS ONLY";
	try (PreparedStatement statement = getConnection().prepareStatement(query)) {
	    statement.setInt(1, amount);
	    try (ResultSet rset = statement.executeQuery()) {
		return getElementsFromResultSet(rset);
	    }
	}
    }
    
    private PreparedStatement getInsertStatement(HistoryEntry historyEntry, Connection conn) 
	    throws SQLException {
	String query = "INSERT INTO SearchHistory(Query, Date, Rating) "
		+ "VALUES (?, ?, ?)";
	PreparedStatement statement = null;
	try {
	    statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    statement.setString(1, historyEntry.getQuery());
	    statement.setDate(2, new java.sql.Date(historyEntry.getDate().getTime()));
	    statement.setLong(3, historyEntry.getRating());
	    return statement;
	} catch (SQLException ex) {
	    statement.close();
	    throw ex;
	}
    }
    
    private PreparedStatement getUpdateStatement(HistoryEntry historyEntry, Connection conn) 
	    throws SQLException {
	String query = "UPDATE SearchHistory SET Query = ?, Date = ?, "
		+ "Rating = ? WHERE Id = ?";
	PreparedStatement statement = null;
	try {
	    statement = conn.prepareStatement(query);
	    statement.setString(1, historyEntry.getQuery());
	    statement.setDate(2, new java.sql.Date(historyEntry.getDate().getTime()));
	    statement.setLong(3, historyEntry.getRating());
	    statement.setLong(4, historyEntry.getId());
	    return statement;
	} catch (SQLException ex) {
	    statement.close();
	    throw ex;
	}
    }

    @Override
    protected List<HistoryEntry> getElementsFromResultSet(ResultSet rset) 
	    throws SQLException {
	List<HistoryEntry> history = new ArrayList<>();
	while (rset.next()) {
	    long id = rset.getLong("Id");
	    String query = rset.getString("Query");
	    long time = rset.getDate("Date").getTime();
	    java.util.Date date = new java.util.Date(time);
	    int rating = rset.getInt("Rating");
	    
	    history.add(new HistoryEntry(id, query, date, rating));
	}
	return history;
    }
}
