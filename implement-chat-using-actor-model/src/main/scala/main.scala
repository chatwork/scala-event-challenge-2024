import java.security.MessageDigest

@main
def main(): Unit = {
  val values = ??? // 実装してください
  println(computeSha256Hex(values))
}

val md = MessageDigest.getInstance("SHA-256")
def computeSha256Hex(values: Seq[String]): String = {
  require(values.nonEmpty, "values must not be empty")
  require(values.length == 100, "values must have 100 elements")
  val s = values.mkString(", ")
  val hash = md.digest(s.getBytes("UTF-8"))
  hash.map("%02x".format(_)).mkString
}