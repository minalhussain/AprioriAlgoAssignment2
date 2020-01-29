import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.util.{Failure, Success, Try}
import util.control.Breaks._
import java.io._

object Apriori {
  type ItemSet = Set[String]
  case class Item(set: ItemSet, support: Int)
  type Transaction = List[ItemSet]
  type FrequentItemSets = List[Item]
  type AssociationRule = (ItemSet, ItemSet, Double)
  
  val dataFIle = "data/mydata.txt"

  def main(args: Array[String]) = {
    println("Please enter min support : ")
    val support = scala.io.StdIn.readLine().toDouble
    
    println("Please enter min confidence : ")
    val confidence = scala.io.StdIn.readLine().toDouble
      
    println("Please enter the desired data : (press enter 2 times after providing data)")
    val writer=new PrintWriter(new File(dataFIle))  
    var lines:Array[String] = Array()
    var text = ""
    breakable {
        while (true) {
          var line = scala.io.StdIn.readLine()
          if(line != ""){
              text += line+"\n"
          }else{
              break
          }
        }
    }
    
    writer.write(text)
    writer.close
    
    readTransactionsFromFile(dataFIle) match {
      case Success(set) => {
          println("Minimum support: " + support)
          println("Minimum confidence: " + confidence + "\n")
          val frequentItems = frequentItemSetsFormation(set, support)
          printRules(associationRulesFormation(frequentItems, confidence))
      }
      case Failure(e) => println(e.getMessage)
    }
  }

  def printRules(rules: List[AssociationRule]): Unit = {
    println("Association rules:")
    rules.foreach {rule =>
      print("( ")
      rule._1.foreach(x => print(x + " "))
      print(") --> ( ")
      rule._2.foreach(l => print(l + " "))
      print(") = ")
      print(rule._3 + "\n")
    }
  }
  
  def frequentItemSetsFormation(transactionList: Transaction, minsupport: Double): List[Item]  = {
    // Map singletons with frequency
    val occuranceMap = transactionList.flatten.foldLeft(Map[String,Int]() withDefaultValue 0) {
      (m,x) => m + (x -> (1 + m(x)))
    }

    val noOfRows = (transactionList.size * minsupport).toDouble
    val currentSet = occuranceMap.filter(item => item._2 >= noOfRows).toList
    val items = currentSet.map(tuple => Item(Set(tuple._1), tuple._2))
    println("1 Frequent ItemSet :\n" + items + "\n")

    var k = 2
    val result = ListBuffer(items)

    breakable {
      while (true) {
        val nextItemSetK = items.flatMap(_.set)
          .combinations(k)
          .map(_.toSet)
          .toList

        val supportedK = nextItemSetK.map(set => Item(set, calculateSupport(set, transactionList)))
          .filter(_.support >= noOfRows)

        if (supportedK.isEmpty)
          break

        result += supportedK
        println(k+" Frequent ItemSets :\n" + supportedK + "\n")
        
        k = k + 1
      }
    }
    result.flatten
      .toList
  }

  def associationRulesFormation(items: FrequentItemSets, conf: Double): List[AssociationRule] = {
    val map = items.map(item => item.set -> item.support)
      .toMap

    val rules = items.map { item =>
      val set = item.set
      set.subsets().filter(x => (x.nonEmpty && x.size != set.size))
        .map {subset =>
          (subset, set diff subset, map(set).toDouble/map(subset).toDouble)
        }.toList
    }.flatten

    rules.filter(rule => rule._3 >= conf)
  }

  def calculateSupport(set: ItemSet, transaction: Transaction): Int =
    transaction.count(line => set.subsetOf(line))


  def readTransactionsFromFile(file: String): Try[Transaction] = Try {
    Source.fromFile(file)
      .getLines()
      .map(_.split(" ").toSet)
      .toList
  }

}