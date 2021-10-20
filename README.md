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

Algorithm: How we grouped students for US4... 
Within our algorithm that from raw quantitative values of student's
skills derived modified scores we included that the (slightly scaled) 
self-assessed  scores would be replaced with their additive inverse mod 10 (
among other things discussed below). Then, when
searching in the k-d tree for a student's nearest neighbors (based off
their raw skills values) the closest students would be those who were
best at the skills that they were worst at. We believe this sort of skill
complementing makes for better teammates and groups.

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

Aside from what was discussed above in regard to how we modified Students'
quantitative skill scores for use with the k-d tree, we also modified the
values in getting derivedSkills by converting our slightly scaled and 
additive-inversed (mod 10) values into z-scores. This means that the biggest
consideration in finding neighbors was how good a orm.Student thought themselves at a
particular skill relative to their abilities in the other skills instead of mere
raw score. This was important to do because it eliminates bias that may occur
from certain Students chronically over- or under-estimating their abilites
across every skill.

Talk about bloom filter and prohibitions on certain pairings...
(maybe make array of people a orm.Student can't work with)