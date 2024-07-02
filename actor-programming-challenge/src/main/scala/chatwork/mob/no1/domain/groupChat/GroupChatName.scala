package chatwork.mob.no1.domain.groupChat

/** グループチャット名。
  *
  * @param value
  *   文字列
  */
final case class GroupChatName(private val value: String) {
  require(value.nonEmpty && value.length <= 64, "value.length must be between 1 and 64")

  def asString: String = value
}
