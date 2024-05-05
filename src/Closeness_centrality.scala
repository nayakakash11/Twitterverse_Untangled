import org.apache.spark.graphx.{EdgeRDD, GraphLoader, VertexRDD, lib}

val time0 = System.currentTimeMillis()
val graph = GraphLoader.edgeListFile(sc, "/Users/akashnayak/Documents/Twitter_Datasets/social_network.txt")

val cnt = graph.vertices.count()

val vertexSeq = graph.vertices.map(v => v._1).collect().toSeq

val spgraph = lib.ShortestPaths.run(graph, vertexSeq)

val closeness = spgraph.vertices.map(vertex => (vertex._1 , (cnt - 1).toFloat/vertex._2.values.sum))

val top10 = closeness.top(10)(Ordering.by(_._2))

top10.foreach {case (id, cn) => println(s"User id: ${id}, Closeness Score: ${cn}") }

val time1 = System.currentTimeMillis()
println(s"Executed in ${(time1-time0)/1000.0} seconds")