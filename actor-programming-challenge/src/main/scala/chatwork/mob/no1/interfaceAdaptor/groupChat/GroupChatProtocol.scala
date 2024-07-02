package chatwork.mob.no1.interfaceAdaptor.groupChat

import chatwork.mob.no1.domain.groupChat.GroupChatError
import chatwork.mob.no1.domain.groupChat.GroupChatId
import chatwork.mob.no1.domain.groupChat.GroupChatName
import chatwork.mob.no1.domain.groupChat.MemberId
import chatwork.mob.no1.domain.groupChat.MemberRole
import chatwork.mob.no1.domain.groupChat.MessageBody
import chatwork.mob.no1.domain.groupChat.MessageId
import chatwork.mob.no1.domain.userAccount.UserAccountId
import org.apache.pekko.actor.typed.ActorRef

object GroupChatProtocol {
  sealed trait GroupChatCommand {
    def id: GroupChatId
  }

  // ---

  final case class CreateGroupChat(
      id: GroupChatId,
      name: GroupChatName,
      ownerId: UserAccountId,
      replyTo: ActorRef[CreateGroupChatReply]
  ) extends GroupChatCommand

  sealed trait CreateGroupChatReply

  case object CreateGroupChatSuccess extends CreateGroupChatReply

  final case class CreateGroupChatFailure(reason: GroupChatError) extends CreateGroupChatReply

  // ---

  final case class RenameGroupChat(
      id: GroupChatId,
      name: GroupChatName,
      executorId: UserAccountId,
      replyTo: ActorRef[RenameGroupChatReply]
  ) extends GroupChatCommand

  sealed trait RenameGroupChatReply

  case object RenameGroupChatSuccess extends RenameGroupChatReply

  final case class RenameGroupChatFailure(reason: GroupChatError) extends RenameGroupChatReply

  // ---

  final case class AddMember(
      id: GroupChatId,
      userAccount: UserAccountId,
      role: MemberRole,
      executorId: UserAccountId,
      replyTo: ActorRef[AddMemberReply]
  ) extends GroupChatCommand

  sealed trait AddMemberReply

  final case class AddMemberSuccess(memberId: MemberId) extends AddMemberReply

  final case class AddMemberFailure(reason: GroupChatError) extends AddMemberReply

  // ---

  final case class RemoveMember(
      id: GroupChatId,
      userAccountId: UserAccountId,
      executorId: UserAccountId,
      replyTo: ActorRef[RemoveMemberReply]
  ) extends GroupChatCommand

  sealed trait RemoveMemberReply

  final case class RemoveMemberSuccess(userAccountId: UserAccountId) extends RemoveMemberReply

  final case class RemoveMemberFailure(reason: GroupChatError) extends RemoveMemberReply

  // ---

  final case class PostMessage(
      id: GroupChatId,
      messageBody: MessageBody,
      senderId: UserAccountId,
      replyTo: ActorRef[PostMessageReply]
  ) extends GroupChatCommand

  sealed trait PostMessageReply

  final case class PostMessageSuccess(messageId: MessageId) extends PostMessageReply

  final case class PostMessageFailure(reason: GroupChatError) extends PostMessageReply

  // ---

  final case class EditMessage(
      id: GroupChatId,
      messageId: MessageId,
      messageBody: MessageBody,
      senderId: UserAccountId,
      replyTo: ActorRef[EditMessageReply]
  ) extends GroupChatCommand

  sealed trait EditMessageReply

  final case class EditMessageSuccess(messageId: MessageId) extends EditMessageReply

  final case class EditMessageFailure(reason: GroupChatError) extends EditMessageReply

  // ---

  final case class DeleteMessage(
      id: GroupChatId,
      messageId: MessageId,
      senderId: UserAccountId,
      replyTo: ActorRef[DeleteMessageReply]
  ) extends GroupChatCommand

  sealed trait DeleteMessageReply

  final case class DeleteMessageSuccess(messageId: MessageId) extends DeleteMessageReply

  final case class DeleteMessageFailure(reason: GroupChatError) extends DeleteMessageReply
  // ---

  final case class DeleteGroupChat(id: GroupChatId, replyTo: ActorRef[DeleteGroupChatReply]) extends GroupChatCommand

  sealed trait DeleteGroupChatReply

  case object DeleteGroupChatSuccess extends DeleteGroupChatReply

  final case class DeleteGroupChatFailure(reason: GroupChatError) extends DeleteGroupChatReply
}
