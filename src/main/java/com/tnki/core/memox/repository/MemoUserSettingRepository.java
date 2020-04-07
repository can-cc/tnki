package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.UserLearnSetting;

import java.util.Optional;

public interface MemoUserSettingRepository {
    Optional<UserLearnSetting> findOne(int userID);
    void save(UserLearnSetting userLearnSetting);
}
