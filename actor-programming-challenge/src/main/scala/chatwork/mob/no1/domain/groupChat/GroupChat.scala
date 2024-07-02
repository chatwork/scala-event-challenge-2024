package chatwork.mob.no1.domain.groupChat

import chatwork.mob.no1.domain.userAccount.UserAccountId

import java.time.Instant

object GroupChat {
  def create(id: GroupChatId, name: GroupChatName, members: Members): GroupChat = {
    GroupChat(id, deleted = false, name, members, Messages.empty, lastUpdatedAt = Instant.now())
  }
}

/** グループチャットエンティティ。
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

  // 課題1
  def postMessage(
      messageId: MessageId,
      messageBody: MessageBody,
      executorId: UserAccountId
  ): Either[GroupChatError, GroupChat] = {
    // TODO: Post message
    throw new NotImplementedError("postMessage is not implemented")
  }

  // 課題2
  def editMessage(
      messageId: MessageId,
      messageBody: MessageBody,
      executorId: UserAccountId
  ): Either[GroupChatError, GroupChat] = {
    // TODO: Edit message
    throw new NotImplementedError("editMessage is not implemented")
  }

  // 課題3
  def deleteMessage(messageId: MessageId, executorId: UserAccountId): Either[GroupChatError, GroupChat] = {
    // TODO: Delete message
    throw new NotImplementedError("deleteMessage is not implemented")
  }
}
