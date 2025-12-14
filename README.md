# 🏗️ Project and Task Management App

## 📌 How to set up

1. **Clone the repository**:
   ```bash
   git clone <repository-url> # will add project url after publishing
   cd backend
   ```

2. **Configure the database**: Start required infrastructure (PostgreSQL + Redis). Install Docker Desktop or Docker Engine first, if needed.
   ```bash
   docker compose up -d
   ```

3. **Configure application properties**

   Open: `src/main/resources/application.properties`

   Fill in the following required configuration values:
   ```properties
   # AWS credentials
   cloud.aws.credentials.access-key=YOUR_AWS_ACCESS_KEY
   cloud.aws.credentials.secret-key=YOUR_AWS_SECRET_KEY

   # Mail configuration
   spring.mail.host=YOUR_MAIL_HOST
   spring.mail.username=YOUR_MAIL_USERNAME
   spring.mail.password=YOUR_MAIL_PASSWORD

   # Google OAuth2
   spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
   ```

4. **Build the project**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## 📌 Overview

Project and Task Management App is a **modern web application** designed to help teams efficiently manage projects and tasks. It provides **a rich feature set** for collaboration, security, and seamless project tracking.

### 🖥️ **Tech Stack**

- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Database**: Postgres + flyway(for migrations), Redis, S3
- **Testing**: JUnit, Mockito

### 🔐 **Authentication & Security**

- OAuth2 authentication via **Google**
- Standard authentication with **JWT**
- **Role-based access control (RBAC)** with **Spring Security**
- **Email verification** for account activation
- **Password reset functionality**

### 🚀 **Core Features**

#### 📌 **Project Management**

✅ Create, update, and track projects  
✅ Advanced filtering & sorting for projects  
✅ **Project Owner Panel** for full project control  
✅ **Project Hierarchy and Access Level control** (Owner, Member)

#### 📝 **Task Management**

✅ Assign tasks, update progress, set deadlines  
✅ Commenting & discussion on tasks  
✅ Task notifications

#### 👥 **Project-Member Interactions**

✅ **Invitations & Applications**: Invite users or allow them to apply  
✅ **Member Management**: Approve/reject applications, remove members  
✅ **Team collaboration**: Assign responsibilities within projects

#### 📢 **Notifications & Communication**

✅ **Built-in Notification System**: In-app notifications for updates  
✅ **Email Notifications**: Stay informed via email alerts  
✅ **Activity Logs**: Keep track of project changes




