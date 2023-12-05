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

