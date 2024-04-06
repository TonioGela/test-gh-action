//> using platform js
//> using scala 3.4.1
//> using toolkit typelevel::0.1.25

//> using jsVersion 1.16.0
//> using jsEsVersionStr es2021
//> using jsModuleKind common
//> using jsMode fullLinkJs
//> using jsModuleKind smallestmodules
//> using jsAvoidClasses true
//> using jsAvoidLetsAndConsts true

import cats.effect.{ExitCode, IO}
import cats.syntax.all.*
import fs2.io.file.{Files, Path}
import fs2.Stream
import com.monovore.decline.Opts
import com.monovore.decline.effect.CommandIOApp

val args = (
  Opts.env[Int]("INPUT_NUMBER-ONE", "The first number"),
  Opts.env[Int]("INPUT_NUMBER-TWO", "The second number"),
  Opts.env[String]("GITHUB_OUTPUT", "The file of the output").map(Path(_))
)

object index extends CommandIOApp("adder", "Summing two numbers"):
  def main = args.mapN { (one, two, path) =>
    Stream(s"result=${one + two}")
      .through(Files[IO].writeUtf8(path))
      .compile
      .drain
      .as(ExitCode.Success)
  }
