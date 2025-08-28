## @KafkaListener Error Handler

![img.png](img.png)

# What We Will Do 

* Spring default: log exception
* Able to implement our own error handler
* Scenario 
  * Publish food order

# Error Handler
* Implement custom logic
* Need error handler
* Use the error handler on @KafkaListener


# What we will Do

* Global error handler: works for all Kafka consumers
* Error handler on Spring container
* Scenario
  * Publish a random number
  * Consuming odd number throws exception
  * Handle using global error handler

