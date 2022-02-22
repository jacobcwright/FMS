# FMS - Family Map Server
Family Map Server Specification
Acknowledgements
Family Map Introduction
Family Map is an application that provides a geographical view of your family history. One of the most exciting aspects of researching family history is discovering your origins. Family Map provides a detailed view of where you came from. It does so by displaying information about important events in your ancestors’ lives (birth, marriage, death, etc.), and plotting their locations on a Google map.

Family Map uses a client/server architecture, which means it consists of two separate programs (a "client" program and a "server" program). The Family Map client is an Android app that lets a user view and interact with their family history information (see image on the left). The Family Map server is a regular, non-Android Java program that runs at a publicly-accessible location in the "cloud" (although, for development purposes you will run it locally on your laptop or some other machine). When a user runs the Family Map client app, they are first asked to login. After authenticating the user’s identity with the Family Map server, the client app retrieves the user’s family history data from the server. The server is responsible for maintaining user accounts as well as dispensing family history data for Family Map users. Family Map’s client/server architecture is typical of many real-world applications that you are probably familiar with (Facebook, Twitter, etc.)

In CS 240 you will design and implement both parts of the Family Map application. For the first project you will create a Family Map server. For the second project you will create a Family Map Android client. (NOTE: In the real world you would typically develop the client and server simultaneously rather than sequentially, but for this class we will ask you to create them sequentially.)

Family Map Server (FMS)
This project focuses on designing, implementing, and testing the Family Map server. The server is a regular Java program. When it runs the server does not display an interactive user interface (i.e., it is "headless"). Rather than interacting with a user, it waits for Family Map clients to connect to it through the internet to authenticate users and retrieve user family history information. Although the server has no user interface, it may display diagnostic or informational messages in the console and/or in a log file. This is useful for debugging the server and monitoring its operation. Additionally, because we do not have real family history data for our users, part of the server’s functionality is to generate artificial family history data for each Family Map user.

The purpose of this project is to learn about and gain experience with the following:

Designing, implementing, and testing a large, multi-faceted program
Relational database concepts and programming
Creation of server programs that publish web APIs
Automated unit and integration testing

Getting Started
We have a starter Intellij project that you should use for this project. It has the passoff driver already set up and ready to go: https://github.com/jerodw/FamilyMapServerStudent. If you wish to not use the starter project, you will still need some files contained inside for the project and you will need to be able to run the junit tests in the project as well. Because of this, we highly recommend using the Intellij starter project with Intellij.
Project Requirements
General Overview
The Family Map Server will be a Java project consisting of these parts: the Server, Handlers, Services, SQLite Database, Data Access Objects, Models, Requests, and Responses. The Server itself will be implemented using the HttpServer class available with the JVM and will accept HTTP requests and return HTTP responses. The HttpServer class already is a working Java server, so you will just need to create Handler classes for each of the endpoints listed later in this document. Handlers are classes that implement the HttpHandler interface. The HttpServer class routes incoming requests to the appropriate handler based on a mapping you create that maps incoming URLs to handlers. Handlers return HTTP responses that get routed back to the requesting client.

To fulfill these HTTP requests, the Handlers will call the appropriate Service classes. The Service classes will perform the actual functionality of the server (logging in, registering, requesting family data, etc). The data the Services use will be stored in the server’s SQLite Database and accessed using the Data Access Object classes (also known as DAOs). Model objects are used to represent the data stored in a single table row and are used as parameters and return values of the DAO methods. Request classes are used to store the data from the request body of HTTP Requests and Response classes are used to store the data that will be returned in the response body of HTTP Responses.
Data Definitions
The following data definitions show the data that is stored in the server’s database with each definition representing a table in the database. You will also create model objects for each table that can hold the data from a single table row.

Note: Each table and model object must contain all of the listed attributes. Do not change any attribute names (including case) or data types.
User
Attribute
Description
Data Type
username
Unique username for user
string
password
User’s password
string
email
User’s email address
string
firstName
User’s first name
string
lastName
User’s last name
string
gender
User’s gender
string "f" or "m"
personID
Unique Person ID assigned to this user’s generated Person
string


Person
Attribute
Description
Data Type
personID
Unique identifier for this person
string
associatedUsername
Username of user to which this person belongs
string
firstName
Person’s first name
string
lastName
Person’s last name
string
gender
Person’s gender
string: "f" or "m"
fatherID
Person ID of person’s father
string, may be null
motherID
Person ID of person’s mother
string, may be null
spouseID
Person ID of person’s spouse
string, may be null

