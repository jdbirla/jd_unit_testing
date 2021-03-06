# JD Unit Testing


##  Junit vs Mockito vs Spring Junit vs Spring Mockito vs Spring Boot junit and Spring Boot Mockito
|#  |Junit|Mockito |Spring Junit|Spring Mockito|Spring Boot junit|Spring Boot Mockito |
|--- |---|--- |---|---|---|--- |
|dependency|Not Required  |junit and mockito-all    |spring-test and mockito-core			|spring-test and mockito-core           |spring-boot-starter	     |spring-boot-starter				  |
|code|```@Test 	public void test() { 		boolean condn = true; 		assertEquals(true, condn); 		assertTrue(condn); 		// assertFalse(condn); 	}```  |```@Test 	public void usingMockito() { 		TodoService todoService = mock(TodoService.class); 		List<String> allTodos = Arrays.asList("Learn Spring MVC", 				"Learn Spring", "Learn to Dance"); 		when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos); 		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService); 		List<String> todos = todoBusinessImpl 				.retrieveTodosRelatedToSpring("Ranga"); 		assertEquals(2, todos.size()); 	}```     |```@RunWith(SpringRunner.class)/@RunWith(SpringJUnit4ClassRunner.class) public class SpringIn5StepsBasicApplicationTests {  	@Test 	public void contextLoads() { 	}  }  @RunWith(SpringRunner.class) @ContextConfiguration(classes = SpringIn5StepsBasicApplication.class) public class BinarySearchTest {  	// Get this bean from the context 	@Autowired 	BinarySearchImpl binarySearch; ```			|```@RunWith(MockitoJUnitRunner.class) public class SomeCdiBusinessTest {  	// Inject Mock 	@InjectMocks 	SomeCdiBusiness business;  	// Create Mock 	@Mock 	SomeCdiDao daoMock;  	@Test 	public void testBasicScenario() { 		Mockito.when(daoMock.getData()).thenReturn(new int[] { 2, 4 }); 		assertEquals(4, business.findGreatest()); 	}	```          |```@Test```			     |```@RunWith(SpringRunner.class) @SpringBootTest public class MockitoDemoApplicationTests {  	@Test 	public void contextLoads() { 	}  } @RunWith(MockitoJUnitRunner.class) public class SomeBusinessMockAnnotationsTest {  	@Mock 	DataService dataServiceMock;  	@InjectMocks 	SomeBusinessImpl businessImpl;  	@Test 	public void testFindTheGreatestFromAllData() { 		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] { 24, 15, 3 }); 		assertEquals(24, businessImpl.findTheGreatestFromAllData()); 	}```				  |

# Junit 
src  : - https://courses.in28minutes.com/courses/enrolled/257253

### First 5 Steps in JUnit

- Git Repository - https://github.com/in28minutes/getting-started-in-5-steps


### Step 1 : What is JUnit and Unit Testing?
 - What is JUnit?
 - What is Unit Testing?
 - Advantages of Unit Testing

### Step 2 : First JUnit Project and Green Bar
 - What is JUnit?
 - First Project with JUnit
 - First JUnit Class
 - No Failure is Success
 - MyMath class with sum method

### Step 3 : First Code and First Unit Test
 - Unit test for the sum method

### Step 4 : Other assert methods
 - assertTrue and assertFalse methods

### Step 5 : Important annotations
  - @Before @After annotations
  - @BeforeClass @AfterClass annotations

### Complete Code Example


### /src/com/in28minutes/junit/MyMath.java

```java
package com.in28minutes.junit;
public class MyMath {
	int sum(int[] numbers) {
		int sum = 0;
		for (int i : numbers) {
			sum += i;
		}
		return sum;
	}
}
```
---

### /test/com/in28minutes/junit/AssertTest.java

```java
package com.in28minutes.junit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
public class AssertTest {
	@Test
	public void test() {
		boolean condn = true;
		assertEquals(true, condn);
		assertTrue(condn);
		// assertFalse(condn);
	}
}
```
---

