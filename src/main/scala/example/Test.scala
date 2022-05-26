package example

import cats.{~>, Parallel}
import cats.arrow.FunctionK
import cats.data.WriterT
import cats.effect.{Concurrent, ExitCode, IO, IOApp}
import cats.syntax.parallel._
import fs2.Stream
import scala.concurrent.duration._

abstract class Test[F[_]: Concurrent: Parallel](iof: IO ~> F) extends IOApp {
  def runF[A](fa: F[A]): IO[Unit]

  def printMemory(): Unit = {
    val rt = Runtime.getRuntime
    println("Memory " ++ List(
      "used" -> (rt.totalMemory - rt.freeMemory),
      "free" -> rt.freeMemory,
      "total" -> rt.totalMemory,
      "max" -> rt.maxMemory
    ).map { case (k, v) => s"$k: $v" }.mkString(", "))
  }

  val longString = "a" * (1024 * 1024)

  val infiniteStream: Stream[F, String] =
    Stream[F, Unit](()).repeat.map(_ => longString)

  def run(args: List[String]): IO[ExitCode] =
    runF(Parallel[F].parProductR(
      iof((IO(printMemory()) >> IO.sleep(1.second)).foreverM))(
      infiniteStream.compile.drain)
    ).as(ExitCode.Success)
}

object TestIO extends Test[IO](FunctionK.id[IO]) {
  def runF[A](fa: IO[A]): IO[Unit] = fa.void
}

object TestWriterT extends Test[WriterT[IO, Unit, *]](
  Lambda[IO ~> WriterT[IO, Unit, *]](io => WriterT(io.map(() -> _)))
) {
  def runF[A](fa: WriterT[IO, Unit, A]): IO[Unit] = fa.run.void
}
