package com.chat.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chat.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query(value = "SELECT * FROM message WHERE userreceived = :s AND usersend = :r OR userreceived = :r AND usersend = :s ORDER BY id DESC LIMIT 20", nativeQuery = true)
	public List<Message> getLast20Message(@Param("r") int receiver, @Param("s") int sender);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE message SET seen = TRUE WHERE userreceived = :r AND usersend = :s", nativeQuery = true)
	public void seenMessage(@Param("r") int receiver, @Param("s") int sender);

	@Query(value = "SELECT * FROM message WHERE userreceived = :r AND usersend = :s AND seen = FALSE", nativeQuery = true)
	public List<Message> getNewMessage(@Param("r") int receiver, @Param("s") int sender);

	@Modifying
	@Transactional
	@Query(value = "UPDATE message SET seen = true WHERE id = :id", nativeQuery = true)
	public void seenNewMessage(@Param("id") int  id);

	@Query(value = "SELECT * FROM message WHERE userreceived = :r AND usersend = :s ORDER BY id DESC LIMIT 1", nativeQuery = true)
	public Message checkSeenUserMessage(@Param("s") int receiver, @Param("r") int sender);

	@Query(value = "SELECT * FROM message WHERE (userreceived = :s AND usersend = :r OR userreceived = :r AND usersend = :s) AND id < :id ORDER BY id DESC LIMIT 20", nativeQuery = true)
	public List<Message> getLoadMoreMessage(@Param("r") int receiver, @Param("s") int sender,@Param("id") int firstChat);

	@Query(value = "SELECT DISTINCT usersend FROM message WHERE userreceived = :u ORDER BY id DESC", nativeQuery = true)
	public List<Integer> loadSendMessage(@Param("u") int user);

	@Query(value = "SELECT DISTINCT userreceived FROM message WHERE usersend = :u ORDER BY id DESC", nativeQuery = true)
	public List<Integer> loadReceivedMessage(@Param("u") int user);
	
	@Query(value = "SELECT * FROM message WHERE usersend = :r AND userreceived = :s OR usersend = :s AND userreceived = :r ORDER BY id DESC LIMIT 1", nativeQuery = true)
	public Message getLastMessageByUser(@Param("r") int receiver, @Param("s") int sender);
	
}
