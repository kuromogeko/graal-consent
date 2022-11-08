# Graal VM Example

Builds for Linux native with Spring 2.7 and GraalVM CE 22.20

## How to build
For classic jar file
```
./mvnw clean package
```
For native image
```
./mvnw clean package -Pnative
```

### Build specialties
META-INF.native image in resources holds a reflect-config.json referencing ConsentInvalidatedEvent since it is not found by Graal agent due to the way reactive context is built.
-> If you want to see the native image crash you can remove the file.
## Runtime Size differences

JS Code part can be activated/disabled by Registering/Removing ConsentEventHandler from Spring Context (e.g. commenting out component annotation)

201402928 executable native with js
-rw-rw-r--  1 noah noah  20454299 Nov  7 16:17 hello-0.0.1-SNAPSHOT-exec.jar
-rw-rw-r--  1 noah noah    145344 Nov  7 16:17 hello-0.0.1-SNAPSHOT.jar
-rwxrwxr-x  1 noah noah 201402928 Nov  7 16:20 hello*

---------------------
native executable without js
-rwxrwxr-x  1 noah noah 113968456 Nov  7 16:23 hello*
-rw-rw-r--  1 noah noah  20453922 Nov  7 16:22 hello-0.0.1-SNAPSHOT-exec.jar
-rw-rw-r--  1 noah noah    144967 Nov  7 16:22 hello-0.0.1-SNAPSHOT.jar


## Examples
Http File for requests against api is: requests.http

### Use Case 1 (Domain consent)
I want to consent to a purpose so my contractual partner can safely grant me access to their software.


### Use Case 2 (Domain purpose)
The law changed, so I want to update a purpose to reflect current jurisdiction. 
I would also like all consents of an earlier version given to this purpose to be invalidated.

### Use Case 3 (Domain tracking)
I want to see the history of a given consent, so I can retrace who acted on behalf of my company in order to verify whether a consent given is legitimate.


## Todo
- add real repo
- tests!
- separate into artifacts
- reflection: Creating Event Class based on reflection


### UUIDS if needed
facade00-0000-4000-a000-000000000000
decade00-0000-4000-a000-000000000000
ad0be000-0000-4000-a000-000000000000
c0c0a000-0000-4000-a000-000000000000
5ca1ab1e-0000-4000-a000-000000000000
f100ded0-0000-4000-a000-000000000000

credit: https://gist.github.com/pesterhazy/120e1926dfeff603c264268fdf52adfb
