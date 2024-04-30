import org.apache.spark.graphx.GraphLoader
import org.apache.spark.graphx._
import org.apache.spark.sql.functions._

val graph = GraphLoader.edgeListFile(sc, "social_network.edgelist")

val pageRank = graph.pageRank(0.85).vertices

val mostInfluentialPerson = pageRank.max()(Ordering.by(_._2))

println(s"The most influential person is User ${mostInfluentialPerson._1} with PageRank ${mostInfluentialPerson._2}")
