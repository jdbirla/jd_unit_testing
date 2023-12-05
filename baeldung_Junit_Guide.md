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
### Mockito When/Then Cookbook
- https://www.baeldung.com/mockito-behavior
 - Configure simple return behavior for mock:
```java
MyList listMock = mock(MyList.class);
when(listMock.add(anyString())).thenReturn(false);

boolean added = listMock.add(randomAlphabetic(6));
assertThat(added).isFalse();
```

 - Configure return behavior for mock in an alternative way:
```java
MyList listMock = mock(MyList.class);
doReturn(false).when(listMock).add(anyString());

boolean added = listMock.add(randomAlphabetic(6));
assertThat(added).isFalse();
```
- Configure mock to throw an exception on a method call:
```java
MyList listMock = mock(MyList.class);
when(listMock.add(anyString())).thenThrow(IllegalStateException.class);

assertThrows(IllegalStateException.class, () -> listMock.add(randomAlphabetic(6)));
```

- Configure the behavior of a method with void return type — to throw an exception:
```java
MyList listMock = mock(MyList.class);
doThrow(NullPointerException.class).when(listMock).clear();

assertThrows(NullPointerException.class, () -> listMock.clear());

```

- Configure the behavior of multiple calls:
```java
MyList listMock = mock(MyList.class);
when(listMock.add(anyString()))
  .thenReturn(false)
  .thenThrow(IllegalStateException.class);

assertThrows(IllegalStateException.class, () -> {
    listMock.add(randomAlphabetic(6));
    listMock.add(randomAlphabetic(6));
});
```

- Configure the behavior of a spy:
```java
MyList instance = new MyList();
MyList spy = spy(instance);

doThrow(NullPointerException.class).when(spy).size();

assertThrows(NullPointerException.class, () -> spy.size());
```

- Configure method to call the real, underlying method on a mock:
```java
MyList listMock = mock(MyList.class);
when(listMock.size()).thenCallRealMethod();

assertThat(listMock).hasSize(1);
```
- Configure mock method call with custom Answer:
```java
MyList listMock = mock(MyList.class);
doAnswer(invocation -> "Always the same").when(listMock).get(anyInt());

String element = listMock.get(1);
assertThat(element).isEqualTo("Always the same");
```
- doAnswer
    - Using doAnswer you can do some additionals actions upon method invocation. For example, trigger a callback on queryBookTitle.
    - When you are using Spy instead of Mock
      - When using when-thenReturn on Spy Mockito will call real method and then stub your answer. This can cause a problem if you don't want to call real method, like in this sample
```java
List list = new LinkedList();
List spy = spy(list);
// Will throw java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
when(spy.get(0)).thenReturn("java");
assertEquals("java", spy.get(0));
```
   - Using doAnswer we can stub it safely
```java
List list = new LinkedList();
List spy = spy(list);
doAnswer(invocation -> "java").when(spy).get(0);
assertEquals("java", spy.get(0));
```
   - Actually, if you don't want to do additional actions upon method invocation, you can just use doReturn.
```java
     List list = new LinkedList();
List spy = spy(list);
doReturn("java").when(spy).get(0);
assertEquals("java", spy.get(0));
```
### Mockito Verify Cookbook
- https://www.baeldung.com/mockito-verify
   - Verify simple invocation on mock:
   ```java
   List<String> mockedList = mock(MyList.class);
mockedList.size();
verify(mockedList).size();
   ```
 - Verify the number of interactions with mock:
```java
List<String> mockedList = mock(MyList.class);
mockedList.size();
verify(mockedList, times(1)).size();
```
 - Verify no interaction with the whole mock occurred:
```java
List<String> mockedList = mock(MyList.class);
verifyNoInteractions(mockedList);
```
 - Verify no interaction with a specific method occurred:
```java
List<String> mockedList = mock(MyList.class);
verify(mockedList, times(0)).size();
```
 - Verify there are no unexpected interactions — this should fail:
```java
List<String> mockedList = mock(MyList.class);
mockedList.size();
mockedList.clear();

verify(mockedList).size();
assertThrows(NoInteractionsWanted.class, () -> verifyNoMoreInteractions(mockedList));
```
 - Verify the order of interactions:
```java
List<String> mockedList = mock(MyList.class);
mockedList.size();
mockedList.add("a parameter");
mockedList.clear();

InOrder inOrder = Mockito.inOrder(mockedList);
inOrder.verify(mockedList).size();
inOrder.verify(mockedList).add("a parameter");
inOrder.verify(mockedList).clear();
```
- Verify an interaction has not occurred:
```java
List<String> mockedList = mock(MyList.class);
mockedList.size();

verify(mockedList, never()).clear();
```
- Verify an interaction has occurred at least a certain number of times:
```java
List<String> mockedList = mock(MyList.class);
mockedList.clear();
mockedList.clear();
mockedList.clear();

verify(mockedList, atLeast(1)).clear();
verify(mockedList, atMost(10)).clear();
```
- Verify interaction with the exact argument:
```java
List<String> mockedList = mock(MyList.class);
mockedList.add("test");

verify(mockedList).add("test");
```

- Verify interaction with flexible/any argument:
```java
List<String> mockedList = mock(MyList.class);
mockedList.add("test");

verify(mockedList).add(anyString());
```
- Verify interaction using argument capture:
```java
List<String> mockedList = mock(MyList.class);
mockedList.addAll(Lists.<String> newArrayList("someElement"));

ArgumentCaptor<List<String>> argumentCaptor = ArgumentCaptor.forClass(List.class);
verify(mockedList).addAll(argumentCaptor.capture());

List<String> capturedArgument = argumentCaptor.getValue();
assertThat(capturedArgument).contains("someElement");
```

