
money transfer/processing example
---------------------------------


Design and implement a RESTful API (including data model and the backing implementation) for money transfers between accounts.


The service allows to create account with initial balance, transfer money, get account list and transaction list.
There is Order entity to transfer money. Order is processing in background.
When Order is completed (if all accounts are exist and have enough money) the balances on accounts are changed
according to order amount.
There are some integration tests in the service to check positive and negative scenarios of money transfer.


Explicit requirements
---------------------

* keep it simple and to the point (e.g. no need to implement any authentication, assume the APi is invoked by another internal system/service)
* use whatever frameworks/libraries you like (except Spring, sorry!) but don't forget about the requirement #1
* the datastore should run in-memory for the sake of this test
* the final result should be executable as a standalone program (should not require a pre-installed container/server)
* demonstrate with tests that the API works as expected


Implicit requirements
---------------------

* the code produced by you is expected to be of high quality.
* there are no detailed requirements, use common sense.


build
-----

```bash
mvn clean install
```

run
---

```bash
java -jar target/transfer.jar
```

...and so RESTful API will be there: http://127.0.0.1:8080/api


REST API
--------

Accounts:
```
POST    /api/account
GET     /api/account/{account-id}
GET     /api/accounts
```

Orders:
```
POST    /api/order/transfer-order
GET     /api/order/{order-id}
GET     /api/orders
```

Transactions:
```
GET     /api/transactions
```

for more information see integration tests: 


technological stack
-------------------

* Micronaut
* H2
* Netty
* Guava
* AutoValue
* Logback
* Jackson
* JUnit
