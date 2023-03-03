FLEE MANAGMENT SYSTEM


1. Introduction
This project aims to implement a flee management platform for electric scooters. It is required to make 
the project in Java while using sockets and threads for communication and concurrency. The project 
consists of two parts: Server and client. The server must allow users to book and reserve scooters 
while also tracking the places of all the scooters and calculating the rewards accordingly. The world is 
shown by 20 X 20 grid map. Though, this doesn’t reflect the real world, it is good enough to 
implement the basics of the app. The server also needs to register users, authenticate them, update their 
information and save the information inside a database or a local storage unit. The client’s job is to 
provide an interface to communicate with the user and send the relevant information to the server. For 
the communication, the app is using TCP.

    1.1 Compiling and Required Libraries:

    The project is compiled with JavaSE-18 (jdk-18.0.1.1) by Eclipse. For communiation java.io, and java.net 
    library is utilized. java.time and java.util is also used to create the project.

2. Functionalities of The System

    2.1. Register Users:
This module is responsible for the registration of the user. At first the client module takes the 
information from the user by its UI. The information is then sent to the server by the TCP connection.
The server needs username and password to register a user. ClientHandler uses fileManager to register 
the user and save it in a local storage. The File Manager uses ReadWriteLock to prevent concurrency 
problems. The fileManager gets the write lock and starts saving the new created user into its cache and 
local storage unit. It calls its save function to save the information in a file. Saving the function is 
outside of the lock to prevent unnecessary waiting. Inside the lock, adding the client into the ArrayList 
is actualized.
After then the Update User Information module is used to fill the user information to the server and 
the server saves the information in its local database. Update system also uses the ReadWriteLock.
    
    2.2. Authenticate Users:
This module is responsible for the authentication of the user. The client side of the module 
takes the username and the password of the user and sends it to the server. The server evaluates it by 
comparing the usernames and passwords to the sent information. It sends back a success or a failure
message to the client side. The client side notifies the user. 
    
    2.3. Update Users Information:
This module is responsible for the update protocol of the app. The client side of the application 
sends the username, password, variable number to be updated and new value to the server. The server 
first authenticates the user and then updates the wanted user object. The update function is inside the 
functionManager class. Therefore, before updating the value, write lock is acquired and then the 
values are updated.
After the update, the server saves the information into a file. 
    
    2.4. Create and Show Rewards:
This module is responsible for creating rewards and sending that information to the clients. 
The rewards are calculated according to the old and new locations of the scooter. The world split into 
sections. In the current module, every section is considered a square with 5 unit edge length.
calculateScootersPerSection module calculates the number of scooters for every section and stores it in 
an ArrayList. If the older location of the scooter is in a section that has more scooters than the new 
section of the scooter, the reward is calculated. To calculate the reward, the number difference is 
calculated and multiplied by a constant called REWARDS_PER_UNIT. With this technique the 
reward is calculated. The module sends the arraylist that contains number of scooters for every 
section.The module runs on the server side and in a different thread. Because it runs in a different 
thread it is needed to know when to calculate the rewards again. Therefore, the class RewardSystems 
gets condition and lock as initial arguments. After the first calculation of the rewards the 
RewardSystem class starts waiting by calling condition.await().The ScooterManager Class also gets 
the condition and lock as initial arguments. When the ClientHandler modules ask from 
ScooterManager to reserve scooter, park scooter, add scooter or remove scooter. The ScooterManager 
class awakes all the threads by calling condition.signalAll(). By using condition, waiting when the 
calculation is unnecessary is ensured and communication between different threads are provided.
    
    2.5. Reserve Scooter:
This module is responsible for reserving a scooter for a given client. Client sends its username, 
password, and unique identifier of the scooter that he wants to reserve. Then the server reserves the 
scooter if there is no lock on the scooter. The server sends back a success or a failure message. Scooter 
Class has a lock that blocks multiple reservation. Whenever the ScooterManager class tries to reserve 
a scooter, the scooter class first tries to get the lock and then reserve it. Not to block the flow tryLock() 
function is used. If somebody reserves the scooter earlier, the function returns false and sends the 
client a failure message.
    
    2.6. Park Scooter:
