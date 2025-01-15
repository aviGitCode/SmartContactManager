# Smart Contact Manager

Smart Contact Manager is a web-based application designed to efficiently manage and organize personal and professional contacts. It features a user-friendly interface, secure authentication, and CRUD operations for managing contacts.

# Features
User Authentication: Secure login and registration.
Contact Management: Add, update, delete, and view contacts.
Responsive Design: Works seamlessly across devices.
Role-Based Access: Different views for admin and regular users.
Search Functionality: Quickly find specific contacts.
Spring Boot Integration: Built using Java and Spring Boot framework.
Thymeleaf Templates: Dynamic web pages rendered using Thymeleaf.
MySQL Database: Persistent storage for user and contact data.

# Technologies Used
Frontend: HTML, CSS, Thymeleaf
Backend: Java, Spring Boot, Spring Security
Database: MySQL
Version Control: Git, GitHub


 # Installation and Setup
1 Clone the Repository:

bash
Copy code
git clone https://github.com/aviGitCode/SmartContactManager.git
cd SmartContactManager

2 Configure Database:

Create a MySQL database (e.g., smart_contact_manager).
Update the database configuration in application.properties:
properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/smart_contact_manager
spring.datasource.username=your-username
spring.datasource.password=your-password

3 Run the Application:

bash
Copy code
mvn spring-boot:run


4 Access the Application: Open your browser and navigate to:

arduino
Copy code
http://localhost:8080

# Contact
If you have any questions or feedback, feel free to reach out:

Email: avinashkhot351@gamil.com
GitHub: aviGitCode