### /test/com/in28minutes/junit/MyMathTest.java

```java
package com.in28minutes.junit;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
public class MyMathTest {
	MyMath myMath = new MyMath();
	@Before
	public void before() {
		System.out.println("Before");
	}
	@After
	public void after() {
		System.out.println("After");
	}
	@BeforeClass
	public static void beforeClass() {
		System.out.println("Before Class");
	}
	@AfterClass
	public static void afterClass() {
		System.out.println("After Class");
	}
	// MyMath.sum
	// 1,2,3 => 6
	@Test
	public void sum_with3numbers() {
		System.out.println("Test1");
		assertEquals(6, myMath.sum(new int[] { 1, 2, 3 }));
	}
	@Test
	public void sum_with1number() {
		System.out.println("Test2");
		assertEquals(3, myMath.sum(new int[] { 3 }));
	}
}
```
---

# Mockito 
src  : - https://www.youtube.com/watch?v=d2KwvXQgQx4
- Git Repository - https://github.com/in28minutes/MockitoTutorialForBeginners
- JD/MockitoTutorialForBeginners-master

### Step 1 : What You Will Learn during this Step:
- Set up an Eclipse Project.
- Set up JUnit and Mockito frameworks.
- First Green Bar

### pom.xml
```
<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.12</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.mockito</groupId>
			<artifactId>mockito-all</artifactId>
			<version>1.10.19</version>
			<scope>test</scope>
		</dependency>
		
```
---
### Step 2 : What You Will Learn during this Step:

- Start Creating an example to start understanding why we need mocks.
- We want to interact with a Todo Management application.
- We want to provide a filtering around Spring related Todo's

### /src/main/java/com/in28minutes/business/TodoBusinessImpl.java
```
package com.in28minutes.business;
import java.util.ArrayList;
import java.util.List;
import com.in28minutes.data.api.TodoService;
public class TodoBusinessImpl {
	private TodoService todoService;
	TodoBusinessImpl(TodoService todoService) {
		this.todoService = todoService;
	}
	public List<String> retrieveTodosRelatedToSpring(String user) {
		List<String> filteredTodos = new ArrayList<String>();
		List<String> allTodos = todoService.retrieveTodos(user);
		for (String todo : allTodos) {
			if (todo.contains("Spring")) {
				filteredTodos.add(todo);
			}
		}
		return filteredTodos;
	}
}
```

### /src/main/java/com/in28minutes/data/api/TodoService.java
```
package com.in28minutes.data.api;
import java.util.List;
// External Service - Lets say this comes from WunderList
public interface TodoService {
	public List<String> retrieveTodos(String user);
}
```
---

## Step : 03 What You Will Learn during this Step
- What is a stub?
- Create an unit test using Stub?
- Disadvantages of Stubs

