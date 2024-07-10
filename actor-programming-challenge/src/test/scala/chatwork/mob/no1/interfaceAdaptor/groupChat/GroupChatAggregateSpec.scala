package chatwork.mob.no1.interfaceAdaptor.groupChat

import chatwork.mob.no1.domain.groupChat.GroupChatId
import chatwork.mob.no1.domain.groupChat.GroupChatName
import chatwork.mob.no1.domain.groupChat.MemberRole
import chatwork.mob.no1.domain.groupChat.MessageBody
import chatwork.mob.no1.domain.userAccount.UserAccountId
import org.apache.pekko.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import org.scalatest.freespec.AnyFreeSpecLike

// FIXME: 時間があれば異常系のテストも追加してみてください。
class GroupChatAggregateSpec extends ScalaTestWithActorTestKit with AnyFreeSpecLike {

  "GroupChatAggregate" - {
    "create" in {
      val id      = GroupChatId.generate()
      val name    = GroupChatName("name")
      val adminId = UserAccountId.generate()

      val aggregate = spawn(GroupChatAggregate(id))

      val probe = createTestProbe[GroupChatProtocol.CreateGroupChatReply]()
      aggregate ! GroupChatProtocol.CreateGroupChat(id, name, adminId, probe.ref)
      probe.expectMessage(GroupChatProtocol.CreateGroupChatSuccess)
    }
    "rename" in {
      val id      = GroupChatId.generate()
      val name    = GroupChatName("name")
      val adminId = UserAccountId.generate()

      val aggregate = spawn(GroupChatAggregate(id))

      val createProbe = createTestProbe[GroupChatProtocol.CreateGroupChatReply]()
      aggregate ! GroupChatProtocol.CreateGroupChat(id, name, adminId, createProbe.ref)
      createProbe.expectMessage(GroupChatProtocol.CreateGroupChatSuccess)

      val newName     = GroupChatName("new name")
      val renameProbe = createTestProbe[GroupChatProtocol.RenameGroupChatReply]()
      aggregate ! GroupChatProtocol.RenameGroupChat(id, newName, adminId, renameProbe.ref)
      renameProbe.expectMessage(GroupChatProtocol.RenameGroupChatSuccess)
    }
    "addMember" in {
      val id      = GroupChatId.generate()
      val name    = GroupChatName("name")
      val adminId = UserAccountId.generate()

      val aggregate = spawn(GroupChatAggregate(id))

      val createProbe = createTestProbe[GroupChatProtocol.CreateGroupChatReply]()
      aggregate ! GroupChatProtocol.CreateGroupChat(id, name, adminId, createProbe.ref)
      createProbe.expectMessage(GroupChatProtocol.CreateGroupChatSuccess)

      val memberId       = UserAccountId.generate()
      val addMemberProbe = createTestProbe[GroupChatProtocol.AddMemberReply]()
      aggregate ! GroupChatProtocol.AddMember(id, memberId, MemberRole.Member, adminId, addMemberProbe.ref)
      addMemberProbe.expectMessageType[GroupChatProtocol.AddMemberSuccess]
    }
    "removeMember" in {
      val id      = GroupChatId.generate()
      val name    = GroupChatName("name")
      val adminId = UserAccountId.generate()

      val aggregate = spawn(GroupChatAggregate(id))

      val createProbe = createTestProbe[GroupChatProtocol.CreateGroupChatReply]()
      aggregate ! GroupChatProtocol.CreateGroupChat(id, name, adminId, createProbe.ref)
      createProbe.expectMessage(GroupChatProtocol.CreateGroupChatSuccess)

      val memberId       = UserAccountId.generate()
      val addMemberProbe = createTestProbe[GroupChatProtocol.AddMemberReply]()
      aggregate ! GroupChatProtocol.AddMember(id, memberId, MemberRole.Member, adminId, addMemberProbe.ref)
      addMemberProbe.expectMessageType[GroupChatProtocol.AddMemberSuccess]

      val removeMemberProbe = createTestProbe[GroupChatProtocol.RemoveMemberReply]()
      aggregate ! GroupChatProtocol.RemoveMember(id, memberId, adminId, removeMemberProbe.ref)
      val actualUserAccountId = removeMemberProbe.expectMessageType[GroupChatProtocol.RemoveMemberSuccess].userAccountId
      assert(actualUserAccountId == memberId)
    }
    // 課題1
    "postMessage" in {
      val id      = GroupChatId.generate()
      val name    = GroupChatName("name")
      val adminId = UserAccountId.generate()

      val aggregate = spawn(GroupChatAggregate(id))

      val createProbe = createTestProbe[GroupChatProtocol.CreateGroupChatReply]()
      aggregate ! GroupChatProtocol.CreateGroupChat(id, name, adminId, createProbe.ref)
      createProbe.expectMessage(GroupChatProtocol.CreateGroupChatSuccess)

      val memberId       = UserAccountId.generate()
      val addMemberProbe = createTestProbe[GroupChatProtocol.AddMemberReply]()
      aggregate ! GroupChatProtocol.AddMember(id, memberId, MemberRole.Member, adminId, addMemberProbe.ref)
      addMemberProbe.expectMessageType[GroupChatProtocol.AddMemberSuccess]

      val messageBody = MessageBody("message")
      val postProbe = createTestProbe[GroupChatProtocol.PostMessageReply]()
      aggregate ! GroupChatProtocol.PostMessage(id, messageBody, memberId, postProbe.ref)
      postProbe.expectMessageType[GroupChatProtocol.PostMessageSuccess]
    }
    // 課題2
    "editMessage" in {
      val id      = GroupChatId.generate()
      val name    = GroupChatName("name")
      val adminId = UserAccountId.generate()

      val aggregate = spawn(GroupChatAggregate(id))

      val createProbe = createTestProbe[GroupChatProtocol.CreateGroupChatReply]()
      aggregate ! GroupChatProtocol.CreateGroupChat(id, name, adminId, createProbe.ref)
      createProbe.expectMessage(GroupChatProtocol.CreateGroupChatSuccess)

      val memberId       = UserAccountId.generate()
      val addMemberProbe = createTestProbe[GroupChatProtocol.AddMemberReply]()
      aggregate ! GroupChatProtocol.AddMember(id, memberId, MemberRole.Member, adminId, addMemberProbe.ref)
      addMemberProbe.expectMessageType[GroupChatProtocol.AddMemberSuccess]

      val messageBody = MessageBody("message")
      val postProbe = createTestProbe[GroupChatProtocol.PostMessageReply]()
      aggregate ! GroupChatProtocol.PostMessage(id, messageBody, memberId, postProbe.ref)
      val messageId = postProbe.expectMessageType[GroupChatProtocol.PostMessageSuccess].messageId

      val updatedMessageBody = MessageBody("updated")
      val editProbe = createTestProbe[GroupChatProtocol.EditMessageReply]()
      aggregate ! GroupChatProtocol.EditMessage(id, messageId, updatedMessageBody, memberId, editProbe.ref)
      editProbe.expectMessageType[GroupChatProtocol.EditMessageSuccess]
    }
    // 課題3
    "deleteMessage" in {
      // TODO: Delete message
    }
  }

}
