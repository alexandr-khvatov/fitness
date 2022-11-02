package com.kh.fitness.validation.sequence;

import com.kh.fitness.validation.group.CheckNotExistAfter;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, CheckNotExistAfter.class})
public interface DefaultAndNotExistComplete {
}
