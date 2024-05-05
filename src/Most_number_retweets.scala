import org.apache.spark.graphx.GraphLoader
import org.apache.spark.graphx._
import org.apache.spark.sql.functions._
import org.apache.spark.graphx.lib._



val graph = GraphLoader.edgeListFile(sc, "retweet_network.edgelist")

val retweets = graph.indegrees

val top10 = retweets.top(10)(Ordering.by(_._2))

top10.foreach {case (id, rt) => println(s"User Id: $id, Number of retweets received: $rt")}


def GlobalAndAverageCC(graph: Graph[(Int, Int, List[Int], List[Int], Int, Double), PartitionID]): (Double, Double) = {
  val triplets = graph.mapVertices((id, attr) => attr._2)
  val triangles = graph.mapVertices((id, attr) => attr._5)
  val localCC = graph.mapVertices((id, attr) => attr._6)

  val numTriangles = triangles.vertices.values.sum()
  val numTriplets = triplets.vertices.values.sum()
  val globalClusteringCoefficient = (3 * numTriangles) / numTriplets
  val averageClusteringCoefficient = localCC.vertices.values.sum() / localCC.vertices.values.count()
  (globalClusteringCoefficient, averageClusteringCoefficient)
}

val test = graph.pregel((0, 0, List(1, 2, 3), List(1, 2, 3), 0, Double.PositiveInfinity), maxIterations = 2, activeDirection = EdgeDirection.Either)(vertexProgram, sendMessage, messageCombiner)

def messageCombiner(msg1: (Int, Int, List[Int], List[Int], Int, Double),
                    msg2: (Int, Int, List[Int], List[Int], Int, Double)):
(Int, Int, List[Int], List[Int], Int, Double) = {
  val degree = mergeDegreeMessage(msg1._1, msg2._1)
  val neighbor = mergeNeighborMessage(msg1._3, msg2._3)
  val neighborIntersect = mergeNeighborMessage(msg1._4, msg2._4)
  (degree, 0, neighbor, neighborIntersect, 0, 0.0)
}