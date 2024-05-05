import org.apache.spark.graphx.GraphLoader
import scala.collection.mutable.HashMap
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx._

val graph = GraphLoader.edgeListFile(sc, "hdfs://nn.xxxxxxxxx:port_no/social_network.txt")

val normalizationFactor:Float = 1f/(graph.vertices.count()-1)

val degrees: VertexRDD[Int] = graph.degrees.persist()

val normalized = degrees.map((s => (s._1, s._2*normalizationFactor)))

val users = sc.textFile("hdfs://nn.xxxxxxxxx:port_no/Twitter_Id.txt").map { line => line.split(" ") match {
  case Array(id, name) => (id.toLong, name.toInt) } }

val ranksByUsername = users.join(normalized).map { case (id, (username, score)) => (username, score) }

val top10 = degree.top(10)(Ordering.by(_._2))

top10.foreach {case (id, dc) => println(s"User Id: $id, Degree Centrality: $dc")}

