# ProcessKafka
Spark streaming consume kafka and insert to hadoop hive

Run:
mvn clean && mvn compile && mvn package && spark-submit --class ProcessKafka target/ProcessKafka-0.0.1-SNAPSHOT-jar-with-dependencies.jar localhost:2181 page_view page_view_logs,click_logs,order_logs 1
