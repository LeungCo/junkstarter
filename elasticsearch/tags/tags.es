delete /tags
PUT /tags
{
    "mappings": {
    "_doc": {
      "properties": {
        "tag": {
          "type": "text"
        },
        "count": {
          "type": "integer"
        },
        "event_ids": {
          "type": "keyword"
        },
        "group_ids": {
          "type": "keyword"
        }
      }
    }
  }
}

delete /events

GET /events/_doc/_search
{
    "query": {
        "range": {
            "fishing_date": {
                "gt": "now"
            }
        }
    }
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
    }
}

GET /events/_doc/_search

//Load the data from /Users/delonleung/git-clones/junkstarter/elasticsearch/events
curl -H "Content-Type: application/json" -XPOST 'https://e0tg4pj00k:y9jdxyprtv@junkstarter-5805005122.ap-southeast-2.bonsaisearch.net/order/_doc/_bulk?pretty' --data-binary "@parsed-events.json"
