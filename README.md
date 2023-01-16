## GraphQL

- GraphQL is a query language for APIs.
- GraphQL is strongly typed like Java.
- Using the type system, backend devs can expose the APIs and communicate how the data is structured.
- Using the type system, the clients have flexibility to explore the API and request specific data.
- GraphQL eliminates the over-fetching/under-fetching problems, it gives exactly what client has requested.
- Analogy: Using SQL to query only specific fields by joining the tables rather making a select * query to each table, same way we use GraphQL to get the data we are interested in.

## Scalar Types

- Int: Integer
- Float: Float
- String: String
- Boolean: Boolean
- ID: Some unique ID serialised as String. UUID/Long. Similar to Primary key constraint. Used to tell that the field value is unique.
- [...] like [Int] - List<Integer>
- We have enum as well.

```
type Product {
  id: ID
  price: Int
  brand: Brand
}

type Customer {
  id: ID! # ! for non-nullable field
  price: Int!
  # Comment using hash
  product: [Product]
}

enum Brand {
  A,
  B,
  C
}
```

## GraphQL has 3 special types
- Query: GET
- Mutation: POST/PUT/DELETE/PATCH
- Subscription: SSE/WebSocket (for streaming)

## Introduction

```
# These are the default properties, adding it to make things more explicit for understanding
 
# graphql
# this is where we keep graphql schema files
spring.graphql.schema.locations=classpath:graphql
# graphql is a spec, underlying it can use http/RSocket, this is base route for graphql endpoint
spring.graphql.path=/graphql
 
# graphiql: ui to interact with graphql server
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql
```

Note: If the controller method name does not match with method name in GraphQL query type then we have to mention it explicitly inside @QueryMapping, example below:

```
@QueryMapping(name = "sayHello")
public Mono<String> helloWorld() {
  return Mono.just("Hello World");
}
```

Note: If we are not passing a parameter value then it is treated as null. We can add ! to make a field value non-nullable.

Note: If the arg name mentioned in method (value in this case) does not match arg name in GraphQL query then we have to explicitly mention inside @Argument.

```
@QueryMapping
public Mono<String> sayHelloToExplicitArgName(@Argument("name") String value) {
return Mono.fromSupplier(() -> "Hello " + value);
}
```

Note: All the different methods mentioned in GraphQL query are triggered parallely. Example of such query below:

```
{
  sayHelloTo(name: "Tanishq"),
  sayHello
}
```
Note:
- GraphQL specification talks about how we can fetch data, mutate data, etc but it does not talk about underlying protocol, because of this we can use any protocol like HTTP, RSocket, etc.
- Since we have used reactive-webflux, so behind the scenes we are using HTTP.

Note: Client can't directly access custom types, client can only access or invoke 3 special types - query, mutation and subscription.

### Getting response

- If GraphQL API is returning some custom type then we have to mention in query that parameters of the custom type object we want in response. 
- Example:
```
type Query {
    customers: [Customer]!
    customerById(id: ID!): Customer
    customerByName(name: String!): [Customer]
}
 
 
// below is query to the api
{
  customers {
    id
    name
    age
    city
  }
  customerById(id: 1) {
    id
    name
  }
  customerByName(name: "j") {
    id
    name
  }
}
```
- We will get output for above api calls in single api response, the query is sent to server at once, same goes for response, it is like calling multiple apis of the server in one call and getting response from all of them altogether.
- If we have multiple inputs then we can pass them in an object using input object type. Example:
```
type Query {
  # filter customers by age range
  customerByAgeRange(filter: AgeRangeFilter): [Customer]!
}

input AgeRangeFilter {
  min: Int!,
  max: Int!
}

// below is query to the api
{
    customerByAgeRange(filter: {min: 0, max: 21}) {
      id
      name
      age
      city
    }
}
```
- Using GraphQL, we can mention exact fields that we want, this means server would itself interact with some particular downstream only and not all. 
- Another benefit is that we don't need to declare a separate api for each use-case, our graphql query would help achieve that.

### Nested Objects Aggregation
```
// @QueryMapping -> Alias for @SchemaMapping
@SchemaMapping(typeName = "Query")
// this means that there is customers field (in schema) which belongs to type Query, Query is the root object
public Flux<Customer> customers() {
 return customerService.allCustomers();
}
 
@SchemaMapping(typeName = "Customer")
// this means that there is orders field (in schema) which belongs to type Customer, Customer is the root object
// we can access parent object i.e. Customer in the method parameters
// this method would be executed only if orders information is requested for the Customer
public Flux<CustomerOrderDto> orders(Customer customer) {
 System.out.println("orders method invoked for: " + customer.getName());
 return this.orderService.ordersByCustomerName(customer.getName());
}
```