Event
Attribute
Description
Data Type
eventID
Unique identifier for this event
string
associatedUsername
Username of user to which this event belongs
string
personID
ID of person to which this event belongs
string
latitude
Latitude of event’s location
float
longitude
Longitude of event’s location
float
country
Country in which event occurred
string
city
City in which event occurred
string
eventType
Type of event
string
year
Year in which event occurred
int


Authorization Token (Authtoken)
Attribute
Description
Data Type
authtoken
Unique authtoken
string
username
Username that is associated with the authtoken
string

User Authentication
When a user logs in to your server, the login request sent from the client to the server must include the user’s username and password. If login succeeds, your server will generate a unique "authorization token" string for the user, and return it to the client. Subsequent api requests sent from the client to your server may include the authtoken, allowing your server to determine which user is making the request. This allows non-login requests to be made without having to include the user’s credentials. For this to work, your server must store each authtoken in the server’s database and record which user each token belongs to.

Each new login request must generate and return a new authtoken. It must also be possible for the same user to be logged in from multiple clients at the same time, which means that each client will have a different authtoken but each authtoken will be associated with the same user.

When a client registers a new user, a new authorization token is generated and returned in the same way as the login. An authtoken must be included in the HTTP request header "Authorization" for all api requests that require an authtoken.
Database / Persistence
All of the data that the server uses must be stored in your server’s database so it is not lost when the server is stopped and restarted. The type of database we will be using for this project is an SQLite database.
Family History Information Generation
Your server will be able to generate and store family history information in its database for each user. This is done when a new user is registered or a fill operation is requested for any existing user. This data will include three types of objects: Users, Persons, and Events. A User object represents a user registered with the server, and each user will have their own separate set of family history data. Each User will have Person objects that represent everyone in that User’s family tree (there will also be a Person object generated for the User themselves). These objects share an identical associatedUsername value that matches the User object’s username, and will each have a unique person id value. Each Person object may also have Events associated with them, representing the various life events of that person. These objects share an identical associatedUsername value that matches the User object’s username, and will each have a unique event id value. Additionally they will also have a person id value that matches the person that they are associated with, as each event will be associated with one person.

When registering a new user, your server will generate 4 generations of family data. When performing a fill request, your server will generate a variable number of generations of family data, with the default being 4 generations. This value must be 0 or higher. For reference of how many people you generate for a given value:

0 generations: generate person and event data for just the user
1 generation: generate person and event data for the user and their parents
2 generations: generate person and event data for the user, the user’s parents, and the user’s grandparents
Pattern continues with more generations

The test driver will only test your server’s fill endpoint with up to 5 generations of family data.

For each Person generated, you must generate a set of Event objects that describe important events from the person’s life. Events generated by the Fill service and generated during registration must meet the following criteria:

Each person, excluding the user, must have at least three events with the following types: birth, marriage, and death. They may have additional events as well, but we will only be checking for these three.
The user’s person object needs to have at least a birth event, and may have additional events, but we will only be checking for the birth event.
Parents must be born at least 13 years before their children.
Parents must be at least 13 years old when they are married.
Parents must not die before their child is born. 
Women must not give birth when older than 50 years old. 
Birth events must be the first event for a person chronologically.
Death events must be the last event for a person chronologically. 
Nobody must die at an age older than 120 years old.
Each person in a married couple has their own marriage event. Each event will have a unique event ID, but both marriage events must have matching years and locations. 
Event locations may be randomly selected, or you may try to make them more realistic (e.g., many people live their lives in a relatively small geographical area). 

Your server must allow for any event type, not just the three event types used when generating family data during the Fill or Register services. For example, a load request may contain an event with the event type "Completed College", and your server needs to be able to store that event in the database.

To assist in person and event generation, there are JSON files provided in the starter project that contain name and location data that can be used during person and event generation. 
Server Command-Line Arguments
Your server should accept the following command-line argument:

Port number on which the server will accept client connections. This value is an integer in the range 1-65535. EX: 8080

When running your server, you will likely be using an IDE. Your IDE will have a place to set the command line arguments to be used when running your server (you will not need to run code from the command line in this class).
Web API Test Page / File Handler
To allow interactive testing of your server, we have created a Web API Test Page that lets you:

Interactively construct a Web API call
Send the Web API call to the server
See the output returned by the server for the call

This will be a key development and testing tool for you, as the passoff driver is designed to work with a server that at least implements all of the endpoints to some degree. The passoff driver will also ensure that the test page is provided by the server as expected.

