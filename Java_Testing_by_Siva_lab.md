# Java Testing Made Easy: Learn to write Unit, Integration, E2E & Performance Tests
- https://www.youtube.com/watch?v=FGoLvCc6BeI&list=PLuNxlOYbv61jtHHFHBOc9N7Dg5jn013ix&index=1
- https://github.com/sivaprasadreddy/java-testing-made-easy

![image](https://user-images.githubusercontent.com/69948118/227894970-cf6d86e1-c655-4999-a9ff-7c2a6f17d90a.png)
![image](https://user-images.githubusercontent.com/69948118/227895013-e28f1d1b-0c1b-454b-b677-db72bfaca025.png)
![image](https://user-images.githubusercontent.com/69948118/227895086-4563be2a-21b2-4993-b195-56b1ad10aeb9.png)
![image](https://user-images.githubusercontent.com/69948118/227895103-b4570c9c-1b48-4b84-8515-4a9a46821f06.png)
![image](https://user-images.githubusercontent.com/69948118/227897253-b23082b0-0ab3-42f3-add1-f9b02b6ed785.png)

## AsertJ
https://assertj.github.io/doc/
```java
package com.sivalabs.jtme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonServiceTest {

    PersonService personService;

    @BeforeEach
    void setUp() {
        PersonRepository repo = new PersonRepository();
        personService = new PersonService(repo);
    }

    @Test
    void shouldCreatePerson() {
        Person person = personService.create(new Person(null, "Siva", "siva@gmail.com"));
        assertThat(person.getId()).isNotNull();
        assertThat(person.getName()).isEqualTo("Siva");
        assertThat(person.getEmail()).isEqualTo("siva@gmail.com").endsWith("@gmail.com");
    }

    @Test
    void shouldThrowExceptionWhenCreatePersonWithDuplicateEmail() {
        String email = UUID.randomUUID().toString()+"@gmail.com";
        personService.create(new Person(null, "Siva", email));

        //Junit 5 assertion
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            personService.create(new Person(null, "Siva", email));
        });
        assertTrue(exception.getMessage()
                .contentEquals("Person with email '"+email+"' already exists"));

        //Assertj assertion
        assertThatThrownBy(()-> {
            personService.create(new Person(null, "Siva", email));
        }).isInstanceOf(RuntimeException.class)
          .hasMessage("Person with email '"+email+"' already exists");
    }

    @Test
    void showAssertjAwesomeness() {
        String name = "Siva Prasad Reddy";
        int age = 35;
        assertThat(name).startsWith("Siv");
        assertThat(name).containsIgnoringCase("pra");
        assertThat(age).isGreaterThan(30);

        /* ================================================ */
        Person person1 = new Person(1L, "Siva", "siva@gmail.com");
        Person person2 = new Person(2L, "Prasad", "prasad@gmail.com");
        Person person3 = new Person(1L, "Siva", "siva@gmail.com");

        assertThat(person1).usingRecursiveComparison().isEqualTo(person3);

        Person person4 = new Person(null, "Siva", "sivalabs@gmail.com");
        Person person5 = new Person(null, "Siva", "sivalabs@gmail.com");
        assertThat(person4)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(person5);

        assertThat(person4)
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "email")
                .isEqualTo(person5);

        /* ================================================ */
        List<Person> personList = List.of(person1, person2, person4);
        Person person = new Person(2L, "Prasad", "prasad@gmail.com");

        assertThat(personList).contains(person);

        assertThat(person)
                .usingRecursiveComparison()
                .comparingOnlyFields("id")
                .isIn(personList);

    }
}
```


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
## Java Testing Made Easy: Testing Kafka, RabbitMQ Messaging Workflows using Testcontainers and Awaitility
![image](https://user-images.githubusercontent.com/69948118/228836497-b1a9c1d9-7315-4573-a59a-f7aa7485c515.png)
![image](https://user-images.githubusercontent.com/69948118/228836577-90784e49-0173-42bb-9419-0468880f3c71.png)

```java
package com.sivalabs.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:tc:postgresql:15.2-alpine:///db?TC_INITSCRIPT=sql/schema.sql"
})
@Testcontainers
@Slf4j
class ProductPriceChangedEventHandlerTest {

    @Container
    static final KafkaContainer kafka =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.2.1"));

    @DynamicPropertySource
    static void overridePropertiesInternal(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Product product = new Product(null, "P100", "Product One", BigDecimal.TEN);
        productRepository.save(product);
    }

    @Test
    void shouldHandleProductPriceChangedEvent() {
        ProductPriceChangedEvent event =
                new ProductPriceChangedEvent("P100", new BigDecimal("14.50"));

        log.info("Publishing ProductPriceChangedEvent with ProductCode: {}", event.getProductCode());
        kafkaTemplate.send("product-price-changes", event.getProductCode(), event);

        await().pollInterval(Duration.ofSeconds(3)).atMost(10, SECONDS).untilAsserted(() -> {
            Optional<Product> optionalProduct = productRepository.findByCode("P100");
            assertThat(optionalProduct).isPresent();
            assertThat(optionalProduct.get().getCode()).isEqualTo("P100");
            assertThat(optionalProduct.get().getPrice()).isEqualTo(new BigDecimal("14.50"));
        });
    }
}
```
![image](https://user-images.githubusercontent.com/69948118/228840475-f04db528-f50d-475d-b8c5-8630141246e8.png)

```java
package com.sivalabs.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.Optional;

import static com.sivalabs.demo.RabbitMQConfig.EXCHANGE_NAME;
import static com.sivalabs.demo.RabbitMQConfig.ROUTING_KEY;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:tc:postgresql:15.2-alpine:///db?TC_INITSCRIPT=sql/schema.sql"
})
@Testcontainers
@Slf4j
class ProductPriceChangedEventHandlerTest {

    @Container
    static final RabbitMQContainer rabbitmq =
            new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.11.11-alpine"));

    @DynamicPropertySource
    static void overridePropertiesInternal(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitmq::getHost);
        registry.add("spring.rabbitmq.port", rabbitmq::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbitmq::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbitmq::getAdminPassword);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Product product = new Product(null, "P100", "Product One", BigDecimal.TEN);
        productRepository.save(product);
    }

    @Test
    void shouldHandleProductPriceChangedEvent() {
        ProductPriceChangedEvent event =
                new ProductPriceChangedEvent("P100", new BigDecimal("14.50"));

        log.info("Publishing ProductPriceChangedEvent with ProductCode: {}", event.getProductCode());
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, event);

        await().atMost(10, SECONDS).untilAsserted(() -> {
            Optional<Product> optionalProduct = productRepository.findByCode("P100");
            assertThat(optionalProduct).isPresent();
            assertThat(optionalProduct.get().getCode()).isEqualTo("P100");
            assertThat(optionalProduct.get().getPrice()).isEqualTo(new BigDecimal("14.50"));
        });
    }
}
```
## Java Testing Made Easy : Testing External API Integrations using WireMock and MockServer
![image](https://user-images.githubusercontent.com/69948118/230580455-5de38681-8be3-4ff2-9458-049f7b44fdbc.png)
![image](https://user-images.githubusercontent.com/69948118/230580769-43808f97-b153-44c4-9781-72e8d2209d41.png)
- https://github.com/sivaprasadreddy/java-testing-made-easy/tree/main/spring-boot-wiremock-demo
```java
package com.sivalabs.tcdemo;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GithubControllerIntegrationTest {

	@Autowired
	protected MockMvc mockMvc;

	@RegisterExtension
	static WireMockExtension wireMockServer = WireMockExtension.newInstance()
		.options(wireMockConfig().dynamicPort())
		.build();

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("github.api.base-url", wireMockServer::baseUrl);
	}

	@Test
	void shouldGetGithubUserProfile() throws Exception {
		String username = "sivaprasadreddy";
		wireMockServer.stubFor(WireMock.get(urlMatching("/users/.*"))
				.willReturn(
					aResponse()
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody("""
						{
							"login": "%s",
							"name": "K. Siva Prasad Reddy",
							"twitter_username": "sivalabs",
							"public_repos": 50
						}
						""".formatted(username))));

		this.mockMvc.perform(get("/api/users/{username}", username))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.login", is(username)))
			.andExpect(jsonPath("$.name", is("K. Siva Prasad Reddy")))
			.andExpect(jsonPath("$.public_repos", is(50)));
	}

	@Test
	void shouldGetFailureResponseWhenGitHubApiFailed() throws Exception {
		String username = "sivaprasadreddy";

		wireMockServer.stubFor(WireMock.get(urlMatching("/users/.*"))
				.willReturn(aResponse().withStatus(500)));

		String expectedError = "Fail to fetch github profile for " + username;
		this.mockMvc.perform(get("/api/users/{username}", username))
				.andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.message", is(expectedError)));
	}
}
```
![image](https://user-images.githubusercontent.com/69948118/230584338-acb03fec-6630-41fe-acfe-a8d3e6e919b6.png)
- https://github.com/sivaprasadreddy/java-testing-made-easy/tree/main/spring-boot-mockserver-demo
```
package com.sivalabs.tcdemo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GithubControllerIntegrationTest {

	static ClientAndServer mockServer;

	@BeforeAll
	static void beforeAll() {
		mockServer = startClientAndServer();
	}

	@DynamicPropertySource
	static void overrideProperties(DynamicPropertyRegistry registry) {
		registry.add("github.api.base-url", () -> "http://localhost:"+mockServer.getPort());
	}

	@AfterAll
	static void afterAll() {
		mockServer.stop();
	}

	@BeforeEach
	void setUp() {
		mockServer.reset();
	}

	@Autowired
	MockMvc mockMvc;

	@Test
	void shouldGetGithubUserProfile() throws Exception {
		String username = "sivaprasadreddy";
		mockServer.when(request().withMethod("GET").withPath("/users/.*"))
				.respond(response().withStatusCode(200)
						.withHeader("Content-Type", "application/json")
						.withBody(json("""
						{
							"login": "%s",
							"name": "K. Siva Prasad Reddy",
							"twitter_username": "sivalabs",
							"public_repos": 50
						}
						""".formatted(username))));

		this.mockMvc.perform(get("/api/users/{username}", username))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.login", is(username)))
				.andExpect(jsonPath("$.name", is("K. Siva Prasad Reddy")))
				.andExpect(jsonPath("$.public_repos", is(50)));

		mockServer.verify(request().withMethod("GET").withPath("/users/.*"),
				VerificationTimes.exactly(1));
	}

	@Test
	void shouldGetFailureResponseWhenGitHubApiFailed() throws Exception {
		String username = "sivaprasadreddy";

		mockServer.when(request().withMethod("GET").withPath("/users/.*"))
				.respond(response().withStatusCode(500));

		String expectedError = "Fail to fetch github profile for " + username;
		this.mockMvc.perform(get("/api/users/{username}", username))
				.andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.message", is(expectedError)));
	}
}
```
## Java Testing Made Easy : End-To-End Testing using Playwright
- https://github.com/sivaprasadreddy/devzone/tree/main/playwright-e2e-tests
- https://playwright.dev/java/








