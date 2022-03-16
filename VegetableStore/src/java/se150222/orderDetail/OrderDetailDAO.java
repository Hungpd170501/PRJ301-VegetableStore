package se150222.orderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import se150222.utils.DBUtils;

public class OrderDetailDAO {
        private static final String CREATE = "INSERT INTO tblOrderDetail(price, quantity, orderID, productID, status) VALUES(?, ?, ?, ?, ?)";        

        public boolean create(OrderDetailDTO orderDetail) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE);
                ptm.setFloat(1, orderDetail.getPrice());
                ptm.setInt(2, orderDetail.getQuantity());
                ptm.setInt(3, orderDetail.getOrderID());
                ptm.setString(4, orderDetail.getProductID());
                ptm.setBoolean(5, true);
                check = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
