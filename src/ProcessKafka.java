import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import scala.Array;
import scala.Tuple2;
import scala.util.Sorting;

public class ProcessKafka {
	private ProcessKafka() {
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 4) {
			System.err.println("Usage: JavaKafkaWordCount <zkQuorum> <group> <topics> <numThreads>");
			System.exit(1);
		}

		// StreamingExamples.setStreamingLogLevels();
		SparkConf sparkConf = new SparkConf().setAppName("ProcessKafka");

		// Create the context with 2 seconds batch size
		JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, new Duration(30000));
		jssc.checkpoint("hdfs://localhost:9000/RddCheckPoint");

		int numThreads = Integer.parseInt(args[3]);
		Map<String, Integer> topicMap = new HashMap<>();
		String[] topics = args[2].split(",");
		for (String topic : topics) {
			topicMap.put(topic, numThreads);
		}

		JavaPairReceiverInputDStream<String, String> messages = KafkaUtils.createStream(jssc, args[0], args[1],
				topicMap);

		JavaDStream<String> lines = messages.map(new Function<Tuple2<String, String>, String>() {
			@Override
			public String call(Tuple2<String, String> tuple2) {
				return tuple2._2();
			}
		});

		Function2<String, String, String> reduceS = new Function2<String, String, String>() {

			@Override
			public String call(String v1, String v2) throws Exception {
				return v1 + "|" + v2;
			}
		};

		lines.foreachRDD(new VoidFunction<JavaRDD<String>>() {
			@Override
			public void call(JavaRDD<String> t) throws Exception {
				if (t.collect().size() == 0) {
					return;
				}
				String jsonStr = t.reduce(reduceS);
				ObjectMapper mapper = new ObjectMapper();
				List<String> arr = new ArrayList<String>();
				for (String retval : jsonStr.split("\\|")) {
					Event event = mapper.readValue(retval, Event.class);
					if (event.ip.isEmpty()) {
						event.setIp("127.0.0.1");
					}
					String value = String.format("(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)", event.getSqlIp(),
							event.createdAt, event.getSqlAgent().replace(";", "\\;"), event.getSqlUuid(),
							event.getSqlReferrer(), event.getSqlUrl(), event.getSqlMetric(), event.getSqlProductId(),
							event.getSqlVideoId(), event.orderId, event.customerId);
					arr.add(value);
				}

				SparkSession sparkSession = SparkSession.builder().appName("ProcessingData")
						.config("spark.sql.warehouse.dir", "/user/hive/warehouse")
						.config("hive.exec.dynamic.partition.mode", "nonstrict").enableHiveSupport().getOrCreate();
				sparkSession.sql("show tables").show();
				String query = String.format("insert into table events values %s", String.join(",", arr));
				System.out.println(query);
				sparkSession.sql(query);
			}

		});

		jssc.start();
		jssc.awaitTermination();
	}
}
