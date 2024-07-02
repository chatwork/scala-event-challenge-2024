package chatwork.mob.no1.domain.groupChat

/** メッセージ本文。
  *
  * @param value
  *   文字列
  */
final case class MessageBody(value: String) {
  require(value.nonEmpty)

  def asString: String = value
}