### Batch Mapping
- To solve the N+1 problem, we can use @BatchMapping annotation, now instead of querying orders for each customer, orders would be queried for all the customers in one go.
Note: Send POST request to http://localhost:8080/graphql in Postman, select GraphQL in request body and select fetch to load GraphQL schema in Postman.

- GraphQL to call same function multiple times
```
{
sayHelloTo(name: "ABC"),
  a: sayHello,
  b: sayHello,
  c: sayHello,
}
```

### Fragment

- fragment: a simple reusable unit, it is alias name to field(s).
- ... is the spread operator used to extract the fields from fragment.
- Example:
```
{
    c1: customers {
        id
        orders {
            ... orderInfo
        }
    }
    c2: customers {
        id
        orders {
            ... orderInfo
        }
    }
}

fragment orderInfo on CustomerOrder {
  id
  description
}
```

- Passing variables:
```
query getCustomer($id: ID! = 2) { // 2 is the default val
  customerById(id: $id) {
    id
    name
  }
}

query getCustomerAnother($name: String!) {
  customerByName(name: $name) {
    id
    name
  }
}

Variables passed:
{
  "id": 1,
  "name": "jake"
}

// We can pass extra vars as well
```
- Directives @Include/@Skip
```
query getCustomer($id: ID! = 2) {
  customerById(id: $id) @include(if: true) {
    id
    name
  }
}
 
query getCustomer($id: ID! = 2) {
  customerById(id: $id) @skip(if: false) {
    id
    name
  }
}

More here: https://dgraph.io/docs/graphql/queries/skip-include
```

- @Deprecated directive: We can mark any field (can be a field in query (special) type or any custom type) as deprecated in graphql schema, if it is deprecated then that's a message to the client of the graphql api that this field is deprecated and won't be part of the response so please migrate/change your logic accordingly if you are using this field.
- Example:

```
type Customer {
  id: ID,
  name: String,
  age: Int,
  city: String @deprecated(reason: "Use City.city instead")
  orders: [CustomerOrder]!
}

type City {
  city: String
}
```

- DataFetchingFieldSelectionSet parameter gives us the fields that have been asked for a type like Customer, Order etc.

### Nested Object Execution Optimization

- In GraphQL nested object execution, first it executes for all parent objects and then later it resolves the nested object (using its corresponding @SchemaMapping(typeName = "Customer") method) as it finds 'orders' method.
- We can add orders field in CustomerDto object and we can check if selected fields in graphql query consists of 'orders' field then we would provide order data as well else not, no need for 'orders' method in controller in this case then.

### Advanced Scalar Types

- We have to register those extended scalar types in our code as well (which we have declared in graphql schema) i.e. create bean for each of those extended scalar types.

### Object Field Type

- Drawback of Object field type is that client cannot ask for specific fields. Sometimes we want to properly create model for our client.
- We can create an interface in GraphQL schema to keep all common fields and different objects would be implementing that interface. 
  
### Interface

- Object that implements interface = same fields (as in interface) + additional fields.
- Java class/POJO name should match with the object implementing the interface in the GraphQL schema, it should match exactly in case of an interface in GraphQL schema.
- If somehow they do not match then can you can add resolver for that class (whose name doesn't match with what we have in schema) along with configuring it using RuntimeWiringConfigurer.

### Union

- Union in GraphQL schema = oneOf.
- In case of GraphQL API which returns a union, we have to expect all type of fields in GraphQL query that can be returned by objects inside union.

### Operation Caching

- If the same query is going to be used again and again then there is no use of doing parsing and validation again for that query as it would increase the execution time.
- We can do above i.e. improve performance of this step using operation caching.

### Mutation Fact
- The fields/apis mentioned inside graphql query are executed in parallel whereas the fields/apis mentioned inside graphql mutation are executed sequentially.

Note: We have data (for actual response) and errors (for errors) section in graphql query response.

### Exception Handling & Input Validation
- If we are using HTTP as the underlying transport for our GraphQL, so client might want to send some headers in the request.
- How to access those headers and take some decision?
    - We have to use WebGraphQLInterceptor to get the header information and pass it to the controller.

