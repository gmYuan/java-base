package com.multhread;


import java.util.Optional;

public class ContainerV1 {
	private Optional<Integer> value = Optional.empty();


	public Optional<Integer> getValue() {
		return value;
	}

	public void setValue(Optional<Integer> value) {
		this.value = value;
	}
}