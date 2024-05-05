import org.apache.spark.graphx.GraphLoader
import org.apache.spark.graphx._
import org.apache.spark.sql.functions._
import org.apache.spark.graphx.lib._



val graph = GraphLoader.edgeListFile(sc, "hdfs://nn.xxxxxxxxx:port_no/retweet_network.txt")

val retweets = graph.indegrees

val top10 = retweets.top(10)(Ordering.by(_._2))

top10.foreach {case (id, rt) => println(s"User Id: $id, Number of retweets received: $rt")}

