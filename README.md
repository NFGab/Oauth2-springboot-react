# OAuth2 Demo Application

This project demonstrates **OAuth2 integration** using **Spring Boot** and **MySQL** for the backend, and **ReactJS** for the frontend.

---

## Prerequisites

Before running the application, make sure to review and adjust the following configurations in the `application.properties` file.

---

## Database Configuration (MySQL)

### Edit the database settings:
In `application.properties`, update these values if needed:

```properties
spring.datasource.url=jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/${DB_NAME:oauth_demo}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
```

- Replace `{DB_NAME}` with your actual database name.  
- Update your **MySQL username** and **password** accordingly.

---

## OAuth2 IDs and Secrets

The following settings control the OAuth2 client credentials used for Google and GitHub login:

```properties
spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID}
spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET}

spring.security.oauth2.client.registration.github.client-id=${GITHUB_CLIENT_ID}
spring.security.oauth2.client.registration.github.client-secret=${GITHUB_CLIENT_SECRET}
```

The syntax `${VAR:default}` means:
> Use the environment variable `VAR` if available; otherwise, fall back to the `default` value.

To configure these credentials, you have **three options**:

---

### **Option 1** - Use Environment Variables

Keep the environment-variable placeholders in `application.properties` and set environment variables in your system or IDE (see next section).

---

### **Option 2 - Use a `.env` File**

Create a `.env` file in your project root:

```env
DB_HOST=localhost
DB_PORT=3306
DB_NAME=oauth_demo
DB_USER=root
DB_PASS=password

GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret

GITHUB_CLIENT_ID=your-github-client-id
GITHUB_CLIENT_SECRET=your-github-client-secret
```


---

### **Option 3 — Hardcode Values**

For quick testing only, you can directly replace the variables with actual values in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/oauth_demo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.security.oauth2.client.registration.google.client-id=your-google-client-id
spring.security.oauth2.client.registration.google.client-secret=your-google-client-secret

spring.security.oauth2.client.registration.github.client-id=your-github-client-id
spring.security.oauth2.client.registration.github.client-secret=your-github-client-secret
```

---

## Setting Environment Variables in IntelliJ IDEA

1. Open **Run → Edit Configurations...**  
2. Select your Spring Boot run configuration (or create one).  
3. Locate the **Environment variables** field
4. Add your variables — either line by line or using the editor UI:

   ```
   DB_HOST=localhost
   DB_PORT=3306
   DB_NAME=oauth_demo
   DB_USER=root
   DB_PASS=password
   GOOGLE_CLIENT_ID=your-google-client-id
   GOOGLE_CLIENT_SECRET=your-google-client-secret
   GITHUB_CLIENT_ID=your-github-client-id
   GITHUB_CLIENT_SECRET=your-github-client-secret
   ```

5. Click **OK** and **Apply**, then run your application.

---

## Running the Application

###  Backend (Spring Boot)
1. Open the `oauth2demo` backend project in **IntelliJ IDEA** (This project was run on community edition).  
2. Run the Spring Boot application — it will start on **`http://localhost:8080`**.

### Frontend (React)
1. Open a terminal and navigate to the `oauth-demo-frontend` folder.  
2. Install dependencies (if not already):
   ```
   npm install
   ```
3. Start the frontend:
   ```
   npm start
   ```
   The React app will run on **`http://localhost:3000`**.

---


- Ensure that your MySQL database is created and accessible before running the backend.

