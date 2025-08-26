# Replication

* Copy data from one broker to another
* Data redundancy benefit
* Increase replication factor(2,3, ...)
* Data will be copied as much as a replication factor
* If a broker is down, other broker will still have the copy of data

![img_5.png](../images/img_5.png)

![img_6.png](../images/img_6.png)

![img_7.png](../images/img_7.png)


## Set Replication Factor on Topic

![img_8.png](../images/img_8.png)


## Consumer Replica Fetching

* Kafka 2.4 or newer
* Broker setting
    * rack.id = XYZ
    * replica.selector.class
* Consumer setting
    * client.rack = XYZ

![img_9.png](../images/img_9.png)