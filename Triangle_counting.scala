import org.apache.spark.graphx.{GraphLoader, PartitionStrategy}
import org.apache.spark.graphx._
import org.apache.spark.sql.functions._
import org.apache.spark.graphx.lib._

val time0 = System.currentTimeMillis()
val graph = GraphLoader.edgeListFile(sc, "social_network.edgelist", true).partitionBy(PartitionStrategy.RandomVertexCut)

val triCounts = graph.triangleCount().vertices

val users = sc.textFile("Twitter_Id.txt").map { line =>
  line.split(" ") match { case Array(id, name) => (id.toLong, name.toInt) } }

val triCountByUsername = users.join(triCounts).map { case (id, (username, tc)) => (username, tc) }

val top10 = triCountByUsername.top(10)(Ordering.by(_._2))

top10.foreach { case (id, tri) => println(s"User Id: $id, Number of triangles: $tri") }

val time1 = System.currentTimeMillis()
println(s"Executed in ${(time1-time0)/1000.0} seconds")



