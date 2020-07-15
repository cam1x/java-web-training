# Object oriented programming

## Requirements
* When implementing the inheritance hierarchy, the derived class must expand the superclass with new properties, for which you need to understand the subject area of ​​the task.
* When describing fields and developing methods if null is returned, use the Optional class.
* Entity classes should be separated from classes with business logic methods.
* When creating objects, use the Factory Method pattern.
* Use enum to describe fields with a limited set of values.
* Use the Repository template to store a collection of objects.
* Develop methods for adding, deleting repository objects.
* Develop specifications for finding objects in the repository. By ID, by name, by others (for example: find all objects whose names begin with a given letter, find all objects whose identifiers are enclosed in a given interval, etc.)
* To sort repository objects, use the Comparator interface implementation and its capabilities. In particular, thenComparing.
* All application classes must be well-structured in packages.
* Code design must comply with Java Code Convention.
* The parameters necessary for creating objects must be entered by reading data from a file (.txt). Among the data in the file should be deliberately incorrect information. There must be processing of invalid object initialization data.
* Use Log4J2 to record logs.
* The code should be covered by Unit tests. Use TestNG.

## Condition
1. Determine the hierarchy of railway rolling stock. 
2. Create a passenger train. 
3. Calculate the number of passengers and luggage. 
4. Sort wagons based on one or more parameters. 
5. Find the wagons in the train corresponding to a given range of parameters of the number of passengers.