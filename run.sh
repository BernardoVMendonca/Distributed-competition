javac -cp . MainServer.java Buffer.java MainBalancer.java  MainClient.java&
java MainServer.java > server.out&  
java Buffer.java > buffer.out &
java MainBalancer.java > balancer.out &
java MainClient.java > client.out