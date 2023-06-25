# AP2_SpeakEasy

The project in advanced programming2.

## Table of Contents
* [General Info](#Chat-Site-SpeakEasy)
* [Login Screen](#Login-Screen)
* [Registration Screen](#Registration-Screen)
* [Chat Screen](#Chat-Screen)
* [Features](#Features)
* [Future Development](#Future-Development)
* [Technologies Used](#Technologies-Used)
* [Getting Started](#Getting-Started)

# Chat App - SpeakEasy
This project is a web UI for a chat site. It allows users to register, log in, and chat with other users on the platform. The project includes three screens: a registration screen, a login screen, and a chat screen.

# Login Screen
The login screen is the main page of the website. Users can enter their username and password to log in or click a link to go to the registration screen. If the user enters the correct credentials, they will be taken to the chat screen. If the user enters incorrect credentials, they will see an error message.

Screenshot: 

<img width="214" alt="image" src="https://github.com/EliTotah/AP2_SpeakEasy/assets/117304079/7cff53d3-20de-4fec-a49d-9beca16cd08f">

# Registration Screen

**important:** 

**According to the instructions of the exercise, during the registration you can upload as a profile picture only pictures of small size. Uploading images that are too large will result in the registration failure (failed to fetch).**

The registration screen allows users to create a new account by entering their username, password, display name, and picture. A password verification field is also included to ensure that the user enters their password correctly. The registration screen has a similar design to the login screen, with a form for entering user details.

<img width="205" alt="image" src="https://github.com/EliTotah/AP2_SpeakEasy/assets/117304079/2c4b2de0-5441-444c-aa03-2b699bd5584a">

<img width="205" alt="image" src="https://github.com/EliTotah/AP2_SpeakEasy/assets/117304079/b2aa90fe-6ccb-4171-8e0e-9654dece1e5d">

<img width="203" alt="image" src="https://github.com/EliTotah/AP2_SpeakEasy/assets/117304079/0e29992d-2b9c-4b09-9848-cb6c9243319f">

<img width="203" alt="image" src="https://github.com/EliTotah/AP2_SpeakEasy/assets/117304079/d8ad982e-7490-42b9-8569-9c9f6bcbe015">

Validation
The registration and login screens have input field validation to ensure that all fields are filled out and the entered values are allowed. Passwords must be at least 8 characters long and a combination of characters and letters. Clear visual indications and messages are displayed to the user to guide them in entering the correct values.

Image and Password Verification
The logic for the image and password verification on the registration screen works correctly. The user can select an existing image from their computer and the password verification ensures that the entered password meets the required criteria.

To personalize your profile in our chat app, you have the option to upload a profile picture during the sign-up process. However, if you choose not to upload a custom image, the sign-up form will automatically assign a default profile image to your account.

To complete the sign-up form and use the default profile image, follow these steps:

1. Navigate to the sign-up page of our chat app.
2. Fill in the required information such as your name, email address, and password.
3. When you reach the profile picture section, you have two options:
   - **Option 1: Upload a Custom Profile Picture** - Click on the "Upload Picture" button and select an image file from your device. The uploaded image will replace the default profile picture.
   - **Option 2: Use the Default Profile Picture** - If you prefer to use the default profile picture, simply proceed with the form without clicking on the "Upload Picture" button. The default profile image will be automatically assigned to your account.

By providing a profile picture, you can personalize your chat experience and make it easier for others to recognize you. However, we respect your choice if you prefer to use the default image or update it at a later time.

<img width="203" alt="image" src="https://github.com/EliTotah/AP2_SpeakEasy/assets/118715083/f1311768-3f00-4878-883d-411be17ac2f3">


# Chat Screen
The chat screen is divided into two parts. The left part shows a list of chats with other users. For each chat, the user's picture, nickname, and last message received (along with the date/time it was received) are displayed. The right part of the screen shows the messages for the selected chat. Users can type messages and send them to the other user in the chat. Messages are displayed in real-time, so users can have a seamless conversation.

<img width="212" alt="image" src="https://github.com/EliTotah/AP2_SpeakEasy/assets/117304079/13ffeef2-8aba-4bcf-bec0-5d19f2999ec8">



<img width="203" alt="image" src="https://github.com/EliTotah/AP2_SpeakEasy/assets/117304079/b4047f72-65fe-4313-8791-5d120abe1a21">

The chat app allows users to add new contacts, which appear in the "in conversations" section on the left part of the screen. Clicking on a conversation from the left part opens the correspondence with that contact on the right part of the screen. The right part of the screen allows users to write and send messages, which appear in the correspondence screen with the corresponding contact and are saved in JavaScript.

# Settings Screen

A settings screen is included in the app, allowing users to modify relevant settings within the application. These settings are saved on the device and utilized accordingly. The following options are available:

Server Address: Users can edit the address of the server they are working with. This feature enables easy switching between different server configurations.

To change the server address for your app, follow these steps:

1. Open the app and navigate to the settings page.

2. Look for the option to change the server address or server URL.

3. In the field provided, enter the complete URL address in the following pattern:

http://10.0.2.2:5000

4. Make sure to include "http://" at the beginning of the address.

5. Replace 10.0.2.2 with the actual IP address or domain name of the desired server.

If applicable, modify the port number 5000 to match the appropriate port used by the server.

App Theme: The settings screen includes a button that allows users to change the theme of the app. By selecting a different theme, the colors of the application will change accordingly.

The edited settings are persistently stored on the device, ensuring that the chosen configurations remain in effect even when the app is closed and reopened.



<img width="208" alt="image" src="https://github.com/EliTotah/AP2_SpeakEasy/assets/117304079/dd581a28-eb46-45d9-9773-4603d7a5f265">

# Dark Mode 

<img width="209" alt="image" src="https://github.com/EliTotah/AP2_SpeakEasy/assets/117304079/17d5174b-5982-439d-9964-0cf1c807caf9">


                                                                                                                                                                         
<img width="205" alt="image" src="https://github.com/EliTotah/AP2_SpeakEasy/assets/117304079/4d8b84f7-cad2-430d-bb63-9a2ab089efbb">

# Features
* User registration
* User login
* Chatting with other users
* Real-time message updates
* Responsive design for Desktop devices

# API Explanation
The server exposes a RESTful API that operates relative to the current logged-in user, identified by a token. The following endpoints are part of the API:

http://localhost:5000/api/Chats: The address for accessing user chats.

* The GET operation retrieves all chats of the current user.

* The POST operation creates a new chat with the specified contact.

http://localhost:5000/api/Tokens: The address for generating a JWT (JSON Web Token) for the registered user.

* The POST operation creates a JWT for the user registered in the system.

http://localhost:5000/api/Users: The address for creating a new user.

* The POST operation creates a new user.

http://localhost:5000/api/Users/:id: The address for retrieving user details by identifier.

* The GET operation gives the details of the user with the specified identifier.

# Technologies Used
The project was created using HTML and CSS. The UI is designed to be simple and user-friendly. The website is fully responsive and works on desktop devices.

# Getting Started

running the server: 

cd Server

npm start

running the App:

insert to Android Studio and run the App

# Author
This project was created by [_@Ofirroth_](https://github.com/Ofirroth) and [_@EliTotah_](https://github.com/EliTotah).
feel free to contact us.