This module is responsible for parking the scooter and removing the reservation from the 
scooter. The client sends its username, password, and the unique identifier of the scooter that he 
reserved among with the location of the scooter. The server checks if the user reserved the scooter. 
Then sends back a success or failure message. If it is a success message, the server also sends back the 
cost of the trip and the prize of the trip. 
    
    2.7. Get Scooter List:
This module is responsible for sending the scooter List to the client or admin. The client sends 
its username and password for authorization. The ClientHandler uses its scooterManager to get all the 
scooters as ArrayList. ScooterManager class is implemented by using a ReadWriteLock to prevent 
concurrency problems. To return the scooter list, it gets the reader lock and returns the list of scooters. 
    
    2.8. Get User Info:
This module is responsible for sending the user information to the client. It first receives 
client’s username and password for authorization. Then the clientHandler class calls fileManager to 
get the user. The FileManager class is implemented by using a ReadWriteLock to prevent concurrency 
problems. To return the chosen user, it first gets the read lock and then search through the user list to 
find the wanted user. Then it returns the user to the clientHandler and unlocks the read lock.
    
    2.9. Add Scooter:
This module is responsible for adding a scooter to the existing list. To use this module admin
authorization is required. To use this module the client has to have an admin authentication. This 
admin sends the new scooters UID (unique identifier) and its location. The client handler gets the 
information through TCP and calls the add method inside the ScooterManager class. The 
scooterManager uses ReadWriteLock. It acquires the write lock and starts checking the scooter’s id
against the current ones. This ensures that there isn’t more than one scooter with the same id. If the 
admin doesn’t want to bother with choosing a unique id, it can ask from the system to choose a 
random one. The system uses an AtomicInteger to generate the UID. This prevents any concurrency 
issues that can be caused by UID generation. After generating the UID the scooterManager saves the 
new scooter into the list and releases the lock. After releasing the lock, the scooterManager saves the 
list into a file. 
    
    2.10. Remove Scooter:
This module is responsible for removing a scooter from the system. To use the module admin 
authorization is required. It takes the UID of the scooter from the admin and removes the scooter while 
considering the concurrency. It does this by using the lock class. ScooterManager first acquires the 
write lock and then removes the scooter from the list. After the release of the lock the scooterManager 
saves the updated list into a file.

3. Communication System’s Explanation:
Every message sent by client or server must start with a message type. The message type is a 
Byte object. There are 14 message types as shown below. The message types and their use cases are 
explained below. All message types are stored in Messages class as static final Byte objects. Every 
message is written to the data stream by DataOutputStream and every message is read from the stream 
by DataInputStream.
    



    3.1 Non Specific Message Types:
This message types can be used by every client in the system.
        
        3.1.1 Update Message Type (cmd_update) :
This message type is used to update user information. To select the wanted variable, the client 
also sends the variable type. The variable types are static Byte object that are used distinguish between
variables. The static Byte objects used for variable selection are located inside the User class.Then 
according to the wanted variable type the new value is send. If its type is Boolean, Boolean type object 
is sent by TCP. If it is an Integer, the integer type object is sent by TCP. After sending the new value, 
the username and password of the client is sent as a String. The String message used is in the format: 
“username;password”. The separator used in the string is “;”.However, if the wanted variable is type 
of String, a special String message is made and sent by TCP. The special string message is in the 
format: “newVariable;username;password” The receiver function splits the string by using a split 
function. The separator used in the string is: “;”. The server gets the message and tries to change the
variable. According to its success, it responds back with the message type: Success or Fail. Variable 
Types: Name, surname, phone number, birthdate, e-mail, username, password, total rides, card, is 
riding, has payment info.
        
        3.1.2 Authorization Message Type (cmd_auth) :
This message type is used to authorize client to use the app. It gets the username and password 
of the client. Because both of the variables are string, the client combines them and send them as a 
special string message. The server responds back with a fail or success type of message.
        
        3.1.3 Register Message Type (cmd_register) :
This message type is used to register a user and save it in the local storage. The client sends 
username and password of the client that is wanted to be registered. The server checks the list if there 
is a user with the same username. If not, the server registers the user and sends back a success type 
message. If there is a user with the same username, the server sends back a fail message type.
        
        3.1.4 Get User Message Type (cmd_get_user) :
