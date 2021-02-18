/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author germa
 */

public class RestControler {
    private final String dspace_password;
    private final String dspace_rest_url;    
    private final String dspace_email;    
    
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    

    public RestControler() {        
        this.dspace_rest_url = "http://localhost:8080/rest/";
        this.dspace_email = "gerdarpog@gmail.com";
        this.dspace_password = "german";
    }
    
    private void close() throws IOException {
        httpClient.close();
    }
 
    private void Autenticar() throws Exception {                
        HttpPost post = new HttpPost(dspace_rest_url+"login");
        StringEntity input = new StringEntity("{\"email\":\""+dspace_email+"\",\"password\":\""+dspace_password+"\"}");         
        input.setContentType("application/json");
        post.setEntity(input);
        HttpResponse response = httpClient.execute(post);
        
        HttpEntity httpEntity = response.getEntity();
        System.out.println(EntityUtils.toString(response.getEntity()));                
        
        //InputStream is = httpEntity.getContent();
    } 
    
    private void sendPost_2(String token) throws IOException{
        
        int itemID = 120;
        String thumbnailPath = "";
        
        //httpClient = HttpClients.createDefault();
        
        HttpPost post = new HttpPost(dspace_rest_url+"items/"+itemID+"/bitstreams");
        post.setHeader("Content-type", "application/json; charset=utf-8");
        post.setHeader("rest-dspace-token", token); // Nro de la sesion.
        File postFile = new File(thumbnailPath);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();        

        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        final File file = new File("/sample.txt");
        FileBody cbFile = new FileBody(file);
        builder.addPart("userfile", cbFile);

        HttpEntity entity = builder.build();
        post.setEntity(entity);
        System.out.println("executing request " + post.getRequestLine());
        HttpResponse response = httpClient.execute(post);
    }
    
    private void sendPsot_3 () {              
        try {
            HttpPost post = new HttpPost("http://www.somesite.net/this.aspx");
            
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            
            final File file = new File("/sample.txt");
            FileBody fb = new FileBody(file);
            
            builder.addPart("file", fb);
            final HttpEntity yourEntity = builder.build();
            post.setEntity(yourEntity);
            httpClient.execute(post);
        } catch (IOException ex) {
            Logger.getLogger(RestControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   public String uploadFile(String url, String path) throws IOException {
    HttpPost post = new HttpPost(url);
    try {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        FileBody fileBody = new FileBody(new File(path)); //image should be a String
        builder.addPart("file", fileBody);
        post.setEntity(builder.build());

        CloseableHttpResponse response = httpClient.execute(post);
        return (response.getStatusLine().toString());
    } finally {
        post.releaseConnection();
    }
}

}
