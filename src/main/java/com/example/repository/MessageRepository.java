package com.example.repository;

import com.example.entity.Message;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message,Integer> {
    List<Message> findAllByPostedBy(int postedBy);
}