The "home page" for your server is the Web API Test Page. This means that when a user points their web browser at your server (e.g., http://localhost:8080/), your server must return the Web API Test Page, allowing the user to interactively test your server. Your server’s Web API Test Page must be fully-functional (including returning images and styling properly).

All of the HTML, CSS, and image files that make up the Web API Test Page are provided in the starter project. These files should be placed in a folder within your server project. When your server receives an HTTP GET request asking for one of these files (based on the request URL, e.g., "/index.html"), it should return the contents of the requested file in the body of the HTTP response. Doing this will allow a user to view your server’s Web API Test Page in their web browser.

The end result will be that your server will perform two separate functions:

Service Web API requests from clients
Act like a normal web server by serving up it’s "home page" (the Web API Test Page)

In web applications, it is typical for a server to do both of these things. 
Web APIs / Handlers & Services
The primary function of the server is to publish a set of web APIs for use by the Family Map client. Your server must implement each of the web API operations described below (with Handler and Service classes).

Each request and response body listed below will be in JSON format, and will be stored as a string when sent from and to a client. You will use the GSON java library to convert between Java objects and JSON strings.

Each endpoint below will be created with it’s own handler and service class. The description of each endpoint describes what the service needs to do. The handler class reads a JSON string from the request body (for requests that have a request body), converts the JSON string and possibly other data from the Http request to a request object, calls the appropriate service class method, converts the return value (Response object) from the service method call to a JSON string, and sends the response JSON string back to the client. The service class performs its function as described below, interacting with the database through data access classes when necessary.

Important Pass-off Requirements: To ensure that your server works with the passoff driver, you must pay extra attention to the API and follow it exactly. Here are some things to keep in mind. Your generated JSON for requests and results must match exactly as shown in the API, including the case of letters in strings. Additionally, when a result contains an error message because something went wrong, that message must contain the word "error" and your server must return a 400 status code for the response code.
/user/register
URL Path: /user/register
Description: 
Creates a new user account (user row in the database)
Generates 4 generations of ancestor data for the new user (just like the /fill endpoint if called with a generations value of 4 and this new user’s username as parameters)
Logs the user in
Returns an authtoken
HTTP Method: POST
Authtoken Required: No
Possible Errors: Request property missing or has invalid value, Username already taken by another user, Internal server error

Request Body:
{
	"username":"susan",		// string 
	"password":"mysecret",	// string
	"email":"susan@gmail.com",	// string		
	"firstName":"Susan",		// string
	"lastName":"Ellis",		// string
"gender":"f"			// string: "f" or "m"
}

Success Response Body:
{
	"authtoken":"cf7a368f",	// string
	"username":"susan",		// string
	"personID":"39f9fe46",	// string
"success":true			// boolean
}

Error Response Body:
{
	"message":"Error:[Description of the error]",	// string
"success":false							// boolean
}
/user/login
URL Path: /user/login
Description: 
Logs the user in
Returns an authtoken.
HTTP Method: POST
Authtoken Required: No
Possible Errors: Request property missing or has invalid value, Internal server error

Request Body:
{
	"username":"susan",		// string 
	"password":"mysecret"		// string
}

Success Response Body:
{
	"authtoken":"cf7a368f",	// string
	"username":"susan",		// string
	"personID":"39f9fe46",	// string
"success":true			// boolean
}

Error Response Body:
{
	"message":"Error:[Description of the error]",	//string
"success":false							// boolean
}
/clear
URL Path: /clear
Description: 
Deletes ALL data from the database, including user, authtoken, person, and event data
HTTP Method: POST
Authtoken Required: No
Possible Errors: Internal server error

Request Body: None

Success Response Body:
{
	"message":"Clear succeeded.",	// string
"success":true				// boolean
}

Error Response Body:
{
	"message":"Error:[Description of the error]",	//string
"success":false							// boolean
}
/fill/[username]/{generations}
URL Path: /fill/
Examples: 
/fill/susan
/fill/susan/3
Description: 
Populates the server's database with generated data for the specified username. The required "username" parameter must be a user already registered with the server. If there is any data in the database already associated with the given username, it is deleted. 
The optional "generations" parameter lets the caller specify the number of generations of ancestors to be generated, and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events).
More details can be found in the earlier section titled “Family History Information Generation”
HTTP Method: POST
Authtoken Required: No
Possible Errors: Invalid username or generations parameter, Internal server error

Request Body: None

