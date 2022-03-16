/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se150222.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import se150222.utils.DBUtils;

/**
 *
 * @author ACER
 */
public class OrderDAO {

    private static final String CREATE = "INSERT INTO tblOrder(orderDate, total, userID, status) VALUES(?, ?, ?, ?)";

    public int create(OrderDTO order) throws SQLException {
        int check = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs=null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
                ptm.setDate(1, order.getOrderDate());
                ptm.setFloat(2, order.getTotal());
                ptm.setString(3, order.getUserID());
                ptm.setBoolean(4, true);
                ptm.execute();
                rs=ptm.getGeneratedKeys();
                if(rs.next()){
                    check=rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs!=null){
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;

    }

}
