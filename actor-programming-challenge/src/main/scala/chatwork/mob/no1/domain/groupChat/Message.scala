package chatwork.mob.no1.domain.groupChat

import chatwork.mob.no1.domain.userAccount.UserAccountId

import java.time.Instant

/** メッセージ値オブジェクト。
  *
  * @param id
  *   [[MessageId]]
  * @param body
  *   [[MessageBody]]
  * @param authorId
  *   [[UserAccountId]]
  * @param sentAt
  *   送信日時
  */
final case class Message(id: MessageId, body: MessageBody, authorId: UserAccountId, sentAt: Instant)

object Message {

  given Ordering[Message] with {
    override def compare(x: Message, y: Message): Int = x.id.toULID.compareTo(y.id.toULID)
  }

}
