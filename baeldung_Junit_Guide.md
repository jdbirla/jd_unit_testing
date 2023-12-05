# Baeldung_Junit_Guide.md
## Mockito Tutorial
- https://www.baeldung.com/mockito-series
### Getting Started with Mockito @Mock, @Spy, @Captor and @InjectMocks
- https://www.baeldung.com/mockito-annotations
- @Mock Annotation
   - The most widely used annotation in Mockito is @Mock. We can use @Mock to create and inject mocked instances without having to call Mockito.mock manually.
    ```java
      when(mockedList.size()).thenReturn(100); //both can work in mock
        doReturn(100).when(spyList).size();
     ```
-  @Spy Annotation
   - @Spy annotation to spy on an existing instance.
     ```java
      // when(mockedList.size()).thenReturn(100);  this is not going to work in Spy object
        doReturn(100).when(spyList).size();
     ```
  - @Captor
     - make use of @Captor for the same purpose, to create an ArgumentCaptor instance
```java
@Mock
List mockedList;

@Captor 
ArgumentCaptor argCaptor;

@Test
public void whenUseCaptorAnnotation_thenTheSame() {
    mockedList.add("one");
    Mockito.verify(mockedList).add(argCaptor.capture());

    assertEquals("one", argCaptor.getValue());
}
```
- @InjectMocks
   - Now let’s discuss how to use the @InjectMocks annotation to inject mock fields into the tested object automatically

### Mockito ArgumentMatchers
- https://www.baeldung.com/mockito-argument-matchers
```java
  verify(flowerService).analyze(or(eq("poppy"), endsWith("y")));
```

### Mocking Exception Throwing using Mockito
- https://www.baeldung.com/mockito-exceptions
```java

```
