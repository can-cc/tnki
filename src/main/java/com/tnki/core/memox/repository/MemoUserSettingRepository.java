package com.tnki.core.memox.repository;

import com.tnki.core.memox.model.UserLearnSetting;

public interface MemoUserSettingRepository {
    UserLearnSetting findUserLearnSetting(String username);
}
