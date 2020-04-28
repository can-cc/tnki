package com.tnki.core.memox.repository.mapper;

import com.tnki.core.memox.model.MemoLearningItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MyBatisMemoLearningItemMapper {

    @Select("SELECT a.user_id, a.ef, a.n, a.next_learn_date, a.last_learn_date, a.is_learning, " +
            "b.id, b.front, b.back, b.tip, b.created_at as item_created_at, b.updated_at as item_updated_at " +
            "FROM user_learn_item as a " +
            "LEFT JOIN learn_item as b " +
            "ON a.item_id = b.id " +
            "WHERE a.user_id = #{userID} " +
            "LIMIT #{limit} OFFSET #{offset}")
    @Results({
            @Result(column = "user_id", property = "userID"),
            @Result(column = "n", property = "learnTime"),
            @Result(column = "ef", property = "EF"),
            @Result(column = "next_learn_date", property = "nextLearnDate"),
            @Result(column = "last_learn_date", property = "lastLearnDate"),
            @Result(column = "is_learning", property = "isLearning"),
            @Result(column = "id", property = "memoItem.ID"),
            @Result(column = "front", property = "memoItem.front"),
            @Result(column = "back", property = "memoItem.back"),
            @Result(column = "tip", property = "memoItem.tip"),
            @Result(column = "item_created_at", property = "memoItem.createdAt"),
            @Result(column = "item_updated_at", property = "memoItem.updatedAt"),
    })
    List<MemoLearningItem> find(@Param("userID") long userID, @Param("offset") int offset, @Param("limit") int limit);

}
