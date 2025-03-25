# ktor-demo-2

Demonstration for kjson-ktor

## Notes

This Party Server runs on port 8102 (configurable in `application.conf`) and it exists to respond to requests from the
`ktor-demo-1` Customer Server.

The Party Server has 4 built-in parties:

| Party Id | Party Name              |
|----------|-------------------------|
| 1        | Alpha Mail Co           |
| 2        | Beater retreats Ltd     |
| 3        | Gamma Raze Inc          |
| 4        | Delta Hairlines Pty Ltd |

The different endpoints illustrate the different technology approaches used.

### `/party/single/{id}`

This will return a single Party synchronously.

### `/party/list/{ids}`

This will return multiple parties (ids are separate by period '`.`') synchronously in a single array.
There is a delay of 3 seconds following each party retrieval, but because no data will be available until the last party
has been retrieved, this delay will be cumulative for the entire request.

### `/party/channel/{ids}`

This will return multiple parties asynchronously using a `Channel`.
The same delay will be incurred following each party retrieval, but in this case the data will be output to the response
stream as it becomes available.

### `/party/flow/{ids}`

This will return multiple parties asynchronously as above, but in this case using a `Flow`.
