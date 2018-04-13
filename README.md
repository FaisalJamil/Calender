# Interview Test for Avira

Assumptions:
I assumed that,  
	
	If user saves an event in calendar app only then it should be save to the app.
	
	If user cancels an event in calendar app the event should not be saved to the app. 
	
	As soon as user saves the event in calendar app and calendar app closes the list screen with updated event is shown. 
	
	If user returns back from calendar app without saving the event they are taken to add event screen.**** 

    A web-service layer was written, to store values on a online database (in future) and that can be easily replaceable when the solution scales. 
	
	Some unit tests were added to the code. 

Technologies involved
-

- [Layered Architecture Pattern][1] ([Clean Architecture][2])
- [Reactive Programming][9] with [RxJava][8]
- [Dependency Injection][3] with [Dagger 2][4]
- Android View [Binding][17] with [Butterknife][5]
- [Repository Pattern][6]
- [HTTP Client API][7]
- [Unit Test][10] following [TDD First][11] approach ([JUnit][12])
- [Testing UI][14] with [Espresso][14] ([black-box testing][15])

[1]: https://www.oreilly.com/ideas/software-architecture-patterns/page/2/layered-architecture
[2]: https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html
[3]: https://martinfowler.com/articles/injection.html
[4]: https://google.github.io/dagger/
[5]: http://jakewharton.github.io/butterknife/
[6]: https://msdn.microsoft.com/en-us/library/ff649690.aspx
[7]: https://square.github.io/retrofit/
[8]: https://github.com/ReactiveX/RxJava
[9]: https://medium.com/@kuassivi/functional-reactive-programming-with-rxjava-part-2-78db194e7d35#.7mx0stygm
[10]: https://developer.android.com/training/testing/unit-testing/index.html
[11]: https://www.versionone.com/agile-101/agile-software-programming-best-practices/test-first-programming/
[12]: http://junit.org/junit4/
[13]: https://developer.android.com/training/testing/unit-testing/local-unit-tests.html
[14]: https://google.github.io/android-testing-support-library/docs/espresso/
[15]: http://www.guru99.com/black-box-testing.html
[16]: http://reactivex.io/documentation/operators.html
[17]: http://softwareengineering.stackexchange.com/questions/200115/what-is-early-and-late-binding

