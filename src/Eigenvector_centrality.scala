import org.apache.spark.graphx.{Graph,GraphLoader, VertexRDD}
import org.apache.spark.{SparkConf, SparkContext}

val time0 = System.currentTimeMillis()
val graph = GraphLoader.edgeListFile(sc, "followers-new.txt")

val nodeNumber = graph.numVertices
val previousValue = graph.mapVertices((vId, eigenvalue) => 1.0 / nodeNumber)
val zeroValue = graph.mapVertices((vId, eigenvalue) => 0.0)
var iter = 1000
var oldValue = previousValue
var absolute = oldValue
var newVertices = previousValue.vertices
var convergence = 10d
var flag = true

var oldGraph = graph.outerJoinVertices(zeroValue.vertices) { (vid, deg, eigenValue) => eigenValue.getOrElse(0.0) }.mapEdges( e => e.attr.toDouble)

do {
  val outDegrees: VertexRDD[Int] = graph.outDegrees
  if (flag==false) {oldValue = zeroValue}else{flag=false}
  var rankGraph = oldValue.outerJoinVertices(graph.outDegrees) { (vid, eigenvalue, deg) => deg.getOrElse(0)}

    .mapTriplets(e => 1.0 / e.srcAttr)
    .outerJoinVertices(newVertices) { (vid, deg, eigenValue) => eigenValue.getOrElse(0.0) }
  newVertices = rankGraph.aggregateMessages[(Double)](
    triplet => { triplet.sendToDst(triplet.srcAttr*triplet.attr) },
    (a, b) => (a + b) )
  rankGraph = rankGraph.outerJoinVertices(newVertices){ (vid, oldvalue, newvalue) => newvalue.getOrElse(0) }
  iter -= 1

  convergence = oldGraph
    .outerJoinVertices(rankGraph.vertices){(vid, oldvalue, newvalue)=> math.abs(newvalue.get-oldvalue)}
    .vertices.map(x => x._2).sum()
  oldGraph = rankGraph
  println(s"Convergence is at ${convergence}")

  if (iter==0){
    val time1 = System.currentTimeMillis()
    println(s"Executed in ${(time1-time0)/1000.0} seconds")

    val top10 = closeness.top(10)(Ordering.by(_._2))
    top10.foreach {case (id, evc) => println(s"User id: ${id}, Eigenvector Centrality Score: ${evc}") }

  }
}
while (convergence > 0.00015 && iter!=0)