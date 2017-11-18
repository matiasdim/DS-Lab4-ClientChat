package edu.gvsu.restapi.client;

import java.io.IOException;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;
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
    public static final String APPLICATION_URI = "http://localhost:8080";

    public RESTClient(){
        super();
    }

		// EXAMPLE HTTP REQUEST #2
		// Let's do an HTTP GET of widget 1 and ask for JSON response.


		// TODO: EXAMPLE HTTP REQUEST #3
		// Do an HTTP PUT to change the name of widget 1 to "An Old Stale Widget".


		// TODO: EXAMPLE HTTP REQUEST #4
		// Do an HTTP DELETE to delete widget 1 from the server.

		// TODO: Example HTTP REQUEST #5
		// DO an HTTP GET for a resource with id=999.


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
		System.out.println("Sending an HTTP POST to " + usersResourceURL + ".");
		Response resp = new Client(Protocol.HTTP).handle(request);

		// now, let's check what we got in response.
		System.out.println(resp.getStatus());
		Representation responseData = resp.getEntity();
		try {
			System.out.println(responseData.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public RegistrationInfo lookup(String name) throws Exception {

        String usersResourceURL = APPLICATION_URI + "/v1/users/" + name;
        Request request = new Request(Method.GET, usersResourceURL);

        // We need to ask specifically for JSON
        request.getClientInfo().getAcceptedMediaTypes().add(new Preference(MediaType.APPLICATION_JSON));

        // Now we do the HTTP GET
        System.out.println("Sending an HTTP GET to " + usersResourceURL + ".");
        Response resp = new Client(Protocol.HTTP).handle(request);

        // Let's see what we got!
        if(resp.getStatus().equals(Status.SUCCESS_OK)) {
            Representation responseData = resp.getEntity();
            try {
                String jsonString = responseData.getText().toString();
                System.out.println("result text=" + jsonString);
                JSONObject jObj = new JSONObject(jsonString);
                RegistrationInfo user = new RegistrationInfo();
                user.setUserName(jObj.getString("userName"));
                user.setStatus(Boolean.getBoolean(jObj.getString("status")));
                user.setHost(jObj.getString("host"));
                user.setPort(Integer.parseInt(jObj.getString("port")));
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
        System.out.println("Sending an HTTP DELETE to " + usersResourceURL + ".");
        Response resp = new Client(Protocol.HTTP).handle(request);

        // now, let's check what we got in response.
        System.out.println(resp.getStatus());
        Representation responseData = resp.getEntity();
        try {
            System.out.println(responseData.getText());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	@Override
	public void setStatus(RegistrationInfo reg, boolean status) throws Exception {
        Form form = new Form();
        form.add("userName",reg.getUserName());
        form.add("host",reg.getHost());
        form.add("port",Integer.toString(reg.getPort()));
        form.add("status",Boolean.toString(status));
        String usersResourceURL = APPLICATION_URI + "/v1/users/" + reg.getUserName();
        Request request = new Request(Method.POST,usersResourceURL);

        // set the body of the HTTP POST command with form data.
        request.setEntity(form.getWebRepresentation());

        // Invoke the client HTTP connector to send the PUT request to the server.
        System.out.println("Sending an HTTP PUT to " + usersResourceURL + ".");
        Response resp = new Client(Protocol.HTTP).handle(request);

        // now, let's check what we got in response.
        System.out.println(resp.getStatus());
        Representation responseData = resp.getEntity();
        try {
            System.out.println(responseData.getText());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	@Override
	public RegistrationInfo[] listRegisteredUsers() throws Exception {
		return new RegistrationInfo[0];
	}
}
