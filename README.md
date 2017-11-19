# Client application
Lab 04 - Ditributed systems CIS656 - Client application.

## Based on the original source code: gae-restlet-client-example
An chat httpClient that connects with [an APi](https://github.com/matiasdim/DS-Lab4-WebServices) to perform several tasks 

## Tree
```bash
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── edu
│   │   │   │   ├── gvsu
│   │   │   │   │   ├── restpi
│   │   │   │   │   │   ├── client
│   │   │   │   │   │   │   ├── App.java
│   │   │   │   │   │   │   ├── PresenceService.java
│   │   │   │   │   │   │   ├── RESTClient.java
│   │   │   │   │   │   │   ├── RegistrationInfo.java
│   │   │   │   │   │   │   ├── TextListener.javaa
```
* **App.java:** Main class, handles all de "UI" related things, that on the scope of this project is de command line.
* **PresenceService.java:** THe provided presenceService interface to implement the needed API calls.
* **RESTClient.java:** The RESTClient class that handles both the api calls and the implementation of the presence service.
* **RegistrationInfo.java:** Provided Registration info class that acts as a model class (User model)
* **TextListener.java:** The class that handles the message reception for the client.
