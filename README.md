# OTP-generator
OTP generator using spring boot, redis, kafka.

Iteration 1: Basic OTP Generation
	1	Set Up Your Project:
	◦	Create a Spring Boot project with dependencies: Spring Web, Spring Data JPA, H2 Database (for testing), and Lombok.
	◦	Define a User entity with fields: id, mobileNumber, otp, and otpCreatedTime.
	2	API for OTP Generation:
	◦	Create a POST API /generate-otp to accept the mobile number and return a generated OTP.
	◦	Generate a random 6-digit OTP in the service layer.
	◦	Save the OTP and creation timestamp in the database.
	3	Test with Postman:
	◦	Send a mobile number to /generate-otp.
	◦	Verify the OTP is generated and stored in the database.

Iteration 2: Resend OTP
	1	Add Caching with Redis:
	◦	Add Redis dependencies to your project.
	◦	Configure Redis in application.properties.
	◦	Use Redis to cache OTPs with a 10-minute TTL.
	2	API for Resending OTP:
	◦	Create a POST API /resend-otp.
	◦	Check Redis for the cached OTP. If found, return it; otherwise, fetch from the database.
	◦	If no OTP exists or 10 minutes have passed, return an error message prompting regeneration.
	3	Test Resend Logic:
	◦	Generate an OTP and call /resend-otp within 10 minutes.
	◦	Verify the cached OTP is returned.
	◦	Call after 10 minutes to test the cache expiration.

Iteration 3: Regenerate OTP
	1	API for Regenerating OTP:
	◦	Create a POST API /regenerate-otp.
	◦	Generate a new OTP and update both the Redis cache and the database.
	2	Test Regeneration Logic:
	◦	Generate an OTP, call /regenerate-otp, and verify the new OTP replaces the old one in both the cache and the database.

Iteration 4: Introduce Kafka for Message Handling
	1	Add Kafka Dependencies:
	◦	Add Kafka dependencies to your project.
	◦	Configure Kafka topics and consumers in application.properties.
	2	Asynchronous OTP Handling:
	◦	Publish OTP generation and resend requests to Kafka.
	◦	Create a consumer that listens for these requests and processes them.
	3	Test Kafka Integration:
	◦	Verify that OTP generation and resend events are published to Kafka and processed correctly.

Iteration 5: Enhance Features
	1	Error Handling:
	◦	Add proper error messages for invalid mobile numbers, expired OTPs, etc.
	2	Rate Limiting:
	◦	Prevent abuse by limiting the number of OTP requests per mobile number.
	3	Additional Fields:
	◦	Extend the user model with fields like email for added functionality.

Iteration 6: Deployment
	1	Dockerize Your Application:
	◦	Use Docker to containerize the Spring Boot app, Redis, and Kafka.
	2	Deploy to a Cloud Platform:
	◦	Deploy the project to AWS, Azure, or Google Cloud.
