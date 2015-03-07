# InformaCast Mobile REST Java Client

A simple, easy to use REST client based on [Resty](https://github.com/beders/Resty)

## Installation

Place the following in your pom file

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
RestClient icmClient = new RestClient('<My Access Token>')
```

Have fun!

```java
// Get first page of users
icmClient.json(Resource.USERS);

// Paginate through all users
Iterator<JSONObject> users = icmClient.list(Resource.USERS);
while(users.hasNext()) {
    System.out.println("Fetched user with name " + users.next().getString("name"));
}

// Search for a user named Jim
Map<String, String> query = new HashMap<String, String>();
query.put("limit", "10");
query.put("q", "Jim");
icmClient.json(Resource.USERS.withQuery(query).build());

// Get a specific user
icmClient.json(Resource.USERS.withIds("de7b51a0-5a1e-11e4-ab31-8a1d033dd637").build());

// Get a specific user's devices
icmClient.json(Resource.USER_DEVICES.withIds("de7b51a0-5a1e-11e4-ab31-8a1d033dd637").build());

// Create a user
JSONObject user = icmClient.json(Resource.USERS, form(data("name", "Jim Bob"), data("email", "jim.bob2@aol.com"))).toObject();

// Update the created user
icmClient.json(Resource.USERS.withIds(user.getString("id")).build(), put(content(new JSONObject().put("name", "Jim Bob The Second"))));

// Delete the updated user
icmClient.json(Resource.USERS.withIds(user.getString("id")).build(), delete());
```

## License

TODO