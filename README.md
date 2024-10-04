# healthcare_project
❤️Healthcare Project❤️

👉Project Overview
The Healthcare Project is a Spring Boot-based web application designed to manage and streamline the interactions between doctors, patients, and administrators in a healthcare system. The project is built as a modular system with separate functionalities for doctors, patients, and admins, providing a user-friendly experience for both healthcare professionals and patients.

👉Key Features
Doctor Module:
Doctors can create and manage their profiles, set availability, and interact with patients through appointment bookings.

Patient Module:
Patients can sign up, search for doctors, book appointments, and view their medical records or appointment history.

Admin Module:
Admins can manage the overall system, including user management (both doctors and patients) and maintaining the appointment system's health.

Authentication & Authorization:
The project uses JWT (JSON Web Token) for secure authentication and role-based authorization to ensure that users (patients, doctors, and admins) can access only their respective features.

👉Technology Stack:
-Backend: Java 17, Spring Boot 3, Spring Security, JWT for authentication
-Frontend: Angular (integration planned)
-Database: MySQL
-Other Tools: Lombok for simplifying boilerplate code, ModelMapper for DTO conversion

👉Modules
-Doctor Module:
-Profile management (create, update)
-Set availability for appointments
-View patient bookings and interact with patients
👉Patient Module:
-Register and log in securely
-Search doctors based on specialties or location
-Book, view, and cancel appointments
-View appointment history
👉Admin Module:
-Manage user roles and permissions
-Oversee the appointment system
-Admin dashboard for system monitoring
👉Security
-Spring Security is implemented with JWT-based authentication to secure endpoints.
-Users are assigned roles (Doctor, Patient, Admin) dynamically during the signup process.
-Role-based access ensures that only authorized users can perform specific actions.

👉API Endpoints:

/api/doctors - Doctor-related operations
/api/patients - Patient-related operations
/api/admin - Admin operations
/api/auth/signup - User registration (dynamic roles)
/api/auth/signin - User login and JWT token generation

▶️Future Enhancements
👉Frontend Integration:
An Angular-based frontend is in progress to provide a user-friendly interface for doctors, patients, and admins.

-Notifications:
Email and SMS notifications for appointment confirmations and reminders.

-Reports & Analytics:
Adding admin reports to visualize the healthcare system's performance.

💠Contributing
We welcome contributions from the community! If you'd like to improve this project or report an issue, feel free to submit a pull request or open an issue on GitHub.
