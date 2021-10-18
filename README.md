# README
To build use:
`mvn package`

To run use:
`./run`

Before running, one must add a text file called `apiKey.txt` that contains 
the api key in plaintext (& nothing but it) within the `secret` directory,
this file is already included in `.gitignore`

------

### TESTING NOTES:

To run JUnit tests use `mvn package` or `mvn test`; to run
the JUnit tests and create the summary page use 
`mvn surefire-report:report` and open **/target/site/surefire-report.html**
* For JUnit java tests to be properly detected and run by
maven they must end in "...Test.java"

To run system tests use `./cs32-test src/test/system/*.test`

------

### DESIGN CHOICES:

Algorithm: How we grouped students for US4... inverse considerations..

For user story 1 (and the rest of the UI of our project) we choose
to have everything interface through a REPL that runs in the command
line. The main reasons driving this decision included us being most
comfortable programming a UI in this way and that doing so allowed us to 
perform system tests on specific input/output of all our functionality 
more easily (namely, in the manner that was introduced in the Onboarding
Lab) than would have been possible with file serialization, a website, or
some other GUI.

------

### QUESTIONS:

Score normalization 

------

### DESIGN CHOICES:

Algorithm: How we grouped students for US4... inverse considerations..

For user story 1 (and the rest of the UI of our project) we choose
to have everything interface through a REPL that runs in the command
line. The main reasons driving this decision included us being most
comfortable programming a UI in this way and that doing so allowed us to 
perform system tests on specific input/output of all our functionality 
more easily (namely, in the manner that was introduced in the Onboarding
Lab) than would have been possible with file serialization, a website, or
some other GUI.

------

### QUESTIONS:

Score normalization 