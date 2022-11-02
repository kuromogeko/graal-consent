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

POST /purposes/
{
 "text": "I agree to the processing of my data in accordance to article 6a for the purpose of marketing and tax declaration."
}

GET /purposes/

POST /consents/
{
 "purposeId": "1234-5678-9102"
}

GET /consents/

### Use Case 2
The law changed, so I want to update a purpose to reflect current jurisdiction. 
I would also like all consents of an earlier version given to this purpose to be invalidated.

PATCH /purposes/

{
 "text": "I agree to the processing of my data in accordance to article 6a for the purpose of tax declaration."
}

GET /consents/

### Use Case 3
I want to see the history of a given consent, so I can retrace who acted on behalf of my company in order to verify whether a consent given is legitimate.

GET /history?consentId=

### Todo (internal)
- Create javascript tracking domain
- add real repo
- tests!
- separate into artifacts
- reflection: Creating Event Class based on reflection