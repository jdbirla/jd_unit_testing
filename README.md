# JD Unit Testing

## Junit 
src  : - https://courses.in28minutes.com/courses/enrolled/257253

## First 5 Steps in JUnit

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

## Complete Code Example


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

## Mockito
https://courses.in28minutes.com/courses/enrolled/257149

## Spring Unit Testing and Mockito
https://github.com/in28minutes/spring-master-class/tree/master/01-spring-in-depth
Step 27 - Spring Unit Testing with a Java Context
Step 28 - Spring Unit Testing with an XML Context
Step 29 - Spring Unit Testing with Mockito

## Spring boot Unit Testing and Mockito

https://github.com/in28minutes/SpringBootForBeginners

Step 24: Writing Unit Tests with Spring Boot and Mockito
Step 25: Writing Unit test for createTodo