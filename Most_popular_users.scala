import org.apache.spark.graphx.GraphLoader
import org.apache.spark.graphx._
import org.apache.spark.sql.functions._
import org.apache.spark.graphx.lib._

val graph = GraphLoader.edgeListFile(sc, "social_network.edgelist")

val degree = graph.degrees

val top10 = degree.top(10)(Ordering.by(_._2))

top10.foreach {case (id, dg) => println(s"User Id: $id, Degree: $dg")}
