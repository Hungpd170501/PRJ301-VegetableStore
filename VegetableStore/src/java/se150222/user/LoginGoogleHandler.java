package se150222.user;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.client.fluent.Request;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@WebServlet(name = "LoginGoogleHandler", urlPatterns = {"/LoginGoogleHandler"})
public class LoginGoogleHandler extends HttpServlet {

    private final static String ADMIN_PAGE = "MainController?action=AdminSearchProduct&search=";
    private final static String USER_PAGE = "MainController?action=SearchProduct&search=";
    private final static String ERROR = "login.jsp";

    public static String GOOGLE_CLIENT_ID = "786996915789-rthi7jf38p48uddnr0s9t78jumilbg5l.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "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";
    public static String GOOGLE_REDIRECT_URI = "http://localhost:8084/VegetableStore/LoginGoogleHandler";
    public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String code = request.getParameter("code");
            if (code != null || !"".equals(code)) {
                URL obj = new URL(GOOGLE_LINK_GET_TOKEN);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                String postParams = "code=" + code
                        + "&client_id=" + GOOGLE_CLIENT_ID
                        + "&client_secret=" + GOOGLE_CLIENT_SECRET
                        + "&redirect_uri=" + GOOGLE_REDIRECT_URI
                        + "&grant_type=" + GOOGLE_GRANT_TYPE;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer responseToken = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    responseToken.append(inputLine);
                }
                in.close();
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(responseToken.toString());
                String accessToken = (String) json.get("access_token").toString().replaceAll("\"", "");
                String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
                String responseDetail = Request.Get(link).execute().returnContent().asString();
                UserGoogleDTO googlePojo = new Gson().fromJson(responseDetail, UserGoogleDTO.class);
                UserDAO dao = new UserDAO();
                UserDTO user = dao.checkLoginGoogle(googlePojo.getEmail());
                HttpSession session = request.getSession();
                if (user != null) {
                    session.setAttribute("LOGIN_USER", user);
                    if ("AD".equals(user.getRoleID())) {
                        url = ADMIN_PAGE;
                    } else if ("US".equals(user.getRoleID())) {
                        url = USER_PAGE;
                    } else {
                        session.setAttribute("ERROR_MESSAGE", "Your role is not allow!");
                    }
                } else {
                    session.setAttribute("ERROR_MESSAGE", "Incorrect user ID or Password");
                }
            }
        } catch (Exception e) {
            log("Error at LoginGoogleHandler: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(LoginGoogleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(LoginGoogleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
