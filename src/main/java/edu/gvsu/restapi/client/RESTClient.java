package edu.gvsu.restapi.client;

import java.io.IOException;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.restlet.Client;
import org.restlet.data.*;
import org.restlet.*;
import org.restlet.representation.Representation;

/**
 * Sample client program that uses the RESTlet framework to access a RESTful web service.
 * @author Jonathan Engelsma (http://themobilemontage.com)
 *
 */
public class RESTClient implements PresenceService {

	// The base URL for all requests.
    public static final String APPLICATION_URI = "http://gae-restlet-example-186301.appspot.com";

    public RESTClient(){
        super();
    }

	@Override
	public void register(RegistrationInfo reg) throws Exception {

		Form form = new Form();
		form.add("userName",reg.getUserName());
        form.add("host",reg.getHost());
        form.add("port",Integer.toString(reg.getPort()));
        form.add("status",Boolean.toString((reg.getStatus())));

		// construct request to create a new widget resource
		String usersResourceURL = APPLICATION_URI + "/v1/users";
		Request request = new Request(Method.POST,usersResourceURL);

		// set the body of the HTTP POST command with form data.
		request.setEntity(form.getWebRepresentation());

		// Invoke the client HTTP connector to send the POST request to the server.
		//System.out.println("Registring user " + usersResourceURL + ".");
        System.out.println("Registring user...");
		Response resp = new Client(Protocol.HTTP).handle(request);

		// now, let's check what we got in response.
		//System.out.println(resp.getStatus());
		Representation responseData = resp.getEntity();
		//try {
			//System.out.println(responseData.getText());
            System.out.println("User registered.");
		//} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
	}

	@Override
	public RegistrationInfo lookup(String name) throws Exception {

        String usersResourceURL = APPLICATION_URI + "/v1/users/" + name;
        Request request = new Request(Method.GET, usersResourceURL);

        // We need to ask specifically for JSON
        request.getClientInfo().getAcceptedMediaTypes().add(new Preference(MediaType.APPLICATION_JSON));

        // Now we do the HTTP GET
        //System.out.println("Sending an HTTP GET to " + usersResourceURL + ".");
        Response resp = new Client(Protocol.HTTP).handle(request);

        // Let's see what we got!
        if(resp.getStatus().equals(Status.SUCCESS_OK)) {
            Representation responseData = resp.getEntity();
            try {
                String jsonString = responseData.getText().toString();
                //System.out.println("result text=" + jsonString);
                JSONObject jObj = new JSONObject(jsonString);
                RegistrationInfo user = new RegistrationInfo();
                user.setUserName(jObj.getString("userName"));
                user.setStatus(jObj.getBoolean("status"));
                user.setHost(jObj.getString("host"));
                user.setPort(jObj.getInt("port"));
                return user;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }
        return null;
	}

	@Override
	public void unregister(String userName) throws Exception {
        String usersResourceURL = APPLICATION_URI + "/v1/users/" + userName;
        Request request = new Request(Method.DELETE, usersResourceURL);

        // We need to ask specifically for JSON
        request.getClientInfo().getAcceptedMediaTypes().add(new Preference(MediaType.APPLICATION_JSON));

        // Now we do the HTTP DELETE
        //System.out.println("Sending an HTTP DELETE to " + usersResourceURL + ".");
        Response resp = new Client(Protocol.HTTP).handle(request);

        // now, let's check what we got in response.
        System.out.println(resp.getStatus());
        Representation responseData = resp.getEntity();
        System.out.println("User unregistered.");
        /*
        try {
            //System.out.println(responseData.getText());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
	}

	@Override
	public void setStatus(RegistrationInfo reg, boolean status) throws Exception {
        Form form = new Form();
        form.add("userName",reg.getUserName());
        form.add("host",reg.getHost());
        form.add("port",Integer.toString(reg.getPort()));
        form.add("status",Boolean.toString(status));
        String usersResourceURL = APPLICATION_URI + "/v1/users/" + reg.getUserName();
        Request request = new Request(Method.PUT,usersResourceURL);

        // set the body of the HTTP PUT command with form data.
        request.setEntity(form.getWebRepresentation());

        // Invoke the client HTTP connector to send the PUT request to the server.
        //(System.out.println("Sending an HTTP PUT to " + usersResourceURL + ".");
        System.out.println("Updating status...");
        Response resp = new Client(Protocol.HTTP).handle(request);

        // now, let's check what we got in response.
        //System.out.println(resp.getStatus());
        Representation responseData = resp.getEntity();
        System.out.println("Status updated");
        /*try {
            //System.out.println(responseData.getText());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
	}

	@Override
	public RegistrationInfo[] listRegisteredUsers() throws Exception {
        // construct request to create a new user resource
        String usersResourceURL = APPLICATION_URI + "/v1/users";
        Request request = new Request(Method.GET,usersResourceURL);
        // Invoke the client HTTP connector to send the GET request to the server.
        //System.out.println("Sending an HTTP GET to " + usersResourceURL + ".");
        System.out.println("Getting users...");
        Response resp = new Client(Protocol.HTTP).handle(request);
        // Let's see what we got!
        if(resp.getStatus().equals(Status.SUCCESS_OK)) {
            Representation responseData = resp.getEntity();
            try {
                String jsonString = responseData.getText().toString();
                //System.out.println("result text=" + jsonString);
                JSONArray jArr = new JSONArray(jsonString);
                RegistrationInfo[] users = new RegistrationInfo[jArr.length()];
                for (int i = 0 ; i < jArr.length(); i++){
                    JSONObject jObj = new JSONObject(jArr.get(i).toString());
                    RegistrationInfo user = new RegistrationInfo();
                    user.setUserName(jObj.getString("userName"));
                    user.setStatus(jObj.getBoolean("status"));
                    user.setHost(jObj.getString("host"));
                    user.setPort(jObj.getInt("port"));
                    users[i] = user;
                    user = null;
                    jObj = null;
                }
                return users;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }
		return new RegistrationInfo[0];
	}
}
