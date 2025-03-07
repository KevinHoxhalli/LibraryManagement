# Library Management
* Java 17
* Spring Boot
* MySql 8
* Hibernate
## Requirments
* Store command
* Search Command
## Benchmark
### Environment
* i9 12900kf
* books ~300_000 and books_categories ~1_300_000 records
``
/api/books/search
{
    "categories": ["Science"]
}
* returns 110585 records in 325ms
``


`{
    "categories": ["Science", "History", "Fantasy"]
}
retrns 31941 recors in 701ms`