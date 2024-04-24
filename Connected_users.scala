import org.apache.spark.graphx.GraphLoader
import org.apache.spark.graphx._
import org.apache.spark.sql.functions._

val graph = GraphLoader.edgeListFile(sc, "higgs-social_network.edgelist")

val cc = graph.connectedComponents().vertices

val users = sc.textFile("TwitterId.txt").map {line => val fields = line.split(" ")(fields(0).toLong, fields(1))}

val join = users.join(cc).map {case (id, (username, cc)) => (username, cc)}

val grp = join.groupBy{ case(_, (cc)) => cc}.mapValues(iter => iter.size)

val top10 = grp.top(10)(Ordering.by(_._2))

top10 foreach {case (cc, size) => println(s"Component ID: $cc, Size: $size")}