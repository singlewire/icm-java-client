# InformaCast Mobile REST Java Client

A simple, easy to use REST client based on [Resty](https://github.com/beders/Resty)

## Installation

Place the following in your pom file and you're ready to rock and roll!

```xml
<dependency>
    <groupId>com.singlewire</groupId>
    <artifactId>icm-client</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Usage

Import the client:

```java
import com.singlewire.RestClient;
```

Create an instance of the client.

```java
RestClient icmClient = new RestClient("<My Access Token>");
```

Have fun!

```java
// Get first page of users
icmClient.json(users());

// Paginate through all users
Iterator<JSONObject> users = icmClient.list(users());
while (users.hasNext()) {
    System.out.println("Fetched user with name " + users.next().getString("name"));
}

// Search for a user named Jim
Map<String, String> query = new HashMap<String, String>();
query.put("limit", "10");
query.put("q", "Jim");
icmClient.json(users() + urlEncode(query));

// Get a specific user
icmClient.json(user("de7b51a0-5a1e-11e4-ab31-8a1d033dd637"));

// Get a specific user's devices
icmClient.json(userDevices("de7b51a0-5a1e-11e4-ab31-8a1d033dd637"));

// Create a user
JSONObject createdUser = icmClient.json(users(), form(data("name", "Jim Bob"), data("email", "jim.bob@aol.com"))).toObject();
String createdUserId = createdUser.getString("id");

// Update the created user
icmClient.json(user(createdUserId), put(content(new JSONObject().put("name", "Jim Bob The Second"))));

// Delete the updated user
icmClient.json(user(createdUserId), delete());
```

## License

Copyright 2015 Singlewire LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.