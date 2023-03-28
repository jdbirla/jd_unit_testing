# Java Testing Made Easy: Learn to write Unit, Integration, E2E & Performance Tests
![image](https://user-images.githubusercontent.com/69948118/227894970-cf6d86e1-c655-4999-a9ff-7c2a6f17d90a.png)
![image](https://user-images.githubusercontent.com/69948118/227895013-e28f1d1b-0c1b-454b-b677-db72bfaca025.png)
![image](https://user-images.githubusercontent.com/69948118/227895086-4563be2a-21b2-4993-b195-56b1ad10aeb9.png)
![image](https://user-images.githubusercontent.com/69948118/227895103-b4570c9c-1b48-4b84-8515-4a9a46821f06.png)
![image](https://user-images.githubusercontent.com/69948118/227897253-b23082b0-0ab3-42f3-add1-f9b02b6ed785.png)

## AsertJ
https://assertj.github.io/doc/
![image](https://user-images.githubusercontent.com/69948118/227898864-a7603582-b4f1-4234-93a5-980dba545859.png)
![image](https://user-images.githubusercontent.com/69948118/227899288-1cda6555-606b-410f-925c-9d2ce1a7303c.png)
![image](https://user-images.githubusercontent.com/69948118/227900065-3f04822d-5679-4320-99d7-fd63c2477d77.png)
![image](https://user-images.githubusercontent.com/69948118/227900416-7ce964aa-6198-405a-b7dd-c976c667d728.png)
![image](https://user-images.githubusercontent.com/69948118/227900755-0a220651-3bc7-40fa-807d-7cbc02218ca3.png)
![image](https://user-images.githubusercontent.com/69948118/227901476-e2b99ea2-d833-4320-ad14-ea26015debff.png)

## Junit 5 features
https://junit.org/junit5/docs/current/user-guide/
![image](https://user-images.githubusercontent.com/69948118/227905284-84cff68f-7e0d-4b6c-8ba8-501bf8c07a20.png)
```java
package com.sivalabs.jtme;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LifecycleCallbacksDemoTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("--------beforeAll()---------");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("--------afterAll()---------");
    }

    @BeforeEach
    void setUp() {
        System.out.println("--------setUp()---------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("--------tearDown()---------");
    }

    @Test
    void test1() {
        System.out.println("------------test1------------");
    }

    @Test
    void test2() {
        System.out.println("------------test2------------");
    }
}
```
```java
package com.sivalabs.jtme;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class TestInstanceLifecycleDemoTest {

    @Test
    @DisplayName("Given a person to save with existing email then it should throw Exception")
    @Tag("unit")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void test1() throws InterruptedException {
        System.out.println("test1:"+this.hashCode());
        Thread.sleep(3000);
    }

    @Test
    @Tag("integration")
    @Disabled
    void test2() {
        System.out.println("test2:"+this.hashCode());
    }

}
```
```java
package com.sivalabs.jtme;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

class ConditionalTestExecutionDemoTest {

    @Test
    @EnabledOnOs(OS.MAC)
    void shouldRunOnlyOnMacOS() {
        System.out.println("This is a test running on MacOS");
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void shouldRunOnlyOnWindows() {
        System.out.println("This is a test running on Windows");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void shouldRunOnlyOnJre17() {
        System.out.println("This is a test running on Java 17");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_21)
    void shouldRunOnlyOnJre21() {
        System.out.println("This is a test running on Java 21");
    }

}
```
![image](https://user-images.githubusercontent.com/69948118/227911511-1de143cc-864b-4220-b05f-2b50cd095c12.png)
```java
package com.sivalabs.jtme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PersonServiceParameterizedTests {
    PersonService personService;

    @BeforeEach
    void setUp() {
        PersonRepository repo = new PersonRepository();
        personService = new PersonService(repo);
    }

    @ParameterizedTest
    @CsvSource({
            "Siva1,siva1@gmail.com",
            "Siva2,siva2@gmail.com",
            "Siva3,siva3@gmail.com",
    })
    void shouldCreatePersonUsingCSVSuccessfully(String name, String email) {
        Person person = personService.create(new Person(null, name, email));
        assertNotNull(person.getId());
        assertEquals(name, person.getName());
        assertEquals(email, person.getEmail());
    }

    @ParameterizedTest
    @MethodSource("personPropsProvider")
    void shouldCreatePersonSuccessfully(String name, String email) {
        Person person = personService.create(new Person(null, name, email));
        assertNotNull(person.getId());
        assertEquals(name, person.getName());
        assertEquals(email, person.getEmail());
    }

    static Stream<Arguments> personPropsProvider() {
        return Stream.of(
                arguments("Siva", "siva@gmail.com"),
                arguments("Prasad", "prasad@gmail.com")
        );
    }

    @ParameterizedTest
    @MethodSource("personObjectsProvider")
    void shouldCreatePersonWithObjectInputSuccessfully(Person personInput) {
        Person person = personService.create(personInput);
        assertNotNull(person.getId());
        assertEquals(personInput.getName(), person.getName());
        assertEquals(personInput.getEmail(), person.getEmail());
    }

    static Stream<Arguments> personObjectsProvider() {
        return Stream.of(
                arguments(new Person(null, "Neha", "neha@gmail.com")),
                arguments(new Person(null, "Yuvaan", "yuvaan@gmail.com"))
        );
    }
}
```
- junit-platform.properties
```
junit.jupiter.execution.parallel.enabled = true
junit.jupiter.execution.parallel.mode.default = concurrent
```
- Configuration parameters to execute top-level classes in parallel but methods in same thread
```
junit.jupiter.execution.parallel.enabled = true
junit.jupiter.execution.parallel.mode.default = same_thread
junit.jupiter.execution.parallel.mode.classes.default = concurrent
```
- Configuration parameters to execute top-level classes sequentially but their methods in parallel
```
junit.jupiter.execution.parallel.enabled = true
junit.jupiter.execution.parallel.mode.default = concurrent
junit.jupiter.execution.parallel.mode.classes.default = same_thread
```

# Mockito
```java
package com.sivalabs.jtme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    PersonRepository repo;

    @InjectMocks
    PersonService personService;

    // Without using @ExtendWith(MockitoExtension.class)
    /*
    @BeforeEach
    void setUp() {
        repo = Mockito.mock(PersonRepository.class);
        personService = new PersonService(repo);
    }
    */

    @Test
    void loginSuccess() {
        //Arrange
        Person person = new Person(1L, "Siva", "siva@gmail.com", "siva123");
        when(repo.findByEmailAndPassword(eq("siva@gmail.com"), anyString()))
                .thenReturn(Optional.of(person));
        //Act
        String token = personService.login("siva@gmail.com", "siva123");

        //Assert
        assertThat(token).isNotNull();
    }

    @Test
    void loginFailure() {
        when(repo.findByEmailAndPassword("siva@gmail.com", "siva123"))
                .thenReturn(Optional.empty());

        String token = personService.login("siva@gmail.com", "siva123");

        assertThat(token).isNull();
    }

    @Test
    void findByEmail() {
        Person person = new Person(1L, "Siva", "siva@gmail.com", "siva123");
        when(repo.findByEmail("siva@gmail.com")).thenReturn(Optional.of(person));

        Optional<Person> optionalPerson = personService.findByEmail("siva@gmail.com");

        assertThat(optionalPerson).isPresent();
        assertThat(optionalPerson.get().getName()).isEqualTo("Siva");
        assertThat(optionalPerson.get().getEmail()).isEqualTo("siva@gmail.com");
    }

    @Test
    void shouldCreatePersonSuccessfully() {
        when(repo.findByEmail("siva@gmail.com")).thenReturn(Optional.empty());
        when(repo.create(any(Person.class))).thenAnswer(answer -> answer.getArgument(0));

        Person person = personService.create("Siva", "SIVA@gmail.com", "siva123");

        assertEquals("Siva", person.getName());
        assertEquals("siva@gmail.com", person.getEmail());

        ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(repo).create(argumentCaptor.capture());
        Person value = argumentCaptor.getValue();
        assertEquals("Siva", value.getName());
        assertEquals("siva@gmail.com", value.getEmail());
    }

    @Test
    void updatePerson() {
        Person person = new Person(1L, "Siva", "siva@gmail.com", "siva123");

        doNothing().when(repo).update(any(Person.class));
        //doThrow(new RuntimeException("Invalid email")).when(repo).update(any(Person.class));

        personService.update(person);

        verify(repo).update(any(Person.class));
        //verify(repo, times(1)).update(any(Person.class));
        //verify(repo, atMostOnce()).update(any(Person.class));
        //verify(repo, atLeastOnce()).update(any(Person.class));
    }
}

```
# Spy
```java
package com.sivalabs.jtme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SalesServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Spy
    SalesReporter salesReporter;

    @InjectMocks
    SalesService salesService;

    /*@BeforeEach
    void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        salesReporter = new SalesReporter();
        salesService = new SalesService(orderRepository, salesReporter);
    }*/

    @Test
    void getSalesSummary() {
        when(orderRepository.getOrders("P100")).thenReturn(
                List.of(
                        new Order(1L,"P100", new BigDecimal("12.0"), 3),
                        new Order(2L,"P100", new BigDecimal("12.0"), 2)
                )
        );
        SalesSummary salesSummary = salesService.getSalesSummary("P100");
        assertThat(salesSummary.getSoldItemsCount()).isEqualTo(5);
        assertThat(salesSummary.getTotalRevenue()).isEqualTo(new BigDecimal("60.0"));

        verify(salesReporter).generateSalesSummary(any());
    }

    @Test
    void getMostSoldProduct() {
        when(orderRepository.getAllOrders()).thenReturn(
                List.of(
                        new Order(1L,"P101", new BigDecimal("12.0"), 3),
                        new Order(2L,"P102", new BigDecimal("16.0"), 6),
                        new Order(3L,"P101", new BigDecimal("12.0"), 4)
                )
        );

        String mostSoldProduct = salesService.getMostSoldProduct();
        assertThat(mostSoldProduct).isEqualTo("P101");
    }

}
```

## Best Practices and Anit-Patterns
![image](https://user-images.githubusercontent.com/69948118/227926726-68297dcf-0e67-49b3-bbfb-6e1e850c968d.png)

## Integration Test
![image](https://user-images.githubusercontent.com/69948118/228099383-a0352eb7-dc15-42c0-a0fc-9a7248661995.png)
![image](https://user-images.githubusercontent.com/69948118/228099526-b8771e32-e35d-4b5c-8fa1-d6f644cd53af.png)
![image](https://user-images.githubusercontent.com/69948118/228101011-669269c5-ee08-4e46-b289-fb9b354329b4.png)

## Integration Testing using TestContainers and RestAssured
https://testcontainers.com/guides/testing-spring-boot-rest-api-using-testcontainers/
https://www.testcontainers.org/
![image](https://user-images.githubusercontent.com/69948118/228104440-d6694fe2-b523-4b20-aa72-5ba8159eb1f6.png)
![image](https://user-images.githubusercontent.com/69948118/228104321-120ac457-acdb-44e8-a7aa-6537f62df3db.png)

```java
package com.sivalabs.tcdemo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        customerRepository.deleteAll();
    }

    @Test
    void shouldGetAllCustomers() {
        List<Customer> customers = List.of(
                new Customer(null, "John", "john@mail.com", "john"),
                new Customer(null, "Dennis", "dennis@mail.com", "dennis")
        );
        customerRepository.saveAll(customers);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/customers")
                .then()
                .statusCode(200)
                .body(".", hasSize(2));
    }
}
```

## Testing Database Repository
```java
package com.sivalabs.tcdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.test.database.replace=none",
        "spring.datasource.url=jdbc:tc:postgresql:15.2-alpine:///db"
})
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
       customerRepository.deleteAll();
    }

    @Test
    void shouldGetAllCustomers() {
        List<Customer> customers = List.of(
                new Customer(null, "John", "john@mail.com", "john"),
                new Customer(null, "Dennis", "dennis@mail.com", "dennis")
        );
        customerRepository.saveAll(customers);

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(2);
    }

    @Test
    void shouldLoginSuccessfully() {
        Customer customer = new Customer(null, "John", "john@mail.com", "john");
        customerRepository.save(customer);

        Optional<Customer> optionalCustomer = customerRepository.findByEmailAndPassword("john@mail.com", "john");
        assertThat(optionalCustomer).isPresent();
    }
}
```

