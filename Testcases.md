### Test Cases

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'CREATE /devices 
content-type : application/json 
 
{"type" : "COMPUTER", "name" : "A1"}'

{"msg":"Successfully added A1"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'CREATE /devices 
content-type : application/json 
 
{"type" : "COMPUTER", "name" : "A2"}'

{"msg":"Successfully added A2"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'CREATE /devices 
content-type : application/json 
 
{"type" : "COMPUTER", "name" : "A3"}'

{"msg":"Successfully added A3"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'CREATE /devices'

{"msg":"Invalid Command"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'CREATE /devices 
content-type : application/json 
 
{"type" : "PHONE", "name" : "A3"}'

{"msg":"type 'PHONE' is not supported"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'CREATE /devices 
content-type : application/json 
 
{"type" : "COMPUTER", "name" : "A1"}'

{"msg":"Device 'A1' already exists"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'CREATE /devices 
content-type : application/json 
 
{"type" : "REPEATER", "name" : "R1"}'

{"msg":"Successfully added R1"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'CREATE /devices 
content-type : application/json 
 
{"type" : "REPEATER", "name" : "R2"}'

{"msg":"Successfully added R2"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'MODIFY /devices/A1/strength 
content-type : application/json 
 
{"value": "Helloworld"}'

{"msg":"Value should be an integer"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'MODIFY /devices/A10/strength 
content-type : application/json'
 
{"value": "Helloworld"}'

{"msg":"Device not found"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'MODIFY /devices/A1/strength 
content-type : application/json
 
{"value": 2}'

{"msg":"Successfully defined strength"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'CREATE /connections 
content-type : application/json 
 
{"source" : "A1", "targets" : ["A2", "A3"]}'

{"msg":"Successfully connected"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'CREATE /connections 
content-type : application/json 
 
{"source" : "A2", "targets" : ["A3"]}'

{"msg":"Successfully connected"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'CREATE /connections 
content-type : application/json 
 
{"source" : "A3", "targets" : ["R1"]}'

{"msg":"Successfully connected"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'CREATE /connections 
content-type : application/json 
 
{"source" : "A3"}'

{"msg":"Invalid command syntax"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'CREATE /connections'

{"msg":"Invalid Command"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'CREATE /connections 
content-type : application/json 
 
{"source" : "A8", "targets" : ["A1"]}'

{"msg":"Node 'A8' not found"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'FETCH /info-routes?from=A1&to=A2'

{"msg": "Route is A2->A2"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'FETCH /info-routes?from=A1&to=A3'

{"msg": "A1->A3"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'FETCH /info-routes?from=A1&to=A4'

{"msg": "A1->A3->A4"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'FETCH /info-routes?from=A1&to=A6'

{"msg": "Route not found"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'FETCH /info-routes?from=A1&to=R1'

{"msg": "Route cannot be calculated with repeater"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'FETCH /info-routes?from=A1'

{"msg": "Invalid Request"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'FETCH /info-routes'

{"msg": "Invalid Request"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'FETCH /info-routes?from=A1&to=A10'

{"msg": "Node 'A10' not found"}

curl --request POST 'http://localhost:8080/ajiranet/process' \
--header 'Content-Type: text/plain' \
--data 'FETCH /devices'

{
    "devices": [
        {
            "type": "COMPUTER",
            "name": "A1"
        },
        {
            "type": "COMPUTER",
            "name": "A2"
        },
        {
            "type": "COMPUTER",
            "name": "A3"
        },
        {
            "type": "REPEATER",
            "name": "R2"
        },
        {
            "type": "REPEATER",
            "name": "R1"
        },
        {
            "type": "COMPUTER",
            "name": "A4"
        },
        {
            "type": "COMPUTER",
            "name": "A6"
        }
    ]
}
