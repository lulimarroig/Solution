TravixTestApplication


The application runs as a Spring boot and is a console application.
It receives 5 mandatory input parameters: origin, destination, departureDate Date, returnDate Date, numberOfPassengers.

ASSUMPTIONS
For simplifying the test it's assumed that all inputs are in the correct format.
I used fake urls for the Supplier services and assumed that the suppliers provide APIs that retrieve the results in JSON format.
The result shows the flights by fare in ascending order

IMPLEMENTATION DETAILS
The main class of the application is TravixTestApplication.java it gets the parameters from the standard input and performs the request to each supplier, orders the results, converts them into JSON and retrieves them to the standard output.

A Factory Class: SupplierFactory.java is used to get the corresponding Supplier class that will then allow to perform the flight search for each of the suppliers (in this case it will be only two: CrazyAir and ToughJet).

The conversion into and from JSON format is made using Jackson.

The logging was done using log4j. I do not have experience with AspectJ, I created an @Aspect class but I used just the recommended allocated time and did not have enough time for finishing the AspectJ learning/implementation, so I decided to use log4j.

FUTURE MUSTS/IMPROVMENTS
The solution needs to be thoroughly tested. This service should be tested using Junit for testing each component and maybe also use another testing approach for integration testing and further coverage. In the scope of this solution no tests were included.
	*Suggestion: Add test cases using real services or simulate services.
Further error handling should be added, with more descriptive errors for the user and considering failure scenarios in the different components (border cases, connection issues, bad requests from the services, and so on).