This message type is used to get user information from the server. The client sends its 
username and password. The server checks if the user is authorized. If the user is authenticated, it 
sends back the user’s information with a success type of message in the front. If not, the server sends 
back a fail message type.
        
        3.1.5 Get Scooter Message Type (cmd_get_scooter):
This message type is used to get the list of scooter. The client sends its username and 
password for authorization. If authorized the server sends back a success message type, number of 
scooters and scooter list. If not authorized the server sends back a fail message type.
        
        3.1.6 Reserve Message Type (cmd_reserve):
This message type is used to reserve a scooter. The client sends the UID of the wanted scooter 
and his username and password for authorization. If the server authorizes, it tries to reserve the
scooter. In a success, it returns a success message type. In other scenarios it returns a fail message 
type.
        
        3.1.7 Park Message Type (cmd_park):
This message type is used to park the scooter. The client sends the UID of the scooter that he 
reserved. The location of the scooter and his username and password. The server checks authorization 
and checks if the scooter is reserved by the user. It updates the location of the scooter and calculates 
the price of the trip according to the time. It returns a success message type along with the price and
the price or a fail message type.
        
        3.1.8 Get Rewards Message Type (cmd_get_rewards):
This message type is used to get the list of the rewards. The client sends its username and 
password for authentication. If it is authorized by the server, the server sends back the success 
message type number of x coordinate sections, number of y coordinate sections and list of number of 
scooters per section. Else it sends back the fail message type.
        
        3.1.9 Success Message Type:
This message type is used to indicate that the wanted function is successfully operated.
        
        3.1.10 Fail Message Type:
This message type is used to indicate that the wanted function is failed. One of the failure reasons
might be the unauthorized user. 



    3.2 Admin Specific Message Types:
This message types are specific to administration usage. Normal clients can not and should not use
them.
        
        3.2.1 Admin Authorize Message Type (admin_auth):
This message type is used to authorize the administrator. The admin sends its username and password. 
The server checks authorization and if it is successful it sends success message type. Else it sends fail 
message type.
        
        3.2.2 Admin Get Scooter Type (admin_auth):
This message type is used to get the existing scooters in the system. The system logic of this 
message type is same as the get scooter message type. The only difference is in the authorization: The 
server checks admin authorization rather than client authorization. 
        
        3.2.3 Add Scooter Message Type (admin_add_scooter):
This message type is used to add scooter to the system. The admin sends the information about 
the scooter that is going to be added. It also sends its username and password for authorization. The 
server tries to authorize. Then it makes sure that there is no scooter with the same UID as the sent 
scooter. It adds the scooter to the system. In a success situation the server sends back a success 
message type and the UID of the scooter.
        
        3.2.4 Remove Scooter Message Type (admin_remove_scooter):
This message type is used to remove scooters from the system. The admin sends the 
UID of the scooter and its username and password. The server checks authorization. The server checks 
if there is a scooter with this UID and removes it if there is no user using it. In success, the server 
sends back a success type message. In other situation, it sends back a fail message type.
        
        3.2.5 Admin Update Message Type (admin_update):
This message type is used to update the admin information. The admin variables include: 
name, surname, password. The admin sends the variable type, the new value of the variable and its 
username and password. The server checks authorization and updates the information. If it is a success
the server sends back a success message type. Else it sends back a fail message type.
Variable Types: Name, surname, username, password





4. Class Explanations:
    
    4.1 User Class Explanation:
User class contains the information about the user such as: name, surname, username,
password, birthdate, card information, phone number, e-mail, balance, rewards, reserved scooters UID 
and location.This class is the base for the clients. 
    
    4.2 Card Class Explanation:
This class contains the information about the credit or debit card. It contains the card number, 
card expiration date and card CVV. Before setting the card number it checks the validity of the card 
number by using Luhn algorithm.
    
    4.3 Scooter Class Explanation:
This class contains the information about the scooter. It contains the location, UID, reservation 
time, username and password of the client who reserved the scooter. The classes uses ReentrantLock 
to prevent concurrency problems.The reserve function reserves the scooter to a client. This function
uses the lock to solve the multiple reservations. Static AtomicInteger is used to generate unique 
identifiers to the scooters.
    
    4.4 FileManager:
This class is created to handle tasks based on saving, adding, removing, updating the user, and 
reading the user information. This class uses ReadWriteLock to solve concurrency problem. Before 
every update, removal and addition, the class acquires the write lock. Before every read operation such 
as finding the user inside the list, it acquires the read lock. The locks are released before the save 
operation. This class is also responsible for the authorization of the user. 
    
    4.5 ScooterManager:
This class is created to handle the tasks based on saving, adding, removing, updating the 
scooter, and reading the scooter information. This class uses ReadWriteLock to solve concurrency 
problem. Before every update, removal and addition, the class acquires the write lock. Before every 
read operation such as finding the scooter inside the list, it acquires the read lock. The locks are 
released before the save operation. This class is also responsible for the reservation and parking of the 
scooters.
    
    4.6 ClientHandler:
This class is responsible for handling the client’s and admin’s requests. It implements the 
runnable interface. As initial arguments it gets the socket, fileManager, scooterManager and 
rewardSystem from the main menu. The reason to get the modules is to create a concurrent system. 
Every client has its own ClientHandler. Those clientHandlers shares the managers and reward system.
This is necessary to keep track of the different aspects of the system. ClientHandler is responsible for 
the every functionality that the system has such as: Registiration, authorization, addition of scooters, 
removal of scooters, getting user information, getting scooter list, updating scooters, updating users, 
updating admin, saving user list, saving scooter list, reservation and parking systems …
    
    4.6 UI:
This class is responsible for providing a user interface. The client class uses UI module to get 
necessary information from the user. 
    
    4.7 Client:
This class is responsible for client-based tasks. It uses UI class to implement a user-friendly
interface. The class has socket that provides the communication between the client and the server. 
When the class is initialized, it tries to connect to the server. It tries connecting for every second till it 
succeeds. The class needs authorization to function. Therefore, every user needs to register or login to 
the system. The class provides the transition between the server and the client by using TCP. It’s 
functionalities can be listed as updating information, listing scooters, reserving a scooter, parking the 
scooter, seeing the rewards, moving the client, showing the user information. This class communicates 
with the server and provides the listed functionalities.
    
    4.8 Admin Client:
This class is responsible for admin-based tasks. The class has socket that provides the 
communication between the client and the server. When the class is initialized, it tries to connect to the 
server. It tries connecting for every second till it succeeds. The admin has to login to the system to be 
able to use it. The class provides the interface for authorization, adding scooters, removing scooters
and updating admin information. It uses Admin class to save its information. 
    
    4.9 Messages:
This class is responsible for distinguishing the message types and UI options. It is created to 
decrease the code readability. Thanks to the class the developer doesn’t have to search for the specific 
number that is associating with the functionalities.
    
    4.10 Server Main:
This class is main class for the server. It creates ScooterManager, FileManager, 
RewardSystem. Then it creates a server socket and receives new connections from the clients. For 
every connection it creates a new ClientHandler object and runs it in a different thread. 
    
    4.11 Client Main:
This class is main class for the client. It creates a new client object and ui object. The class is 
responsible for combining the user interface with the functionality. After the client object’s
connection, the class calls UI’s showMainMenu method to communicate with the user. The user 
chooses between register and login options. After the authorization, it shows the menu for the 
functionalities. They can be listed as updating information, listing scooters, reserving a scooter, 
parking the scooter, seeing the rewards, moving the client, showing the user information. After the 
user chooses the functionality through the UI interface provided by UI class, it calls the client object to 
actualize the functionality. 
    
    4.12 Admin Main:
This class is main class for the admin. It creates a new admin client object and ui object. The 
class is responsible for combining the user interface with the functionality. After the client object’s
connection, the class calls UI’s showAdminMenu method to communicate with the user. The user has 
to login to the system to use admin privileges. After the authorization, it shows the menu for the 
functionalities. They can be listed as updating information, adding scooters, removing scooters and 
listing scooters. After the user chooses the functionality through the UI interface provided by UI class, 
it calls the admin client object to actualize the functionality. 