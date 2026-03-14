package com.multhread;

import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ContainerV2 {
	// 还没有被消费掉;
	private Condition notConsumedYet;
	// 还没有被生产出来
	private Condition notProducedYet;
  // 容器内的值
	private Optional<Integer> value = Optional.empty();

	public ContainerV2(ReentrantLock lock) {
		this.notConsumedYet = lock.newCondition();
		this.notProducedYet = lock.newCondition();
	}

	public Condition getNotConsumedYet() {
		return notConsumedYet;
	}

	public Condition getNotProducedYet() {
		return notProducedYet;
	}

	public Optional<Integer> getValue() {
		return value;
	}

	public void setValue(Optional<Integer> value) {
		this.value = value;
	}


}