delete /events
PUT /events
{
    "mappings": {
    "_doc": {
      "properties": {
        "fishing_date": {
          "type": "date",
          "format":"yyyy-MM-dd"
        },
        "event_count": {
          "type": "integer"
        },
        "isWeekend": {
            "type": "boolean"
        }
      }
    }
  }
}

//Load the data
curl -H "Content-Type: application/json" -XPOST 'https://e0tg4pj00k:y9jdxyprtv@junkstarter-5805005122.ap-southeast-2.bonsaisearch.net/order/_doc/_bulk?pretty' --data-binary "@parsed-events.json"

GET /events/_doc/_search

GET /events/_doc/_search
{
    "query": {
        "range": {
            "fishing_date": {
                "gt": "now"
            }
        }
    },
    "sort": [
        "fishing_date"
    ]
}

GET /events/_doc/_search
{
    "query": {
        "bool": {
            "must": [
                {
                    "range": {
                        "fishing_date": {
                            "gt": "now"
                        }
                    }
                },
                {
                    "term": {
                        "isWeekend": true
                    }
                }
            ]
        }
    },
    "sort": [
        "fishing_date"
    ]
}


