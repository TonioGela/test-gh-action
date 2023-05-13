//> using scala "3.2.2"
//> using platform "js"
//> using jsVersion "1.13.1"
//> using jsModuleKind "common"
//> using dep "org.typelevel::toolkit::latest.release"

import cats.effect.{ExitCode, IO, IOApp}
import cats.effect.std.Env
import fs2.io.file.{Files, Path}
import fs2.Stream

def getInput(input: String): IO[Option[String]] =
  Env[IO].get(s"INPUT_${input.toUpperCase.replace(' ', '_')}")

def outputFile: IO[Path] =
  Env[IO].get("GITHUB_OUTPUT").map(_.get).map(Path.apply) // unsafe Option.get

def setOutput(name: String, value: String): IO[Unit] =
  outputFile.flatMap(path =>
    Stream[IO, String](s"${name}=${value}")
      .through(Files[IO].writeUtf8(path))
      .compile
      .drain
  )

object index extends IOApp.Simple:
  def run = for {
    number1 <- getInput("number-one").map(_.get.toInt) // unsafe Option.get
    number2 <- getInput("number-two").map(_.get.toInt) // unsafe Option.get
    _ <- setOutput("result", s"${number1 + number2}")
  } yield ()
