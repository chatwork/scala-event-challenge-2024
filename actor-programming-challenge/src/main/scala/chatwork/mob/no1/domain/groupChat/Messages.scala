package chatwork.mob.no1.domain.groupChat

/** メッセージ集合のファーストクラスコレクション。
  *
  * @param values
  *   メッセージ集合
  */
class Messages(private val values: Vector[Message]) {
  def add(message: Message): Messages                        = Messages(values :+ message)
  def removeByMessageId(messageId: MessageId): Messages      = Messages(values.filterNot(_.id == messageId))
  def containsByMessageId(messageId: MessageId): Boolean     = values.exists(_.id == messageId)
  def findByMessageId(messageId: MessageId): Option[Message] = values.find(_.id == messageId)
  def editByMessageId(messageId: MessageId, messageBody: MessageBody): Messages =
    Messages(values.map(m => if (m.id == messageId) m.copy(body = messageBody) else m))
  def size: Int                 = values.size
  def isEmpty: Boolean          = values.isEmpty
  def nonEmpty: Boolean         = values.nonEmpty
  def toVector: Vector[Message] = values
}

object Messages {
  def empty: Messages = Messages(Vector.empty)
}
