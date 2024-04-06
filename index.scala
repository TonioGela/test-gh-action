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
