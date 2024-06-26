import org.apache.spark.graphx.GraphLoader
import org.apache.spark.graphx._
import org.apache.spark.sql.functions._

val graph = GraphLoader.edgeListFile(sc, "hdfs://nn.xxxxxxxxx:port_no/social_network.txt")

val pageRank = graph.pageRank(0.85).vertices

val mostPopularPerson = pageRank.max()(Ordering.by(_._2))

println(s"The most popular person is User ${mostPopularPerson._1} with PageRank ${mostPopularPerson._2}")
