# Distributed Parallel Programming
HHU distributed parallel programming

#### Copy the file "foobar.txt" from the local host to a remote host 
`$ scp foobar.txt your_username@remotehost.edu:/some/remote/directory`

example: `$ scp helloWorld.jar schaefer@sollipulli:helloWorld.jar` 

#### login at sollipulli
`$ ssh schaefer@sollipulli`

#### print free nodes
`$ node print-all`

#### alloc a node
`$ node alloc node44`

#### login at node
`$ ssh node44`

#### Logout from node
`$ logout`

#### Free alloc 
`$ node free-all`

#### Run JAR:
`$ java -jar test.jar`

## Git

#### Clone data:
`$ git clone https://github.com/lidox/distributed-parallel-programming.git`
