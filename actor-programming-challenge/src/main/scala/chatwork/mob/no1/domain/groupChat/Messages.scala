package chatwork.mob.no1.domain.groupChat

/** メッセージ集合のファーストクラスコレクション。
  *
  * @param values
  *   メッセージ集合
  */
class Messages(private val values: Vector[Message]) {
  /**
   * メッセージを追加する。
   *
   * @param message [[Message]]
   * @return [[Messages]]
   */
  def add(message: Message): Messages                        = Messages(values :+ message)

  /**
   * メッセージを削除する。
   *
   * @param messageId [[MessageId]]
   * @return [[Messages]]
   */
  def removeByMessageId(messageId: MessageId): Messages      = Messages(values.filterNot(_.id == messageId))

  /**
   * メッセージIDを指定してメッセージが含まれているかどうかを判定する。
   *
   * @param messageId [[MessageId]]
   * @return メッセージが含まれている場合はtrue
   */
  def containsByMessageId(messageId: MessageId): Boolean     = values.exists(_.id == messageId)

  /**
   * メッセージIDを指定してメッセージを取得する。
   *
   * @param messageId [[MessageId]]
   * @return メッセージ
   */
  def findByMessageId(messageId: MessageId): Option[Message] = values.find(_.id == messageId)

  /**
   * メッセージIDを指定してメッセージを編集する。
   *
   * @param messageId [[MessageId]]
   * @param messageBody [[MessageBody]]
   * @return [[Messages]]
   */
  def editByMessageId(messageId: MessageId, messageBody: MessageBody): Messages =
    Messages(values.map(m => if (m.id == messageId) m.copy(body = messageBody) else m))

  /**
   * メッセージ数を取得する。
   *
   * @return メッセージ数
   */
  def size: Int                 = values.size

  /**
   * 当該コレクションが空かどうかを判定する。
   *
   * @return 空の場合はtrue
   */
  def isEmpty: Boolean          = values.isEmpty

  /**
   * 当該コレクションが空でないかどうかを判定する。
   *
   * @return 空でない場合はtrue
   */
  def nonEmpty: Boolean         = values.nonEmpty

  /**
   * 当該コレクションをVectorに変換する。
   *
   * @return [[Vector]]
   */
  def toVector: Vector[Message] = values
}

object Messages {
  def empty: Messages = Messages(Vector.empty)
}
