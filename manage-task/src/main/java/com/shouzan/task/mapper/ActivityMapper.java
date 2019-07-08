package com.shouzan.task.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityMapper {

    int activityTimingLower(@Param("localeDate") String localeDate);

    int activityTimingUpper(@Param("localeDate") String localeDate);

}