---
##  Step : 04 What You Will Learn during this Step:
- Your first Mockito code! Hurrah!!!
- Lets use Mockito to mock TodoService
- Visit Mockito Official Documentation - [Mockito Documentation] (http://site.mockito.org/mockito/docs/current/org/mockito/Mockito.html)


### Useful Snippets and References
Easier Static Imports
```
Window > Preferences > Java > Editor > Content Assist > Favorites
org.junit.Assert
org.mockito.BDDMockito
org.mockito.Mockito
org.hamcrest.Matchers
org.hamcrest.CoreMatchers
```

### /src/test/java/com/in28minutes/mockito/FirstMockitoTest.java
```
package com.in28minutes.mockito;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
public class FirstMockitoTest {
	@Test
	public void test() {
		assertTrue(true);
	}
}
```

### /src/test/java/com/in28minutes/business/TodoBusinessImplMockitoTest.java
```
package com.in28minutes.business;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import com.in28minutes.data.api.TodoService;
public class TodoBusinessImplMockitoTest {
	@Test
	public void usingMockito() {
		TodoService todoService = mock(TodoService.class);
		List<String> allTodos = Arrays.asList("Learn Spring MVC",
				"Learn Spring", "Learn to Dance");
		when(todoService.retrieveTodos("Ranga")).thenReturn(allTodos);
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
		List<String> todos = todoBusinessImpl
				.retrieveTodosRelatedToSpring("Ranga");
		assertEquals(2, todos.size());
	}
}
```
---
### Step 05 : What You Will Learn during this Step:
- A few mockito examples mocking List class
- Multiple return values
- Introduction to Argument Matchers
- stub method
- Throwing exceptions


---
# Spring Unit Testing and Mockito

## Spring Unit Testing : with JUnit And Mockito

- src : https://www.udemy.com/course/spring-tutorial-for-beginners/learn/lecture/7725784#overview
- git: jd/SpringJunitMockito

https://github.com/in28minutes/spring-master-class/tree/master/01-spring-in-depth
Step 27 - Spring Unit Testing with a Java Context
Step 28 - Spring Unit Testing with an XML Context
Step 29 - Spring Unit Testing with Mockito



### Pom.xml
```
	<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
		</dependency>

		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
		</dependency>

		<dependency>
			<groupId>ch.qos.logback</groupId>
			<artifactId>logback-classic</artifactId>
		</dependency>

		<dependency>
			<groupId>javax.inject</groupId>
			<artifactId>javax.inject</artifactId>
			<version>1</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>org.mockito</groupId>
			<artifactId>mockito-core</artifactId>
			<scope>test</scope>
		</dependency>

```

### Spring Junit
```
package com.in28minutes.spring.basics.springin5steps.basic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.in28minutes.spring.basics.springin5steps.SpringIn5StepsBasicApplication;

//Load the context
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringIn5StepsBasicApplication.class)
public class BinarySearchTest {

	// Get this bean from the context
	@Autowired
	BinarySearchImpl binarySearch;

	@Test
	public void testBasicScenario() {
		
		// call method on binarySearch
		int actualResult = binarySearch.binarySearch(new int[] {}, 5);

		// check if the value is correct
		assertEquals(3, actualResult);

	}

}

```
### Spring Mockito

```
package com.in28minutes.spring.basics.springin5steps.cdi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SomeCdiBusinessTest {

	// Inject Mock
	@InjectMocks
	SomeCdiBusiness business;

	// Create Mock
	@Mock
	SomeCdiDao daoMock;

	@Test
	public void testBasicScenario() {
		Mockito.when(daoMock.getData()).thenReturn(new int[] { 2, 4 });
		assertEquals(4, business.findGreatest());
	}

	@Test
	public void testBasicScenario_NoElements() {
		Mockito.when(daoMock.getData()).thenReturn(new int[] { });
		assertEquals(Integer.MIN_VALUE, business.findGreatest());
	}

	@Test
	public void testBasicScenario_EqualElements() {
		Mockito.when(daoMock.getData()).thenReturn(new int[] { 2,2});
		assertEquals(2, business.findGreatest());
	}

}

```



### Another example
```
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.in28minutes.example.layering.business.api.TodoBusinessService;
import com.in28minutes.example.layering.model.api.client.Todo;

@Configuration
@ComponentScan(basePackages = {
		"com.in28minutes.example.layering.business.impl",
		"com.in28minutes.example.layering.data.stub" })
class SpringTestContext {
}

// 1. We need to test using Spring
// 2. How do we tell Spring to use specific Configuration
// 3. How do autowire the TodoBusinessService
// 4. How do we auto wire TodoDataServiceStub
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = SpringTestContext.class)
public class TodoBusinessServiceJavaStubTest {

	@Autowired
	TodoBusinessService businessService;

	@Test
	public void testGetTodosAboutSpring() {
		List<Todo> todos = businessService
				.retrieveTodosRelatedToSpring("Ranga");
		System.out.println(todos);
		assertEquals(2, todos.size());
	}
}
```

```
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.stub;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.in28minutes.example.layering.business.impl.TodoBusinessServiceImpl;
import com.in28minutes.example.layering.data.api.TodoDataService;
import com.in28minutes.example.layering.model.api.client.Todo;

@RunWith(MockitoJUnitRunner.class)
public class TodoBusinessServiceMockitoTest {

	@Mock
	private TodoDataService todoDs;

	@InjectMocks
	private TodoBusinessServiceImpl todoBs;

	@Before
	public void initializeMockito() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testRetrieveTodosRelatedToSpring() {

		List<Todo> todos = Arrays.asList(new Todo(
				"Mockito - Complete Spring Tutorial", new Date(), false),
				new Todo("Mockito - Complete Spring MVC Tutorial", new Date(),
						false), new Todo("Mockito - Complete All Tutorials",
								new Date(), false));

		stub(todoDs.retrieveTodos(anyString())).toReturn(todos);

		List<Todo> filteredTodos = todoBs
				.retrieveTodosRelatedToSpring("dummyUser");

		assertEquals(2, filteredTodos.size());
	}
}
```


---

# Spring boot Unit Testing and Mockito

https://github.com/in28minutes/SpringBootForBeginners

Step 24: Writing Unit Tests with Spring Boot and Mockito
Step 25: Writing Unit test for createTodo

src : - https://courses.in28minutes.com/courses/enrolled/257149

## First 5 Steps in Mockito

Mockito is the most famous mocking framework in Java.

- Git Repository - https://github.com/in28minutes/getting-started-in-5-steps
- Pre-requisites 
    - Java & Eclipse - https://www.youtube.com/playlist?list=PLBBog2r6uMCSmMVTW_QmDLyASBvovyAO3
    - JUnit - https://courses.in28minutes.com/p/junit-tutorial-for-beginners
- Easier Static Imports
  - Window > Preferences > Java > Editor > Content Assist > Favorites
    - org.junit.Assert
    - org.mockito.BDDMockito
    - org.mockito.Mockito
    - org.hamcrest.Matchers
    - org.hamcrest.CoreMatchers
- More information 
   - Visit Mockito Official Documentation - [Mockito Documentation] (http://site.mockito.org/mockito/docs/current/org/mockito/Mockito.html)

- Step 1 : Setting up an example using http://start.spring.io.
- Step 2 : Using a Stubs - Disadvantages
- Step 3 : Your first mock. 
- Step 4 : Using Mockito Annotations - @Mock, @InjectMocks, @RunWith(MockitoJUnitRunner.class)
- Step 5 : Mocking List interface
- Next Steps

## Complete Code Example


### /pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.in28minutes.mockito</groupId>
	<artifactId>mockito-demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>mockito-demo</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.0.BUILD-SNAPSHOT</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

	<repositories>
		<repository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>https://repo.spring.io/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
		<repository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
	</repositories>

	<pluginRepositories>
		<pluginRepository>
			<id>spring-snapshots</id>
			<name>Spring Snapshots</name>
			<url>https://repo.spring.io/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</pluginRepository>
		<pluginRepository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</pluginRepository>
	</pluginRepositories>


</project>
```
---

### /src/main/java/com/in28minutes/mockito/mockitodemo/DataService.java

```java
package com.in28minutes.mockito.mockitodemo;
public interface DataService {
	int[] retrieveAllData();
}
```
---

### /src/main/java/com/in28minutes/mockito/mockitodemo/MockitoDemoApplication.java

```java
package com.in28minutes.mockito.mockitodemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class MockitoDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(MockitoDemoApplication.class, args);
	}
}
```
---

### /src/main/java/com/in28minutes/mockito/mockitodemo/SomeBusinessImpl.java

```java
package com.in28minutes.mockito.mockitodemo;
public class SomeBusinessImpl {
	private DataService dataService;
	public SomeBusinessImpl(DataService dataService) {
		super();
		this.dataService = dataService;
	}
	int findTheGreatestFromAllData() {
		int[] data = dataService.retrieveAllData();
		int greatest = Integer.MIN_VALUE;
		for (int value : data) {
			if (value > greatest) {
				greatest = value;
			}
		}
		return greatest;
	}
}
```
---

### /src/main/resources/application.properties

```properties
```
---

### /src/test/java/com/in28minutes/mockito/mockitodemo/ListTest.java

```java
package com.in28minutes.mockito.mockitodemo;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;
public class ListTest {
	@Test
	public void testSize() {
		List listMock = mock(List.class);
		when(listMock.size()).thenReturn(10);
		assertEquals(10, listMock.size());
		assertEquals(10, listMock.size());
	}
	@Test
	public void testSize_multipleReturns() {
		List listMock = mock(List.class);
		when(listMock.size()).thenReturn(10).thenReturn(20);
		assertEquals(10, listMock.size());
		assertEquals(20, listMock.size());
		assertEquals(20, listMock.size());
	}
	@Test
	public void testGet_SpecificParameter() {
		List listMock = mock(List.class);
		when(listMock.get(0)).thenReturn("SomeString");
		assertEquals("SomeString", listMock.get(0));
		assertEquals(null, listMock.get(1));
	}
	@Test
	public void testGet_GenericParameter() {
		List listMock = mock(List.class);
		when(listMock.get(Mockito.anyInt())).thenReturn("SomeString");
		assertEquals("SomeString", listMock.get(0));
		assertEquals("SomeString", listMock.get(1));
	}
}
```
---

### /src/test/java/com/in28minutes/mockito/mockitodemo/MockitoDemoApplicationTests.java

```java
package com.in28minutes.mockito.mockitodemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
public class MockitoDemoApplicationTests {
	@Test
	public void contextLoads() {
	}
}
```
---

### /src/test/java/com/in28minutes/mockito/mockitodemo/SomeBusinessMockAnnotationsTest.java

```java
package com.in28minutes.mockito.mockitodemo;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class SomeBusinessMockAnnotationsTest {
	@Mock
	DataService dataServiceMock;
	@InjectMocks
	SomeBusinessImpl businessImpl;
	@Test
	public void testFindTheGreatestFromAllData() {
		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] { 24, 15, 3 });
		assertEquals(24, businessImpl.findTheGreatestFromAllData());
	}
	@Test
	public void testFindTheGreatestFromAllData_ForOneValue() {
		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] { 15 });
		assertEquals(15, businessImpl.findTheGreatestFromAllData());
	}
	@Test
	public void testFindTheGreatestFromAllData_NoValues() {
		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] {});
		assertEquals(Integer.MIN_VALUE, businessImpl.findTheGreatestFromAllData());
	}
}
```
---

### /src/test/java/com/in28minutes/mockito/mockitodemo/SomeBusinessMockTest.java

```java
package com.in28minutes.mockito.mockitodemo;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
public class SomeBusinessMockTest {
	@Test
	public void testFindTheGreatestFromAllData() {
		DataService dataServiceMock = mock(DataService.class);
		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] { 24, 15, 3 });
		SomeBusinessImpl businessImpl = new SomeBusinessImpl(dataServiceMock);
		int result = businessImpl.findTheGreatestFromAllData();
		assertEquals(24, result);
	}
	@Test
	public void testFindTheGreatestFromAllData_ForOneValue() {
		DataService dataServiceMock = mock(DataService.class);
		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] { 15 });
		SomeBusinessImpl businessImpl = new SomeBusinessImpl(dataServiceMock);
		int result = businessImpl.findTheGreatestFromAllData();
		assertEquals(15, result);
	}
}
```
---

### /src/test/java/com/in28minutes/mockito/mockitodemo/SomeBusinessStubTest.java

```java
package com.in28minutes.mockito.mockitodemo;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
public class SomeBusinessStubTest {
	@Test
	public void testFindTheGreatestFromAllData() {
		SomeBusinessImpl businessImpl = new SomeBusinessImpl(new DataServiceStub());
		int result = businessImpl.findTheGreatestFromAllData();
		assertEquals(24, result);
	}
}
class DataServiceStub implements DataService {
	@Override
	public int[] retrieveAllData() {
		return new int[] { 24, 6, 15 };
	}
}
```
---