/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package bookify.Interface;

/**
 *
 * @author luisfelippe
 */

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRepository {
    public void save(String table, String[] columns, String[] values) throws SQLException;
    public void delete(String table, String id) throws SQLException;
    public void update(String table, String[] columns, String[] values, String filter) throws SQLException; 
    public ResultSet get(String table) throws SQLException;
    public ResultSet get(String table, String filter) throws SQLException;
    public ResultSet get(String search, String table, String filter) throws SQLException;
}
