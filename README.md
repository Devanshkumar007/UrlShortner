# **Choti URL**

### **Your one-stop solution for short, shareable links.**

Are you tired of long, unwieldy URLs? Choti URL (meaning "Short URL" in Hindi) is a robust and efficient URL shortening service built with modern technologies. It allows users to transform long links into short, manageable ones, perfect for sharing on social media, in emails, or anywhere else.

### **✨ Features**

* **URL Shortening**: Easily create compact, trackable short URLs.  
* **Custom Aliases**: Personalize your short URLs with custom, human-readable names (e.g., choti.myproject).  
* **Click Analytics**: Track the number of clicks on each of your shortened links in real-time.  
* **Search & Manage**: Find and manage your shortened URLs by ID, short URL, or long URL.  
* **URL Redirection**: Seamlessly redirect users from the short URL to the original destination.  
* **Built-in Expiration**: Each short URL is set to expire after a certain period to keep the database clean and efficient.

### **💻 Technologies Used**

This project is a full-stack web application with a strong, scalable backend and a simple, intuitive frontend.

# **Backend**

* **Spring Boot**: The core framework for building the RESTful API.  
* **Java 17**: The programming language for the backend logic.  
* **Spring Data JPA & Hibernate**: For efficient data persistence and object-relational mapping.  
* **MySQL**: The relational database used to store URL mappings.

#### **Frontend**

* **HTML**: The structure of the web application.  
* **Tailwind CSS**: A utility-first CSS framework for a responsive and modern design.  
* **JavaScript**: For all client-side logic and API communication.

### **🚀 Getting Started**

To get a local copy of this project up and running, follow these simple steps.

#### **Prerequisites**

* Java Development Kit (JDK) 17 or higher  
* Apache Maven  
* MySQL Database (version 8.0 or higher)

#### **1\. Clone the repository**

git clone \[https://github.com/devanshkumar007/urlshortner.git\](https://github.com/devanshkumar007/urlshortner.git)  
cd urlshortner

#### **2\. Database Setup**

Create a new MySQL database named urlshortner. The application is configured to connect to this database with the default username root and password password. You can update these credentials in src/main/resources/application.properties if needed.

#### **3\. Run the Application**

You can use the Maven wrapper to run the application directly from the command line.

./mvnw spring-boot:run

The application will start on port 9191\. You can access the web interface at http://localhost:9191.

### **🔗 API Endpoints**

The backend provides a set of RESTful API endpoints for interaction.

| Endpoint | Method | Description |
| :---- | :---- | :---- |
| /api/url/create | POST | Creates a new short URL. |
| /api/url/long | POST | Finds a short URL from a given long URL. |
| /api/url/short/{shortUrl} | GET | Finds a URL mapping from a given short URL. |
| /api/url/{id} | GET | Finds a URL mapping by its unique ID. |
| /api/url/all | GET | Retrieves a list of all URL mappings. |
| /api/url/delete/{id} | DELETE | Deletes a URL mapping by its unique ID. |
| /s/{shortUrl} | GET | Redirects to the original long URL and increments clicks. |

### **Contributing**

Contributions are what make the open-source community an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project  
2. Create your Feature Branch (git checkout \-b feature/AmazingFeature)  
3. Commit your Changes (git commit \-m 'feat: Add some AmazingFeature')  
4. Push to the Branch (git push origin feature/AmazingFeature)  
5. Open a Pull Request

### **License**

Distributed under the Apache License 2.0. See LICENSE for more information.