Success Response Body:
{
	"message":"Successfully added X persons and Y events to the database.",			//string
"success":true		// boolean
}

Error Response Body:
{
	"message":"Error:[Description of the error]",	// string
"success":false							// boolean
}
/load
URL Path: /load
Description: 
Clears all data from the database (just like the /clear API)
Loads the user, person, and event data from the request body into the database.
HTTP Method: POST
Authtoken Required: No
Possible Errors: Invalid request data (missing values, invalid values, etc.), Internal server error

Request Body: 
Note: The json of the objects in the three arrays will have attributes matching those given in the Data Definitions section.
{
	"users":[json array of User objects],		// User[]
	"persons":[json array of Person objects],	// Person[]
	"events":[json array of Event objects]		// Event[]
}

Success Response Body:
{
	"message":"Successfully added X users, Y persons, and Z events to the database.",			//string
"success":true 		// boolean
}

Error Response Body:
{
	"message":"Error:[Description of the error]",	//string
"success":false							// boolean
}
/person/[personID]
URL Path: /person/
Example: /person/7255e93e
Description: 
Returns the single Person object with the specified ID (if the person is associated with the current user). The current user is determined by the provided authtoken.
HTTP Method: GET
Authtoken Required: Yes
Possible Errors: Invalid auth token, Invalid personID parameter, Requested person does not belong to this user, Internal server error

Request Body: None

Success Response Body:
{
	"associatedUsername":"susan",	// string
	"personID":"7255e93e",		// string
	"firstName":"Stuart",			// string
	"lastName":"Klocke",			// string
	"gender":"m",				// string: "f" or "m"
	"fatherID": "7255e93e",	// string [OPTIONAL, can be missing]
	"motherID":"d3gz214j",	// string [OPTIONAL, can be missing]
	"spouseID":"f42126c8"	,	// string [OPTIONAL, can be missing]
"success":true				// boolean
}

Error Response Body:
{
	"message":"Error:[Description of the error]",	// string
"success":false							// boolean
}
/person
URL Path: /person
Description: 
Returns ALL family members of the current user. The current user is determined by the provided authtoken.
HTTP Method: GET
Authtoken Required: Yes
Possible Errors: Invalid auth token, Internal server error

Request Body: None

Success Response Body: 
Note: Each entry in the json array has the same attributes as given for the Person model in the Data Definitions section.
{
	"data":[json array of Person objects],		// Person[]
"success":true						// boolean
}

Error Response Body:
{
	"message":"Error:[Description of the error]",	// string
"success":false							// boolean
}
/event/[eventID]
URL Path: /event/
Example: /event/251837d7
Description: 
Returns the single Event object with the specified ID (if the event is associated with the current user). The current user is determined by the provided authtoken.
HTTP Method: GET
Authtoken Required: Yes
Possible Errors: Invalid auth token, Invalid eventID parameter, Requested event does not belong to this user, Internal server error

Request Body: None

Success Response Body:
{
	"associatedUsername":"susan", 	// string
	"eventID":"251837d7",			// string
	"personID":"7255e93e",		// string
	"latitude":65.6833,			// float
	"longitude":-17.9,			// float
	"country":"Iceland",			// string
	"city":"Akureyri",			// string
	"eventType":"birth",			// string
	"year":1912,				// int
"success":true				// boolean
}

Error Response Body:
{
	"message":"Error:[Description of the error]",	// string
"success":false							// boolean
}

/event
URL Path: /event
Description: 
Returns ALL events for ALL family members of the current user. The current user is determined from the provided auth token. 
HTTP Method: GET
Authtoken Required: Yes
Possible Errors: Invalid auth token, Internal server error

Request Body: None

Success Response Body: 
Note: Each entry in the json array has the same attributes as given for the Event model in the Data Definitions section.
{
	"data":[json array of Event objects],		// Event[]
"success":true						// boolean
}

Error Response Body:
{
	"message":"Error:[Description of the error]",	// string
"success":false							// boolean
}

Additional Examples
For the successful /person and /event responses, here are some larger examples:

/person (success response):
{
	"data":[
		{
	"associatedUsername":"susan",
	"personID":"7255e93e",
	"firstName":"Stuart",
"lastName":"Klocke",
	"gender":"m",
	"fatherID": "7255e93e",
	"motherID":"d3gz214j",
	"spouseID":"askdnwjd"
},
{
	"associatedUsername":"susan",
	"personID":"askdnwjd",
	"firstName":"Susan",
"lastName":"Klocke",
	"gender":"f",
	"fatherID": "asdfeafdsa",
	"motherID":"aswdesd",
	"spouseID":"7255e93e"
}
],
"success":true	
}

