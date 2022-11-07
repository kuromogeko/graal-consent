### Stylish UUIDS
facade00-0000-4000-a000-000000000000
decade00-0000-4000-a000-000000000000
ad0be000-0000-4000-a000-000000000000
c0c0a000-0000-4000-a000-000000000000
5ca1ab1e-0000-4000-a000-000000000000
f100ded0-0000-4000-a000-000000000000

credit: https://gist.github.com/pesterhazy/120e1926dfeff603c264268fdf52adfb
### Use Case 1
I want to consent to a purpose so my contractual partner can safely grant me access to their software.


### Use Case 2
The law changed, so I want to update a purpose to reflect current jurisdiction. 
I would also like all consents of an earlier version given to this purpose to be invalidated.



### Use Case 3
I want to see the history of a given consent, so I can retrace who acted on behalf of my company in order to verify whether a consent given is legitimate.

GET /history?consentId=

### Todo (internal)
- add real repo
- tests!
- separate into artifacts
- reflection: Creating Event Class based on reflection

## Runtime Size differences 
201402928 executable native with js
-rw-rw-r--  1 noah noah  20454299 Nov  7 16:17 hello-0.0.1-SNAPSHOT-exec.jar
-rw-rw-r--  1 noah noah    145344 Nov  7 16:17 hello-0.0.1-SNAPSHOT.jar
-rwxrwxr-x  1 noah noah 201402928 Nov  7 16:20 hello*

---------------------
native executable without js
-rwxrwxr-x  1 noah noah 113968456 Nov  7 16:23 hello*
-rw-rw-r--  1 noah noah  20453922 Nov  7 16:22 hello-0.0.1-SNAPSHOT-exec.jar
-rw-rw-r--  1 noah noah    144967 Nov  7 16:22 hello-0.0.1-SNAPSHOT.jar
