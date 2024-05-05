# Twitterverse_Untangled

#### Team Members Information - Aditya Raj (22BDS002), Akash Nayak (22BDS003)

#### IMPORTANT: Please setup Hadoop, Spark and GraphX using documentation from Apache <br>
#### Download the src folder and run everything on spark-shell.sh after single-client/multi-node setup configuration depending on the algorithm complexity<br>

#### Project Report Link - [https://docs.google.com/document/d/1eKzupUwlNBVfo8zvZM7SBaJVt7C00i4ZdpvMbNbRuJ0/edit](https://docs.google.com/document/d/18nTFf80qHTaip3tbqqrWkevjoprEKCucx80OEa2rxX0/edit?usp=sharing) <br>


## GraphX Programs

The source code for all the programs are located in src/.

This repository consists of eight programs all written in scala. We initially wrote these scala programs as objects but converted it to line-by-line scala code for easy execution in the spark-shell. For converting the scala programs into jar files and for execution on Hadoop(MapReduce), you must use sbt for conversion and then spark-submit.

Algorithms Utilised - <br>
1. PageRank
2. Connected Components
3. Triangle Counting
4. Shortest Paths
<br>


Dataset File Names:- <br>
1. Social Network Dataset - social_network.txt
2. Retweet Network Dataset - retweet_network.txt
3. Mention Network Datase - mention_network.txt
4. Twitter ID - Twitter_ID.txt
<br>

Due to Github's Upload Size limitation we have uploaded the datasets on Google Drive. Link - [https://drive.google.com/drive/folders/1BKLjBFBPrYlw1Xd8ufVSIHavvok-QNrd?usp=sharing](https://drive.google.com/drive/folders/1BKLjBFBPrYlw1Xd8ufVSIHavvok-QNrd?usp=sharing)

After downloading these files, upload the files onto HDFS using the HDFS -put (insert local address) (insert hadoop file address). After which replace the generic address in the source code with the address of the file in your setup HDFS. 


