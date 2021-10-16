# README
To build use:
`mvn package`

To run use:
`./run`

------

### TESTING NOTES

To run JUNIT tests use `mvn package` or `mvn test`; to run
the JUNIT tests and create the summary page use 
`mvn surefire-report:report` and open **/target/site/surefire-report.html**
* For JUNIT java tests to be properly detected and run by
maven they must end in "...Test.java"

To run system tests use `./cs32-test src/test/system/*.test`