/event (success response):
{
	"data":[
		{
	"associatedUsername":"susan",
	"eventID":"251837d7",
	"personID":"7255e93e",
	"latitude":65.6833,
	"longitude":-17.9,
	"country":"Iceland",
	"city":"Akureyri",
	"eventType":"birth",
	"year":1912
},
{
	"associatedUsername":"susan",
	"eventID":"asdadasdas",
	"personID":"7255e93e",
	"latitude":65.6833,
	"longitude":-17.9,
	"country":"Iceland",
	"city":"Akureyri",
	"eventType":"death",
	"year":1972,
}
],
"success":true	
}
Automated Unit Testing
Requirements
Use the JUnit 5 testing framework to implement automated unit tests for your Data Access and Service classes.

You are expected to have a minimum of 2 test cases for each public method found in these classes–one "positive" test case and one "negative" test case. The "positive" test cases invoke methods with inputs that should return a successful or normal result from the methods, while the "negative" test cases invoke methods with inputs that should cause the method to fail, return an error, or properly deal with abnormal input.

Some methods might not have the possibility of failing or being able to have an error. In this case, you should have 2 "positive" test cases. One of these "positive" test cases should test the main usage scenario (sometimes called the "happy path"). The other "positive" test case should test an alternative scenario or case. Review the following Q&A for more details.
Test Case Q&A
Q: How many test cases do I need?
A: You need at least 2 test cases per public method found in your Service and DAO classes.

Q: Can I make more test cases than those required?
A: Absolutely. You may find doing more test cases to be helpful as a debugging tool.

Q: How much do I need to do for each test case?
A: Each test case should run through a complete execution of the method that it is testing and then use assertions to ensure that the method ran as expected. Most (if not all) test cases will require multiple assertions to properly test a method.

Q: What does a "negative" test case look like? Is it okay if my code crashes?
A: Your code should not crash. If your method is designed to throw a certain exception, there is a Junit assertion to test that (assertThrows). Some examples of "negative" test cases include trying to log in with a bad password, trying to find a personID that does not exist, or passing invalid parameters. This is just the beginning though. We want you to think about the possible weak points in your code, and try to find them. Make them as unique as possible; don’t just pass null into the parameters for every "negative" case. You will lose points for bad test cases.

Q: Is it okay that my test cases depend on methods besides the one it tests?
A: Yes. We wish to avoid this where possible because it makes debugging easier when the test only relies on one method, but it’s fine to have to call a getter in order to check that your add method worked or to call the add method in order to have something to get. Just remember that you’re testing the result of the method that the test case is for, not the methods it uses to ensure correct operation.
Pass-Off
In order to ensure that your server works properly and will work with the Client you design in the next project, we have designed a suite of tests to be used. These tests are found as part of a starter IntelliJ project for the server linked at the start of this document. You will use this test driver in order to help make sure you are ready to Pass-Off the server. As with all test drivers that we provide you with, we ask that you do not treat this as your only debugging tool, but only as a supplementary tool. The web api test page will be far more useful to you when working on the project to test your server.
 
Once you have ensured that your server passes the test driver, get on the queue for passoff and the TA’s will instruct you further.
Source Code / Test Case Evaluation
After you pass off your project with a TA, you should immediately submit your project source code for grading. Your grade on the project will be determined by the date you submitted your source code after passing off, not the date that you passed off. If we never receive your source code, you will not receive credit for the assignment. Here are the instructions for submitting your project source code:

In Intellij, navigate to the "Build" menu at the top of the screen and select "Clean Project" to remove auto-generated build files (if this option is not available, you can skip this step).
Create a ZIP file containing your whole project folder (not just the Java source files).
Submit your ZIP file on Canvas to the assignment titled Family Map Server Code Assignment.
To demonstrate that your test cases execute successfully, you should run your unit tests inside Intellij and take a screenshot of the successful test results displayed by Intellij. Please take one screenshot that shows the result of all of your tests passing. The tests used for the screenshot must be the same as the ones submitted in the Code assignment zip.
Submit your screenshot to the Family Map Server Test Run Assignment on Canvas. The screenshot is submitted separately from your code ZIP file.
Grading
Please refer to the following document for details on how your source/test code will be evaluated: Family Map Client/Server Code Expectations. See the course policies page on the CS 240 website for details on how much each of the different project components counts toward your overall course grade.


