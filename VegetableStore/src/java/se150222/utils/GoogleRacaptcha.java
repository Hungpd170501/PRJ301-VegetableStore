package se150222.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class GoogleRacaptcha {

    private static final String RECAPTCHA_SERVICE_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String SECRET_KEY = "6LeCa7IeAAAAANH_-p0h1EH_PiJwnYqa9TjCUsyy";

    public static boolean isValid(String clientRecaptchaResponse) throws IOException, ParseException {
        if (clientRecaptchaResponse == null || "".equals(clientRecaptchaResponse)) {
            return false;
        }
        URL obj = new URL(RECAPTCHA_SERVICE_URL);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        String postParams
                = "secret=" + SECRET_KEY
                + "&response=" + clientRecaptchaResponse;
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response.toString());
        Boolean success = (Boolean) json.get("success");
        Double score = (Double) json.get("score");
        return (success && score >= 0.5);
    }
}
