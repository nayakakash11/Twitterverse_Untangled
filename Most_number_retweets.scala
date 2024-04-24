import org.apache.spark.graphx.GraphLoader
import org.apache.spark.graphx._
import org.apache.spark.sql.functions._
import org.apache.spark.graphx.lib._

val graph = GraphLoader.edgeListFile(sc, "/Users/akashnayak/Documents/higgs-retweet_network.edgelist")

val retweets = graph.degrees

val top10 = retweets.top(10)(Ordering.by(_._2))

top10.foreach {case (cc, dc) => println(s"User Id: $cc, Number of retweets received: $dc")}