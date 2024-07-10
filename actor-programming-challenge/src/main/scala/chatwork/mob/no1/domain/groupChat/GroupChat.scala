package chatwork.mob.no1.domain.groupChat

import chatwork.mob.no1.domain.userAccount.UserAccountId

import java.time.Instant

object GroupChat {
  def create(id: GroupChatId, name: GroupChatName, members: Members): GroupChat = {
    GroupChat(id, deleted = false, name, members, Messages.empty, lastUpdatedAt = Instant.now())
  }
}

/** グループチャットエンティティ。
  *
  * @param id
  *   [[GroupChatId]]
  * @param deleted
  *   削除済みかどうか
  * @param name
  *   [[GroupChatName]]
  * @param members
  *   [[Members]]
  * @param messages
  *   [[Messages]]
  * @param lastUpdatedAt
  *   最終更新日時
  */
final case class GroupChat private (
    id: GroupChatId,
    deleted: Boolean,
    name: GroupChatName,
    members: Members,
    messages: Messages,
    lastUpdatedAt: Instant
) {
  require(members.containsAdministrator, "members must contain administrator")

  /** グループチャットの名前を変更する。
    *
    * @param name
    *   新しいグループチャット名
    * @param executorId
    *   実行者のユーザーアカウントID
    * @return
    *   エラーまたは新しいグループチャット エラーケース:
    *   - [[GroupChatError.AlreadyDeletedError]] グループチャットが削除済みの場合
    *   - [[GroupChatError.NotAdministratorError]] 実行者が管理者ではない場合
    *   - [[GroupChatError.AlreadyExistsNameError]] 既に同じ名前のグループチャットが存在する場合 正常ケース:
    *   - [[GroupChat]] 新しいグループチャット
    */
  def rename(name: GroupChatName, executorId: UserAccountId): Either[GroupChatError, GroupChat] = {
    if (deleted) {
      Left(GroupChatError.AlreadyDeletedError(id))
    } else if (!members.isAdministrator(executorId)) {
      Left(GroupChatError.NotAdministratorError(id, executorId))
    } else if (name == this.name) {
      Left(GroupChatError.AlreadyExistsNameError(id))
    } else {
      Right(copy(name = name, lastUpdatedAt = Instant.now()))
    }
  }

  /** グループチャットにメンバーを追加する。
    *
    * @param memberId
    *   [[MemberId]]
    * @param userAccountId
    *   [[UserAccountId]]
    * @param role
    *   [[MemberRole]]
    * @param executorId
    *   実行者の[[UserAccountId]]
    * @return
    *   エラーまたは新しいグループチャット エラーケース:
    *   - [[GroupChatError.AlreadyDeletedError]] グループチャットが削除済みの場合
    *   - [[GroupChatError.NotAdministratorError]] 実行者が管理者ではない場合
    *   - [[GroupChatError.AlreadyExistsMemberError]] 既に同じユーザーアカウントIDのメンバーが存在する場合 正常ケース:
    *   - [[GroupChat]] 新しいグループチャット
    */
  def addMember(
      memberId: MemberId,
      userAccountId: UserAccountId,
      role: MemberRole,
      executorId: UserAccountId
  ): Either[GroupChatError, GroupChat] = {
    if (deleted) {
      Left(GroupChatError.AlreadyDeletedError(id))
    } else if (!members.isAdministrator(executorId)) {
      Left(GroupChatError.NotAdministratorError(id, executorId))
    } else if (members.containsByUserAccountId(userAccountId)) {
      Left(GroupChatError.AlreadyExistsMemberError(id))
    } else {
      val member   = Member(memberId, userAccountId, role)
      val newState = copy(members = members.add(member), lastUpdatedAt = Instant.now())
      Right(newState)
    }
  }

  /** グループチャットからメンバーを削除する。
    *
    * @param userAccountId
    *   [[UserAccountId]]
    * @param executorId
    *   実行者の[[UserAccountId]]
    * @return
    *   エラーまたは新しいグループチャット エラーケース:
    *   - [[GroupChatError.AlreadyDeletedError]] グループチャットが削除済みの場合
    *   - [[GroupChatError.NotAdministratorError]] 実行者が管理者ではない場合
    *   - [[GroupChatError.NotFoundMemberError]] メンバーが見つからない場合 正常ケース:
    *   - [[GroupChat]] 新しいグループチャット
    */
  def removeMember(
      userAccountId: UserAccountId,
      executorId: UserAccountId
  ): Either[GroupChatError, GroupChat] = {
    if (deleted) {
      Left(GroupChatError.AlreadyDeletedError(id))
    } else if (!members.isAdministrator(executorId)) {
      Left(GroupChatError.NotAdministratorError(id, executorId))
    } else if (!members.containsByUserAccountId(userAccountId)) {
      Left(GroupChatError.NotFoundMemberError(id, userAccountId))
    } else {
      Right(
        copy(
          members = members.removeByUserAccountId(userAccountId),
          lastUpdatedAt = Instant.now()
        )
      )
    }
  }

  /** 課題1:メッセージを投稿する。
    *
    * @param messageId
    *   [[MessageId]]
    * @param messageBody
    *   [[MessageBody]]
    * @param executorId
    *   実行者の[[UserAccountId]]
    * @return
    *   エラーまたは新しいグループチャット エラーケース:
    *   - [[GroupChatError.AlreadyDeletedError]] グループチャットが削除済みの場合
    *   - [[GroupChatError.NotMemberError]] 実行者がメンバーではない場合 正常ケース:
    *   - [[GroupChat]] 新しいグループチャット
    */
  def postMessage(
      messageId: MessageId,
      messageBody: MessageBody,
      executorId: UserAccountId
  ): Either[GroupChatError, GroupChat] = {
    if (deleted) {
      Left(GroupChatError.AlreadyDeletedError(id))
    } else if (!members.containsByUserAccountId(executorId)) {
      Left(GroupChatError.NotMemberError(id, executorId))
    } else {
      val message  = Message(messageId, messageBody, executorId, Instant.now())
      val newState = copy(messages = messages.add(message), lastUpdatedAt = Instant.now())
      Right(newState)
    }
  }

  /** 課題3:メッセージを編集する。
    *
    * @param messageId
    *   [[MessageId]]
    * @param messageBody
    *   [[MessageBody]]
    * @param executorId
    *   実行中の[[UserAccountId]]
    * @return
    *   エラーまたは新しいグループチャット エラーケース:
    *   - [[GroupChatError.AlreadyDeletedError]] グループチャットが削除済みの場合
    *   - [[GroupChatError.NotFoundMessageError]] メッセージが見つからない場合
    *   - [[GroupChatError.NotAuthorError]] 実行者がメッセージの投稿者ではない場合 正常ケース:
    *   - [[GroupChat]] 新しいグループチャット
    */
  def editMessage(
      messageId: MessageId,
      messageBody: MessageBody,
      executorId: UserAccountId
  ): Either[GroupChatError, GroupChat] = {
    if (deleted) Left(GroupChatError.AlreadyDeletedError)
    else {
      messages.findByMessageId(messageId) match {
        case Some(message) =>
          if (message.authorId == executorId)
            Right(
              copy(
                messages = messages.editByMessageId(messageId, messageBody)
              )
            )
          else Left(GroupChatError.NotAuthorError)
      }
    }
    // TODO: Edit message
    throw new NotImplementedError("editMessage is not implemented")
  }

  /** 課題3:メッセージを削除する。
    *
    * @param messageId
    *   [[MessageId]]
    * @param executorId
    *   実行中の[[UserAccountId]]
    * @return
    *   エラーまたは新しいグループチャット エラーケース:
    *   - [[GroupChatError.AlreadyDeletedError]] グループチャットが削除済みの場合
    *   - [[GroupChatError.NotFoundMessageError]] メッセージが見つからない場合
    *   - [[GroupChatError.NotAuthorError]] 実行者がメッセージの投稿者ではない場合 正常ケース:
    *   - [[GroupChat]] 新しいグループチャット
    */
  def deleteMessage(messageId: MessageId, executorId: UserAccountId): Either[GroupChatError, GroupChat] = {
    // TODO: Delete message
    throw new NotImplementedError("deleteMessage is not implemented")
  }
}
