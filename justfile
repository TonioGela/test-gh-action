# Prints this list
_default:
    @just --list --unsorted

# Compiles the action with a specific scala-cli version
compile cli-version="1.0.0-RC2":
    scala-cli --power --cli-version {{cli-version}} package -f index.scala