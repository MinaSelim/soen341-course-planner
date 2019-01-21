package http;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    private String user;
    private String pass;


    /**
     * Initialize an HttpManager instance by providing it the credentials used for basic authentication.
     * @param user
     * @param pass
     */
    public HttpClient(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }


    /**
     *
     *
     * This method was written using: https://dzone.com/articles/how-to-implement-get-and-post-request-through-simp
     * @param url
     * @return
     */
    public String get (String url) throws IOException {

        URL getUrl = new URL("https://jsonplaceholder.typicode.com/posts/1");
        //URL getUrl = new URL(url);
        String readLine = null;

        //Setup connection
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        connection.setRequestMethod("GET");

        //Set Http Basic Authentication Header
        connection.setRequestProperty  ("Authorization", "Basic " + getAuthenticationHeader());

        int responseCode = connection.getResponseCode();
        String httpResponse = "";

        if (responseCode == HttpURLConnection.HTTP_OK) {

            //Retrieve the call's input stream response
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            //IOUtils is an external JAR that is able to convert inputstream objects to string
            httpResponse = IOUtils.toString(inputStream, "UTF-8");

            if(inputStream != null)
                inputStream.close();

            if(connection != null)
                connection.disconnect();
        } else {
            System.out.println("Failed to receive HTTP GET response");
        }

        return httpResponse;
    }


    /**
     * This method was written using: https://stackoverflow.com/questions/3283234/http-basic-authentication-in-java-using-httpclient
     * @return
     */

    private String getAuthenticationHeader()
    {
        String credentials = user + ":" + pass;
        String headerValue = Base64.encodeBase64String(credentials.getBytes());
        return headerValue;
    }


}
