//> using scala "3.2.2"
//> using platform "js"
//> using jsVersion "1.13.1"
//> using jsModuleKind "common"
//> using dep "org.typelevel::toolkit::latest.release"

import cats.effect.{IO, ExitCode}
import cats.syntax.all.*
import fs2.Stream
import fs2.io.file.{Files, Path}
import com.monovore.decline.Opts
import com.monovore.decline.effect.CommandIOApp

val args = (
  Opts.env[Int]("INPUT_NUMBER-ONE", "The first number"),
  Opts.env[Int]("INPUT_NUMBER-TWO", "The second number"),
  Opts.env[String]("GITHUB_OUTPUT", "The file of the output").map(Path.apply)
)

object index extends CommandIOApp("adder", "Summing two numbers"):
  def main = args.mapN { (one, two, path) =>
    Stream(s"result=${one + two}")
      .through(Files[IO].writeUtf8(path))
      .compile
      .drain
      .as(ExitCode.Success)
  }
