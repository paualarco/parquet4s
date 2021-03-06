package com.github.mjakubowski84.parquet4s.core

import com.github.mjakubowski84.parquet4s.{ParquetReader, ParquetWriter}
import com.google.common.io.Files

import scala.util.Random

object WriteIncrementallyAndReadApp extends App {

  case class Data(id: Int, text: String)

  val count = 100
  val data = (1 to count).map { i => Data(id = i, text = Random.nextString(4)) }
  val path = Files.createTempDir().getAbsolutePath

  // write
  val writer = ParquetWriter.writer[Data](s"$path/data.parquet")
  try {
    data.foreach(entity => writer.write(entity))
  } finally writer.close()

  //read
  val readData = ParquetReader.read[Data](path)
  try {
    readData.foreach(println)
  } finally readData.close()

}
