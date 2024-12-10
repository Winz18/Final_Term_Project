# WebDev Course - Final Term Project

## Introduction

**Final_Term_Project** is a web application developed using Spring Boot in my uni final term project. The project's goal is to provide a data management platform with a user-friendly interface and support for business operations.

The project leverages modern technologies such as:
- **Spring Boot** (Starter Web, Data JPA, Thymeleaf)
- **Database**: MySQL
- **Front-end**: Thymeleaf combined with HTML and CSS.

## Features

- **User Management**: Login, registration, and role-based access control.
- **Product Management**: Create, read, update, and delete product information.
- **Order Management**: Track order status and details.
- **User Interface**: Display information and enable seamless interaction.

## Project Structure

```plaintext
Final_Term_Project/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── hcmute/uni/  # Java files (controller, service, repository, model)
│   │   ├── resources/
│   │   │   ├── static/      # CSS, JS
│   │   │   ├── templates/   # Thymeleaf templates
│   │   │   └── application.properties
│   └── test/                # Test files
│
├── pom.xml                  # Maven configuration file
└── README.md
```

## Setup and Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Winz18/Final_Term_Project.git
   cd Final_Term_Project
   ```

2. Install Maven dependencies:

   ```bash
   mvn clean install
   ```

3. Configure the database in `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

5. Access the application at [http://localhost:8080](http://localhost:8080).

## Contributing

We welcome contributions from the community. To contribute:
1. Fork the repository.
2. Create a new branch: `git checkout -b feature/new-feature`.
3. Commit your changes: `git commit -m "Add new feature"`.
4. Push to the branch: `git push origin feature/new-feature`.
5. Open a pull request on GitHub.

## License

This project is currently unlicensed. If you use the source code, please acknowledge its origin.

