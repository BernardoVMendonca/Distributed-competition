rm -f server.out
rm -f buffer.out
rm -f balancer.out
rm -f client.out
> DataBase-1.txt
> DataBase-2.txt
javac -cp . MainServer.java Buffer.java MainBalancer.java  MainClient.java
sleep 1
java MainServer.java > server.out&  
java Buffer.java > buffer.out &
java MainBalancer.java > balancer.out &
java MainClient.java > client.out