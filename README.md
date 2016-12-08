
# ProcessKafka
Spark streaming consume kafka and insert to hadoop hive

Create table event in hive:
```
CREATE TABLE IF NOT EXISTS events (ip STRING, created_at BIGINT, agent STRING, user_agent_family STRING, user_agent_major STRING, user_agent_minor STRING, os_family STRING, os_major STRING, os_minor STRING, device_family STRING, uuid STRING, referrer STRING, url STRING, metric STRING, product_id STRING, video_id STRING, order_id BIGINT, customer_id BIGINT);
```


Run:
```
mvn clean && mvn compile && mvn package && spark-submit --class ProcessKafka target/ProcessKafka-0.0.1-SNAPSHOT-jar-with-dependencies.jar localhost:2181 page_view page_view_logs,click_logs,order_logs 1
```

