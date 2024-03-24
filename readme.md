# Documentation
- Simple project using spring state machine
  - config on /infra/config
- This project have 2 example
  - case 1: normal case that apply success, and database will be updated.
  - case 2: fail transition -> retry.

# HOW TO RUN
1. database postgres on port 5432
2. create new table "origination_statemachine"
```sql
create table origination_statemachine
(
    id    int,
    state varchar(256)
);
```
3. run the application
4. call postman test normal case
```
  curl --location 'localhost:8080/los/upload' \
  --header 'Content-Type: application/json' \
  --data '{
  "context":"huy5",
  "id":4
  }'
```
5. call postman test case retry when error
```
  curl --location 'localhost:8080/los/upload-retry' \
  --header 'Content-Type: application/json' \
  --data '{
  "context":"hoang",
  "id":4
  }'
```