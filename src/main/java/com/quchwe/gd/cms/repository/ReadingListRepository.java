package com.quchwe.gd.cms.repository;

import com.quchwe.gd.cms.bean.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by quchwe on 2017/3/15 0015.
 */
public interface ReadingListRepository extends JpaRepository<Book,Long>{
    List<Book> findByReader(String reader);
}
