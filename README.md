# **API Gateway**

The **API Gateway** microservice is responsible for routing requests and aggregating data from the **Transactions** and **Account** microservices. This approach minimizes coupling between the two microservices and separates their responsibilities. The frontend will interact solely with the API Gateway, ensuring a unified entry point for all client requests. API documentation is available through [Swagger](https://app-api-gateway-1e49ad80c12d.herokuapp.com/swagger-ui/index.html#/).

To run locally, you need to clone the repository to your machine and run the project using a Maven-compatible compiler.


## **Continuous Integration and Deployment**
The project follows the Gitflow workflow with two main branches:

- **develop**: Active development happens here.
- **main**: Production-ready code.

**GitHub Actions** automates the CI/CD pipeline in the **.github/workflows**  directory

Every push to the main branch triggers a deploy to Heroku.

---

## **Features**
- **Create Account**
- **Create Transaction**
- **Get Customer Information**

If you want to test the APIs, you can use this [Postman Collection](https://drive.google.com/file/d/1LXyFl2Vm6OmnLVdpeqPfJ9iBvw2pSvmJ/view?usp=sharing) for both Local and Development environment


---

## **Technologies**
- **Java 21** and **Spring Boot**
- **JUnit** (Testing)
- **Swagger** (API Documentation)
- **GitHub Actions** (CI/CD)
- **Heroku** (Deployment)

---

## **Database**
The service does not use any database. However, the two underlying microservices do, and they each use their own databases.

![AssignmentDB drawio](https://github.com/user-attachments/assets/526042b0-4ae5-4344-8939-c4b94a3c6c45)
